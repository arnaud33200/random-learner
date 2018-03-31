package arnaud.radomlearner.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import arnaud.radomlearner.R;
import arnaud.radomlearner.model.QuizCollectionManager;

/**
 * Created by arnaud on 2018/03/30.
 */

public class DictSettingViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;
    private final ImageView mSelectImageView;
    private QuizCollectionManager.DictType mDict;

    private ClickListener clickListener;

    public interface ClickListener {
        void dictTypeRowClickAction(QuizCollectionManager.DictType dictType);
    }

    public DictSettingViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.text_view);
        mSelectImageView = itemView.findViewById(R.id.selection_image_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.dictTypeRowClickAction(mDict);
                }
            }
        });
    }

    @SuppressLint("NewApi")
    public void setDictType(QuizCollectionManager.DictType dictType) {
        mDict = dictType;
        mTextView.setText(dictType.getFullTitle());
        if (dictType.isCurrentlySelected()) {
            mSelectImageView.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_checked));
        } else {
            mSelectImageView.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_unchecked));
        }
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
