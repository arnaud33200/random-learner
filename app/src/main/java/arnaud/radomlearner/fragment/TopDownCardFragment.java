package arnaud.radomlearner.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

import arnaud.radomlearner.GuessWordView;
import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/06.
 */

public class TopDownCardFragment extends AbstractLearnerFragment {

    private GuessWordView topGuessWordView;
    private GuessWordView bottomGuessWordView;
    private Button nextButton;

    private RelativeLayout guessLayout;
    private TextView questionTextView;

    @Override
    protected int getMainLayoutRes() { return R.layout.top_down_card_fragment; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

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
    protected void updateDisplayWithNewWordMap() {
        if (topGuessWordView == null || bottomGuessWordView == null) {
            return;
        }
        topGuessWordView.setTextAndDisplayMode("", false);
        bottomGuessWordView.setTextAndDisplayMode("", false);
        nextButtonClickAction();
    }

    private void nextButtonClickAction() {
        if (wordMap == null) {
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
            }
            return;
        }

        final int max = wordMap.size()-1;
        final int randomIndex = DataHelper.getRadomNumber(0, max);
        final int randomBool = DataHelper.getRadomNumber(0, 1);

        String adj = (String) wordMap.keySet().toArray()[randomIndex];
        String translation = wordMap.get(adj);

        topGuessWordView.setTextAndDisplayMode(adj, randomBool == 1);
        bottomGuessWordView.setTextAndDisplayMode(translation, randomBool == 0);

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
