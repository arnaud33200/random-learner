package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.RandomLearnerApp;
import arnaud.radomlearner.TwoSideSliderButtonView;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzViewHolder extends RecyclerView.ViewHolder implements TwoSideSliderButtonView.SideSliderListener {

    private QuizzAdapter.QuizzRow mQuizzRow;
    String userAnswer;

    private TextView questionTextView;
    private TwoSideSliderButtonView twoSideSliderButtonView;

    public interface ButtonActionListener {
        void onUserAnswerAction(String question, String answer, boolean correct);
    }

    ButtonActionListener listener;

    public QuizzViewHolder(View itemView) {
        super(itemView);

        userAnswer = "";

        questionTextView = itemView.findViewById(R.id.question_text_view);

        twoSideSliderButtonView = new TwoSideSliderButtonView(itemView.findViewById(R.id.button_layout));
        twoSideSliderButtonView.listener = this;
    }

    public void setViewWithQuizzRow(QuizzAdapter.QuizzRow quizzRow, String answer, ButtonActionListener listener) {
        mQuizzRow = quizzRow;
        userAnswer = answer != null ? answer : "";
        this.listener = listener;
        questionTextView.setText(quizzRow.question);

        twoSideSliderButtonView.setText(quizzRow.answerArray.get(0), quizzRow.answerArray.get(1), quizzRow.correctAnswer);

        twoSideSliderButtonView.setToInitialState();
        if (userAnswer.length() > 0) {
            boolean left = answer.equals(quizzRow.answerArray.get(0));
            twoSideSliderButtonView.animationSlideButton(left, false);
        }
    }

    @Override
    public void onSliderClickAction(String answer) {
        this.userAnswer = answer;
        boolean correct = this.userAnswer.equals(mQuizzRow.correctAnswer);
        if (listener != null) {
            listener.onUserAnswerAction(mQuizzRow.question, userAnswer, correct);
        }
    }

//    @Override
//    public void onClick(View view) {
//        if (this.userAnswer.length() > 0) {
//            return; // already answer
//        }

//    }

}
