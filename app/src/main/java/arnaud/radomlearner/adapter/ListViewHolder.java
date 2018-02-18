package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.TwoSideSliderButtonView;
import arnaud.radomlearner.action_interface.UserActionListener;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class ListViewHolder extends RecyclerView.ViewHolder {

    private Quiz mQuizzRow;

    private RelativeLayout leftLayout;
    private RelativeLayout rightLayout;

    private TextView questionTextView;
    private TextView answerTextView;

    public ListViewHolder(View itemView) {
        super(itemView);

        questionTextView = itemView.findViewById(R.id.question_text_view);
        answerTextView = itemView.findViewById(R.id.answer_text_view);

        leftLayout = itemView.findViewById(R.id.left_layout);
        rightLayout = itemView.findViewById(R.id.right_layout);
    }

    public void setViewWithQuizzRow(Quiz quizzRow, int position) {
        mQuizzRow = quizzRow;
        questionTextView.setText(quizzRow.questionArray.get(0));
        answerTextView.setText(quizzRow.answerArray.get(0));

        if (position % 2 == 0) {
            leftLayout.setBackgroundColor(RandomLearnerApp.getContextColor(R.color.colorAccent_AA));
            rightLayout.setBackgroundColor(RandomLearnerApp.getContextColor(R.color.white));
        } else {
            leftLayout.setBackgroundColor(RandomLearnerApp.getContextColor(R.color.colorAccent));
            rightLayout.setBackgroundColor(RandomLearnerApp.getContextColor(R.color.grey_back_text));
        }
    }

}
