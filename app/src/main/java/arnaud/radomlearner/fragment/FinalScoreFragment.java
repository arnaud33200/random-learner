package arnaud.radomlearner.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashSet;

import arnaud.radomlearner.R;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/03/04.
 */

@SuppressLint("ValidFragment")
public class FinalScoreFragment extends Fragment {

    private HashSet<Quiz> goodAnswerArray;
    private HashSet<Quiz> badAnswerArray;

    TextView resultTextView;

    RelativeLayout badAnswerLayout;
    TextView badAnswerTextView;
    RecyclerView recyclerView;

    Button resetAllButton;
    Button resetBadButton;

    FinalScoreActionListener listener;

    private FinalScoreFragment() {

    }

    public FinalScoreFragment(FinalScoreActionListener listener, AbstractLearnerFragment fragment) {
        super();
        this.listener = listener;
        this.goodAnswerArray = fragment.mUserCorrectAnswerSet;
        this.badAnswerArray = fragment.mUserBadAnswerSet;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_end_score, container, false);

        resultTextView = rootView.findViewById(R.id.result_textview);
        setupResultText();

        badAnswerLayout = rootView.findViewById(R.id.bad_answer_layout);
        badAnswerTextView = rootView.findViewById(R.id.bad_answer_textview);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        // will be used later
        badAnswerLayout.setVisibility(View.GONE);

        resetAllButton = rootView.findViewById(R.id.reset_all_button);
        resetAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) { listener.onFinalScoreResetAllButtonClickAction(); }
            }
        });

        resetBadButton = rootView.findViewById(R.id.reset_bad_button);
        resetBadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) { listener.onFinalScoreResetBadButtonClickAction(); }
            }
        });
        if (badAnswerArray.size() == 0) {
            resetBadButton.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void setupResultText() {
        int total = badAnswerArray.size() + goodAnswerArray.size();
        int good = goodAnswerArray.size();
        resultTextView.setText("" + good + " / " + total + " good answers");
    }

    public interface FinalScoreActionListener {
        void onFinalScoreResetAllButtonClickAction();
        void onFinalScoreResetBadButtonClickAction();
    }

}
