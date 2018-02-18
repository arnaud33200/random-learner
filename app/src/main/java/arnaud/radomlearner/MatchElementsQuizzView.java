package arnaud.radomlearner;

import android.content.Context;
import android.graphics.Rect;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by arnaud on 2018/02/17.
 */

public class MatchElementsQuizzView extends RelativeLayout {

    ArrayList<ElementToMatchView> topElementToMatchArrayList;
    ArrayList<ElementToMatchView> bottomElementToMatchArrayList;

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

        topElementToMatchArrayList = new ArrayList<>();
        LinearLayout topLinearLayout = rootView.findViewById(R.id.top_linear_layout);
        for (int i=0; i<topLinearLayout.getChildCount(); ++i) {
            ElementToMatchView view = (ElementToMatchView) topLinearLayout.getChildAt(i);
            topElementToMatchArrayList.add(view);
        }

        bottomElementToMatchArrayList = new ArrayList<>();
        LinearLayout bottomLinearLayout = rootView.findViewById(R.id.bottom_linear_layout);
        for (int i=0; i<bottomLinearLayout.getChildCount(); ++i) {
            ElementToMatchView view = (ElementToMatchView) bottomLinearLayout.getChildAt(i);
            bottomElementToMatchArrayList.add(view);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            Log.d("TOUCH", "DOWN");
//            Log.d("TOUCH", "action " + action + " (" + event.getX() + " , " + event.getY());
        }
        else if (action == MotionEvent.ACTION_MOVE) {

        }
        else {
            Log.d("TOUCH", "UP");
        }

        ArrayList<ElementToMatchView> arrayList = new ArrayList();
        for (ElementToMatchView view : topElementToMatchArrayList) {
            view.setNormalState();
            if (positionInsideElement(view, event)) {
                view.setSelectedState();
            }
        }
        for (ElementToMatchView view : bottomElementToMatchArrayList) {
            view.setNormalState();
            if (positionInsideElement(view, event)) {
                view.setSelectedState();
            }
        }

        return true;
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
}

