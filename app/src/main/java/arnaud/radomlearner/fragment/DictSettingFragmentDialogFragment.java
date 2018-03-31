package arnaud.radomlearner.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import arnaud.radomlearner.adapter.DictSettingAdapter;

/**
 * Created by arnaud on 2018/03/30.
 */

public class DictSettingFragmentDialogFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private DictSettingAdapter mAdapter;
    private Runnable onCloseCallBack;

    public DictSettingFragmentDialogFragment() {
        mAdapter = new DictSettingAdapter();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(mRecyclerView);
        return dialogBuilder.create();
    }

    public void show(FragmentManager fragmentManager, Runnable runnable) {
        this.onCloseCallBack = runnable;
        this.show(fragmentManager, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.onCloseCallBack != null) {
            this.onCloseCallBack.run();
        }
    }
}
