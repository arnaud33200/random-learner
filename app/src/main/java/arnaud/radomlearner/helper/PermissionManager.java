package arnaud.radomlearner.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;

/**
 * Created by arnaud on 2018/03/31.
 */

public class PermissionManager {

    public enum PermissionType {
        WriteStorage(4, R.string.permission_read_storage, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        Sip(1, R.string.permission_use_sip, Manifest.permission.USE_SIP),
//        RecordAudio(2, R.string.permission_record_audio, Manifest.permission.RECORD_AUDIO),
//        Camera(5, R.string.permission_camera, Manifest.permission.CAMERA),
//        ReadStorage(3, R.string.permission_read_storage, Manifest.permission.READ_EXTERNAL_STORAGE),
//
//        PhoneContact(0, R.string.permission_read_contacts, Manifest.permission.READ_CONTACTS);

        public final int requestCode;
        public final int messageRes;
        public final String permissionString;

        PermissionType(int requestCode, int messageRes, String permissionString) {
            this.requestCode = requestCode;
            this.messageRes = messageRes;
            this.permissionString = permissionString;
        }

        public static PermissionType fromRequestCode(int code) {
            for (PermissionType type : PermissionType.values()) {
                if (code == type.requestCode) {
                    return type;
                }
            }
            return WriteStorage;
        }
    }

    public static final int ALL_PERMISSIONS = 11;

    private static ArrayList<PermissionRequestWithCodeListener> permissionRequestListenerArrayList;

    private static final String sTag = PermissionManager.class.getSimpleName();

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region GLOBAL CHECK

    // refactor do to: all function should use this method, the logic is same everywhere

    public static boolean hasGrantedPermission(PermissionType permissionType) {
        return hasGrantedPermission(RandomLearnerApp.getContext(), permissionType);
    }

    public static boolean hasGrantedPermission(final Context context, PermissionType permissionType) {
        if (context == null) {
            return false;
        }
        String permissionString = permissionType.permissionString;
        int status = ContextCompat.checkSelfPermission(context, permissionString);
        return status == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean alreadyRequestOneTime(final Activity activity, PermissionType permissionType) {
        if (activity == null) {
            return false;
        }
        String permissionString = permissionType.permissionString;
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionString)) {
            return true;
        }
        return false;
    }

    public static boolean checkPermissionAndDisplayIfNotGranted(final Activity activity, PermissionType permissionType) {
        return checkPermissionAndDisplayIfNotGranted(activity, permissionType, null);
    }

    public static boolean checkPermissionAndDisplayIfNotGranted(final Activity activity, final PermissionType permissionType, final PermissionRequestListener listener) {

        // Already accepted
        if (hasGrantedPermission(activity, permissionType)) {
            if (listener != null) {
                listener.onRequestPermissionResult(true, permissionType.requestCode);
            }
            return true;
        }

        addPermissionListener(listener, permissionType.requestCode);
        ActivityCompat.requestPermissions(activity, new String[]{permissionType.permissionString}, permissionType.requestCode);
        return false;
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region CALL BACK REQUEST RESULT

    public interface PermissionRequestListener {
        void onRequestPermissionResult(boolean granted, int requestCode);
    }

    public interface PermissionRequestWithCodeListener extends PermissionRequestListener {
        int getRequestCode();
    }

    private static void addPermissionListener(final PermissionRequestListener listener, final int requestCode) {
        if (listener == null) {
            return;
        }
        if (permissionRequestListenerArrayList == null) {
            permissionRequestListenerArrayList = new ArrayList<>();
        }
        PermissionRequestWithCodeListener codeListener = new PermissionRequestWithCodeListener() {
            @Override
            public int getRequestCode() {
                return requestCode;
            }

            @Override
            public void onRequestPermissionResult(boolean granted, int requestCode) {
                listener.onRequestPermissionResult(granted, requestCode);
            }
        };
        permissionRequestListenerArrayList.add(codeListener);
    }

    public static void onRequestPermissionsResult(final Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int result = grantResults != null && grantResults.length > 0 ? grantResults[0] : -1;
        boolean granted = result == PackageManager.PERMISSION_GRANTED;
        PermissionType permissionType = PermissionType.fromRequestCode(requestCode);

        if (result < 0) {
            // IGNORE IT FOR THE MOMENT
//            UIHelper.displayAlert(R.string.permission_request_wont_show_title, R.string.permission_request_wont_show_message, activity, new UIHelper.AlertDialogClickListener() {
//                @Override
//                public void onClick(boolean cancel) {
//                    Intent intent = new Intent();
//                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", SkrumbleApplication.getInstance().getPackageName(), null);
//                    intent.setData(uri);
//                    activity.startActivity(intent);
//                }
//            });
        }

        if (permissionRequestListenerArrayList == null) {
            return; // no listener
        }
        ArrayList<PermissionRequestWithCodeListener> listenerArray = (ArrayList<PermissionRequestWithCodeListener>) permissionRequestListenerArrayList.clone();
        for (PermissionRequestWithCodeListener listener : listenerArray) {
            if (listener.getRequestCode() == requestCode) {
                listener.onRequestPermissionResult(granted, requestCode);
                permissionRequestListenerArrayList.remove(listener);
            }
        }
    }

// endregion

}

