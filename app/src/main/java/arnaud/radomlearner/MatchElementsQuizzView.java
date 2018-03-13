package arnaud.radomlearner;

import android.content.Context;
import android.graphics.Rect;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import arnaud.radomlearner.fragment.MatchElementQuizFragment;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/17.
 */

public class MatchElementsQuizzView extends RelativeLayout {

    ArrayList<ElementToMatchView> elementToMatchArrayList;
    ElementToMatchView firstSelectedElement;
    ElementToMatchView secondSelectedElement;

    Button nextButton;

    ArrayList<Pair<String, String>> userAnswerArray;

    static final int[] colorSelectionArray = new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorAccentDark, R.color.colorPrimaryDark};

    private Quiz quiz;
    private MatchElementActionListener listener;

    private LinearLayout topLinearLayout;
    private LinearLayout bottomLinearLayout;

    public MatchElementsQuizzView(Context context) {
        super(context);
        init(context);
    }

    public MatchElementsQuizzView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.view_match_elements_quizz, this, true);

        userAnswerArray = new ArrayList<>();

        nextButton = rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) { nextButtonClickAction(); }
        });
        nextButton.setVisibility(INVISIBLE);

        elementToMatchArrayList = new ArrayList<>();
        topLinearLayout = rootView.findViewById(R.id.top_linear_layout);
        for (int i=0; i<topLinearLayout.getChildCount(); ++i) {
            ElementToMatchView view = (ElementToMatchView) topLinearLayout.getChildAt(i);
            view.top = true;
            elementToMatchArrayList.add(view);
        }

        bottomLinearLayout = rootView.findViewById(R.id.bottom_linear_layout);
        for (int i=0; i<bottomLinearLayout.getChildCount(); ++i) {
            ElementToMatchView view = (ElementToMatchView) bottomLinearLayout.getChildAt(i);
            view.top = false;
            elementToMatchArrayList.add(view);
        }
    }

    private void nextButtonClickAction() {
        if (this.listener != null) {
            listener.onUserFinishMatching(quiz);
        }
    }

    public void setCurrentQuiz(Quiz quiz) {
        nextButton.setVisibility(INVISIBLE);
        topLinearLayout.setAlpha(1.0f);
        bottomLinearLayout.setAlpha(1.0f);
        userAnswerArray = new ArrayList<>();
        this.quiz = quiz;
        int halfSize = elementToMatchArrayList.size()/2;
        for (int i=0; i<halfSize; i++) {
            int j = i + halfSize;
            elementToMatchArrayList.get(i).setText(quiz.questionArray.get(i));
            elementToMatchArrayList.get(j).setText(quiz.answerArray.get(i));
        }
        updateViewNormalAndSelectionState();
    }

    private int getCurrentSelectionColor() {
        int index = userAnswerArray.size();
        return getColorAtIndex(index);
    }

    private int getColorAtIndex(int index) {
        if (index >= colorSelectionArray.length) {
            index = 0;
        }
        return colorSelectionArray[index];
    }

    private boolean userAnsweredEverything() {
        if (userAnswerArray.size() >= quiz.questionArray.size()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (userAnsweredEverything()) {
            return false;
        }

// SELECT
        if (action == MotionEvent.ACTION_DOWN) {
            firstSelectedElement = null;
            secondSelectedElement = null;
            ElementToMatchView element = getElementToMatch(event);
            if (element != null) {
                firstSelectedElement = element;
            }
        }

// MOVE
        else if (action == MotionEvent.ACTION_MOVE) {
            ElementToMatchView elementSelected = null;
            for (ElementToMatchView element : elementToMatchArrayList) {
                boolean sameSide = elementSameSideAsCurrentlySelected(element);
                boolean positionInside = positionInsideElement(element, event);
                int i = getIndexOfUserAnswer(element);
                if (positionInside && sameSide == false && i < 0) {
                    elementSelected = element;
                    break;
                }
            }
            secondSelectedElement = elementSelected;
        }

// UP
        else {
            checkUserAsnwer();
            firstSelectedElement = null;
            secondSelectedElement = null;
        }

        updateViewNormalAndSelectionState();

        return true;
    }

    private void checkUserAsnwer() {
        if (firstSelectedElement == null || secondSelectedElement == null) {
            return;
        }
        String topText = firstSelectedElement.top ? firstSelectedElement.getText() : secondSelectedElement.getText();
        String bottomText = firstSelectedElement.top == false ? firstSelectedElement.getText() : secondSelectedElement.getText();
        String correctAnswer = quiz.correctMap.get(topText);
        if (bottomText.equals(correctAnswer)) {
            Pair<String, String> currentSelection = new Pair<>(topText, bottomText);
            userAnswerArray.add(currentSelection);
        }
        quiz.setUserAnswer(topText, bottomText);
        if (userAnsweredEverything()) {
            nextButton.setVisibility(VISIBLE);
            topLinearLayout.setAlpha(0.4f);
            bottomLinearLayout.setAlpha(0.4f);
        }
    }

    private void updateViewNormalAndSelectionState() {
        int selectionColor = getCurrentSelectionColor();
        for (ElementToMatchView element : elementToMatchArrayList) {

        // USER ANSWER
            int i = getIndexOfUserAnswer(element);
            if (i >= 0) {
                int color = colorSelectionArray[i];
                element.setSelectedState(color);
                continue;
            }

            boolean alreadySelected = elementAlreadySelected(element);
        // Currently Selected
            if (alreadySelected) {
                element.setSelectedState(selectionColor);
                continue;
            }

        // not selected yet
            element.setNormalState();
        }
    }

    private int getIndexOfUserAnswer(ElementToMatchView element) {
        int i = 0;
        String text = element.getText();
        for (Pair userAnswer : userAnswerArray) {
            if (userAnswer.first.equals(text) || userAnswer.second.equals(text)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    private boolean elementAlreadySelected(ElementToMatchView element) {
        if (firstSelectedElement != null && firstSelectedElement.equals(element)) {
            return true;
        }
        if (secondSelectedElement != null && secondSelectedElement.equals(element)) {
            return true;
        }
        return false;
    }

    private boolean elementSameSideAsCurrentlySelected(ElementToMatchView element) {
        if (firstSelectedElement == null) {
            return false;
        }
        if (element.top && firstSelectedElement.top) {
            return true;
        }
        else if (element.top == false && firstSelectedElement.top == false) {
            return true;
        }
        return false;
    }

    private ElementToMatchView getElementToMatch(MotionEvent event) {
        for (ElementToMatchView view : elementToMatchArrayList) {
            view.setNormalState();
            boolean positionInside = positionInsideElement(view, event);
            boolean alreadySelected = elementAlreadySelected(view);
            if (positionInside && alreadySelected == false) {
                return view;
            }
        }
        return null;
    }

    private boolean positionInsideElement(View v, MotionEvent event) {

        int[] screenlocation = new int[2];
        this.getLocationInWindow(screenlocation);

        int[] location = new int[2];
        v.getLocationInWindow(location);

        Rect rect = new Rect();
        v.getLocalVisibleRect(rect);

        int viewX = location[0];
        int viewXWidth = viewX + v.getWidth();
        int viewY = location[1] - screenlocation[1];
        int viewYHeight = viewY + v.getHeight();

        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE) {
            return false;
        }

        float x = event.getX();
        float y = event.getY();
        if (x < viewX || x > viewXWidth) {
            return false;
        }

        if (y < viewY || y > viewYHeight) {
            return false;
        }

        return true;
    }

    public interface MatchElementActionListener {
        void onUserFinishMatching(Quiz quiz);
    }

    public void setListener(MatchElementActionListener listener) {
        this.listener = listener;
    }
}

