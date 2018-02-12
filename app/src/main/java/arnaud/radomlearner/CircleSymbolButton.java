package arnaud.radomlearner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by arnaud on 2018/02/11.
 */

public class CircleSymbolButton extends RelativeLayout {

    public enum Position { TOP, BOTTOM, MIDDLE }

    private TextView symbolTextView;

    public CircleSymbolButton(Context context) {
        super(context); init();
    }

    public CircleSymbolButton(Context context, AttributeSet attrs) {
        super(context, attrs); init();
    }

//    public CircleSymbolButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr); init();
//    }

    private void init() {

    }

    public void setSymbolText(String text) {
        if (symbolTextView == null) {
            symbolTextView = findViewById(R.id.symbol_textview);
        }
        symbolTextView.setText(text);
    }


    @SuppressLint("NewApi")
    public void updateView(Position position) {
        if (position == Position.TOP) {
            symbolTextView.setTextColor(RandomLearnerApp.getContextColor(R.color.card_text));
            setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.white_circle));
        }
        else if (position == Position.BOTTOM) {
            symbolTextView.setTextColor(RandomLearnerApp.getContextColor(R.color.white));
            setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.color_circle));
        }
    }
}
