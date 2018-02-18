package arnaud.radomlearner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import arnaud.radomlearner.MatchElementsQuizzView;
import arnaud.radomlearner.R;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/17.
 */

public class MatchElementQuizFragment extends AbstractLearnerFragment {

    private MatchElementsQuizzView matchElementsQuizzView;
    private ArrayList<Quiz> quizArrayList;
    private int currentIndex = 0;

    @Override
    protected int getMainLayoutRes() {
        return R.layout.fragment_match_element_quizz;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        matchElementsQuizzView = rootView.findViewById(R.id.match_element_view);
        initializeCurrentQuiz();
        return rootView;
    }

    private void initializeCurrentQuiz() {
        if (matchElementsQuizzView == null) {
            return;
        }
        Quiz quiz = getCurrentQuiz();
        matchElementsQuizzView.setCurrentQuiz(quiz);
    }

    private Quiz getCurrentQuiz() {
        if (currentIndex < quizArrayList.size()) {
            return quizArrayList.get(currentIndex);
        }
        return null;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {
        this.quizArrayList = quizArrayList;
        currentIndex = 0;
        initializeCurrentQuiz();
    }

    @Override
    protected int getNumberOfQuestion() {
        return 3;
    }

    @Override
    protected int getNumberOfAnswer() {
        return 3;
    }
}
