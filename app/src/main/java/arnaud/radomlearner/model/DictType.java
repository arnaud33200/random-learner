package arnaud.radomlearner.model;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by arnaud on 2018/03/31.
 */

public class DictType implements Comparable<DictType> {
    public final String mainType;
    public final String subType;
    public final String keyId;

    public interface HashMapCallBackInterface {
        HashMap<String, String> getCollectionMap();
    }
    public final HashMapCallBackInterface hashMapCallBack;

    public DictType(String mainType, HashMapCallBackInterface hashMapCallBack) {
        this(mainType, "", hashMapCallBack);
    }

    public DictType(String mainType, String subType, HashMapCallBackInterface hashMapCallBack) {
        this.mainType = mainType;
        this.subType = subType;
        this.keyId = (subType.length() > 0 ? mainType + "_" + subType : mainType).toLowerCase();
        this.hashMapCallBack = hashMapCallBack;
    }

    public String getFullTitle() {
        return mainType + " " + subType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DictType == false) {
            return super.equals(obj);
        }
        DictType dictType = (DictType) obj;
        return (this.keyId.equals(dictType.keyId));
    }

    public boolean isCurrentlySelected() {
        return QuizCollectionManager.getInstance().isDictTypeSelected(this);
    }

    @Override
    public int compareTo(@NonNull DictType dictType) {
        return dictType.keyId.compareTo(this.keyId);
    }
}