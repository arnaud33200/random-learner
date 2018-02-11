package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import arnaud.radomlearner.R;
import arnaud.radomlearner.action_interface.UserActionListener;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizzViewHolder> {

    public UserActionListener listener;

    private ArrayList<Quiz> mQuizzRowArrayList;

    public QuizzAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
    }

    public void setWordMap(ArrayList<Quiz> quizRowArrayList) {
        mQuizzRowArrayList = quizRowArrayList;
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
        Quiz quizzRow = mQuizzRowArrayList.get(position);
        holder.setViewWithQuizzRow(quizzRow, listener);
    }

    @Override
    public int getItemCount() {
        return mQuizzRowArrayList.size();
    }
}
