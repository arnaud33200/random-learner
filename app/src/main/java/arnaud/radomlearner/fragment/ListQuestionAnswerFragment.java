package arnaud.radomlearner.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import arnaud.radomlearner.R;
import arnaud.radomlearner.adapter.ListAdapter;
import arnaud.radomlearner.adapter.QuizzAdapter;
import android.support.v7.widget.SearchView;

import arnaud.radomlearner.helper.DataHelper;
import arnaud.radomlearner.model.Quiz;

/**
 * Created by arnaud on 2018/02/08.
 */

public class ListQuestionAnswerFragment extends AbstractLearnerFragment implements SearchView.OnQueryTextListener {

    RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    private MenuItem mSearchOption;
    private String currentSearch = "";

    private boolean revert;


    @Override protected int getMainLayoutRes() {
        return R.layout.quizz_fragment;
    }

    @Override protected int getNumberOfAnswer() { return 1; }
    @Override protected int getNumberOfQuestion() { return 1; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new ListAdapter();
        updateAdapterWithSearch(currentSearch);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

//        if (mAdapter == null) {
//            mAdapter = new ListAdapter();
//        }

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    protected void updateDisplayWithNewWordMap(ArrayList<Quiz> quizArrayList) {
        updateAdapterWithSearch(currentSearch);
    }

    @SuppressLint("NewApi")
    private void updateAdapterWithSearch(String search) {
        currentSearch = search;
        if (mAdapter == null) {
            return;
        }
//        if (mAdapter != null) {
//            mAdapter = new ListAdapter();
//        }

        ArrayList<Quiz> arrayList = new ArrayList<>(mQuizArrayList);
        if (search.length() > 0) {
            arrayList.removeIf(Quiz.getSearchPredicate(currentSearch));
        }
        mAdapter.setWordMap(arrayList);
    }

    @Override
    public boolean needToDisplayTopStatusBar() {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        mSearchOption = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchOption.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        updateAdapterWithSearch(newText);
        return false;
    }
}
