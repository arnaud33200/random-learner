package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private QuizzAdapter.QuizzRow mQuizzRow;

    public interface ButtonActionListener {
        void onUserAnswerAction(String question, String answer);
    }

    TextView questionTextView;

    Button answer1Button;
    Button answer2Button;

    String userAnswer;

    ButtonActionListener listener;

    public QuizzViewHolder(View itemView) {
        super(itemView);

        userAnswer = "";

        questionTextView = itemView.findViewById(R.id.question_text_view);
        answer1Button = itemView.findViewById(R.id.button_1);
        answer1Button.setOnClickListener(this);
        answer2Button = itemView.findViewById(R.id.button_2);
        answer2Button.setOnClickListener(this);
    }

    public void setViewWithQuizzRow(QuizzAdapter.QuizzRow quizzRow, String answer, ButtonActionListener listener) {
        mQuizzRow = quizzRow;
        userAnswer = answer != null ? answer : "";
        this.listener = listener;
        questionTextView.setText(quizzRow.question);
        answer1Button.setText(quizzRow.answer1);
        answer2Button.setText(quizzRow.answer2);
        updateButtonsUiAfterAnswer();
    }

    @Override
    public void onClick(View view) {
        if (this.userAnswer.length() > 0) {
            return; // already answer
        }
        Button button = (Button) view;
        this.userAnswer = button.getText().toString();
        updateButtonsUiAfterAnswer();
        if (listener != null) {
            listener.onUserAnswerAction(mQuizzRow.question, userAnswer);
        }
    }

    private void updateButtonsUiAfterAnswer() {
        int button1ColorRes = R.color.not_answer;
        float button1Alpha = 1.0f;
        int button2ColorRes = R.color.not_answer;
        float button2Alpha = 1.0f;


        if (userAnswer.length() > 0) {
            int answerColorRes = userAnswer.equals(mQuizzRow.correctAnswer) ? R.color.correct_answer : R.color.bad_answer;

            if (userAnswer.equals(mQuizzRow.answer1)) {
                button1ColorRes = answerColorRes;
                button2Alpha = 0.5f;
            } else {
                button2ColorRes = answerColorRes;
                button1Alpha = 0.5f;
            }
        }

        answer1Button.setBackgroundColor(RandomLearnerApp.getContextColor(button1ColorRes));
        answer1Button.setAlpha(button1Alpha);
        answer2Button.setBackgroundColor(RandomLearnerApp.getContextColor(button2ColorRes));
        answer2Button.setAlpha(button2Alpha);
    }
}
