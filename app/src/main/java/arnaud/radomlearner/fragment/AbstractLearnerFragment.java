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

    private HashSet<Quiz> mUserCorrectAnswerSet;
    private HashSet<Quiz> mUserBadAnswerSet;
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

        mQuizArrayList = new ArrayList<>();

        int numberOfQuestion = getNumberOfQuestion();
        int numberOfAnswer = getNumberOfAnswer();

        ArrayList<String> keyArray = new ArrayList<>(wordMapGenerated.keySet());
        while (keyArray.size() > 0 && wordMapGenerated.size() >= numberOfAnswer) {

            int random = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String originalQuestion = keyArray.remove(random);
            String originalAnswer = wordMapGenerated.get(originalQuestion);

            Quiz quizzRow = new Quiz();
            quizzRow.addQuestionAndAsnwer(originalQuestion, originalAnswer);

            while (quizzRow.questionArray.size() < numberOfQuestion || quizzRow.answerArray.size() < numberOfAnswer) {
            // Add Question
                String answer = "";
                String question = "";
                if (keyArray.size() > 0) {
                    int r = DataHelper.getRadomNumber(0, keyArray.size()-1);
                    question = keyArray.get(r);
                    answer = wordMapGenerated.get(question);
                }

                if (mQuizArrayList.size() > 0 && (question.length() == 0 || quizzRow.questionAlreadyAdded(question))) {
                    int r = DataHelper.getRadomNumber(0, mQuizArrayList.size()-1);
                    Quiz row = mQuizArrayList.get(r);
                    Pair<String, String> pair = row.getRandomCorrectPair();
                    answer = revert ? pair.first : pair.second;
                    question = revert ? pair.second : pair.first;
                }

                quizzRow.addQuestionAndAsnwer(question, answer);
            }

            if (revert) {
                quizzRow.invertQuestionAnswer();
            }
            mQuizArrayList.add(quizzRow);

            if (limit > 0 && mQuizArrayList.size() >= limit) {
                break;
            }
        }

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
        int totalAnswer = mUserBadAnswerSet.size() + mUserCorrectAnswerSet.size();
        listener.onUserAnswerChanged(mQuizArrayList.size(), totalAnswer, mUserCorrectAnswerSet.size());
    }

    public boolean getRevert() {
        return revert;
    }
}
