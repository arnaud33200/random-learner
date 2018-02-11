package arnaud.radomlearner.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import arnaud.radomlearner.GuessWordView;
import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.helper.DataHelper;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/06.
 */

public class TopDownCardFragment extends AbstractLearnerFragment {

    private GuessWordView topGuessWordView;
    private GuessWordView bottomGuessWordView;

    private int currentIndex;

    private RelativeLayout guessLayout;
    private TextView questionTextView;
    private ArrayList<Quiz> mQuizArrayList;

    @Override
    protected int getMainLayoutRes() { return R.layout.top_down_card_fragment; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        currentIndex = 0;
        if (mQuizArrayList == null) {
            mQuizArrayList = new ArrayList<>();
        }

        topGuessWordView = new GuessWordView(rootView.findViewById(R.id.top_view_guess_word));
        topGuessWordView.setColorMode();
        bottomGuessWordView = new GuessWordView(rootView.findViewById(R.id.bottom_view_guess_word));

        guessLayout = rootView.findViewById(R.id.guess_layout);
        questionTextView = rootView.findViewById(R.id.middle_question_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButtonClickAction();
            }
        });

        nextButtonClickAction();

        return rootView;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {
        mQuizArrayList = quizArrayList;
        if (topGuessWordView == null || bottomGuessWordView == null) {
            return;
        }
        topGuessWordView.setTextAndDisplayMode("", false);
        bottomGuessWordView.setTextAndDisplayMode("", false);
        nextButtonClickAction();
    }

    @Override
    protected int getNumberOfAnswer() {
        return 1;
    }

    private void nextButtonClickAction() {
        if (mQuizArrayList == null || mQuizArrayList.size() == 0) {
            currentIndex = 0;
            return;
        }

        Boolean revealAction = true;
        if (topGuessWordView.getHideMode()) {
            topGuessWordView.textViewClickAction();
        }
        else if (bottomGuessWordView.getHideMode()) {
            bottomGuessWordView.textViewClickAction();
        }
        else {
            revealAction = false;
        }

        if (revealAction) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                updateMiddleGuestLayout();
                Quiz quiz = mQuizArrayList.get(currentIndex);
                quiz.userAnswer = quiz.correctAnswer;
                onUserAnswerAction(quiz);
            }
            return;
        }

        currentIndex++;
        Quiz quiz = mQuizArrayList.get(currentIndex);

        final int randomBool = DataHelper.getRadomNumber(0, 1);

        topGuessWordView.setTextAndDisplayMode(quiz.question, randomBool == 1);
        bottomGuessWordView.setTextAndDisplayMode(quiz.correctAnswer, randomBool == 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            updateMiddleGuestLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateMiddleGuestLayout() {
        guessLayout.setVisibility(View.VISIBLE);
        if (topGuessWordView.getHideMode()) {
            questionTextView.setTextColor(RandomLearnerApp.getContext().getColor(R.color.black));
            guessLayout.setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.white_circle));
        }
        else if (bottomGuessWordView.getHideMode()) {
            questionTextView.setTextColor(RandomLearnerApp.getContext().getColor(R.color.white));
            guessLayout.setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.color_circle));
        }
        else {
            guessLayout.setVisibility(View.INVISIBLE);
        }
    }
}
