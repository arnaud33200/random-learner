package arnaud.radomlearner.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

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

    private HashMap<String, Quiz> mUserCorrectAnswerMap;
    private HashMap<String, Quiz> mUserBadAnswerMap;
    private ArrayList<Quiz> mQuizArrayList;

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
        for (Quiz quizzRow : mUserBadAnswerMap.values()) {
            if (revert) {
                badWordMap.put(quizzRow.correctAnswer, quizzRow.correctQuestion);
            }
            else {
                badWordMap.put(quizzRow.correctQuestion, quizzRow.correctAnswer);
            }
        }
        ArrayList<Quiz> quizArrayList = generateQuizArray(badWordMap);
        updateDisplayWithNewWordMap(quizArrayList);
        sendUserAnswerChanged();
    }

    private ArrayList<Quiz> generateQuizArray(HashMap<String, String> wordMapGenerated) {
        // reset answer at this point
        mUserCorrectAnswerMap = new HashMap<>();
        mUserBadAnswerMap = new HashMap<>();

        mQuizArrayList = new ArrayList<>();

        int numberOfQuestion = getNumberOfQuestion();
        int numberOfAnswer = getNumberOfAnswer();

        ArrayList<String> keyArray = new ArrayList<>(wordMapGenerated.keySet());
        while (keyArray.size() > 0 && wordMapGenerated.size() >= numberOfAnswer) {

            int random = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String originalQuestion = keyArray.remove(random);
            String originalAnswer = wordMapGenerated.get(originalQuestion);

            ArrayList<String> questionArray = new ArrayList<>();
            ArrayList<String> answerArray = new ArrayList<>();

            questionArray.add(originalQuestion);
            answerArray.add(originalAnswer);

            int i=0;
            while (questionArray.size() < numberOfQuestion || answerArray.size() < numberOfAnswer) {
            // Add Question
                String a = "";
                String q = "";
                if (keyArray.size() > 0) {
                    int r = DataHelper.getRadomNumber(0, keyArray.size()-1);
                    q = keyArray.get(r);
                    a = wordMapGenerated.get(q);
                } else {
                    int r = DataHelper.getRadomNumber(0, mQuizArrayList.size()-1);
                    Quiz row = mQuizArrayList.get(r);
                    a = revert ? row.correctQuestion : row.correctAnswer;
                    q = revert ? row.correctAnswer : row.correctQuestion;
                }

                int insertIndex = DataHelper.getRadomNumber(0, answerArray.size());
                if (questionArray.size() < numberOfQuestion) {
                    if (revert) { questionArray.add(insertIndex, a); }
                    else { questionArray.add(insertIndex, q); }
                }

                if (answerArray.size() < numberOfAnswer) {
                    if (revert) { answerArray.add(insertIndex, q); }
                    else { answerArray.add(insertIndex, a); }
                }

                i++;
            }

            Quiz quizzRow = new Quiz(originalQuestion, originalAnswer, questionArray, answerArray);
            mQuizArrayList.add(quizzRow);
            if (limit > 0 && mQuizArrayList.size() >= limit) {
                break;
            }
        }
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
            mUserCorrectAnswerMap.put(mQuizzRow.correctQuestion, mQuizzRow);
        } else {
            mUserBadAnswerMap.put(mQuizzRow.correctQuestion, mQuizzRow);
        }

        sendUserAnswerChanged();
    }

    private void sendUserAnswerChanged() {
        if (listener == null) {
            return;
        }
        int totalAnswer = mUserBadAnswerMap.size() + mUserCorrectAnswerMap.size();
        listener.onUserAnswerChanged(mQuizArrayList.size(), totalAnswer, mUserCorrectAnswerMap.size());
    }

    public boolean getRevert() {
        return revert;
    }
}
