package arnaud.radomlearner.fragment;

import java.util.ArrayList;

import arnaud.radomlearner.R;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/17.
 */

public class MatchElementQuizFragment extends AbstractLearnerFragment {

    @Override
    protected int getMainLayoutRes() {
        return R.layout.fragment_match_element_quizz;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {

    }

    @Override
    protected int getNumberOfAnswer() {
        return 3;
    }
}
