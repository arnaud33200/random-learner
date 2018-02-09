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

    private ArrayList<QuizzRow> mQuizzRowArrayList;
    private HashMap<String, String> mUserAnswerMap;

    public QuizzAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
        mUserAnswerMap = new HashMap<>();
    }

    public void setWordMap(HashMap<String, String> wordMap) {
        mQuizzRowArrayList = new ArrayList<>();

        ArrayList<String> keyArray = new ArrayList<>(wordMap.keySet());
        while (keyArray.size() > 0) {

            ArrayList<String> answerArray = new ArrayList<>();

            int random = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String question = keyArray.remove(random);
            String answer = wordMap.get(question);
            QuizzRow quizzRow = new QuizzRow(question, answer);
            answerArray.add(answer);

            for (int i=0; i<NUMBER_OF_BUTTON-1; ++i) {
                String a = "";
                if (keyArray.size() > 0) {
                    int r = DataHelper.getRadomNumber(0, keyArray.size()-1);
                    String q = keyArray.get(r);
                    a = wordMap.get(q);
                } else {
                    int r = DataHelper.getRadomNumber(0, mQuizzRowArrayList.size()-1);
                    QuizzRow row = mQuizzRowArrayList.get(r);
                    a = row.correctAnswer;
                }
                int insertIndex = DataHelper.getRadomNumber(0, answerArray.size());
                answerArray.add(insertIndex, a);
            }

            quizzRow.answerArray = answerArray;
            mQuizzRowArrayList.add(quizzRow);
        }

        notifyDataSetChanged();
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
    public void onUserAnswerAction(String question, String answer) {
        mUserAnswerMap.put(question, answer);
    }
}
