package arnaud.radomlearner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.adapter.QuizzAdapter;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzFragment extends AbstractLearnerFragment implements QuizzAdapter.UserAnswerListener {

    RecyclerView mRecyclerView;
    private QuizzAdapter mAdapter;
    private Button revertButton;
    private Button resetButton;
    private Button badButton;
    private TextView statusTextView;

    private boolean revert;


    @Override
    protected int getMainLayoutRes() {
        return R.layout.quizz_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        revert = false;

        statusTextView = rootView.findViewById(R.id.status_text_view);

        mAdapter = new QuizzAdapter();
        mAdapter.listener = this;

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setWordMap(wordMap, revert);

        resetButton = rootView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { resetButtonClickAction(); }
        });

        revertButton = rootView.findViewById(R.id.revert_button);
        revertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revertButtonClickAction();
            }
        });

        badButton = rootView.findViewById(R.id.bad_button);
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                badButtonClickAction();
            }
        });

        return rootView;
    }

    private void badButtonClickAction() {
        mAdapter.setWordMapOnlyKeepBad(wordMap, revert);
    }

    private void revertButtonClickAction() {
        revert = revert == false;
        mAdapter.setWordMap(wordMap, revert);
    }

    private void resetButtonClickAction() {
        mAdapter.setWordMap(wordMap, revert);
    }

    @Override
    protected void updateDisplayWithNewWordMap() {
        if (mAdapter != null) {
            mAdapter.setWordMap(wordMap, revert);
        }
    }

    @Override
    public void onUserAnswerChanged(int totalQuestion, int totalAnswer, int totalCorrect) {
        String statusText = "" + totalAnswer + " / " + totalQuestion;
        if (totalAnswer > 0) {
            statusText = statusText + " (" + totalCorrect + " correct)";
        }
        statusTextView.setText(statusText);
    }
}
