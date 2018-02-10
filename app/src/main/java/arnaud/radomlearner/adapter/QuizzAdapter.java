package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import arnaud.radomlearner.R;
import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizzViewHolder> implements QuizzViewHolder.ButtonActionListener {

    public interface UserAnswerListener {
        void onUserAnswerChanged(int totalQuestion, int totalAnswer, int totalCorrect);
    }

    public static final int NUMBER_OF_BUTTON = 2;

    public static class QuizzRow {
        public String question;
        public String correctAnswer;
        public ArrayList<String> answerArray;

        public QuizzRow(String question, String correct) {
            this.question = question;
            this.correctAnswer = correct;
            this.answerArray = new ArrayList<>();
        }

        public QuizzRow(String question, String correct, ArrayList<String> answerArray) {
            this(question, correct);
            this.answerArray = answerArray;
        }
    }

    public UserAnswerListener listener;

    private ArrayList<QuizzRow> mQuizzRowArrayList;
    private HashMap<String, String> mUserAnswerMap;
    private int numberOfCorrectAnswer;

    public QuizzAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
        mUserAnswerMap = new HashMap<>();
        numberOfCorrectAnswer = 0;
    }

    public void setWordMapOnlyKeepBad(HashMap<String, String> wordMap, boolean revert) {
        HashMap<String, String> badWordMap = new HashMap<>();
        for (String question : mUserAnswerMap.keySet()) {
            String answer = mUserAnswerMap.get(question);
            String correct = wordMap.get(question);
            if (correct != null && answer.equals(correct) == false) {
                badWordMap.put(question, answer);
            }
        }
        setWordMap(badWordMap, revert);
    }

    public void setWordMap(HashMap<String, String> wordMap, boolean revert) {

        mQuizzRowArrayList = new ArrayList<>();
        mUserAnswerMap = new HashMap<>();
        numberOfCorrectAnswer = 0;

        ArrayList<String> keyArray = new ArrayList<>(wordMap.keySet());
        while (keyArray.size() > 0 && wordMap.size() >= NUMBER_OF_BUTTON) {

            ArrayList<String> answerArray = new ArrayList<>();

            int random = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String question = keyArray.remove(random);
            String answer = wordMap.get(question);
            QuizzRow quizzRow = new QuizzRow(question, answer);
            if (revert) {
                quizzRow = new QuizzRow(answer, question);
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
                    QuizzRow row = mQuizzRowArrayList.get(r);
                    a = row.correctAnswer;
                    q = row.question;
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

        if (listener != null) {
            listener.onUserAnswerChanged(mQuizzRowArrayList.size(), mUserAnswerMap.size(), numberOfCorrectAnswer);
        }
    }

    @Override
    public QuizzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_quizz, null);
        QuizzViewHolder holder = new QuizzViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuizzViewHolder holder, int position) {
        QuizzRow quizzRow = mQuizzRowArrayList.get(position);
        String answer = mUserAnswerMap.get(quizzRow.question);
        holder.setViewWithQuizzRow(quizzRow, answer, this);
    }

    @Override
    public int getItemCount() {
        return mQuizzRowArrayList.size();
    }

    @Override
    public void onUserAnswerAction(String question, String answer, boolean correct) {
        mUserAnswerMap.put(question, answer);
        if (correct) {
            numberOfCorrectAnswer++;
        }
        if (listener != null) {
            listener.onUserAnswerChanged(mQuizzRowArrayList.size(), mUserAnswerMap.size(), numberOfCorrectAnswer);
        }
    }
}
