package arnaud.radomlearner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.adapter.QuizzAdapter;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzFragment extends AbstractLearnerFragment {

    RecyclerView mRecyclerView;
    private QuizzAdapter mAdapter;

    @Override
    protected int getMainLayoutRes() {
        return R.layout.quizz_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mAdapter = new QuizzAdapter();
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setWordMap(wordMap);

        return rootView;
    }

    @Override
    protected void updateDisplayWithNewWordMap() {
        if (mAdapter != null) {
            mAdapter.setWordMap(wordMap);
        }
    }
}
