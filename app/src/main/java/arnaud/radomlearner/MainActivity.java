package arnaud.radomlearner;

import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import arnaud.radomlearner.fragment.ListQuestionAnswerFragment;
import arnaud.radomlearner.fragment.MatchElementQuizFragment;
import arnaud.radomlearner.fragment.QuizzFragment;
import arnaud.radomlearner.fragment.TopDownCardFragment;
import arnaud.radomlearner.UserPreference.DictType;

public class MainActivity extends AppCompatActivity implements QuizzAnswerListener, TwoSideSliderButtonView.SideSliderListener {

    private AbstractLearnerFragment currentFragment;

    private RelativeLayout statusBarLayout;
    private Button revertButton;
    private Button resetButton;
    private Button badButton;
    private TextView statusTextView;

    private int limit = -1;

    private TwoSideSliderButtonView twoSideSliderButtonView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        statusBarLayout = findViewById(R.id.quizz_status_layout);

        statusTextView = findViewById(R.id.status_text_view);

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFragment.resetQuizArray(); }
        });

        revertButton = findViewById(R.id.revert_button);
        revertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFragment.swapQuestionAnswer(); }
        });

        badButton = findViewById(R.id.bad_button);
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment.resetWithOnlyBadUserAnswer();
            }
        });

        twoSideSliderButtonView = new TwoSideSliderButtonView(findViewById(R.id.switch_button), true);
        twoSideSliderButtonView.setOnlyOneAnswer(false);
        twoSideSliderButtonView.setText("ALL", "30", "");
        twoSideSliderButtonView.animationSlideButton(true, false);
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

        HashMap<String, String> wordMap = getCurrentWordMap();
        currentFragment.setWordMap(wordMap, limit);
    }

    private void replaceCurrentFragment(AbstractLearnerFragment fragment) {
        HashMap<String, String> wordMap = null;
        if (currentFragment != null) {
            wordMap = currentFragment.getWordMap();
        }
        if (wordMap == null) {
            wordMap = getCurrentWordMap();
        }

        currentFragment = fragment;
        currentFragment.listener = this;

        fragment.setWordMap(wordMap, limit);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_contain_layout, currentFragment);
        transaction.commit();

        statusBarLayout.setVisibility(currentFragment.needToDisplayTopStatusBar() ? View.VISIBLE : View.GONE);
    }

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

        DictType dictType = DictType.Adjective;
        if (item.getItemId() == R.id.adjective_menu) { dictType = DictType.Adjective; }
        else if (item.getItemId() == R.id.verb_menu) { dictType = DictType.Verb; }
        else if (item.getItemId() == R.id.hiragana_menu) { dictType = DictType.Hiragana; }
        else if (item.getItemId() == R.id.katana_menu_1) { dictType = DictType.KatakanaPart1; }
        else if (item.getItemId() == R.id.katana_menu_all) { dictType = DictType.KatakanaAll; }

        UserPreference.getInstance().setCurrentSelectedDictType(dictType);
        initWordMapFromCurrentDictType();

        return true;
    }

    @Override
    public void onSliderClickAction(String answer) {
        limit = answer.equals("ALL") ? -1 : 30;
        currentFragment.resetQuizArray(limit);
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
    }

    private void initWordMapFromCurrentDictType() {
        HashMap<String, String> wordMap = getCurrentWordMap();
        currentFragment.setWordMap(wordMap, limit);
    }

    private HashMap<String, String> getCurrentWordMap() {
        DictType dictType = UserPreference.getInstance().getCurrentSelectedDictType();
        HashMap<String, String> wordMap = new HashMap<>();
        if (dictType == DictType.Adjective) { wordMap = initAdjectiveDict(); }
        else if (dictType == DictType.Verb) { wordMap = initVerbMap(); }
        else if (dictType == DictType.Hiragana) { wordMap = initHiraganaMap(); }
        else if (dictType == DictType.KatakanaPart1) { wordMap = initKatakanaPart1Map(); }
        else if (dictType == DictType.KatakanaAll) { wordMap = initKatakanaAllMap(); }
        return wordMap;
    }

    private HashMap<String, String> initAdjectiveDict() {
        this.setTitle("Adjective");
        HashMap<String, String> wordMap = new HashMap<String, String>();
        wordMap.put("ookii", "big, large"); wordMap.put("chiisai", "short"); wordMap.put("takai", "expensive"); wordMap.put("yasui", "cheap"); wordMap.put("yoi", "good"); wordMap.put("warui", "bad"); wordMap.put("se ga takai", "tall"); wordMap.put("se ga hikui", "short"); wordMap.put("utsukushii", "beautiful"); wordMap.put("minikui", "ugly"); wordMap.put("fukai", "deep"); wordMap.put("asai", "shallow"); wordMap.put("hiroi", "wide"); wordMap.put("semai", "narrow"); wordMap.put("atarashii", "new"); wordMap.put("furuii", "old"); wordMap.put("shinsen na", "fresh"); wordMap.put("kusatta", "rotten"); wordMap.put("atsui", "hot"); wordMap.put("samui", "cold (weather)"); wordMap.put("nagai", "long"); wordMap.put("mijikai", "short"); wordMap.put("atsui", "thick"); wordMap.put("usui", "thin"); wordMap.put("omoi", "heavy"); wordMap.put("karui", "light (weight)"); wordMap.put("akarui", "light"); wordMap.put("kurai", "dark"); wordMap.put("katai", "hard"); wordMap.put("yawarakai", "soft"); wordMap.put("kitanai", "dirty"); wordMap.put("kirei na", "clean"); wordMap.put("chikai", "near"); wordMap.put("tooi", "far"); wordMap.put("hayai", "fast"); wordMap.put("osoi", "slow"); wordMap.put("tsuyoi", "strong"); wordMap.put("yowai", "weak"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "easy"); wordMap.put("abunai", "dangerous"); wordMap.put("anzenna", "safe"); wordMap.put("kawaita", "dry"); wordMap.put("nureta", "wet"); wordMap.put("tadashii", "correct"); wordMap.put("machigatta", "wrong"); wordMap.put("kantan na", "simple"); wordMap.put("fukuzatsu na", "complicated"); wordMap.put("tashika na", "sure"); wordMap.put("hontou no", "TRUE"); wordMap.put("usoano", "FALSE"); wordMap.put("saisho no", "first"); wordMap.put("saigo no", "last"); wordMap.put("tsugi no", "next"); wordMap.put("zenbu no", "all"); wordMap.put("maimai", "each time"); wordMap.put("yaku ni tatsu", "usefull"); wordMap.put("shizuka na", "quite"); wordMap.put("urusai", "noisy"); wordMap.put("meiwaku na", "troublesome"); wordMap.put("juubun na", "enough"); wordMap.put("tarinai", "lacking"); wordMap.put("ippai no", "full"); wordMap.put("isogashii", "busy"); wordMap.put("tadano", "free"); wordMap.put("sukoshi no", "a little bit"); wordMap.put("takusan no", "plenty"); wordMap.put("okane-mochi no", "rich"); wordMap.put("binboo na", "poor"); wordMap.put("onaka ga suita", "hungry"); wordMap.put("nodo ga kawaita", "thirsty"); wordMap.put("iya na", "nasty"); wordMap.put("daiji na", "important"); wordMap.put("marui", "round"); wordMap.put("nioi ga yoi", "a good smell"); wordMap.put("kusai", "bad odor"); wordMap.put("aoi", "blue"); wordMap.put("akai", "red"); wordMap.put("akarui", "light, bright"); wordMap.put("atatakai", "warm"); wordMap.put("atarashii", "new"); wordMap.put("atsui", "hot (air)"); wordMap.put("atsui", "thick"); wordMap.put("abunai", "dangerous"); wordMap.put("amai", "sweet"); wordMap.put("ii", "good"); wordMap.put("isogashii", "to be busy"); wordMap.put("itai", "to be painful"); wordMap.put("usui", "thin"); wordMap.put("oishii", "tasty, delicious"); wordMap.put("ookii", "big"); wordMap.put("osoi", "late, slow"); wordMap.put("omoi", "heavy"); wordMap.put("omoshiroi", "intersting, funny"); wordMap.put("karai", "hot, spicy"); wordMap.put("karui", "light (not heavy)"); wordMap.put("kawaii", "cute, pretty"); wordMap.put("kiiroi", "yellow"); wordMap.put("kitanai", "dirty"); wordMap.put("kurai", "dark"); wordMap.put("kuroi", "black"); wordMap.put("samui", "cold"); wordMap.put("shiroi", "white"); wordMap.put("suzushii", "cool"); wordMap.put("semai", "narrow"); wordMap.put("takai", "high, expensive"); wordMap.put("tanoshii", "pleasant, enjoyable"); wordMap.put("chiisai", "small"); wordMap.put("chikai", "near, close"); wordMap.put("tsumaranai", "uninteresting"); wordMap.put("tsumetai", "cold"); wordMap.put("tsuyoi", "strong"); wordMap.put("tooi", "far"); wordMap.put("nagai", "long"); wordMap.put("hayai", "early"); wordMap.put("hayai", "fast, quick"); wordMap.put("hikui", "low"); wordMap.put("hiroi", "wide, spacious"); wordMap.put("futoi", "thick, fat"); wordMap.put("furui", "old"); wordMap.put("hoshii", "to want something"); wordMap.put("hosoi", "thin, fine"); wordMap.put("mazui", "bad tasting"); wordMap.put("marui", "round"); wordMap.put("mijikai", "short"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "gentle"); wordMap.put("yasui", "cheap"); wordMap.put("wakai", "young");
        return wordMap;
    }

    private HashMap<String, String> initVerbMap() {
        this.setTitle("Verb");
        HashMap<String, String> wordMap = new HashMap<String, String>();
        wordMap.put("hairu", "enter"); wordMap.put("hashiru", "run"); wordMap.put("heru", "decrease, reduce"); wordMap.put("iru", "need"); wordMap.put("kaeru", "return"); wordMap.put("keru", "kick"); wordMap.put("kiru", "cut"); wordMap.put("shiru", "know"); wordMap.put("ageru", "give"); wordMap.put("akeru", "open"); wordMap.put("arau", "wash"); wordMap.put("aru", "have, exist"); wordMap.put("aruku", "walk"); wordMap.put("asobu", "play"); wordMap.put("dasu", "extract, take out"); wordMap.put("dekiru", "be able"); wordMap.put("deru", "go out"); wordMap.put("fueru", "increase"); wordMap.put("fureru", "touch"); wordMap.put("hajimaru", "begin"); wordMap.put("hajimeru", "begin"); wordMap.put("hanasu", "speak"); wordMap.put("harau", "pay"); wordMap.put("hataraku", "work"); wordMap.put("hiku", "pull"); wordMap.put("homeru", "praise"); wordMap.put("horeru", "fall in love"); wordMap.put("horu", "dig"); wordMap.put("hoshigaru", "want, desire"); wordMap.put("ikiru", "be alive"); wordMap.put("iku", "go"); wordMap.put("iru", "be (location living things)"); wordMap.put("isogu", "hurry"); wordMap.put("iu", "say"); wordMap.put("iwau", "celebrate, congratulate"); wordMap.put("kaeru", "change"); wordMap.put("kaku", "write"); wordMap.put("kamu", "bite"); wordMap.put("kangaeru", "think"); wordMap.put("kariru", "borrow, rent"); wordMap.put("katsu", "win"); wordMap.put("kau", "buy"); wordMap.put("kawakasu", "dry"); wordMap.put("kazoeru", "count"); wordMap.put("kiku", "listen"); wordMap.put("kinjiru", "ban, prohibit"); wordMap.put("kiru", "wear"); wordMap.put("komaru", "be in trouble"); wordMap.put("konomu", "like"); wordMap.put("korobu", "fall down"); wordMap.put("korosu", "kill"); wordMap.put("kotaeru", "answer"); wordMap.put("kowagaru", "fear"); wordMap.put("kowareru", "break"); wordMap.put("kowasu", "smash"); wordMap.put("kuraberu", "compare"); wordMap.put("kurasu", "live"); wordMap.put("kuru", "come"); wordMap.put("mamoru", "protect"); wordMap.put("manabu", "learn, study"); wordMap.put("matsu", "wait"); wordMap.put("miru", "look"); wordMap.put("miseru", "show"); wordMap.put("mitsukeru", "find"); wordMap.put("modoru", "return, go back"); wordMap.put("morau", "receive, get"); wordMap.put("motsu", "have, hold, own"); wordMap.put("nagameru", "watch, view"); wordMap.put("naguru", "punch, hit"); wordMap.put("naku", "cry"); wordMap.put("namakeru", "be lazy"); wordMap.put("naosu", "repair, cure"); wordMap.put("narau", "learn"); wordMap.put("naru", "become"); wordMap.put("nemuru", "fall asleep"); wordMap.put("noboru", "climb"); wordMap.put("nomu", "drink"); wordMap.put("nuru", "paint"); wordMap.put("nusumu", "steal"); wordMap.put("oboeru", "remember, learn"); wordMap.put("odoru", "dance"); wordMap.put("okiru", "get up"); wordMap.put("oku", "put, place"); wordMap.put("okureru", "be late, lag behind"); wordMap.put("omou", "think"); wordMap.put("osou", "attack"); wordMap.put("osu", "push, press"); wordMap.put("owaru", "end"); wordMap.put("oyogu", "swim"); wordMap.put("sagasu", "search for"); wordMap.put("sakeru", "avoid, dodge"); wordMap.put("saku", "bloom"); wordMap.put("shimeru", "shut"); wordMap.put("shinu", "die"); wordMap.put("sumu", "live, reside"); wordMap.put("suru", "do, make"); wordMap.put("taberu", "eat"); wordMap.put("tameru", "save, store, accumulate"); wordMap.put("tamesu", "test, try, taste"); wordMap.put("tanoshimu", "enjoy, have fun"); wordMap.put("tasu", "add"); wordMap.put("tasukeru", "help"); wordMap.put("tatamu", "fold"); wordMap.put("tateru", "build"); wordMap.put("tatsu", "stand up"); wordMap.put("tetsudau", "help"); wordMap.put("tobu", "jump, fly"); wordMap.put("toku", "solve, untie"); wordMap.put("tomaru", "stop"); wordMap.put("torikaeru", "exchange"); wordMap.put("tsukareru", "get tired"); wordMap.put("tsukau", "use, operate"); wordMap.put("tsuku", "arrive"); wordMap.put("tsureru", "lead"); wordMap.put("tsuru", "fish"); wordMap.put("ueru", "plant"); wordMap.put("ugoku", "move"); wordMap.put("umareru", "be born"); wordMap.put("uru", "sell"); wordMap.put("utagau", "doubt, suspect"); wordMap.put("utau", "sing"); wordMap.put("wakaru", "understand"); wordMap.put("warau", "laugh"); wordMap.put("wasureru", "forget"); wordMap.put("yakeru", "burn"); wordMap.put("yaku", "bake, grill"); wordMap.put("yobu", "call"); wordMap.put("yomu", "read");
        return wordMap;
    }

    private HashMap<String, String> initHiraganaMap() {
        this.setTitle("Hiragana");
        HashMap<String, String> wordMap = new HashMap<String, String>();
        wordMap.put("あ", "A"); wordMap.put("い", "I"); wordMap.put("う", "U"); wordMap.put("え", "E"); wordMap.put("お", "O"); wordMap.put("か", "KA"); wordMap.put("が", "GA"); wordMap.put("き", "KI"); wordMap.put("ぎ", "GI"); wordMap.put("く", "KU"); wordMap.put("ぐ", "GU"); wordMap.put("け", "KE"); wordMap.put("げ", "GE"); wordMap.put("こ", "KO"); wordMap.put("ご", "GO"); wordMap.put("さ", "SA"); wordMap.put("ざ", "ZA"); wordMap.put("し", "SI"); wordMap.put("じ", "ZI"); wordMap.put("す", "SU"); wordMap.put("ず", "ZU"); wordMap.put("せ", "SE"); wordMap.put("ぜ", "ZE"); wordMap.put("そ", "SO"); wordMap.put("ぞ", "ZO"); wordMap.put("た", "TA"); wordMap.put("だ", "DA"); wordMap.put("ち", "CHI"); wordMap.put("ぢ", "DI"); wordMap.put("つ", "TSU"); wordMap.put("づ", "DU"); wordMap.put("て", "TE"); wordMap.put("で", "DE"); wordMap.put("と", "TO"); wordMap.put("ど", "DO"); wordMap.put("な", "NA"); wordMap.put("に", "NI"); wordMap.put("ぬ", "NU"); wordMap.put("ね", "NE"); wordMap.put("の", "NO"); wordMap.put("は", "HA"); wordMap.put("ば", "BA"); wordMap.put("ぱ", "PA"); wordMap.put("ひ", "HI"); wordMap.put("び", "BI"); wordMap.put("ぴ", "PI"); wordMap.put("ふ", "FU"); wordMap.put("ぶ", "BU"); wordMap.put("ぷ", "PU"); wordMap.put("へ", "HE"); wordMap.put("べ", "BE"); wordMap.put("ぺ", "PE"); wordMap.put("ほ", "HO"); wordMap.put("ぼ", "BO"); wordMap.put("ぽ", "PO"); wordMap.put("ま", "MA"); wordMap.put("み", "MI"); wordMap.put("む", "MU"); wordMap.put("め", "ME"); wordMap.put("も", "MO"); wordMap.put("や", "YA"); wordMap.put("ゆ", "YU"); wordMap.put("よ", "YO"); wordMap.put("ら", "RA"); wordMap.put("り", "RI"); wordMap.put("る", "RU"); wordMap.put("れ", "RE"); wordMap.put("ろ", "RO"); wordMap.put("わ", "WA"); wordMap.put("ゐ", "WI"); wordMap.put("ゑ", "WE"); wordMap.put("を", "WO"); wordMap.put("ん", "N");
        return wordMap;
    }

    private HashMap<String, String> initKatakanaPart1Map() {
        this.setTitle("Katakana");
        HashMap<String, String> wordMap = new HashMap<String, String>();
        wordMap.put("ア", "A"); wordMap.put("イ", "I"); wordMap.put("ウ", "U"); wordMap.put("エ", "E"); wordMap.put("オ", "O"); wordMap.put("カ", "KA"); wordMap.put("キ", "KI"); wordMap.put("ク", "KU"); wordMap.put("ケ", "KE"); wordMap.put("コ", "KO"); wordMap.put("サ", "SA"); wordMap.put("シ", "SI"); wordMap.put("ス", "SU"); wordMap.put("セ", "SE"); wordMap.put("ソ", "SO"); wordMap.put("タ", "TA"); wordMap.put("チ", "CHI"); wordMap.put("ツ", "TSU"); wordMap.put("テ", "TE"); wordMap.put("ト", "TO"); wordMap.put("ナ", "NA"); wordMap.put("ニ", "NI"); wordMap.put("ヌ", "NU"); wordMap.put("ネ", "NE"); wordMap.put("ノ", "NO"); wordMap.put("ハ", "HA"); wordMap.put("ヒ", "HI"); wordMap.put("フ", "FU"); wordMap.put("ヘ", "HE"); wordMap.put("ホ", "HO"); wordMap.put("マ", "MA"); wordMap.put("ミ", "MI"); wordMap.put("ム", "MU"); wordMap.put("メ", "ME"); wordMap.put("モ", "MO"); wordMap.put("ヤ", "YA"); wordMap.put("ユ", "YU"); wordMap.put("ヨ", "YO"); wordMap.put("ラ", "RA"); wordMap.put("リ", "RI"); wordMap.put("ル", "RU"); wordMap.put("レ", "RE"); wordMap.put("ロ", "RO"); wordMap.put("ワ", "WA"); wordMap.put("ヲ", "WO"); wordMap.put("ン", "N");
        return wordMap;
    }

    private HashMap<String, String> initKatakanaAllMap() {
        this.setTitle("Katakana");
        HashMap<String, String> wordMap = new HashMap<String, String>();
        wordMap.put("ア", "A"); wordMap.put("イ", "I"); wordMap.put("ウ", "U"); wordMap.put("エ", "E"); wordMap.put("オ", "O"); wordMap.put("カ", "KA"); wordMap.put("ガ", "GA"); wordMap.put("キ", "KI"); wordMap.put("ギ", "GI"); wordMap.put("ク", "KU"); wordMap.put("グ", "GU"); wordMap.put("ケ", "KE"); wordMap.put("ゲ", "GE"); wordMap.put("コ", "KO"); wordMap.put("ゴ", "GO"); wordMap.put("サ", "SA"); wordMap.put("ザ", "ZA"); wordMap.put("シ", "SI"); wordMap.put("ジ", "ZI"); wordMap.put("ス", "SU"); wordMap.put("ズ", "ZU"); wordMap.put("セ", "SE"); wordMap.put("ゼ", "ZE"); wordMap.put("ソ", "SO"); wordMap.put("ゾ", "ZO"); wordMap.put("タ", "TA"); wordMap.put("ダ", "DA"); wordMap.put("チ", "CHI"); wordMap.put("ヂ", "DI"); wordMap.put("ツ", "TSU"); wordMap.put("ヅ", "DU"); wordMap.put("テ", "TE"); wordMap.put("デ", "DE"); wordMap.put("ト", "TO"); wordMap.put("ド", "DO"); wordMap.put("ナ", "NA"); wordMap.put("ニ", "NI"); wordMap.put("ヌ", "NU"); wordMap.put("ネ", "NE"); wordMap.put("ノ", "NO"); wordMap.put("ハ", "HA"); wordMap.put("バ", "BA"); wordMap.put("パ", "PA"); wordMap.put("ヒ", "HI"); wordMap.put("ビ", "BI"); wordMap.put("ピ", "PI"); wordMap.put("フ", "FU"); wordMap.put("ブ", "BU"); wordMap.put("プ", "PU"); wordMap.put("ヘ", "HE"); wordMap.put("ベ", "BE"); wordMap.put("ペ", "PE"); wordMap.put("ホ", "HO"); wordMap.put("ボ", "BO"); wordMap.put("ポ", "PO"); wordMap.put("マ", "MA"); wordMap.put("ミ", "MI"); wordMap.put("ム", "MU"); wordMap.put("メ", "ME"); wordMap.put("モ", "MO"); wordMap.put("ヤ", "YA"); wordMap.put("ユ", "YU"); wordMap.put("ヨ", "YO"); wordMap.put("ラ", "RA"); wordMap.put("リ", "RI"); wordMap.put("ル", "RU"); wordMap.put("レ", "RE"); wordMap.put("ロ", "RO"); wordMap.put("ワ", "WA"); wordMap.put("ヰ", "WI"); wordMap.put("ヱ", "WE"); wordMap.put("ヲ", "WO"); wordMap.put("ン", "N"); wordMap.put("ヴ", "VU");
        return wordMap;
    }
}
