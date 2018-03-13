package arnaud.radomlearner.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import arnaud.radomlearner.action_interface.QuizzAnswerListener;
import arnaud.radomlearner.action_interface.UserActionListener;
import arnaud.radomlearner.helper.DataHelper;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/06.
 */

public abstract class AbstractLearnerFragment extends Fragment implements UserActionListener {

    private static final int NUMBER_QUESTION_PER_EXERCISE = 30;

    private HashMap<String, String> wordMap;
    private boolean revert = false;
    private int limit;

    public HashSet<Quiz> mUserCorrectAnswerSet;
    public HashSet<Quiz> mUserBadAnswerSet;
    public ArrayList<Quiz> mQuizArrayList;

    public QuizzAnswerListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getMainLayoutRes(), container, false);

        return rootView;
    }

    protected abstract int getMainLayoutRes();
    protected abstract void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList);
    public abstract boolean needToDisplayTopStatusBar();
    protected abstract int getNumberOfQuestion();
    protected abstract int getNumberOfAnswer();

    public void setWordMap(HashMap<String, String> wordMap, int limit) {
        this.limit = limit;
        this.wordMap = wordMap;
        if (this.wordMap == null) {
            this.wordMap = new HashMap<>();
        }
        ArrayList<Quiz> quizArrayList = generateQuizArray(wordMap);
        updateDisplayWithNewWordMap(quizArrayList);
        sendUserAnswerChanged();
    }

    public void swapQuestionAnswer() {
        revert = revert == false;
        resetQuizArray();
    }

    public void resetQuizArray(int limit) {
        setWordMap(wordMap, limit);
    }

    public void resetQuizArray() {
        setWordMap(wordMap, limit);
    }

    public void resetWithOnlyBadUserAnswer() {
        HashMap<String, String> badWordMap = new HashMap<>();
        for (Quiz quizzRow : mUserBadAnswerSet) {
            for (String correctQuestion : quizzRow.correctMap.keySet()) {
                String correctAnswer = quizzRow.correctMap.get(correctQuestion);
                if (revert) {
                    badWordMap.put(correctAnswer, correctQuestion);
                }
                else {
                    badWordMap.put(correctQuestion, correctAnswer);
                }
            }
        }
        ArrayList<Quiz> quizArrayList = generateQuizArray(badWordMap);
        updateDisplayWithNewWordMap(quizArrayList);
        sendUserAnswerChanged();
    }

    private ArrayList<Quiz> generateQuizArray(HashMap<String, String> wordMapGenerated) {
        // reset answer at this point
        mUserCorrectAnswerSet = new HashSet<>();
        mUserBadAnswerSet = new HashSet<>();

        int numberOfQuestion = getNumberOfQuestion();
        int numberOfAnswer = getNumberOfAnswer();

        mQuizArrayList = Quiz.generateQuizArray(wordMapGenerated, numberOfQuestion, numberOfAnswer, revert, limit);

//        for (Quiz q : mQuizArrayList) {
//            Log.d("QUESTION", q.questionArray.get());
//        }

        return mQuizArrayList;
    }

    public HashMap<String, String> getWordMap() {
        return wordMap;
    }

// ~~~~~~~~~~~~

    @Override
    public void onUserAnswerAction(Quiz mQuizzRow) {
        boolean correct = mQuizzRow.isCorrectAnswer();
        if (correct) {
            mUserCorrectAnswerSet.add(mQuizzRow);
        } else {
            mUserBadAnswerSet.add(mQuizzRow);
        }
        sendUserAnswerChanged();
    }

    private void sendUserAnswerChanged() {
        if (listener == null) {
            return;
        }

        int numberOfQuestion = getNumberOfQuestion();
        int totalQuestion = numberOfQuestion * mQuizArrayList.size();

        int totalAnswer = (mUserBadAnswerSet.size() + mUserCorrectAnswerSet.size()) * numberOfQuestion;

        listener.onUserAnswerChanged(totalQuestion, totalAnswer, mUserCorrectAnswerSet.size());
    }

    public boolean getRevert() {
        return revert;
    }
}
