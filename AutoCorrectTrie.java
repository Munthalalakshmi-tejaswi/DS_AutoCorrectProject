import java.util.*;

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean eow;

    public TrieNode() {
        eow = false;
        for (int i = 0; i < 26; i++) {
            children[i] = null;
        }
    }
}

class AutoCorrectTrie {
    TrieNode root;

    AutoCorrectTrie() {
        root = new TrieNode();
    }

    void insert(String word) {
        word = word.toLowerCase().replaceAll("[^a-z]", ""); // keep only a-z
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26) continue; // skip invalid chars
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.eow = true;
    }

    boolean search(String word) {
        word = word.toLowerCase().replaceAll("[^a-z]", "");
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26 || node.children[idx] == null) {
                return false;
            }
            node = node.children[idx];
        }
        return node.eow;
    }

    void collectSuggestions(TrieNode node, String prefix, List<String> suggestions) {
        if (node == null) return;
        if (node.eow) suggestions.add(prefix);
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                collectSuggestions(node.children[i], prefix + (char) (i + 'a'), suggestions);
            }
        }
    }

    List<String> autoCorrect(String word) {
        word = word.toLowerCase().replaceAll("[^a-z]", "");
        TrieNode node = root;
        List<String> suggestions = new ArrayList<>();
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26 || node.children[idx] == null) {
                return suggestions;
            }
            node = node.children[idx];
        }
        collectSuggestions(node, word, suggestions);
        return suggestions;
    }

    public static void main(String[] args) {
        AutoCorrectTrie trie = new AutoCorrectTrie();
        String[] dict = {
            "the","and","to","of","a","in","was","that","he","not","his","her","it","you","had",
            "with","for","she","as","on","is","at","be","him","have","said","from","but","me","my",
            "were","they","all","by","this","would","do","out","up","one","so","an","could","or",
            "been","them","did","we","are","what","there","about","which","their","lets","automatic","classical","pleading","Christianity","lofty","supporting","crawling",
            "storage","Peggy","Sharon","v","tan","streams","doctrine","launch","dash","Leah","harvest",
            "Mme","chased","definition","theories","Travis","threats","describing","engage","enabled",
            "hurting","piercing","stiffened","Neil","commanding","petty","smallest","nothin","institution",
            "crush","reverse","Satan","yanked","strategy","Jacques","accomplish","lt","deceived",
            "smoothed","axe","rub","swaying","punched","poked","marching","network","spoil","git","occurs",
            "Rita","wolves","Meredith","Victorian","fuss","regretted","ample","dangers","cannon",
            "communicate","stockings","expenses","resort","dagger","tests","connections","hedge","scratch",
            "dangling","hostess","Violet","hardened","seventh","Academy","h","existing","railing","enterprise",
            "yon","coincidence","flashlight","justified","fare","refrigerator","assuming","virgin","talents",
            "verses","blamed","illegal","bands","fake","irritation","perception","briskly","stations","skinny",
            "Murphy","cough","instincts","novelist","Alas","boiling","demons","Duchess","Paula","strikes",
            "restore","cancer","protecting","surgery","congregation","Hugo","inspection","someday","Abraham",
            "trunks","federal","Homer","Jersey","rural","cavalry","commenced","expectation","whites","grandson",
            "twentieth","knives","Christine","institutions","concert","fingertips","pierced","Pa","wrap",
            "Sheila","dreaded","induced","survey","imagining","whisky","agitation","annoyance","inward",
            "whatsoever","commitment","recovery","explore","ledge","Mitchell","slap","shadowy","projects",
            "endured","fluttered","Egyptian","suppressed","heaved","procedure","il","react","wax","wears",
            "tongues","retire","Dale","assignment","learnt","contented","blackness","hunters","eagle","sphere",
            "pupils","click","electronic","touches","personnel","advancing","rats","alter","bounced","nicely",
            "Kirk","Harvard","towers","tissue","necklace","logs","transfer","technical","semi","Derek","winced",
            "dimly","indicating","depart","scholars","freed","Conrad","shawl","pleasing","lads","stiffly",
            "gigantic","Hector","Rosie","poetic","Adrian","forgiveness","moist","hoarse","situated","sentimental",
            "creeping","enthusiastic","laundry","deepest","holidays","coward","fits","enclosed","saloon","dialogue",
            "external","advantages","attract","u","Hitler","Louisa","offence","protective","clumsy","Perry",
            "retained","burial","Institute","mat","rented","apples","convincing","prints","relieve","possessions",
            "formidable","Milton","Sebastian","Doug","presumably","challenged","beads","includes","recommended",
            "viewed","composition","overheard","Ohio","desperation","sweating","owing","collecting","uncertainty",
            "Hudson","clan","harmless","wearily","units","cliffs","essentially","packet","admirable","fold",
            "varied","rag","submit","reminds","claws","barked","thirst","reproach","gleamed","climate","legged",
            "resisted","hunted","Jefferson","hearty","ensure","draft","multitude","circular","spectacles","admiring",
            "yielded","thieves","helicopter","Alec","expectations","overnight","Wallace","outstretched","Diane",
            "removing","Kay","gender","neighbour","Ernest","Venice","sends","erected","Turner","Eliot","beams",
            "salvation","attic","surveyed","unfamiliar","sobbed","everyday","seeds","Andrea","bathed","battles",
            "convey","Madison","esteem","adds","crude","imposed","eighteenth","composure","Marion","Natalie","Eliza",
            "hurled","lent","tastes","haze","colony","refusal","formation","hospitality","machinery","weeds","sobs",
            "exhaustion","policemen","pacing","operating","plains","loan","dozens","Jackie","leaping","Lauren",
            "plague","rod","horrified","sob","Evans","sustained","jewelry","coughed","comprehend","Dublin","VII",
            "antique","entertain","Gus","exile","Reed","earthly","listed","convent","drives","satisfactory","gallant",
            "sucking","skies","relate","dumped","index","planes","phase","justify","collapse","periods","factor",
            "mocking","neglect","tourists","matching","folding","boldly","doomed","pigs","tool","exaggerated",
            "assembly","damaged","eagerness","bothering","desirable","supernatural","laden","goddamn","sections",
            "sung","Butler","Byron","Evan","rifles","elephant","frantically","residents","borrow","Constance",
            "Geoffrey","ripe","commands","sinister","apartments","Eugene","producing","mysteries","slippers",
            "springs","stroking","charms","atop","Nicole","plucked","lordship","bis","pro","umbrella","sermon",
            "generosity","instruction","frail","preceding","identification","stride","merchants","ivory","pills",
            "breed","despised","wasting","mustache","Wade","consolation","keeper","muscular","dispute","Romans",
            "fruits","pipes","choosing","tiger","slice","resignation","practices","invasion","industrial","blinking",
            "upwards","fortnight","cable","MS","products","admission","Burke","speeches","lacking","buffalo","dine",
            "customary","tapping","fee","tooth","trains","Ashley","torment","rescued","ambitious","correctly",
            "tortured","chewing","jaws","Finn","humiliation","fer","void","Venus","illuminated","meets","Flora",
            "intellect","cakes","obligation","seize","lamb","balanced","hen","province","hasty","sullen","Ron",
            "hairs","invariably","employ","blades","sewing","mornings","Brandon","armies","goose","discomfort",
            "joking","patches","momentary","implied","calf","terrific","indication","crop","flickered","beamed",
            "lashes","jolly","Mississippi","porter","francs","glaring","thorough","basin","exotic","bathing",
            "decade","verge","rejoined","Nate","sentiments","tire","comedy","actress","briefcase","wi","Asia","bacon",
            "whistled","Donna","puzzle","defiance","models","specifically","technique","stray","Marquis","Dora","na",
            "Samantha","Diego","Hebrew","guarantee","wary","boarding","Melanie","chambers","spark","layer",
            "entertaining","piles","shabby","designs","decline","Scotch","invention","VIII","chuckle","deceased",
            "unnatural","contributed","peak","Randy","hum","limb","punish","pose","faculty","humming","ham","saints",
            "executive","rumors","commonly","Lloyd","Vera","stammered","edited","tavern","est","squinted","detected",
            "Drake","objective","films","screwed","crashing","outcome","Marty","dependent","Holland","float","insight",
            "chickens","proposition","script","inquiries","Chloe","grumbled","blaze","Pennsylvania","notions","Greece",
            "spin","sternly","countless","stables","sturdy","blurred","throbbing","characteristics","crest","hovered",
            "reporters","shotgun","testing","pearls","posted","custody","hurts","fortunes","arise","remarkably",
            "glove","realise","occupy","rags","investigate","shakes","squire","nursery","Celia","frustrated","dove",
            "critic","rosy","Hester","pictured","instances","rebellion","confirm","unusually","rape","whistling",
            "Kansas","pickup","Grandpa","radiant","posts","Shelley","napkin","filed","Ellis","Giles","heavenly",
            "conveyed","triumphant","snakes","ISBN","chewed","worries","frontier","matches","shrieked","sandwiches",
            "bonds","grabbing","heal","sequence","bows","Paradise","remembers","grove","reasoning","bruised",
            "steaming","regions","Monica","stacked","knuckles","mound","web","flickering","detached","transparent",
            "honored","baked","disappearance","allows","muttering","converted","whore","incredibly","mines","kettle",
            "trailer","wardrobe","bolted","delayed","Bella","inferior","roofs","starving","escorted","p.m.","trailing",
            "Bailey","murders","via","Elaine","console","compartment","homosexual","singer","nuclear","choking",
            "Richmond","guts","reverence","stages","dazed","cans","cathedral","qui","enable","signature","sub",
            "travels","dresser","consulted","complaining","civilized","crap","encouraging","dizzy","improvement",
            "Vietnam","Dolly","remorse","academic","bonnet","festival","awkwardly","morality","tasks","straining",
            "riders","Easter","mildly","intensely","Tina","bunk","dreary","inevitably","reasonably","worldly",
            "phenomenon","acceptable","developing","Penguin","disappearing","barking","groom","terrifying","philosopher",
            "garment","heroine","rely","presume","uniforms","hovering","efficient","vampire","Teresa","praised","stays",
            "reflecting","detectives","safer","gambling","Ella","practiced","equivalent","orderly","emerge","sights",
            "perpetual","surge","ecstasy","distressed","Saxon","mamma","bicycle","timid","brute","flour","toss","abrupt",
            "interval","Wright","captive","option","dating","curly","impressions","draws","tents","freezing","tested",
            "lesser","Stephanie","radical","bloom","attitudes","embarrassing","obedience","si","steward","withdraw",
            "deserves","impress","poised","amiable","vigorous","hanged","wink","choices","pub","objected","Dana",
            "doubled","treasures","namely","miniature","extend","observations","armchair","specially","rusty","inquire",
            "rattle","conspiracy","audible","pious","dealer","pump","campus","scrap","injustice","dignified","gasping",
            "hopeful","complaints","Dallas","cycle","ot","olive","pinched","interrupt","extending","fundamental",
            "regards","suggestions","Jared","chips","pairs","Tracy","taut","horseback","conclusions","serpent","toys",
            "strongest","reckless","Providence","peoples","guidance","forgiven","descend","howling","helplessly","CIA",
            "imitation","reminding","cupped","intercourse","legends","scowled","lone","notwithstanding","Columbia",
            "Julius","fascination","flee","Betsy","inheritance","barren","legitimate","horrid","disorder","fore",
            "conceive","jammed","hunched","Gwen","adjoining","proclaimed","candidate","nap","sly","representation",
            "underwear","Jose","errand","infinitely","mature","summit","freshly","Nan","moan","fewer","arrogant","buddy",
            "fortress","enters","speculation","cradle","regardless","spaces","stung","sweetly","Abbey","liar","desolate",
            "heed","employer","Manhattan","deemed","fade","swelling","Fiona","villagers","animated","gin","salute",
            "trading","genuinely","shares","mute","devices","parlour","pausing","hates","involving","assumption","stamp",
            "Jr.","strokes","Mercedes","Li","flared","coolly","disliked","stem","Timothy","reflections","glistening",
            "bearded","sincerity","afforded","into","like","if","bond", "inform", "N.", "narrator", "tap", "insane", "Buck", "ignoring", "hurriedly", "les", 
    "Keith", "borrowed", "girlfriend", "peering", "hire", "cheerfully", "crimson", "poets", "pad", "resemblance",
    "shifting", "backwards", "traced", "kicking", "outfit", "Felix", "exists", "sailors", "measured", "odds",
    "madam", "mayor", "blushed", "Negro", "deeds", "Karl", "notebook", "painfully", "irony", "dearest",
    "grows", "concentration", "coldly", "requested", "Austin", "loneliness", "mechanical", "darted", "Jerusalem", "shaft",
    "solitude", "neglected", "yelling", "obeyed", "guarded", "moaned", "cunning", "cousins", "flush", "condemned",
    "und", "vol", "copper", "outward", "license", "examining", "Canadian", "conversations", "hitting", "nuts",
    "disguise", "Moore", "aircraft", "elected", "warrant", "bullets", "Donald", "anyhow", "boards", "reserve",
    "commission", "temples", "invented", "dislike", "transformed", "Gary", "sickness", "Campbell", "disbelief", "drift",
    "pop", "Kennedy", "temptation", "penny", "da", "booth", "punch", "tub", "folly", "patrol",
    "scanned", "perfection", "paths", "stubborn", "standards", "closest", "Caesar", "beef", "owners", "conceived",
    "farmers", "application", "artists", "episode", "whence", "Englishman", "costume", "Orleans", "rival", "affect",
    "realm", "perfume", "echo", "smells", "vengeance", "chains", "resolve", "behave", "reign", "VI",
    "prime", "increasingly", "pan", "session", "Ivan", "disturbing", "appearing", "Frederick", "Carrie", "misfortune",
    "widely", "bald", "independence", "discourse", "Co.", "Gerald", "begging", "twist", "attraction", "refer",
    "Admiral", "fiery", "Lou", "probable", "breaks", "ninety", "wrists", "gravity", "achieve", "Navy",
    "Arnold", "Grandma", "Stanley", "rabbit", "resentment", "happier", "strictly", "Raymond", "alcohol", "regiment",
    "lust", "hood", "shelves", "fuel", "coin", "occupation", "spat", "stack", "measures", "jewels",
    "suite", "snorted", "hearth", "softened", "parallel", "pleasures", "feeble", "underground", "shine", "rug",
    "Rosa", "twisting", "Logan", "declare", "Noah", "Vincent", "assurance", "Hal", "j", "Atlantic",
    "artistic", "Seth", "cocked", "fascinated", "erect", "shorts", "sparkling", "Countess", "dwelling", "registered",
    "socks", "languages", "awareness", "Frances", "stricken", "wrath", "renewed", "helmet", "ankle", "destination",
    "mug", "medium", "Quinn", "edged", "apologize", "core", "tomb", "motives", "Tyler", "era",
    "painter", "topic", "rocking", "casting", "purely", "vulnerable", "ashore", "snap", "scarce", "bade",
    "preparation", "spilled", "coarse", "momentarily", "primary", "Megan", "Antonio", "gleam", "childish", "ache",
    "comic", "dense", "disturb", "joining", "sworn", "teasing", "strolled", "strings", "nail", "intervals",
    "winked", "sobbing", "mix", "handling", "furiously", "lab", "taller", "downtown", "leapt", "beautifully",
    "settling", "intimacy", "Ken", "mistakes", "examples", "Hollywood", "essence", "defeated", "requires", "ties",
    "sleepy", "reflect", "caution", "drifting", "propped", "pet", "promising", "sixth", "detailed", "persisted",
    "sunny", "Napoleon", "incidents", "whirled", "bout", "attacks", "breathless", "blazing", "logical", "pathetic",
    "dated", "roused", "eyelids", "gracious", "husbands", "wonders", "draped", "consisted", "channel", "tube",
    "relatively", "perceive", "isolated", "vessels", "overwhelmed", "battered", "floors", "Nelson", "smashed", "yield",
    "wrought", "solemnly", "Gilbert", "colleagues", "columns", "differently", "Clare", "compliment", "dates", "linked",
    "obscure", "accurate", "hears", "frankly", "hastened", "surgeon", "creative", "caring", "strict", "texts",
    "Todd", "fragments", "partners", "critics", "delivery", "acres", "shells", "titles", "earnestly", "outline",
    "instruments", "dismay", "Sydney", "hideous", "repeating", "Tess", "Senator", "suspicions", "boring", "canoe",
    "Georgia", "tendency", "darker", "Miranda", "grimly", "frowning", "adjusted", "roaring", "licked", "association",
    "wars", "hast", "housekeeper", "tumbled", "churches", "irritated", "rider", "depressed", "Maurice", "rotten",
    "cemetery", "teased", "earliest", "Mister", "apology", "chaos", "noisy", "paced", "judged", "Jan",
    "quest", "sour", "recognised", "referring", "coins", "overwhelming", "youthful", "subsequent", "frantic", "rivers",
    "anticipated", "Eva", "traces", "di", "spared", "Joyce", "awaiting", "arguing", "weep", "Stella",
    "shivering", "warmed", "psychological", "glowed", "neighbours", "chiefly", "ultimately", "squeeze", "Miriam", "prose",
    "unaware", "heroic", "Kyle", "handing", "considerably", "dedicated", "interpretation", "experiment", "Carlos", "attending",
    "trailed", "drunken", "transferred", "kidding", "Connie", "Leslie", "revelation", "filthy", "ruled", "bedside",
    "aching", "hurrying", "Claude", "succession", "weighed", "Hans", "Thompson", "ached", "wider", "sniffed",
    "magical", "baseball", "ankles", "descent", "Christians", "arrows", "engineer", "Amelia", "thereby", "swell",
    "mouse", "identical", "fatigue", "Judy", "complaint", "smoothly", "Malcolm", "Fortunately", "goat", "mock",
    "cord", "wipe", "tips", "ribbon", "revolver", "profile", "sized", "eyebrow", "warmly", "trigger",
    "wander", "acceptance", "noticing", "giggled", "harbor", "wiping", "opera", "levels", "Craig", "circled",
    "dipped", "corrected", "panting", "wallet", "sweetheart", "stillness", "Joey", "interfere", "beans", "comrades",
    "contest", "blocked", "classic", "inspiration", "improved", "cargo", "natives", "healing", "repair", "beheld",
    "comfortably", "mischief", "derived", "proportion", "Mum", "sandwich", "debate", "observing", "tangled", "Pearl",
    "survival", "administration", "spray", "eternity", "travelled", "simultaneously", "situations", "failing", "chorus", "lump",
    "conception", "hitherto", "jar", "downward", "item", "coats", "Dennis", "brutal", "affectionate", "competition",
    "deputy", "accepting", "chimney", "propose", "tennis", "countryside", "chat", "apt", "fascinating", "scandal",
    "refusing", "i.e.", "liking", "cafe", "thankful", "pony", "artificial", "finishing", "kin", "approved",
    "costs", "worker", "fools", "seas", "engines", "neighbourhood", "addressing", "landlord", "perched", "Edgar",
    "cease", "unnecessary", "packing", "photos", "positively", "behold", "rounds", "simplicity", "garments", "bury",
    "Collins", "kneeling", "Belle", "fancied", "colours", "video", "pinned", "Cassie", "constructed", "clapped",
    "preparations", "ropes", "trash", "inherited", "crashed", "extensive", "swords", "Marshall", "Herr", "scorn",
    "sentences", "crowds", "consumed", "improve", "tribes", "hauled", "horns", "hesitate", "matched", "huddled",
    "unlocked", "essay", "depression", "Museum", "unexpectedly", "awfully", "enjoyment", "boiled", "ne", "bat",
    "jet", "Jon", "nurses", "quantity", "cautious", "mourning", "describes", "customer", "fading", "declined",
    "ter", "supreme", "satin", "retreated", "mattress", "avoiding", "Lizzie", "launched", "guardian", "instinctively",
    "jest", "cents", "deaths", "passions", "bred", "crooked", "luggage", "helpful", "representative", "Harvey",
    "deliberate", "rattled", "accordingly", "toy", "diamonds", "attendant", "races", "groan", "slumped", "feather",
    "tenderly", "render", "register", "carries", "Cal", "Evelyn", "rocky", "punished", "resembled", "Gregory",
    "swam", "vehicles", "appreciation", "apprehension", "armor", "Oscar", "Joanna", "behaved", "heartily", "applause",
    "headache", "sales", "doubtful", "crawl", "cuts", "mused", "agitated", "Carolina", "im", "preacher",
    "Rev.", "respects", "ol", "shiver", "spectacle", "blush", "shrug", "bike", "heated", "panel",
    "Mitch", "disgrace", "Kent", "meadow", "objection", "helps", "nineteen", "complexion", "suggesting", "flicked",
    "bronze", "wheeled", "fright", "Ellie", "funds", "Harrison", "recollection", "tremble", "Phoebe", "rises",
    "locks", "masses", "willingly", "opportunities", "unseen", "spoon", "Baker", "Becky", "diary", "Robinson",
    "hooked", "virtually", "laughs", "plump", "pounded", "torch", "Irene", "jerk", "yourselves", "Horace",
    "liberal", "Harper", "mentally", "monkey", "fragile", "screams", "rim", "tho", "proceedings", "vulgar",
    "barrier", "traditions", "followers", "awaited", "pillows", "reads", "stamped", "entertained", "replace", "rational",
    "Murray", "Troy", "Edinburgh", "Cape", "calculated", "separation", "merit", "Leon", "cowboy", "thrill",
    "pots", "parade", "vegetables", "frightening", "solve", "exceedingly", "unwilling", "economy", "crumpled", "rocked",
    "pleasantly", "Solomon", "golf", "makeup", "gasp", "fashionable", "folds", "peasants", "spoiled", "muddy",
    "pulls", "candy", "sexy", "harmony", "lend", "shades", "vacant", "couples", "stall", "Ethan",
    "anchor", "Scottish", "shorter", "ridden", "bulk", "random", "del", "advise", "Stewart", "references",
    "Edmund", "rack", "providing", "resigned", "executed", "appreciated", "cupboard", "smelling", "imaginary", "drawers",
    "lingering", "bursting", "leisure", "wits", "comforting", "quivering", "visions", "ambulance", "scare", "Melissa",
    "waitress", "shudder", "annual", "Leonard", "distinguish", "brushing", "startling", "salad", "subdued", "tossing",
    "Watson", "organ", "drum", "gladly", "substantial", "tickets", "destined", "recording", "transport", "incapable",
    "remainder", "masculine", "Gloria", "correspondence", "squad", "technology", "emptied", "duck", "cells", "Ali",
    "monk", "celebration", "Beatrice", "Russians", "shrill", "announcement", "encouragement", "encourage", "lounge", "gut",
    "Jess", "yell", "cute", "monstrous", "Ruby", "watches", "vicious", "beliefs", "Cynthia", "Mars",
    "wreck", "wheat", "swayed", "arch", "robbed", "rolls", "gestures", "conventional", "celebrate", "inviting",
    "soothing", "picnic", "realization", "lacked", "Republic", "junior", "scarf", "virtues", "curb", "sketch",
    "decades", "stored", "mule", "ditch", "Roland", "severely", "dwell", "fro", "provides", "clicked",
    "prompted", "nursing", "cruelty", "yonder", "Teddy", "partially", "Heather", "provisions", "Wells", "judges",
    "explains", "confronted", "trucks", "betray", "whispers", "Arab", "Steven", "statements", "thrilled", "Caleb",
    "successfully", "wages", "slavery", "flooded", "Eden", "deaf", "employees", "writings", "arc", "ho",
    "repeatedly", "represents", "flock", "ducked", "steering", "scholar", "paces", "garbage", "Percy", "a.m.",
    "sped", "Sophia", "Mademoiselle", "Cameron", "automatically", "chasing", "compound", "adults", "trips", "Wayne",
    "oddly", "crisp", "robes", "surprisingly", "approve", "stooped", "lo", "Spencer", "streaming", "slain",
    "depended", "actors", "Wales", "Ibid", "salary", "timber", "functions", "Dante", "ash", "welfare",
    "sweetness", "biting", "trifle", "scar", "honesty", "sails", "forests", "wagons", "Nell", "odor",
    "phrases", "clubs", "compare", "Joel", "selection", "prices", "Hawk", "Casey", "Ay", "oven",
            "who","when","no","can","will","your","back","time","more","know","then","man","down",
            "champion", "Eli", "Highness", "Pamela", "Andhra Pradesh", "Amaravati",
            "Red","Blue","Green","Yellow","Father","Mother","Brother","Sister"
        };

        // Insert words
        for (String word : dict) {
            trie.insert(word);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String input = sc.nextLine();

        if (trie.search(input)) {
            System.out.println("Word is Correct");
        } else {
            System.out.println("Word Not Found");
            List<String> suggestions = trie.autoCorrect(input);
            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found");
            } else {
                System.out.println("Did you mean:");
                for (String s : suggestions) {
                    System.out.println(" - " + s);
                }
            }
        }
    }
}
