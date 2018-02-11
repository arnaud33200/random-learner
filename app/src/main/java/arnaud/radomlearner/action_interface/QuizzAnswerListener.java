package arnaud.radomlearner.action_interface;

/**
 * Created by arnaud on 2018/02/11.
 */

public interface QuizzAnswerListener {
    void onUserAnswerChanged(int totalQuestion, int totalAnswer, int totalCorrect);
}
