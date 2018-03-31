package arnaud.radomlearner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import arnaud.radomlearner.action_interface.QuizzAnswerListener;
import arnaud.radomlearner.fragment.AbstractLearnerFragment;
import arnaud.radomlearner.fragment.DictSettingFragmentDialogFragment;
import arnaud.radomlearner.fragment.FinalScoreFragment;
import arnaud.radomlearner.fragment.ListQuestionAnswerFragment;
import arnaud.radomlearner.fragment.MatchElementQuizFragment;
import arnaud.radomlearner.fragment.QuizzFragment;
import arnaud.radomlearner.fragment.TopDownCardFragment;
import arnaud.radomlearner.model.QuizCollectionManager;
import arnaud.radomlearner.helper.DataHelper;

public class MainActivity extends AppCompatActivity implements QuizzAnswerListener, TwoSideSliderButtonView.SideSliderListener, FinalScoreFragment.FinalScoreActionListener {

    private AbstractLearnerFragment currentFragment;

    private TextView screenTitle;
    private TextView screenSubTitle;

    private RelativeLayout statusBarLayout;
    private Button revertButton;
    private Button resetButton;
    private Button randomButton;
    private TextView statusTextView;

    private int limit = -1;

    private TwoSideSliderButtonView twoSideSliderButtonView;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    // TOOL BAR
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        screenTitle = findViewById(R.id.screen_title);
        screenTitle.setText("Random Learner");
        screenSubTitle = findViewById(R.id.screen_sub_title);

        statusBarLayout = findViewById(R.id.quizz_status_layout);

        statusTextView = findViewById(R.id.status_text_view);

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { onStatusBarResetButtonCLickAction(); }
        });

        revertButton = findViewById(R.id.revert_button);
        revertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onStatusBarRevertButtonAction(); }
        });

        randomButton = findViewById(R.id.random_button);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onStatusBarRandomButtonAction(); }
        });

        twoSideSliderButtonView = findViewById(R.id.switch_button);
        twoSideSliderButtonView.setOnlyOneAnswer(false);
        twoSideSliderButtonView.setText("ALL", "30", "");
        twoSideSliderButtonView.listener = this;

        replaceCurrentFragment(new TopDownCardFragment());

        findViewById(R.id.list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { replaceCurrentFragment(new ListQuestionAnswerFragment()); }
        });
        findViewById(R.id.top_down_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { replaceCurrentFragment(new TopDownCardFragment()); }
        });
        findViewById(R.id.quizz_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { replaceCurrentFragment(new QuizzFragment()); }
        });
        findViewById(R.id.match_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { replaceCurrentFragment(new MatchElementQuizFragment()); }
        });

        initWordMapWithCurrentDict();
    }

    @Override public void setTitle(int titleId) { /* Override to erase */ }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
        screenSubTitle.setText(title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (twoSideSliderButtonView != null) {
            twoSideSliderButtonView.animationSlideButton(true, false);
        }
    }

    @Override
    public void onUserAnswerChanged(int totalQuestion, int totalAnswer, int totalCorrect) {
        if (statusTextView == null) {
            return;
        }
        String statusText = "" + totalAnswer + " / " + totalQuestion;
//        if (totalAnswer > 0) {
//            statusText = statusText + " (" + totalCorrect + " correct)";
//        }
        statusTextView.setText(statusText);

        if (totalQuestion == totalAnswer && totalAnswer > 0) {
            displayFinalScoreFragment();
        }
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region VIEW SETTINGS

    private void replaceCurrentFragment(AbstractLearnerFragment fragment) {
        HashMap<String, String> wordMap = null;

        if (currentFragment != null && fragment.getClass() == currentFragment.getClass()) {
            return; // already selected
        }

        if (currentFragment != null) {
            wordMap = currentFragment.getWordMap();
        }
        if (wordMap == null) {
            QuizCollectionManager.getInstance().getCurrentCollection();
        }

        currentFragment = fragment;
        currentFragment.listener = this;
        fragment.setWordMap(wordMap, limit);

        changeFragmentContainer(currentFragment);
        setStatusBarVisibility(currentFragment.needToDisplayTopStatusBar() ? View.VISIBLE : View.GONE);
    }

    private void displayFinalScoreFragment() {
        setStatusBarVisibility(View.GONE);
        FinalScoreFragment fragment = new FinalScoreFragment(this, currentFragment);
        changeFragmentContainer(fragment);
    }

    private void restoreOriginalScoreFragment() {
        changeFragmentContainer(currentFragment);
        setStatusBarVisibility(currentFragment.needToDisplayTopStatusBar() ? View.VISIBLE : View.GONE);
    }

    private void changeFragmentContainer(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_contain_layout, fragment);
        transaction.commit();
    }

    private void setStatusBarVisibility(int visibility) {
        statusBarLayout.setVisibility(visibility);

        int height = (int) DataHelper.convertDpToPixel(100);
        int bottomPadding = (int) DataHelper.convertDpToPixel(45);
        if (visibility == View.GONE) {
            bottomPadding = 0;
            height = (int) DataHelper.convertDpToPixel(55);
        }
        toolbar.setPadding(0, 0, 0, bottomPadding);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = height;
        toolbar.setLayoutParams(layoutParams);
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region OPTION MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() != R.id.dict_option) {
            return false;
        }

        DictSettingFragmentDialogFragment fragment = new DictSettingFragmentDialogFragment();
        fragment.show(currentFragment.getFragmentManager(), new Runnable() {

            @Override public void run() {
                initWordMapWithCurrentDict();
            }
        });
        return true;
    }


// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region USER ACTION

    @Override
    public void onSliderClickAction(String answer) {
        limit = answer.equals("ALL") ? -1 : 30;
        currentFragment.resetQuizArray(limit);
    }

    private void onStatusBarRevertButtonAction() {
        currentFragment.swapQuestionAnswer();
    }

    private void onStatusBarRandomButtonAction() {
        currentFragment.toggleRandomOption();
    }

    private void onStatusBarResetButtonCLickAction() {
        currentFragment.resetQuizArray();
    }

    @Override
    public void onFinalScoreResetAllButtonClickAction() {
        restoreOriginalScoreFragment();
        currentFragment.resetQuizArray();
    }

    @Override
    public void onFinalScoreResetBadButtonClickAction() {
        restoreOriginalScoreFragment();
        currentFragment.resetWithOnlyBadUserAnswer();
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region DICT MAP

    private void initWordMapWithCurrentDict() {
        QuizCollectionManager quizCollectionManager = QuizCollectionManager.getInstance();
        HashMap<String, String> wordMap = quizCollectionManager.getCurrentCollection();
        String subTitle = quizCollectionManager.generateSubTitle();
        setTitle(subTitle);
        if (currentFragment != null) {
            currentFragment.setWordMap(wordMap, limit);
        }
    }



// endregion

}
