package arnaud.radomlearner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import arnaud.radomlearner.model.DictType.HashMapCallBackInterface;

import arnaud.radomlearner.preference.UserPreferenceItem;

import static arnaud.radomlearner.preference.UserPreferenceItem.PREFERENCE_KEY_DICT_TYPE;

/**
 * Created by arnaud on 2018/03/30.
 */

public class QuizCollectionManager {

    private final UserPreferenceItem<HashSet<String>> currentDictType;
    private HashMap<String, DictType> dictTypeHashMap;

    public boolean isDictTypeSelected(DictType dictType) {
        HashSet<String> hashSet = getUserSelectionHashSet();
        return hashSet.contains(dictType.keyId);
    }

    private static QuizCollectionManager instance;
    public static QuizCollectionManager getInstance() {
        if (instance == null) {
            instance = new QuizCollectionManager();
        }
        return instance;
    }

    private QuizCollectionManager() {
        super();

    // Adjective
        addDictType(new DictType("Adjective", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("ookii", "big, large"); wordMap.put("chiisai", "short"); wordMap.put("takai", "expensive"); wordMap.put("yasui", "cheap"); wordMap.put("yoi", "good"); wordMap.put("warui", "bad"); wordMap.put("se ga takai", "tall"); wordMap.put("se ga hikui", "short"); wordMap.put("utsukushii", "beautiful"); wordMap.put("minikui", "ugly"); wordMap.put("fukai", "deep"); wordMap.put("asai", "shallow"); wordMap.put("hiroi", "wide"); wordMap.put("semai", "narrow"); wordMap.put("atarashii", "new"); wordMap.put("furuii", "old"); wordMap.put("shinsen na", "fresh"); wordMap.put("kusatta", "rotten"); wordMap.put("atsui", "hot"); wordMap.put("samui", "cold (weather)"); wordMap.put("nagai", "long"); wordMap.put("mijikai", "short"); wordMap.put("atsui", "thick"); wordMap.put("usui", "thin"); wordMap.put("omoi", "heavy"); wordMap.put("karui", "light (weight)"); wordMap.put("akarui", "light"); wordMap.put("kurai", "dark"); wordMap.put("katai", "hard"); wordMap.put("yawarakai", "soft"); wordMap.put("kitanai", "dirty"); wordMap.put("kirei na", "clean"); wordMap.put("chikai", "near"); wordMap.put("tooi", "far"); wordMap.put("hayai", "fast"); wordMap.put("osoi", "slow"); wordMap.put("tsuyoi", "strong"); wordMap.put("yowai", "weak"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "easy"); wordMap.put("abunai", "dangerous"); wordMap.put("anzenna", "safe"); wordMap.put("kawaita", "dry"); wordMap.put("nureta", "wet"); wordMap.put("tadashii", "correct"); wordMap.put("machigatta", "wrong"); wordMap.put("kantan na", "simple"); wordMap.put("fukuzatsu na", "complicated"); wordMap.put("tashika na", "sure"); wordMap.put("hontou no", "TRUE"); wordMap.put("usoano", "FALSE"); wordMap.put("saisho no", "first"); wordMap.put("saigo no", "last"); wordMap.put("tsugi no", "next"); wordMap.put("zenbu no", "all"); wordMap.put("maimai", "each time"); wordMap.put("yaku ni tatsu", "usefull"); wordMap.put("shizuka na", "quite"); wordMap.put("urusai", "noisy"); wordMap.put("meiwaku na", "troublesome"); wordMap.put("juubun na", "enough"); wordMap.put("tarinai", "lacking"); wordMap.put("ippai no", "full"); wordMap.put("isogashii", "busy"); wordMap.put("tadano", "free"); wordMap.put("sukoshi no", "a little bit"); wordMap.put("takusan no", "plenty"); wordMap.put("okane-mochi no", "rich"); wordMap.put("binboo na", "poor"); wordMap.put("onaka ga suita", "hungry"); wordMap.put("nodo ga kawaita", "thirsty"); wordMap.put("iya na", "nasty"); wordMap.put("daiji na", "important"); wordMap.put("marui", "round"); wordMap.put("nioi ga yoi", "a good smell"); wordMap.put("kusai", "bad odor"); wordMap.put("aoi", "blue"); wordMap.put("akai", "red"); wordMap.put("akarui", "light, bright"); wordMap.put("atatakai", "warm"); wordMap.put("atarashii", "new"); wordMap.put("atsui", "hot (air)"); wordMap.put("atsui", "thick"); wordMap.put("abunai", "dangerous"); wordMap.put("amai", "sweet"); wordMap.put("ii", "good"); wordMap.put("isogashii", "to be busy"); wordMap.put("itai", "to be painful"); wordMap.put("usui", "thin"); wordMap.put("oishii", "tasty, delicious"); wordMap.put("ookii", "big"); wordMap.put("osoi", "late, slow"); wordMap.put("omoi", "heavy"); wordMap.put("omoshiroi", "intersting, funny"); wordMap.put("karai", "hot, spicy"); wordMap.put("karui", "light (not heavy)"); wordMap.put("kawaii", "cute, pretty"); wordMap.put("kiiroi", "yellow"); wordMap.put("kitanai", "dirty"); wordMap.put("kurai", "dark"); wordMap.put("kuroi", "black"); wordMap.put("samui", "cold"); wordMap.put("shiroi", "white"); wordMap.put("suzushii", "cool"); wordMap.put("semai", "narrow"); wordMap.put("takai", "high, expensive"); wordMap.put("tanoshii", "pleasant, enjoyable"); wordMap.put("chiisai", "small"); wordMap.put("chikai", "near, close"); wordMap.put("tsumaranai", "uninteresting"); wordMap.put("tsumetai", "cold"); wordMap.put("tsuyoi", "strong"); wordMap.put("tooi", "far"); wordMap.put("nagai", "long"); wordMap.put("hayai", "early"); wordMap.put("hayai", "fast, quick"); wordMap.put("hikui", "low"); wordMap.put("hiroi", "wide, spacious"); wordMap.put("futoi", "thick, fat"); wordMap.put("furui", "old"); wordMap.put("hoshii", "to want something"); wordMap.put("hosoi", "thin, fine"); wordMap.put("mazui", "bad tasting"); wordMap.put("marui", "round"); wordMap.put("mijikai", "short"); wordMap.put("muzukashii", "difficult"); wordMap.put("yasashii", "gentle"); wordMap.put("yasui", "cheap"); wordMap.put("wakai", "young");
                return wordMap;
            }
        }));

        addDictType(new DictType("Verb", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("hairu", "enter"); wordMap.put("hashiru", "run"); wordMap.put("heru", "decrease, reduce"); wordMap.put("iru", "need"); wordMap.put("kaeru", "return"); wordMap.put("keru", "kick"); wordMap.put("kiru", "cut"); wordMap.put("shiru", "know"); wordMap.put("ageru", "give"); wordMap.put("akeru", "open"); wordMap.put("arau", "wash"); wordMap.put("aru", "have, exist"); wordMap.put("aruku", "walk"); wordMap.put("asobu", "play"); wordMap.put("dasu", "extract, take out"); wordMap.put("dekiru", "be able"); wordMap.put("deru", "go out"); wordMap.put("fueru", "increase"); wordMap.put("fureru", "touch"); wordMap.put("hajimaru", "begin"); wordMap.put("hajimeru", "begin"); wordMap.put("hanasu", "speak"); wordMap.put("harau", "pay"); wordMap.put("hataraku", "work"); wordMap.put("hiku", "pull"); wordMap.put("homeru", "praise"); wordMap.put("horeru", "fall in love"); wordMap.put("horu", "dig"); wordMap.put("hoshigaru", "want, desire"); wordMap.put("ikiru", "be alive"); wordMap.put("iku", "go"); wordMap.put("iru", "be (location living things)"); wordMap.put("isogu", "hurry"); wordMap.put("iu", "say"); wordMap.put("iwau", "celebrate, congratulate"); wordMap.put("kaeru", "change"); wordMap.put("kaku", "write"); wordMap.put("kamu", "bite"); wordMap.put("kangaeru", "think"); wordMap.put("kariru", "borrow, rent"); wordMap.put("katsu", "win"); wordMap.put("kau", "buy"); wordMap.put("kawakasu", "dry"); wordMap.put("kazoeru", "count"); wordMap.put("kiku", "listen"); wordMap.put("kinjiru", "ban, prohibit"); wordMap.put("kiru", "wear"); wordMap.put("komaru", "be in trouble"); wordMap.put("konomu", "like"); wordMap.put("korobu", "fall down"); wordMap.put("korosu", "kill"); wordMap.put("kotaeru", "answer"); wordMap.put("kowagaru", "fear"); wordMap.put("kowareru", "break"); wordMap.put("kowasu", "smash"); wordMap.put("kuraberu", "compare"); wordMap.put("kurasu", "live"); wordMap.put("kuru", "come"); wordMap.put("mamoru", "protect"); wordMap.put("manabu", "learn, study"); wordMap.put("matsu", "wait"); wordMap.put("miru", "look"); wordMap.put("miseru", "show"); wordMap.put("mitsukeru", "find"); wordMap.put("modoru", "return, go back"); wordMap.put("morau", "receive, get"); wordMap.put("motsu", "have, hold, own"); wordMap.put("nagameru", "watch, view"); wordMap.put("naguru", "punch, hit"); wordMap.put("naku", "cry"); wordMap.put("namakeru", "be lazy"); wordMap.put("naosu", "repair, cure"); wordMap.put("narau", "learn"); wordMap.put("naru", "become"); wordMap.put("nemuru", "fall asleep"); wordMap.put("noboru", "climb"); wordMap.put("nomu", "drink"); wordMap.put("nuru", "paint"); wordMap.put("nusumu", "steal"); wordMap.put("oboeru", "remember, learn"); wordMap.put("odoru", "dance"); wordMap.put("okiru", "get up"); wordMap.put("oku", "put, place"); wordMap.put("okureru", "be late, lag behind"); wordMap.put("omou", "think"); wordMap.put("osou", "attack"); wordMap.put("osu", "push, press"); wordMap.put("owaru", "end"); wordMap.put("oyogu", "swim"); wordMap.put("sagasu", "search for"); wordMap.put("sakeru", "avoid, dodge"); wordMap.put("saku", "bloom"); wordMap.put("shimeru", "shut"); wordMap.put("shinu", "die"); wordMap.put("sumu", "live, reside"); wordMap.put("suru", "do, make"); wordMap.put("taberu", "eat"); wordMap.put("tameru", "save, store, accumulate"); wordMap.put("tamesu", "test, try, taste"); wordMap.put("tanoshimu", "enjoy, have fun"); wordMap.put("tasu", "add"); wordMap.put("tasukeru", "help"); wordMap.put("tatamu", "fold"); wordMap.put("tateru", "build"); wordMap.put("tatsu", "stand up"); wordMap.put("tetsudau", "help"); wordMap.put("tobu", "jump, fly"); wordMap.put("toku", "solve, untie"); wordMap.put("tomaru", "stop"); wordMap.put("torikaeru", "exchange"); wordMap.put("tsukareru", "get tired"); wordMap.put("tsukau", "use, operate"); wordMap.put("tsuku", "arrive"); wordMap.put("tsureru", "lead"); wordMap.put("tsuru", "fish"); wordMap.put("ueru", "plant"); wordMap.put("ugoku", "move"); wordMap.put("umareru", "be born"); wordMap.put("uru", "sell"); wordMap.put("utagau", "doubt, suspect"); wordMap.put("utau", "sing"); wordMap.put("wakaru", "understand"); wordMap.put("warau", "laugh"); wordMap.put("wasureru", "forget"); wordMap.put("yakeru", "burn"); wordMap.put("yaku", "bake, grill"); wordMap.put("yobu", "call"); wordMap.put("yomu", "read");
                return wordMap;
            }
        }));

        addDictType(new DictType("Hiragana", "Basic", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("あ", "A"); wordMap.put("い", "I"); wordMap.put("う", "U"); wordMap.put("え", "E"); wordMap.put("お", "O"); wordMap.put("か", "KA"); wordMap.put("き", "KI"); wordMap.put("く", "KU"); wordMap.put("け", "KE"); wordMap.put("こ", "KO"); wordMap.put("さ", "SA"); wordMap.put("し", "SHI"); wordMap.put("す", "SU"); wordMap.put("せ", "SE"); wordMap.put("そ", "SO"); wordMap.put("た", "TA"); wordMap.put("ち", "CHI"); wordMap.put("つ", "TSU"); wordMap.put("て", "TE"); wordMap.put("と", "TO"); wordMap.put("な", "NA"); wordMap.put("に", "NI"); wordMap.put("ぬ", "NU"); wordMap.put("ね", "NE"); wordMap.put("の", "NO"); wordMap.put("は", "HA"); wordMap.put("ひ", "HI"); wordMap.put("ふ", "FU"); wordMap.put("へ", "HE"); wordMap.put("ほ", "HO"); wordMap.put("ま", "MA"); wordMap.put("み", "MI"); wordMap.put("む", "MU"); wordMap.put("め", "ME"); wordMap.put("も", "MO"); wordMap.put("や", "YA"); wordMap.put("ゆ", "YU"); wordMap.put("よ", "YO"); wordMap.put("ら", "RA"); wordMap.put("り", "RI"); wordMap.put("る", "RU"); wordMap.put("れ", "RE"); wordMap.put("ろ", "RO"); wordMap.put("わ", "WA"); wordMap.put("を", "WO"); wordMap.put("ん", "N");
                return wordMap;
            }
        }));

        addDictType(new DictType("Hiragana", "Combination", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("きゃ", "KYA"); wordMap.put("きゅ", "KYU"); wordMap.put("きょ", "KYO"); wordMap.put("しゃ", "SHA"); wordMap.put("しゅ", "SHU"); wordMap.put("しょ", "SHO"); wordMap.put("ちゃ", "CHA"); wordMap.put("ちゅ", "CHU"); wordMap.put("ちょ", "CHO"); wordMap.put("にゃ", "NYA"); wordMap.put("にゅ", "NYU"); wordMap.put("にょ", "NYO"); wordMap.put("ひゃ", "HYA"); wordMap.put("ひゅ", "HYU"); wordMap.put("ひょ", "HYO"); wordMap.put("みゃ", "MYA"); wordMap.put("みゅ", "MYU"); wordMap.put("みょ", "MYO"); wordMap.put("りゃ", "RYA"); wordMap.put("りゅ", "RYU"); wordMap.put("りょ", "RYO"); wordMap.put("ぎゃ", "GYA"); wordMap.put("ぎゅ", "GYU"); wordMap.put("ぎょ", "GYO"); wordMap.put("じゃ", "JA"); wordMap.put("じゅ", "JU"); wordMap.put("じょ", "JO"); wordMap.put("びゃ", "BYA"); wordMap.put("びゅ", "BYU"); wordMap.put("びょ", "BYO"); wordMap.put("ぴゃ", "PYA"); wordMap.put("ぴゅ", "PYU"); wordMap.put("ぴょ", "PYO");
                return wordMap;
            }
        }));

        addDictType(new DictType("Hiragana", "Accent", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("が", "GA"); wordMap.put("ぎ", "GI"); wordMap.put("ぐ", "GU"); wordMap.put("げ", "GE"); wordMap.put("ご", "GO"); wordMap.put("ざ", "ZA"); wordMap.put("じ", "JI"); wordMap.put("ず", "ZU"); wordMap.put("ぜ", "ZE"); wordMap.put("ぞ", "ZO"); wordMap.put("だ", "DA"); wordMap.put("ぢ", "DI"); wordMap.put("づ", "DU"); wordMap.put("で", "DE"); wordMap.put("ど", "DO"); wordMap.put("ば", "BA"); wordMap.put("ぱ", "PA"); wordMap.put("び", "BI"); wordMap.put("ぴ", "PI"); wordMap.put("ぶ", "BU"); wordMap.put("ぷ", "PU"); wordMap.put("べ", "BE"); wordMap.put("ぺ", "PE"); wordMap.put("ぼ", "BO"); wordMap.put("ぽ", "PO"); wordMap.put("ゐ", "WI"); wordMap.put("ゑ", "WE");
                return wordMap;
            }
        }));

        addDictType(new DictType("Katana", "Base", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("ア", "A"); wordMap.put("イ", "I"); wordMap.put("ウ", "U"); wordMap.put("エ", "E"); wordMap.put("オ", "O"); wordMap.put("カ", "KA"); wordMap.put("キ", "KI"); wordMap.put("ク", "KU"); wordMap.put("ケ", "KE"); wordMap.put("コ", "KO"); wordMap.put("サ", "SA"); wordMap.put("シ", "SI"); wordMap.put("ス", "SU"); wordMap.put("セ", "SE"); wordMap.put("ソ", "SO"); wordMap.put("タ", "TA"); wordMap.put("チ", "CHI"); wordMap.put("ツ", "TSU"); wordMap.put("テ", "TE"); wordMap.put("ト", "TO"); wordMap.put("ナ", "NA"); wordMap.put("ニ", "NI"); wordMap.put("ヌ", "NU"); wordMap.put("ネ", "NE"); wordMap.put("ノ", "NO"); wordMap.put("ハ", "HA"); wordMap.put("ヒ", "HI"); wordMap.put("フ", "FU"); wordMap.put("ヘ", "HE"); wordMap.put("ホ", "HO"); wordMap.put("マ", "MA"); wordMap.put("ミ", "MI"); wordMap.put("ム", "MU"); wordMap.put("メ", "ME"); wordMap.put("モ", "MO"); wordMap.put("ヤ", "YA"); wordMap.put("ユ", "YU"); wordMap.put("ヨ", "YO"); wordMap.put("ラ", "RA"); wordMap.put("リ", "RI"); wordMap.put("ル", "RU"); wordMap.put("レ", "RE"); wordMap.put("ロ", "RO"); wordMap.put("ワ", "WA"); wordMap.put("ヲ", "WO"); wordMap.put("ン", "N");
                return wordMap;
            }
        }));

        addDictType(new DictType("Katana", "Accent", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("ガ", "GA"); wordMap.put("ギ", "GI"); wordMap.put("グ", "GU"); wordMap.put("ゲ", "GE"); wordMap.put("ゴ", "GO"); wordMap.put("ザ", "ZA"); wordMap.put("ジ", "JI"); wordMap.put("ズ", "ZU"); wordMap.put("ゼ", "ZE"); wordMap.put("ゾ", "ZO"); wordMap.put("ダ", "DA"); wordMap.put("ヂ", "DI"); wordMap.put("ヅ", "DU"); wordMap.put("デ", "DE"); wordMap.put("ド", "DO"); wordMap.put("バ", "BA"); wordMap.put("パ", "PA"); wordMap.put("ビ", "BI"); wordMap.put("ピ", "PI"); wordMap.put("ブ", "BU"); wordMap.put("プ", "PU"); wordMap.put("ベ", "BE"); wordMap.put("ペ", "PE"); wordMap.put("ボ", "BO"); wordMap.put("ポ", "PO"); wordMap.put("ヴ", "VU");
                return wordMap;
            }
        }));

        addDictType(new DictType("Katana", "Combination", new HashMapCallBackInterface() {
            @Override
            public HashMap<String, String> getCollectionMap() {
                HashMap<String, String> wordMap = new HashMap<String, String>();
                wordMap.put("キャ", "KYA"); wordMap.put("キュ", "KYU"); wordMap.put("キョ", "KYO"); wordMap.put("シャ", "SHA"); wordMap.put("シュ", "SHU"); wordMap.put("ショ", "SHO"); wordMap.put("チャ", "CHA"); wordMap.put("チュ", "CHU"); wordMap.put("チョ", "CHO"); wordMap.put("ニャ", "NYA"); wordMap.put("ニュ", "NYU"); wordMap.put("ニョ", "NYO"); wordMap.put("ヒャ", "HYA"); wordMap.put("ヒュ", "HYU"); wordMap.put("ヒョ", "HYO"); wordMap.put("ミャ", "MYA"); wordMap.put("ミュ", "MYU"); wordMap.put("ミョ", "MYO"); wordMap.put("リャ", "RYA"); wordMap.put("リュ", "RYU"); wordMap.put("リョ", "RYO"); wordMap.put("ギャ", "GYA"); wordMap.put("ギュ", "GYU"); wordMap.put("ギョ", "GYO"); wordMap.put("ジャ", "JA"); wordMap.put("ジュ", "JU"); wordMap.put("ジョ", "JO"); wordMap.put("ビャ", "BYA"); wordMap.put("ビュ", "BYU"); wordMap.put("ビョ", "BYO"); wordMap.put("ピャ", "PYA"); wordMap.put("ピュ", "PYU"); wordMap.put("ピョ", "PYO");
                return wordMap;
            }
        }));

        currentDictType = new UserPreferenceItem(PREFERENCE_KEY_DICT_TYPE, getDefaultValue());
    }



    private void addDictType(DictType dictType) {
        if (dictTypeHashMap == null) {
            dictTypeHashMap = new HashMap<>();
        }
        dictTypeHashMap.put(dictType.keyId, dictType);
    }

    private HashSet<String> getDefaultValue() {
        String firstKey = dictTypeHashMap.keySet().iterator().next();
        HashSet<String> keyArray = new HashSet<>(); keyArray.add(firstKey);
        return keyArray;
    }

    private HashSet<String> getUserSelectionHashSet() {
        HashSet<String> hashSet = null;
        try { hashSet = currentDictType.getValue(); }
        catch (Exception e) { }
        if (hashSet == null) {
            hashSet = getDefaultValue();
        }
        return hashSet;
    }

    public void toggleSelectionDictType(DictType dictType) {
        HashSet<String> hashSet = getUserSelectionHashSet();
        String key = dictType.keyId;
        if (hashSet.contains(key)) {
            hashSet.remove(key);
        } else {
            hashSet.add(key);
        }
        currentDictType.setValue(hashSet);
    }

    public HashMap<String, String> getCurrentCollection() {
        HashSet<String> hashSet = getUserSelectionHashSet();
        HashMap<String, String> collection = new HashMap<>();
        for (String key : hashSet) {
            DictType dictType = dictTypeHashMap.get(key);
            if (dictType != null) {
                collection.putAll(dictType.hashMapCallBack.getCollectionMap());
            }
        }
        return collection;
    }

    public String generateSubTitle() {
        String title = "";
        HashSet<String> hashSet = getUserSelectionHashSet();
        for (String key : hashSet) {
            DictType dictType = dictTypeHashMap.get(key);
            if (dictType != null) {
                title = title.length() == 0 ? dictType.getFullTitle() : title + " + " + dictType.getFullTitle();
            }
        }
        return title;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region DICT ARRAY

    public ArrayList<DictType> getDictTypeArray() {
        ArrayList<DictType> array = new ArrayList<DictType>();
        for (DictType dictType : dictTypeHashMap.values()) {
            array.add(dictType);
        }
        Collections.sort(array);
        return array;
    }

// endregion

}
