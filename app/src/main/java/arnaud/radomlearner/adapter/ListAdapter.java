package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.action_interface.UserActionListener;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private ArrayList<Quiz> mQuizzRowArrayList;
    public ListAdapter() {
        mQuizzRowArrayList = new ArrayList<>();
    }

    public void setWordMap(ArrayList<Quiz> quizRowArrayList) {
        mQuizzRowArrayList = quizRowArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_list, null);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Quiz quizzRow = mQuizzRowArrayList.get(position);
        holder.setViewWithQuizzRow(quizzRow, position);
    }

    @Override
    public int getItemCount() {
        return mQuizzRowArrayList.size();
    }
}
