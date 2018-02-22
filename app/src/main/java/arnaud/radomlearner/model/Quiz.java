package arnaud.radomlearner.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import arnaud.radomlearner.helper.DataHelper;

/**
 * Created by arnaud on 2018/02/10.
 */

public class Quiz {

    public String firstQuestion;
    public String firstAnswer;

    public ArrayList<String> questionArray;
    public ArrayList<String> answerArray;

    public HashMap<String, String> correctMap;
    private HashMap<String, String> answerMap;

    public Quiz() {
        this(new HashMap<String, String>(), new ArrayList(), new ArrayList());
    }

    public Quiz(HashMap<String, String> correctMap, ArrayList questionArray, ArrayList answerArray) {
        this.questionArray = questionArray;
        this.answerArray = answerArray;
        this.correctMap = correctMap;
        this.answerMap = new HashMap<>();
    }

    public Pair<String, String> getFirsCorrectPair() {
        return getCorrectPairAtIndex(0);
    }

    public Pair<String, String> getRandomCorrectPair() {
        int r = DataHelper.getRadomNumber(0, correctMap.size()-1);
        return getCorrectPairAtIndex(r);
    }

    public Pair<String, String> getCorrectPairAtIndex(int index) {
        String key = new ArrayList<>(correctMap.keySet()).get(index);
        if (key == null) {
            return new Pair<>("", "");
        }
        String value = correctMap.get(key);
        return new Pair<>(key, value);
    }

    public void setUserAnswer(String question, String answer) {
        String userAnswer = answerMap.get(question);
        // only if he did answer yet
        if (userAnswer == null) {
            answerMap.put(question, answer);
        }
    }

    public String getCorrectAnswer(String question) {
        String correctAnswer = correctMap.get(question);
        return correctAnswer;
    }

    public boolean isCorrectAnswer() {
        boolean correct = true;
        for (String question : answerMap.keySet()) {
            String answer = answerMap.get(question);
            String correctAnswer = getCorrectAnswer(question);
            if (answer.equals(correctAnswer) == false) {
                correct = false; break;
            }
        }
        return correct;
    }

    public HashMap<String, String> getAnswerMap() {
        return answerMap;
    }

    public String getFirstUserAnswer() {
        int index = 0;
        ArrayList<String> userAnswerArray = new ArrayList<>(answerMap.values());
        if (userAnswerArray.size() == 0) {
            return "";
        }
        String answer = userAnswerArray.get(index);
        if (answer == null) {
            answer = "";
        }
        return answer;
    }

    public boolean questionAlreadyAdded(String question) {
        String answer = getCorrectAnswer(question);
        boolean alreadyAdded = answer != null;
        if (alreadyAdded) {
            answer = "";
        }
        return alreadyAdded;
    }

    public void addQuestionAndAsnwer(String question, String answer) {
        addQuestion(question, answer);
        addAnswer(answer);
    }

    public void addQuestion(String question, String correctAnswer) {
        int insertIndex = DataHelper.getRadomNumber(0, answerArray.size());
        if (questionArray.size() == 0) {
            firstQuestion = question;
        }
        questionArray.add(insertIndex, question);
        correctMap.put(question, correctAnswer);
    }

    public void addAnswer(String answer) {
        int insertIndex = DataHelper.getRadomNumber(0, answerArray.size());
        if (answerArray.size() == 0) {
            firstAnswer = answer;
        }
        answerArray.add(insertIndex, answer);
    }



    public void invertQuestionAnswer() {
        String copyFirstQuestion = firstQuestion;
        firstQuestion = firstAnswer;
        firstAnswer = firstQuestion;

        ArrayList<String> copyQuestionArray = (ArrayList<String>) questionArray.clone();
        questionArray = answerArray;
        answerArray = copyQuestionArray;

        Collections.reverse(questionArray);
        Collections.reverse(answerArray);

        HashMap<String, String> newCorrectMap = new HashMap<>();
        for (String question : correctMap.keySet()) {
            String answer = correctMap.get(question);
            newCorrectMap.put(answer, question);
        }
        correctMap = newCorrectMap;
    }
}
