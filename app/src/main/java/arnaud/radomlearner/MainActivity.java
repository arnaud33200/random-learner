package arnaud.radomlearner;

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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GuessWordView topGuessWordView;
    private GuessWordView bottomGuessWordView;
    private HashMap<String, String> wordMap;
    private Button nextButton;
    private RelativeLayout guessLayout;
    private TextView questionTextView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initAdjectiveDict();

        topGuessWordView = new GuessWordView(findViewById(R.id.top_view_guess_word));
        topGuessWordView.setColorMode();
        bottomGuessWordView = new GuessWordView(findViewById(R.id.bottom_view_guess_word));

        guessLayout = findViewById(R.id.guess_layout);
        questionTextView = findViewById(R.id.middle_question_textview);

        RelativeLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButtonClickAction();
            }
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButtonClickAction();
            }
        });
        initTopAndBottom();
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

        if (item.getItemId() == R.id.adjective_menu) {
            initAdjectiveDict();
        }
        else if (item.getItemId() == R.id.verb_menu) {
            initVerbMap();
        }
        else if (item.getItemId() == R.id.hiragana_menu) {
            initHiraganaMap();
        }
        else {
            return false;
        }
        initTopAndBottom();
        return true;
    }

    private void initTopAndBottom() {
        topGuessWordView.setTextAndDisplayMode("", false);
        bottomGuessWordView.setTextAndDisplayMode("", false);
        nextButtonClickAction();
    }

    private void nextButtonClickAction() {

        Boolean revealAction = true;
        if (topGuessWordView.getHideMode()) {
            topGuessWordView.textViewClickAction();
        }
        else if (bottomGuessWordView.getHideMode()) {
            bottomGuessWordView.textViewClickAction();
        }
        else {
            revealAction = false;
        }

        if (revealAction) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                updateMiddleGuestLayout();
            }
            return;
        }

        final int min = 0;
        final int max = wordMap.size()-1;
        final int randomIndex = new Random().nextInt((max - min) + 1) + min;
        final int randomBool = new Random().nextInt(2);

        String adj = (String) wordMap.keySet().toArray()[randomIndex];
        String translation = wordMap.get(adj);

        topGuessWordView.setTextAndDisplayMode(adj, randomBool == 1);
        bottomGuessWordView.setTextAndDisplayMode(translation, randomBool == 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            updateMiddleGuestLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateMiddleGuestLayout() {
        guessLayout.setVisibility(View.VISIBLE);
        if (topGuessWordView.getHideMode()) {
            questionTextView.setTextColor(RandomLearnerApp.getContext().getColor(R.color.black));
            guessLayout.setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.white_circle));
        }
        else if (bottomGuessWordView.getHideMode()) {
            questionTextView.setTextColor(RandomLearnerApp.getContext().getColor(R.color.white));
            guessLayout.setBackground(RandomLearnerApp.getContext().getDrawable(R.drawable.color_circle));
        }
        else {
            guessLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void initAdjectiveDict() {
        wordMap = new HashMap<String, String>();
        wordMap.put("ookii", "big, large"); wordMap.put("chiisai", "short"); wordMap.put("takai", "expensive"); wordMap.put("yasui", "cheap"); wordMap.put("yoi", "good"); wordMap.put("warui", "bad"); wordMap.put("se ga takai", "tall"); wordMap.put("se ga hikui", "short"); wordMap.put("utsukushii", "beautiful"); wordMap.put("minikui", "ugly"); wordMap.put("fukai", "deep"); wordMap.put("asai", "shallow"); wordMap.put("hiroi", "wide"); wordMap.put("semai", "narrow"); wordMap.put("atarashii", "new"); wordMap.put("furuii", "old"); wordMap.put("shinsen na", "fresh"); wordMap.put("kusatta", "rotten"); wordMap.put("atsui", "hot"); wordMap.put("samui", "cold (weather)"); wordMap.put("nagai", "long"); wordMap.put("mijikai", "short"); wordMap.put("atsui", "thick"); wordMap.put("usui", "thin"); wordMap.put("omoi", "heavy"); wordMap.put("karui", "light (weight)"); wordMap.put("akarui", "light"); wordMap.put("kurai", "dark"); wordMap.put("katai", "hard"); wordMap.put("yawarakai", "soft"); wordMap.put("kitanai", "dirty"); wordMap.put("kirei na", "clean"); wordMap.put("chikai", "near"); wordMap.put("tooi", "far"); wordMap.put("hayai", "fast"); wordMap.put("osoi", "slow"); wordMap.put("tsuyoi", "strong"); wordMap.put("yowai", "weak"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "easy"); wordMap.put("abunai", "dangerous"); wordMap.put("anzenna", "safe"); wordMap.put("kawaita", "dry"); wordMap.put("nureta", "wet"); wordMap.put("tadashii", "correct"); wordMap.put("machigatta", "wrong"); wordMap.put("kantan na", "simple"); wordMap.put("fukuzatsu na", "complicated"); wordMap.put("tashika na", "sure"); wordMap.put("hontou no", "TRUE"); wordMap.put("usoano", "FALSE"); wordMap.put("saisho no", "first"); wordMap.put("saigo no", "last"); wordMap.put("tsugi no", "next"); wordMap.put("zenbu no", "all"); wordMap.put("maimai", "each time"); wordMap.put("yaku ni tatsu", "usefull"); wordMap.put("shizuka na", "quite"); wordMap.put("urusai", "noisy"); wordMap.put("meiwaku na", "troublesome"); wordMap.put("juubun na", "enough"); wordMap.put("tarinai", "lacking"); wordMap.put("ippai no", "full"); wordMap.put("isogashii", "busy"); wordMap.put("tadano", "free"); wordMap.put("sukoshi no", "a little bit"); wordMap.put("takusan no", "plenty"); wordMap.put("okane-mochi no", "rich"); wordMap.put("binboo na", "poor"); wordMap.put("onaka ga suita", "hungry"); wordMap.put("nodo ga kawaita", "thirsty"); wordMap.put("iya na", "nasty"); wordMap.put("daiji na", "important"); wordMap.put("marui", "round"); wordMap.put("nioi ga yoi", "a good smell"); wordMap.put("kusai", "bad odor"); wordMap.put("aoi", "blue"); wordMap.put("akai", "red"); wordMap.put("akarui", "light, bright"); wordMap.put("atatakai", "warm"); wordMap.put("atarashii", "new"); wordMap.put("atsui", "hot (air)"); wordMap.put("atsui", "thick"); wordMap.put("abunai", "dangerous"); wordMap.put("amai", "sweet"); wordMap.put("ii", "good"); wordMap.put("isogashii", "to be busy"); wordMap.put("itai", "to be painful"); wordMap.put("usui", "thin"); wordMap.put("oishii", "tasty, delicious"); wordMap.put("ookii", "big"); wordMap.put("osoi", "late, slow"); wordMap.put("omoi", "heavy"); wordMap.put("omoshiroi", "intersting, funny"); wordMap.put("karai", "hot, spicy"); wordMap.put("karui", "light (not heavy)"); wordMap.put("kawaii", "cute, pretty"); wordMap.put("kiiroi", "yellow"); wordMap.put("kitanai", "dirty"); wordMap.put("kurai", "dark"); wordMap.put("kuroi", "black"); wordMap.put("samui", "cold"); wordMap.put("shiroi", "white"); wordMap.put("suzushii", "cool"); wordMap.put("semai", "narrow"); wordMap.put("takai", "high, expensive"); wordMap.put("tanoshii", "pleasant, enjoyable"); wordMap.put("chiisai", "small"); wordMap.put("chikai", "near, close"); wordMap.put("tsumaranai", "uninteresting"); wordMap.put("tsumetai", "cold"); wordMap.put("tsuyoi", "strong"); wordMap.put("tooi", "far"); wordMap.put("nagai", "long"); wordMap.put("hayai", "early"); wordMap.put("hayai", "fast, quick"); wordMap.put("hikui", "low"); wordMap.put("hiroi", "wide, spacious"); wordMap.put("futoi", "thick, fat"); wordMap.put("furui", "old"); wordMap.put("hoshii", "to want something"); wordMap.put("hosoi", "thin, fine"); wordMap.put("mazui", "bad tasting"); wordMap.put("marui", "round"); wordMap.put("mijikai", "short"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "gentle"); wordMap.put("yasui", "cheap"); wordMap.put("wakai", "young");
    }

    private void initVerbMap() {
        wordMap = new HashMap<String, String>();
        wordMap.put("hairu", "enter"); wordMap.put("hashiru", "run"); wordMap.put("heru", "decrease, reduce"); wordMap.put("iru", "need"); wordMap.put("kaeru", "return"); wordMap.put("keru", "kick"); wordMap.put("kiru", "cut"); wordMap.put("shiru", "know"); wordMap.put("ageru", "give"); wordMap.put("akeru", "open"); wordMap.put("arau", "wash"); wordMap.put("aru", "have, exist"); wordMap.put("aruku", "walk"); wordMap.put("asobu", "play"); wordMap.put("dasu", "extract, take out"); wordMap.put("dekiru", "be able"); wordMap.put("deru", "go out"); wordMap.put("fueru", "increase"); wordMap.put("fureru", "touch"); wordMap.put("hajimaru", "begin"); wordMap.put("hajimeru", "begin"); wordMap.put("hanasu", "speak"); wordMap.put("harau", "pay"); wordMap.put("hataraku", "work"); wordMap.put("hiku", "pull"); wordMap.put("homeru", "praise"); wordMap.put("horeru", "fall in love"); wordMap.put("horu", "dig"); wordMap.put("hoshigaru", "want, desire"); wordMap.put("ikiru", "be alive"); wordMap.put("iku", "go"); wordMap.put("iru", "be (location living things)"); wordMap.put("isogu", "hurry"); wordMap.put("iu", "say"); wordMap.put("iwau", "celebrate, congratulate"); wordMap.put("kaeru", "change"); wordMap.put("kaku", "write"); wordMap.put("kamu", "bite"); wordMap.put("kangaeru", "think"); wordMap.put("kariru", "borrow, rent"); wordMap.put("katsu", "win"); wordMap.put("kau", "buy"); wordMap.put("kawakasu", "dry"); wordMap.put("kazoeru", "count"); wordMap.put("kiku", "listen"); wordMap.put("kinjiru", "ban, prohibit"); wordMap.put("kiru", "wear"); wordMap.put("komaru", "be in trouble"); wordMap.put("konomu", "like"); wordMap.put("korobu", "fall down"); wordMap.put("korosu", "kill"); wordMap.put("kotaeru", "answer"); wordMap.put("kowagaru", "fear"); wordMap.put("kowareru", "break"); wordMap.put("kowasu", "smash"); wordMap.put("kuraberu", "compare"); wordMap.put("kurasu", "live"); wordMap.put("kuru", "come"); wordMap.put("mamoru", "protect"); wordMap.put("manabu", "learn, study"); wordMap.put("matsu", "wait"); wordMap.put("miru", "look"); wordMap.put("miseru", "show"); wordMap.put("mitsukeru", "find"); wordMap.put("modoru", "return, go back"); wordMap.put("morau", "receive, get"); wordMap.put("motsu", "have, hold, own"); wordMap.put("nagameru", "watch, view"); wordMap.put("naguru", "punch, hit"); wordMap.put("naku", "cry"); wordMap.put("namakeru", "be lazy"); wordMap.put("naosu", "repair, cure"); wordMap.put("narau", "learn"); wordMap.put("naru", "become"); wordMap.put("nemuru", "fall asleep"); wordMap.put("noboru", "climb"); wordMap.put("nomu", "drink"); wordMap.put("nuru", "paint"); wordMap.put("nusumu", "steal"); wordMap.put("oboeru", "remember, learn"); wordMap.put("odoru", "dance"); wordMap.put("okiru", "get up"); wordMap.put("oku", "put, place"); wordMap.put("okureru", "be late, lag behind"); wordMap.put("omou", "think"); wordMap.put("osou", "attack"); wordMap.put("osu", "push, press"); wordMap.put("owaru", "end"); wordMap.put("oyogu", "swim"); wordMap.put("sagasu", "search for"); wordMap.put("sakeru", "avoid, dodge"); wordMap.put("saku", "bloom"); wordMap.put("shimeru", "shut"); wordMap.put("shinu", "die"); wordMap.put("sumu", "live, reside"); wordMap.put("suru", "do, make"); wordMap.put("taberu", "eat"); wordMap.put("tameru", "save, store, accumulate"); wordMap.put("tamesu", "test, try, taste"); wordMap.put("tanoshimu", "enjoy, have fun"); wordMap.put("tasu", "add"); wordMap.put("tasukeru", "help"); wordMap.put("tatamu", "fold"); wordMap.put("tateru", "build"); wordMap.put("tatsu", "stand up"); wordMap.put("tetsudau", "help"); wordMap.put("tobu", "jump, fly"); wordMap.put("toku", "solve, untie"); wordMap.put("tomaru", "stop"); wordMap.put("torikaeru", "exchange"); wordMap.put("tsukareru", "get tired"); wordMap.put("tsukau", "use, operate"); wordMap.put("tsuku", "arrive"); wordMap.put("tsureru", "lead"); wordMap.put("tsuru", "fish"); wordMap.put("ueru", "plant"); wordMap.put("ugoku", "move"); wordMap.put("umareru", "be born"); wordMap.put("uru", "sell"); wordMap.put("utagau", "doubt, suspect"); wordMap.put("utau", "sing"); wordMap.put("wakaru", "understand"); wordMap.put("warau", "laugh"); wordMap.put("wasureru", "forget"); wordMap.put("yakeru", "burn"); wordMap.put("yaku", "bake, grill"); wordMap.put("yobu", "call"); wordMap.put("yomu", "read");
    }

    private void initHiraganaMap() {
        wordMap = new HashMap<String, String>();
        wordMap.put("あ","a"); wordMap.put("い","i"); wordMap.put("う","u"); wordMap.put("え","e"); wordMap.put("お","o"); wordMap.put("か","ka"); wordMap.put("き","ki"); wordMap.put("く","ku"); wordMap.put("け","ke"); wordMap.put("こ","ko"); wordMap.put("た","ta"); wordMap.put("ち","chi"); wordMap.put("つ","tsu"); wordMap.put("て","te"); wordMap.put("と","to"); wordMap.put("さ","sa"); wordMap.put("し","shi"); wordMap.put("す","su"); wordMap.put("せ","se"); wordMap.put("そ","so"); wordMap.put("な","na"); wordMap.put("に","ni"); wordMap.put("ぬ","nu"); wordMap.put("ね","ne"); wordMap.put("の","no"); wordMap.put("は","ha"); wordMap.put("ひ","hi"); wordMap.put("ふ","fu"); wordMap.put("へ","he"); wordMap.put("ほ","ho"); wordMap.put("ま","ma"); wordMap.put("み","mi"); wordMap.put("む","mu"); wordMap.put("め","me"); wordMap.put("も","mo"); wordMap.put("ら","ra"); wordMap.put("り","ri"); wordMap.put("る","ru"); wordMap.put("れ","re"); wordMap.put("ろ","ro"); wordMap.put("や","ya"); wordMap.put("ゆ","yu"); wordMap.put("よ","yo"); wordMap.put("わ","wa"); wordMap.put("を","o (wo)"); wordMap.put("ん","n");
    }

}
