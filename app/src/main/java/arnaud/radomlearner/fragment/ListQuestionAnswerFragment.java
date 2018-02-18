package arnaud.radomlearner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import arnaud.radomlearner.R;
import arnaud.radomlearner.adapter.ListAdapter;
import arnaud.radomlearner.adapter.QuizzAdapter;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class ListQuestionAnswerFragment extends AbstractLearnerFragment {

    RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    private boolean revert;


    @Override protected int getMainLayoutRes() {
        return R.layout.quizz_fragment;
    }

    @Override protected int getNumberOfAnswer() { return 1; }
    @Override protected int getNumberOfQuestion() { return 1; }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        if (mAdapter == null) {
            mAdapter = new ListAdapter();
        }

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {
        if (mAdapter == null) {
            mAdapter = new ListAdapter();
        }
        mAdapter.setWordMap(quizArrayList);
    }

    @Override
    public boolean needToDisplayTopStatusBar() {
        return false;
    }
}
