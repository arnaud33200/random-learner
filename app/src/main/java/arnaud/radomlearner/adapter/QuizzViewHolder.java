package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.TwoSideSliderButtonView;
import arnaud.radomlearner.action_interface.UserActionListener;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class QuizzViewHolder extends RecyclerView.ViewHolder implements TwoSideSliderButtonView.SideSliderListener {

    private Quiz mQuizzRow;

    private TextView questionTextView;
    private TwoSideSliderButtonView twoSideSliderButtonView;

    UserActionListener listener;

    public QuizzViewHolder(View itemView) {
        super(itemView);

        questionTextView = itemView.findViewById(R.id.question_text_view);

        twoSideSliderButtonView = new TwoSideSliderButtonView(itemView.findViewById(R.id.button_layout));
        twoSideSliderButtonView.listener = this;
    }

    public void setViewWithQuizzRow(Quiz quizzRow, UserActionListener listener) {
        mQuizzRow = quizzRow;
        this.listener = listener;

    // init view
        String question = quizzRow.firstQuestion;
        questionTextView.setText(question);
        String correctAnswer = quizzRow.getCorrectAnswer(question);
        twoSideSliderButtonView.setText(quizzRow.answerArray.get(0), quizzRow.answerArray.get(1), correctAnswer);
        twoSideSliderButtonView.setToInitialState();

    // user answer
        String answer = mQuizzRow.getFirstUserAnswer();
        if (answer.length() > 0) {
            boolean left = answer.equals(quizzRow.answerArray.get(0));
            twoSideSliderButtonView.animationSlideButton(left, false);
        }
    }

    @Override
    public void onSliderClickAction(String answer) {
        String question = mQuizzRow.firstQuestion;
        mQuizzRow.setUserAnswer(question, answer);
        if (listener != null) {
            listener.onUserAnswerAction(mQuizzRow);
        }
    }

//    @Override
//    public void onClick(View view) {
//        if (this.userAnswer.length() > 0) {
//            return; // already answer
//        }

//    }

}
