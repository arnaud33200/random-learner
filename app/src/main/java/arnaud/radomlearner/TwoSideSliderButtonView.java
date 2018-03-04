package arnaud.radomlearner;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by arnaud on 2018/02/10.
 */

public class TwoSideSliderButtonView extends RelativeLayout {

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

    public void setNeutralColor(boolean neutralColor) {
        this.neutralColor = neutralColor;
    }

    public void setOnlyOneAnswer(boolean b) {
        onlyOneAction = b;
    }

    public interface SideSliderListener {
        void onSliderClickAction(String answer);
    }
    public SideSliderListener listener;

    public TwoSideSliderButtonView(Context context) {
        super(context);
        init(context, null);
    }

    public TwoSideSliderButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.view_two_side_slider_button, this);
        currentPosition = Position.MIDDLE;

        onlyOneAction = true;

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

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TwoSideSliderButtonView, 0, 0);
            try {
                neutralColor = a.getBoolean(R.styleable.TwoSideSliderButtonView_neutralColor, false);
            }
            finally {
                a.recycle();
            }
        }

        correctBackgroundRes = neutralColor ? R.drawable.round_neutral_background : R.drawable.round_correct_background;
        badBackgroundRes = neutralColor ? R.drawable.round_neutral_background : R.drawable.round_wrong_background;
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
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

    @SuppressLint("NewApi")
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
        if (width == 0) {
            moveMovableViewWithoutAnimation(left);
        }
        else {
            animateMovableView(left, width, animationTime);
        }
//
        animateView(100,40, animationTime, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                float alpha = value / 100.f;
                float oppositeValue = (-1 * value) + 140;
                if (left) {
                    rightTextView.setAlpha(alpha);
                    leftTextView.setAlpha(oppositeValue);
                } else {
                    leftTextView.setAlpha(alpha);
                    rightTextView.setAlpha(oppositeValue);
                }
            }
        });
    }

    @SuppressLint("NewApi")
    private void moveMovableViewWithoutAnimation(boolean left) {
        LayoutParams layoutparam = (LayoutParams) movableView.getLayoutParams();
        if (left) {
            layoutparam.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutparam.removeRule(RelativeLayout.RIGHT_OF);
            layoutparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutparam.addRule(RelativeLayout.LEFT_OF, R.id.central_view);
        }
        else {
            layoutparam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutparam.removeRule(RelativeLayout.LEFT_OF);
            layoutparam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutparam.addRule(RelativeLayout.RIGHT_OF, R.id.central_view);
        }
        movableView.setLayoutParams(layoutparam);
    }

    @SuppressLint("NewApi")
    private void animateMovableView(final boolean left, int width, int animationTime) {

        // restore original layout param
        LayoutParams layoutparam = (LayoutParams) movableView.getLayoutParams();
        layoutparam.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutparam.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutparam.addRule(RelativeLayout.LEFT_OF, R.id.right_layout);
        layoutparam.addRule(RelativeLayout.RIGHT_OF, R.id.left_layout);
        if (left && currentPosition == Position.LEFT) {
            layoutparam.leftMargin = 0;
            layoutparam.rightMargin = -width;
        }
        if (left == false && currentPosition == Position.RIGHT) {
            layoutparam.leftMargin = -width;
            layoutparam.rightMargin = 0;
        }
        movableView.setLayoutParams(layoutparam);

        int LeftStartingValue = ((RelativeLayout.LayoutParams) movableView.getLayoutParams()).leftMargin;
        int leftvalue = (left?width:0);

        animateView(LeftStartingValue, -leftvalue, animationTime, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) movableView.getLayoutParams();
                layoutParam.leftMargin = value;
                movableView.setLayoutParams(layoutParam);
            }
        });

        int rightStartingValue = ((RelativeLayout.LayoutParams) movableView.getLayoutParams()).rightMargin;
        int rightvalue = (left?0:width);

        animateView(rightStartingValue, -rightvalue, animationTime, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) movableView.getLayoutParams();
                layoutParam.rightMargin = value;
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
