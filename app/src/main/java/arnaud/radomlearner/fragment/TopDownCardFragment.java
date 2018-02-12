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

import arnaud.radomlearner.CircleSymbolButton;
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

    private CircleSymbolButton leftButton;
    private CircleSymbolButton centralButton;
    private CircleSymbolButton rightButton;

    private int currentIndex;
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

        leftButton = rootView.findViewById(R.id.left_button);
        leftButton.setSymbolText("✔");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { buttonClickAction(true); }
        });

        centralButton = rootView.findViewById(R.id.central_button);
        centralButton.setSymbolText("?");
        centralButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { centralButtonClickAction(); }
        });

        rightButton = rootView.findViewById(R.id.right_button);
        rightButton.setSymbolText("✖");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { buttonClickAction(false); }
        });

        prepareNextGuest();

        return rootView;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {
        mQuizArrayList = quizArrayList;
        currentIndex = 0;
        if (topGuessWordView == null || bottomGuessWordView == null) {
            return;
        }
        topGuessWordView.setTextAndDisplayMode("", false);
        bottomGuessWordView.setTextAndDisplayMode("", false);
        prepareNextGuest();
    }

    @Override
    protected int getNumberOfAnswer() {
        return 1;
    }

    private void buttonClickAction(boolean correct) {
        if (checkNeedToReveal()) {
            return;
        }

        Quiz quiz = mQuizArrayList.get(currentIndex);
        quiz.userAnswer = correct ? quiz.correctAnswer : "WRONG";
        onUserAnswerAction(quiz);

        currentIndex++;
        prepareNextGuest();
    }

    private void prepareNextGuest() {
        if (mQuizArrayList == null || mQuizArrayList.size() == 0) {
            currentIndex = 0;
            return;
        }

        if (currentIndex >= mQuizArrayList.size()) {
            centralButton.setVisibility(View.GONE);
            leftButton.setVisibility(View.GONE);
            rightButton.setVisibility(View.GONE);
            topGuessWordView.setHideMode();
            bottomGuessWordView.setHideMode();
            return;
        }
        Quiz quiz = mQuizArrayList.get(currentIndex);

//        final int randomBool = DataHelper.getRadomNumber(0, 1);

        if (getRevert()) {
            topGuessWordView.setTextAndDisplayMode(quiz.question, true);
            bottomGuessWordView.setTextAndDisplayMode(quiz.correctAnswer, false);
        } else {
            topGuessWordView.setTextAndDisplayMode(quiz.correctAnswer, false);
            bottomGuessWordView.setTextAndDisplayMode(quiz.question, true);
        }

        updateMiddleGuestLayout();
    }

    private void centralButtonClickAction() {
        if (checkNeedToReveal() == false) {
            return;
        }

        if (topGuessWordView.getHideMode()) {
            topGuessWordView.textViewClickAction();
        }
        else if (bottomGuessWordView.getHideMode()) {
            bottomGuessWordView.textViewClickAction();
        }

        updateMiddleGuestLayout();
    }

    private boolean checkNeedToReveal() {
        return (topGuessWordView.getHideMode() || bottomGuessWordView.getHideMode());
    }

    private void updateMiddleGuestLayout() {

        CircleSymbolButton.Position position = CircleSymbolButton.Position.MIDDLE;
        if (topGuessWordView.getHideMode()) {
            position = CircleSymbolButton.Position.TOP;
        }
        else if (bottomGuessWordView.getHideMode()) {
            position = CircleSymbolButton.Position.BOTTOM;
        }

        if (checkNeedToReveal()) {
            centralButton.setVisibility(View.VISIBLE);
            leftButton.setVisibility(View.GONE);
            rightButton.setVisibility(View.GONE);

        }
        else {
            centralButton.setVisibility(View.GONE);
            leftButton.setVisibility(View.VISIBLE);
            rightButton.setVisibility(View.VISIBLE);

        }

        centralButton.updateView(position);
        leftButton.updateView(position);
        rightButton.updateView(position);
    }
}
