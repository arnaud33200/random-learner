package arnaud.radomlearner.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by arnaud on 2018/02/06.
 */

public abstract class AbstractLearnerFragment extends Fragment {

    protected HashMap<String, String> wordMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getMainLayoutRes(), container, false);

        return rootView;
    }

    protected abstract int getMainLayoutRes();

    public void setWordMap(HashMap<String, String> wordMap) {
        this.wordMap = wordMap;
        updateDisplayWithNewWordMap();
    }

    protected abstract void updateDisplayWithNewWordMap();
}
