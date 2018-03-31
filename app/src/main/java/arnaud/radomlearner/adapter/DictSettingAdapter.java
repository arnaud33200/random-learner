package arnaud.radomlearner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import arnaud.radomlearner.R;
import arnaud.radomlearner.model.QuizCollectionManager;

/**
 * Created by arnaud on 2018/03/30.
 */

public class DictSettingAdapter extends RecyclerView.Adapter<DictSettingViewHolder> implements DictSettingViewHolder.ClickListener {

    final ArrayList<QuizCollectionManager.DictType> dictTypeArrayList;

    public DictSettingAdapter() {
        super();
        dictTypeArrayList = QuizCollectionManager.getInstance().getDictTypeArray();
    }

    @Override
    public DictSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dict_setting, null);
        DictSettingViewHolder holder = new DictSettingViewHolder(view);
        holder.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(DictSettingViewHolder holder, int position) {
        QuizCollectionManager.DictType dictType = dictTypeArrayList.get(position);
        holder.setDictType(dictType);
    }

    @Override
    public int getItemCount() {
        return dictTypeArrayList.size();
    }

    @Override
    public void dictTypeRowClickAction(QuizCollectionManager.DictType dictType) {
        QuizCollectionManager.getInstance().setCurrentDictType(dictType);
        notifyDataSetChanged();
    }
}
