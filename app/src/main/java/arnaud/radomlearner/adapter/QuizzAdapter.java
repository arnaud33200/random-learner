package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import arnaud.radomlearner.R;
import arnaud.radomlearner.helper.DataHelper;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizzViewHolder> implements QuizzViewHolder.ButtonActionListener {

    public interface UserAnswerListener {
        void onUserAnswerChanged(int totalQuestion, int totalAnswer, int totalCorrect);
    }

    public static final int NUMBER_OF_BUTTON = 2;

    public UserAnswerListener listener;

    private ArrayList<Quiz> mQuizzRowArrayList;

    private HashMap<String, Quiz> mUserCorrectAnswerMap;
    private HashMap<String, Quiz> mUserBadAnswerMap;

    public QuizzAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
        mUserCorrectAnswerMap = new HashMap<>();
        mUserBadAnswerMap = new HashMap<>();
    }

    public void setWordMapOnlyKeepBad(HashMap<String, String> wordMap, boolean revert) {
        HashMap<String, String> badWordMap = new HashMap<>();

        for (Quiz quizzRow : mUserBadAnswerMap.values()) {
            if (revert) {
                badWordMap.put(quizzRow.correctAnswer, quizzRow.question);
            }
            else {
                badWordMap.put(quizzRow.question, quizzRow.correctAnswer);
            }
        }
        setWordMap(badWordMap, revert);
    }

    public void setWordMap(HashMap<String, String> wordMap, boolean revert) {
        mQuizzRowArrayList = new ArrayList<>();
        mUserCorrectAnswerMap = new HashMap<>();
        mUserBadAnswerMap = new HashMap<>();

        ArrayList<String> keyArray = new ArrayList<>(wordMap.keySet());
        while (keyArray.size() > 0 && wordMap.size() >= NUMBER_OF_BUTTON) {

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

            for (int i=0; i<NUMBER_OF_BUTTON-1; ++i) {
                String a = "";
                String q = "";
                if (keyArray.size() > 0) {
                    int r = DataHelper.getRadomNumber(0, keyArray.size()-1);
                    q = keyArray.get(r);
                    a = wordMap.get(q);
                } else {
                    int r = DataHelper.getRadomNumber(0, mQuizzRowArrayList.size()-1);
                    Quiz row = mQuizzRowArrayList.get(r);
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
            mQuizzRowArrayList.add(quizzRow);
        }

        notifyDataSetChanged();

        sendUserAnswerChanged();
    }

    private void sendUserAnswerChanged() {
        if (listener != null) {
            int totalAnswer = mUserBadAnswerMap.size() + mUserCorrectAnswerMap.size();
            listener.onUserAnswerChanged(mQuizzRowArrayList.size(), totalAnswer, mUserCorrectAnswerMap.size());
        }
    }

//    private String getUserAnswerForQuestion(Quiz question) {
//        String answer = mUserCorrectAnswerMap.get(question);
//        if (answer == null) {
//            answer = mUserBadAnswerMap.get(question);
//        }
//        return answer;
//    }

    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_quizz, null);
        QuizzViewHolder holder = new QuizzViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuizzViewHolder holder, int position) {
        Quiz quizzRow = mQuizzRowArrayList.get(position);
        holder.setViewWithQuizzRow(quizzRow, this);
    }

    @Override
    public int getItemCount() {
        return mQuizzRowArrayList.size();
    }

    @Override
    public void onUserAnswerAction(Quiz mQuizzRow) {
        boolean correct = mQuizzRow.isCorrectAnswer();
        if (correct) {
            mUserCorrectAnswerMap.put(mQuizzRow.question, mQuizzRow);
        } else {
            mUserBadAnswerMap.put(mQuizzRow.question, mQuizzRow);
        }

        sendUserAnswerChanged();
    }
}
