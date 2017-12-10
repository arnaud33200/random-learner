package arnaud.radomlearner;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by arnaud on 2017/12/06.
 */

public class GuessWordView {

    private final View mRootView;
    public TextView textView;

    private Boolean hideMode;
    private String mText;

    public GuessWordView(View rootView) {
        mRootView = rootView;

        textView = mRootView.findViewById(R.id.text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewClickAction();
            }
        });

        hideMode = false;
    }

    public Boolean getHideMode() {
        return hideMode;
    }

    public void setTextAndDisplayMode(String text, Boolean hideMode) {
        mText = text;
        if (hideMode) { setHideMode(); }
        else { setDisplayMod(); }
    }

    public void setColorMode() {
        mRootView.setBackgroundColor(RandomLearnerApp.sharedInstance.getApplicationContext().getColor(R.color.colorAccent));
        textView.setTextColor(RandomLearnerApp.sharedInstance.getApplicationContext().getColor(R.color.white));
    }

    public void textViewClickAction() {
        if (hideMode == false) {
            return;
        }
        this.setDisplayMod();
    }

    private void setDisplayMod() {
        hideMode = false;
        textView.setAlpha(1.f);
        textView.setText(mText);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
    }

    private void setHideMode() {
        hideMode = true;
//        textView.setAlpha(0.6f);
        textView.setText(". . .");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,50);
    }

}
