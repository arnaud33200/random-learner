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

public class QuizzAdapter extends RecyclerView.Adapter<QuizzViewHolder> {

    public static class QuizzRow {
        public String question;
        public String correctAnswer;
        public String badAnswer;

        public QuizzRow(String question, String correct, String bad) {
            this.question = question;
            this.correctAnswer = correct;
            this.badAnswer = bad;
        }
    }

    private ArrayList<QuizzRow> mQuizzRowArrayList;

    public QuizzAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
    }

    public void setWordMap(HashMap<String, String> wordMap) {
        mQuizzRowArrayList = new ArrayList<>();

        ArrayList<String> keyArray = new ArrayList<>(wordMap.keySet());
        while (keyArray.size() > 0) {
            int i = DataHelper.getRadomNumber(0, keyArray.size()-1);
            String question1 = keyArray.remove(i);
            String anwer1 = wordMap.get(question1);

            String anwer2 = "";
            if (keyArray.size() > 0) {
                i = DataHelper.getRadomNumber(0, keyArray.size()-1);
                String question2 = keyArray.remove(i);
                anwer2 = wordMap.get(question2);
                QuizzRow row2 = new QuizzRow(question2, anwer2, anwer1);
                mQuizzRowArrayList.add(row2);
            } else {
                i = DataHelper.getRadomNumber(0, mQuizzRowArrayList.size()-1);
                QuizzRow row2 = mQuizzRowArrayList.get(i);
                anwer2 = row2.correctAnswer;
            }

            QuizzRow row1 = new QuizzRow(question1, anwer1, anwer2);
            mQuizzRowArrayList.add(row1);
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
        holder.setViewWithQuizzRow(quizzRow);
    }

    @Override
    public int getItemCount() {
        return mQuizzRowArrayList.size();
    }
}
