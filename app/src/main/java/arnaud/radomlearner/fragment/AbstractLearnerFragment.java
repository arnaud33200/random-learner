package arnaud.radomlearner.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import arnaud.radomlearner.helper.DataHelper;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/06.
 */

public abstract class AbstractLearnerFragment extends Fragment {

    private HashMap<String, String> wordMap;
    protected boolean revert = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getMainLayoutRes(), container, false);

        return rootView;
    }

    protected abstract int getMainLayoutRes();
    protected abstract void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList);
    protected abstract int getNumberOfAnswer();

    public void setWordMap(HashMap<String, String> wordMap) {
        this.wordMap = wordMap;
        if (this.wordMap == null) {
            this.wordMap = new HashMap<>();
        }
        ArrayList<Quiz> quizArrayList = generateQuizArray();
        updateDisplayWithNewWordMap(quizArrayList);
    }

    public ArrayList<Quiz> generateQuizArray() {
        ArrayList<Quiz> quizArrayList = new ArrayList<>();

        int numberOfAnswer = getNumberOfAnswer();
        ArrayList<String> keyArray = new ArrayList<>(wordMap.keySet());
        while (keyArray.size() > 0 && wordMap.size() >= numberOfAnswer) {

            ArrayList<String> answerArray = new ArrayList<>();

            int random = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String question = keyArray.remove(random);
            String answer = wordMap.get(question);
            Quiz quizzRow = new Quiz(question, answer);
            if (revert) {
                quizzRow = new Quiz(answer, question);
                answerArray.add(question);
            } else {
                answerArray.add(answer);
            }

            for (int i=0; i<numberOfAnswer-1; ++i) {
                String a = "";
                String q = "";
                if (keyArray.size() > 0) {
                    int r = DataHelper.getRadomNumber(0, keyArray.size()-1);
                    q = keyArray.get(r);
                    a = wordMap.get(q);
                } else {
                    int r = DataHelper.getRadomNumber(0, quizArrayList.size()-1);
                    Quiz row = quizArrayList.get(r);
                    a = revert ? row.question : row.correctAnswer;
                    q = revert ? row.correctAnswer : row.question;
                }
                int insertIndex = DataHelper.getRadomNumber(0, answerArray.size());
                if (revert) {
                    answerArray.add(insertIndex, q);
                } else {
                    answerArray.add(insertIndex, a);
                }

            }

            quizzRow.answerArray = answerArray;
            quizArrayList.add(quizzRow);
        }
        return quizArrayList;
    }

    public HashMap<String, String> getWordMap() {
        return wordMap;
    }
}
