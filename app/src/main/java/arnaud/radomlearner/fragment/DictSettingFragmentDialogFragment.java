package arnaud.radomlearner.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import arnaud.radomlearner.R;
import arnaud.radomlearner.adapter.DictSettingAdapter;
import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/03/30.
 */

public class DictSettingFragmentDialogFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private DictSettingAdapter mAdapter;
    private Runnable onCloseCallBack;
    private Button mApplyButton;

    public DictSettingFragmentDialogFragment() {
        mAdapter = new DictSettingAdapter();
    }

//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialog_fragment_dict_setting, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mApplyButton = rootView.findViewById(R.id.apply_button);
        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = (int) DataHelper.convertDpToPixel(300);
        int height = (int) DataHelper.convertDpToPixel(400);
        getDialog().getWindow().setLayout(width, height);
        mRecyclerView.setAdapter(mAdapter);
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
