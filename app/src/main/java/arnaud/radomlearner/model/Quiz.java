package arnaud.radomlearner.model;

import java.util.ArrayList;

/**
 * Created by arnaud on 2018/02/10.
 */

public class Quiz {

    public String question;

    public String userAnswer;
    public String correctAnswer;

    public ArrayList<String> answerArray;

    public Quiz(String question, String correct) {
        this.question = question;
        this.correctAnswer = correct;
        this.userAnswer = "";
        this.answerArray = new ArrayList<>();
    }

    public boolean isCorrectAnswer() {
        if (this.userAnswer == null) {
            return false;
        }
        return correctAnswer.equals(this.userAnswer);
    }
}
