package arnaud.radomlearner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by arnaud on 2018/02/17.
 */

public class ElementToMatchView extends RelativeLayout {

    TextView textView;
    private ImageView backgroundImageView;

    public ElementToMatchView(Context context) {
        super(context);
        init(context);
    }

    public ElementToMatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ElementToMatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.view_element_to_match, this, true);

        textView = rootView.findViewById(R.id.text_view);
        textView.setText("YESSSS");

        backgroundImageView = rootView.findViewById(R.id.background_imageview);

        setNormalState();
    }

    public void setNormalState() {
        int backgroundColor = RandomLearnerApp.getContextColor(R.color.not_answer);
        backgroundImageView.setColorFilter(backgroundColor);
    }

    public void setSelectedState() {
        int backgroundColor = RandomLearnerApp.getContextColor(R.color.colorAccent);
        backgroundImageView.setColorFilter(backgroundColor);
    }

}
