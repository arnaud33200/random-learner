package arnaud.radomlearner.model;

import java.util.ArrayList;

import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/10.
 */

public class Quiz {

    public ArrayList<String> questionArray;
    public ArrayList<String> answerArray;

    public String userAnswer;

    public String correctQuestion;
    public String correctAnswer;

    public Quiz(String correctQuestion, String correctAnswer, ArrayList questionArray, ArrayList answerArray) {
        this.questionArray = questionArray;
        this.answerArray = answerArray;
        this.correctQuestion = correctQuestion;
        this.correctAnswer = correctAnswer;
        this.userAnswer = "";
    }

    public String getRandomQuestion() {
        int r = DataHelper.getRadomNumber(0, questionArray.size()-1);
        return questionArray.get(r);
    }

    public boolean isCorrectAnswer() {
        if (this.userAnswer == null) {
            return false;
        }
        return correctAnswer.equals(this.userAnswer);
    }
}
