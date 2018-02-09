package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzViewHolder extends RecyclerView.ViewHolder {

    TextView questionTextView;
    Button answer1Button;
    Button answer2Button;

    String correctAnswer;

    public QuizzViewHolder(View itemView) {
        super(itemView);

        questionTextView = itemView.findViewById(R.id.question_text_view);
        answer1Button = itemView.findViewById(R.id.button_1);
        answer2Button = itemView.findViewById(R.id.button_2);
    }

    public void setViewWithQuizzRow(QuizzAdapter.QuizzRow quizzRow) {
        correctAnswer = quizzRow.correctAnswer;
        questionTextView.setText(quizzRow.question);
        int random = DataHelper.getRadomNumber(0, 1);
        if (random == 0) {
            answer1Button.setText(quizzRow.correctAnswer);
            answer2Button.setText(quizzRow.badAnswer);
        }
        else {
            answer2Button.setText(quizzRow.correctAnswer);
            answer1Button.setText(quizzRow.badAnswer);
        }
    }
}
