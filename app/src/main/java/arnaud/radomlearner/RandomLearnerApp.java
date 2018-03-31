package arnaud.radomlearner;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arnaud on 2017/12/07.
 */

public class RandomLearnerApp extends Application {

    public static RandomLearnerApp sharedInstance = null;

    private SharedPreferences preference;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;

        Context context = getContext();
        preference = context.getSharedPreferences("preference", 0);
    }


    public static SharedPreferences getPreference() {
        return sharedInstance.preference;
    }

    public static Context getContext() {
        return sharedInstance.getApplicationContext();
    }

    @SuppressLint("NewApi")
    public static int getContextColor(int colorRes) {
        return sharedInstance.getApplicationContext().getColor(colorRes);
    }
}
