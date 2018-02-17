package arnaud.radomlearner;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by arnaud on 2018/02/10.
 */

public class TwoSideSliderButtonView {

    public enum Position {
        LEFT, MIDDLE, RIGHT;
    }

    RelativeLayout leftLayout;
    RelativeLayout rightLayout;

    View centralView;

    View movableView;

    TextView leftTextView;
    TextView rightTextView;

    int correctBackgroundRes;
    int badBackgroundRes;

    Position currentPosition;

    boolean neutralColor;
    boolean onlyOneAction;

    boolean leftCorrect;

    public void setOnlyOneAnswer(boolean b) {
        onlyOneAction = b;
    }

    public interface SideSliderListener {
        void onSliderClickAction(String answer);
    }
    public SideSliderListener listener;

    public TwoSideSliderButtonView(View rootView) {
        this(rootView, false);
    }

    public TwoSideSliderButtonView(View rootView, boolean neutralColor) {

        currentPosition = Position.MIDDLE;

        onlyOneAction = true;
        this.neutralColor = neutralColor;

        correctBackgroundRes = neutralColor ? R.drawable.round_neutral_background : R.drawable.round_correct_background;
        badBackgroundRes = neutralColor ? R.drawable.round_neutral_background : R.drawable.round_wrong_background;

        leftLayout = rootView.findViewById(R.id.left_layout);
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftLayoutClickAction();
            }
        });
        rightLayout = rootView.findViewById(R.id.right_layout);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightLayoutClickAction();
            }
        });

        centralView = rootView.findViewById(R.id.central_view);

        movableView = rootView.findViewById(R.id.movable_view);

        leftTextView = rootView.findViewById(R.id.left_textview);
        rightTextView = rootView.findViewById(R.id.right_textview);
    }

    public void setText(String leftText, String rightText, String correctText) {
        leftTextView.setText(leftText);
        rightTextView.setText(rightText);
        this.leftCorrect = leftText.equals(correctText);
    }

    private void rightLayoutClickAction() {
        if (listener != null) {
            String answer = rightTextView.getText().toString();
            listener.onSliderClickAction(answer);
        }
        animationSlideButton(false, true);
    }

    private void leftLayoutClickAction() {
        if (listener != null) {
            String answer = leftTextView.getText().toString();
            listener.onSliderClickAction(answer);
        }
        animationSlideButton(true, true);
    }

    public void setToInitialState() {
        currentPosition = Position.MIDDLE;
        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { leftLayoutClickAction(); }
        });
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { rightLayoutClickAction(); }
        });

        int textColor = RandomLearnerApp.getContextColor(R.color.not_answer);
        leftTextView.setTextColor(textColor);
        rightTextView.setTextColor(textColor);
        movableView.setBackgroundColor(textColor);

        RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) movableView.getLayoutParams();
        layoutParam.rightMargin = 0;
        layoutParam.leftMargin = 0;
        movableView.setLayoutParams(layoutParam);

        leftTextView.setAlpha(1.0f);
        rightTextView.setAlpha(1.0f);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void animationSlideButton(final boolean left, boolean animation) {

        Position newPosition = left ? Position.LEFT : Position.RIGHT;
        if (currentPosition == newPosition) {
            return;
        }
        currentPosition = newPosition;

        if (onlyOneAction) {
            leftLayout.setOnClickListener(null);
            rightLayout.setOnClickListener(null);
        }

        int animationTime = animation ? 300 : 0;

        int textColor = RandomLearnerApp.getContextColor(R.color.white);
        int normalColor = RandomLearnerApp.getContextColor(R.color.not_answer);
        int backgroundRes = 0;
        if (left) {
            leftTextView.setTextColor(textColor);
            rightTextView.setTextColor(normalColor);
            backgroundRes = leftCorrect ? correctBackgroundRes : badBackgroundRes;
        } else {
            leftTextView.setTextColor(normalColor);
            rightTextView.setTextColor(textColor);
            backgroundRes = leftCorrect == false ? correctBackgroundRes : badBackgroundRes;
        }


        movableView.setBackground(RandomLearnerApp.getContext().getDrawable(backgroundRes));

        centralView.setBackgroundColor(RandomLearnerApp.getContextColor(R.color.transparent));
//        int centralWidth = centralView.getWidth();
        final int width = leftLayout.getWidth();
        animateMovableView(true, (left?width:0), animationTime);
        animateMovableView(false, (left?0:width), animationTime);
//
        animateView(100,40, animationTime, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                float alpha = value / 100.f;
                float oppositeValue = value + width;
                if (left) {
                    rightTextView.setAlpha(alpha);

                } else {
                    leftTextView.setAlpha(alpha);
                }
            }
        });
    }

    private void animateMovableView(final boolean left, int value, int animationTime) {
        int startingValue = ((RelativeLayout.LayoutParams) movableView.getLayoutParams()).rightMargin;
        if (left) {
            startingValue = ((RelativeLayout.LayoutParams) movableView.getLayoutParams()).leftMargin;
        }

        animateView(startingValue, -value, animationTime, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) movableView.getLayoutParams();
                if (left) {
                    layoutParam.leftMargin = value;
                } else {
                    layoutParam.rightMargin = value;
                }
                movableView.setLayoutParams(layoutParam);
            }
        });
    }

    private void animateView(int valueStart, int valueEnd, int time, ValueAnimator.AnimatorUpdateListener callBack) {
        ValueAnimator slideAnimator = ValueAnimator.ofInt(valueStart, valueEnd);
        slideAnimator.setDuration(time);
        slideAnimator.addUpdateListener(callBack);
        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

}
