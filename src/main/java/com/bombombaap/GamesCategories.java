package com.bombombaap;

import java.util.List;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;

public class GamesCategories {

    private static final String[] categorien = {
        "Iets wat je op een kinderboerderij kan vinden",
        "Iets wat je kan gooien",
        "Een diepvriesproduct",
        "Iets wat lichter dan 5 kilo is",
        "Broodbeleg",
        "Een bedrijf",
        "Iets jonko gerelateerds",
        "Iets waardoor je van je fiets kunt vallen",
        "Een beroemd landmerk",
        "Iets wat je bij de bakker kunt kopen",
        "Een spel (geen game)",
        "Een pokemon",
        "Een gerecht",
        "Een stad in europa",
        "Iets wat je zou verstoppen voor je ouders",
        "Iets wat je op zolder vindt",
        "Iets wat je niet op kan tillen",
        "Topo",
        "Een manier om van je stress af te komen",
        "Een hobby",
        "Een verzorgingsproduct",
        "Een bekend persoon",
        "Een brawler / Clash Royale kaart",
        "Een evenement",
        "Een item uit fortnite",
        "Iets waarmee je tip kan draaien",
        "Iets wat je in de coffeeshop kan halen",
        "Een winkel",
        "Iets waar je je jonko mee kan aanstampen",
        "Een jongens naam",
        "Een coffeeshop",
        "Een pokoe",
        "Een kledingmerk",
        "Een saus",
        "Een vape smaak",
        "Iets dat je op een boerderij kunt vinden",
        "Iets wat je in een bos kunt vinden",
        "Iets wat je bij de slager kan halen",
        "Een meme/trend",
        "Een plant",
        "Een natuurkundige benaming",
        "Iets uit je jeugd",
        "Je gaat jilla, je mag volgende ronde niet meespelen.",
        "Iets wat je in de schuur kan vinden",
        "Iets wat je in de natuur kan vinden",
        "Een film/serie",
        "Electronica",
        "Iets wat je niet mee kan nemen in het vliegtuig",
        "Iets wat met sport te maken heeft",
        "Iets hoger dan 5 meter",
        "Een lichaamsdeel",
        "Iets waar chocolade in zit",
        "Iets waar je bang voor kan zijn",
        "Verander van speelrichting",
        "Iets wat je kunt breken",
        "Iets wat plakt",
        "Een parfum-merk/soort",
        "Iets wat je doet als je stoned bent",
        "Iets wat stinkt",
        "Iets wat je in een pretpark kunt vinden",
        "Eten",
        "Een feestdag",
        "Iets uit de tuin",
        "Een landing spot in fortnite",
        "Gereedschap",
        "Iets wat in de koelkast/vriezer ligt",
        "Een meiden naam",
        "Een geur",
        "Snoep",
        "Een dier",
        "Iets wat je doodmaakt als het van 10 meter hoog op je valt",
        "Een soort bier",
        "Een sportclub",
        "Iets wat je bij de Action koopt",
        "Iets wat je niet in je mond moet stoppen",
        "Een youtube kanaal",
        "Iets dat je kan vinden op een rave",
        "Een gebeurtenis/persoon uit de geschiedenis",
        "Een rivier/zee",
        "Een kleur",
        "Een activiteit",
        "Iets gevaarlijks",
        "Een app",
        "Een scooter/auto onderdeel",
        "Iets duurder dan €10.000",
        "Een kledingstuk",
        "Iets wat met het heelal te maken heeft",
        "Iets wat je bij de gamma kan kopen",
        "Een afkorting",
        "Iets wat je kan roken",
        "Iets wat je in een bibliotheek kunt vinden",
        "Een dier dat voorkomt in Nederland",
        "Een insect",
        "Een scherp object",
        "Een zeldzaam dier",
        "Iets wat je doet als je dronken bent",
        "Een soort speelgoed",
        "Iets wat je als asbak kunt gebruiken",
        "Iets wat je op vakantie meeneemt",
        "Iets wat je op werk kan vinden",
        "Een beroep",
        "Iets wat in een heuptasje past",
        "Een game",
        "Een bekende Nederlander",
        "Een superheld",
        "Iets wat je in je zak kan meenemen uit de supermarkt",
        "Een artiest",
        "Een verslaving",
        "Iets waar je boos van wordt",
        "Een OV station",
        "Iets wat te maken heeft met kaas",
        "Een medicijn / geneesmiddel",
        "Een plek waar je kan smoken",
        "Een letter uit het alfabet",
        "Iets typisch nederlands",
        "Iets uit de Nederlandse keuken",
        "Een soort vervoer",
        "Een plaats in Nederland",
        "Fast food",
        "Iets typisch Rotterdams",
        "Een restaurant",
        "Iets wat je met je hand kan doen",
        "Iets wat je kunt dragen tijdens carnaval",
        "Iets wat je bij de visboer kunt kopen",
        "Iets uit de keuken",
        "Een dealernaam",
        "Automerk / soort",
        "Iets waardoor je slipper krijgt",
        "Iets wat in een scooterbuddy past",
        "Een opleiding",
        "Iets wat jij dood kan maken",
        "Een uitvinding die je dagelijks gebruikt",
        "Iets uit de zee",
        "Een kinderprogramma/ cartoon",
        "Drinken",
        "Iets van de politiek",
        "Iets waarmee je een moord kunt plegen",
        "Iets waar je een bong van kan maken",
        "Een ziekte",
        "Straattaal",
        "Iets wat je kunt kopen bij de juwelier",
        "Iets wat vlambaar is",
        "Een wietsoort",
        "Een soort drugs",
        "Iets waar je een lidwoord voor kan zetten",
        "Een scheldwoord",
        "Valuta",
        "Iets waar je comfortabel op kunt zitten",
        "Een manier hoe je ontslagen kan worden",
        "Een instrument",
        "Een dubbel pof",
        "Iets wat je in een museum kunt vinden",
        "Iets wat je op een camping kunt vinden",
        "Iets wat je kunt huren",
        "Een voetbalspeler",
        "Een achternaam",
        "Iets wat je bij de tuinwinkel kan halen",
        "Een straatnaam",
        "Groente / Fruit",
        "Een Duits woord",
        "Iets goedkoper dan €5",
        "Make-up",
        "Een item uit minecraft",
        "Een huisdier",
        "Een zonde",
        "Iets waar je op kan slapen",
        "Iets zachts",
        "Iets keihards",
        "Iets belachelijk duurs",
        "Iets verdacht goedkoops",
        "Iets heel snel",
        "Iets heel traag",
        "Iets dat lawaai maakt",
        "Iets dat muisstil is",
        "Iets zoets",
        "Iets zuurs",
        "Iets pittigs",
        "Iets groens",
        "Iets roods",
        "Iets zwarts",
        "Iets wits",
        "Iets gouds",
        "Iets doorzichtigs",
        "Iets dat glinstert",
        "Iets waar je gegarandeerd over uitglijdt",
        "Iets dat er duur uitziet maar dat niet is",
        "Iets dat in je portemonnee past",
        "Iets dat je liever niet in je broekzak hebt",
        "Iets dat je verder dan 5 meter kunt trappen",
        "Iets dat pijn doet als je erop gaat zitten",
        "Iets dat elektrisch is",
        "Iets dat werkt zonder dat iemand weet hoe",
        "Iets dat je vroeger cool vond",
        "Iets dat alleen oude mensen gebruiken",
        "Iets dat kinderen veel te leuk vinden",
        "Iets dat je stiekem verzamelt",
        "Iets dat je kwijtraakt als je het nodig hebt",
        "Iets dat je alleen in sprookjes tegenkomt",
        "Iets dat je niet mag aanraken",
        "Iets dat je absoluut niet mag googelen op werk",
        "Iets dat je alleen per ongeluk tegenkomt",
        "Iets dat gevaarlijk is",
        "Iets waarbij iemand ooit zei: hold my beer",
        "Iets dat levensgevaarlijk is als het van een lantaarnpaal op je hoofd valt",
        "Iets dat je kunt alleen tillen",
        "Iets dat je permanent rugschade geeft",
        "Iets dat lekker ruikt",
        "Iets dat verdacht ruikt",
        "Iets dat je gelukkig maakt",
        "Iets dat je meteen spijt geeft",
        "Iets dat beter lijkt dan het is"
    };

    private static final String[] maastrichtCategorien = {
        "Iets typisch maastrichts",
        "Een dispuut uit maas",
        "Een cafe in maas",
        "Een restaurant in maas",
        "Een henk uit maas",
        "Een drankje bij de falstaff",
        "Een lekkere chick uit maas",
        "Een huis in maas",
        "Een straatnaam in maas",
        "Een feest in maas",
        "Een goon die te vaak in de roto staat",

        "Een maas npc/zwerver",
        "Iemand die bij de falstaff werkt",
        "Iemand die bij de falstaff werkte",
        "Iemand die je hebt geregeld in maas",
        "Een plank uit maas",
        "Iemand met dikke tieten uit maas",
        "Iemand uit maas die zn bek moet houden",
        "Een maastricht celebrity",
        "Een echte sjeng",
        "een alcoholist uit maas",

        "een drugsverslaafde uit maas"
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST",
        // "maastrichtTEST"
    };

    private static final String BIMBAMBONUSSEN = """
[
    "⚡ BomBommBonus\nReverse: De speelrichting draait om",
    "⚡ BomBommBonusReverse: De speelrichting draait om",
    "⚡ BomBommBonus\nMute: Je mag alleen antwoorden door uit te beelden, niet praten!",
    "⚡ BomBommBonusMute: Je mag alleen antwoorden door uit te beelden, niet praten!",
    "⚡ BomBommBonus\nDouble down: Je MAG nog een keer spelen. Fout = 2x jilla",
    "⚡ BomBommBonusDouble down: Je MAG nog een keer spelen. Fout = 2x jilla",
    "⚡ BomBommBonus\nDe Verlosser: Haal een andere speler uit Jilla",
    "⚡ BomBommBonusDe Verlosser: Haal een andere speler uit Jilla",
    "⚡ BomBommBonus\nKies iemand uit die jilla gaat",
    "⚡ BomBommBonus\nNeem nog een extra pof bij een goed antwoord",
    "⚡ BomBommBonusNeem nog een extra pof bij een goed antwoord",
    "⚡ BomBommBonus\nStoomtrein: Neem bij een goed antwoord, een power pof",
    "⚡ BomBommBonusStoomtrein: Neem bij een goed antwoord, een power pof",
    "⚡ BomBommBonus\nCopycat: Je mag vorige antwoorden herhalen",
    "⚡ BomBommBonusCopycat: Je mag vorige antwoorden herhalen",
    "⚡ BomBommBonus\n1x uit jilla kaart",
    "⚡ BomBommBonus1x uit jilla kaart",
    "⚡ BomBommBonus\nKies een nieuwe beginletter voor de volgende speler",
    "⚡ BomBommBonus\nTikkie: Deel je straf met je linkerbuurman/vrouw. Samen Jilla, samen poffen.",
    "⚡ BomBommBonusTikkie: Deel je straf met je linkerbuurman/vrouw. Samen Jilla, samen poffen.",
    "⚡ BomBommBonusKies een nieuwe beginletter voor de volgende speler",
    "⚡ BomBommBonus\nSkip: De volgende speler wordt overgeslagen",
    "⚡ BomBommBonusSkip: De volgende speler wordt overgeslagen",
    "⚡ BomBommBonusKies iemand uit die jilla gaat",
    "⚡ BomBommBonus\nDe Dealer: Jij bepaalt wie de volgende vraag moet beantwoorden",
    "⚡ BomBommBonusDe Dealer: Jij bepaalt wie de volgende vraag moet beantwoorden"
]
""";

    private static final Random rand = new Random();
    private static String currentLetter = randomLetter();
    private static String currentCategory = randomCategory();
    // private static final String[] BONUS_CATEGORIES = loadBonusCategories();
    private static boolean show404 = false;
    private static int bouncerCounter = 0;
    private static int pofCounter = 0;
    private static int palindromenCounter = 0;
    private static int jillaCounter = 0;
    private static int totalCounter = 0;
    private static boolean showLetter = true;
    private static boolean started = false;
    private static boolean maastrichtSelected = false;
    private static int currentPlayerIndex = 0;
    private static Player currentPlayer = null;
    private static List<Player> activePlayers = null;

    private static void resetGame() {
        show404 = false;
        bouncerCounter = 0;
        pofCounter = 0;
        palindromenCounter = 0;
        jillaCounter = 0;
        totalCounter = 0;
        showLetter = true;
        started = false;
        currentCategory = randomCategory();
        currentLetter = randomLetter();
        currentPlayerIndex = 0;
        currentPlayer = null;
        if (activePlayers != null) {
            for (Player p : activePlayers) {
                p.setInJilla(false);
            }
        }
        Main.setCurrentActivePlayer(null);
    }

    public static void registerRoutes() {
        get("/categories", (req, res) -> {
            resetGame();
            activePlayers = getActivePlayers();
            if (!activePlayers.isEmpty()) {
                currentPlayer = activePlayers.get(0);
            }
            Main.setCurrentActivePlayer(currentPlayer);
            res.type("text/html");
            return htmlPage(currentLetter, currentCategory, show404, showLetter, started);
        });
        post("/next", (req, res) -> {
            if (!started) { // start knop geklikt
                started = true;
                showLetter = false;
                return "ok";
            }
            if (show404) { // timer afgegaan, update nieuwe ronde
                show404 = false;
                showLetter = false;
                currentCategory = randomCategory(); // Only new category, do not restart
            } else { // optijd antwoord, 1 pof
                addPof(currentPlayer, 1);
                addBeurt(currentPlayer, 1);
                // updateStats(currentPlayer, 1, 0, 0, 0); // + 1 pof
                showLetter = false;
                pofCounter++;
                totalCounter += 1;
                currentCategory = randomCategory();
            }
            setCurrentPlayer();
            return "ok";
        });
        post("/wasoptijd", (req, res) -> {
            // toch op tijd, + 1 pof
            // updateStats(currentPlayer, 0, 0, 0, -1); // + 1 pof
            addPof(currentPlayer, 1);
            addJilla(currentPlayer, -1);
            addBeurt(currentPlayer, 1);
            updateJillaTracker(currentPlayer, false);
            System.out.print(currentPlayer.name + " was toch op tijd! -1 jilla");
            setCurrentPlayer();
            show404 = false;
            currentCategory = randomCategory();
            return "ok";
        });
        post("/left", (req, res) -> {
            // +1 bouncer
            // updateStats(currentPlayer, 2, 1, 0, 0); // + 2 poffen + 1 bouncer
            addPof(currentPlayer, 2);
            addBouncer(currentPlayer, 1);
            addBeurt(currentPlayer, 1);
            setCurrentPlayer();
            bouncerCounter++;
            totalCounter += 2;
            showLetter = false;
            currentCategory = randomCategory();
            return "ok";
        });
        post("/right", (req, res) -> {
            // +1 palindroom
            // updateStats(currentPlayer, 3, 0, 1, 0); // + 3 poffen + 1 palindroom
            addPof(currentPlayer, 3);
            addPalindromen(currentPlayer, 1);
            addBeurt(currentPlayer, 1);
            setCurrentPlayer();
            palindromenCounter++;
            totalCounter += 3;
            showLetter = false;
            currentCategory = randomCategory();
            return "ok";
        });
        post("/toggle-maastricht", (req, res) -> {
            maastrichtSelected = !maastrichtSelected;
            currentCategory = randomCategory();
            res.redirect("/categories");
            return "";
        });
        post("/timeout", (req, res) -> {
            // timer afgegaan, +1 jilla
            // updateStats(currentPlayer, 1, 0, 0, 1); // + 1 pof + 1 jilla
            addPof(currentPlayer, 1);
            addJilla(currentPlayer, 1);
            addBeurt(currentPlayer, 1);
            updateJillaTracker(currentPlayer, true);
            jillaCounter++;
            pofCounter++;
            totalCounter += 1;
            show404 = true;
            return "timeout";
        });
    }

    private static void setCurrentPlayer() {
        // jilla update: per player tracken of ze momenteel in de jilla zitten
        // zo ja, speler overslaan en jilla tracker updaten
        // zo nee, normaal volgende speler

        if (activePlayers == null || activePlayers.isEmpty()) {
            currentPlayer = null;
            currentPlayerIndex = 0;
            Main.setCurrentActivePlayer(null);
            return;
        }
        int nextPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();

        int skippedJillaCount = 0;

        for (int playerIndexes = 0; playerIndexes < activePlayers.size(); playerIndexes++) {
            Player nextPlayer = activePlayers.get(nextPlayerIndex);
            if (nextPlayer.isInJilla()) {
                skippedJillaCount++;
                System.out.print(nextPlayer.name + " zit momenteel in de jilla, volgende speler is aan de beurt.");
                updateJillaTracker(nextPlayer, false);
                nextPlayerIndex = (nextPlayerIndex + 1) % activePlayers.size();
            } else {
                // speler zit niet in de jilla, gewoon volgende speler
                currentPlayerIndex = nextPlayerIndex;
            }
        }
        if (skippedJillaCount == activePlayers.size()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
        }
        currentPlayer = activePlayers.get(currentPlayerIndex);
        Main.setCurrentActivePlayer(currentPlayer);
    }

    private static void addPof(Player player, int poffen) {
        player.addPof(poffen);
        System.out.println("Added " + poffen + " poffen to " + player.name);
        Main.saveData();
    }

    private static void addBouncer(Player player, int bouncers) {
        player.addBouncer(bouncers);
        System.out.println("Added " + bouncers + " bouncers to " + player.name);
        Main.saveData();
    }

    private static void addPalindromen(Player player, int palindromen) {
        player.addPalindromen(palindromen);
        System.out.println("Added " + palindromen + " palindromen to " + player.name);
        Main.saveData();
    }

    private static void addJilla(Player player, int jillas) {
        player.addJilla(jillas);
        System.out.println("Added " + jillas + " jillas to " + player.name);
        Main.saveData();
    }

    private static void addBeurt(Player player, int beurtenGehad) {
        player.addBeurt(beurtenGehad);
        System.out.println("Added " + beurtenGehad + " beurten gehad to " + player.name);
        Main.saveData();
    }

    private static void updateJillaTracker(Player player, boolean inJilla) {
        player.setInJilla(inJilla);
    }
    
    private static String escapeHtml(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    private static List<Player> getActivePlayers() {
        activePlayers = Main.getActivePlayers();
        System.out.println("Active players: " + activePlayers.stream().map(p -> p.name).toList());
        return activePlayers;
    }

    private static String randomLetter() {
        return String.valueOf((char) ('A' + rand.nextInt(26)));
    }
    private static String randomCategory() {
        // if (BONUS_CATEGORIES.length > 0 && rand.nextInt(100) < BONUS_TRIGGER_PERCENT) {
        //     return BONUS_CATEGORIES[rand.nextInt(BONUS_CATEGORIES.length)];
        // }
        String[] allCategories = getAvailableCategories();
        return allCategories[rand.nextInt(allCategories.length)];
    }

    // private static String[] loadBonusCategories() {
    //     try {
    //         JSONArray bonusArray = new JSONArray(BIMBAMBONUSSEN);
    //         String[] categories = new String[bonusArray.length()];
    //         for (int i = 0; i < bonusArray.length(); i++) {
    //             categories[i] = bonusArray.getString(i);
    //         }
    //         return categories;
    //     } catch (JSONException e) {
    //         return new String[0];
    //     }
    // }

    private static String[] getAvailableCategories() {
        if (!maastrichtSelected) {
            System.out.println("Maastricht categorien niet geselecteerd, alleen reguliere categorien beschikbaar.");
            return categorien;
        }
        System.out.println("Maastricht categorien geselecteerd, reguliere + maastricht categorien beschikbaar.");

        String[] allCategories = new String[categorien.length + maastrichtCategorien.length];
        System.arraycopy(categorien, 0, allCategories, 0, categorien.length);
        System.arraycopy(maastrichtCategorien, 0, allCategories, categorien.length, maastrichtCategorien.length);
        return allCategories;
    }
    private static String htmlPage(String letter, String category, boolean error, boolean showLetter, boolean started) {
        String leftBtnColor = error ? "#888" : "#d32f2f";
        String middleBtnColor = error ? "#d32f2f" : "#ffd600";
        String rightBtnColor = error ? "#888" : "#ffd600";
        String maastrichtLabel = maastrichtSelected ? "[x] Maastricht" : "[ ] Maastricht";
        String maastrichtBtnBg = maastrichtSelected ? "linear-gradient(135deg,#1f7a36 0%,#0f4d1f 100%)" : "linear-gradient(135deg,#222 0%,#111 100%)";
        String maastrichtBtnBorder = maastrichtSelected ? "#9cff57" : "#ffd600";
        String maastrichtBtnGlow = maastrichtSelected ? "#9cff57" : "#ffd600";
        String btnStyle = "font-family:'Press Start 2P',monospace; padding:18px 36px; font-size:1.3em; border:4px solid #ffd600; border-radius:16px; cursor:pointer; margin:0 16px;min-width:160px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 4px 24px #111,0 0 16px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String bigBtnStyle = "font-family:'Press Start 2P',monospace;padding:28px 56px;font-size:2.2em;border:4px solid #ffd600;border-radius:20px;cursor:pointer;margin:0 16px;min-width:200px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 8px 32px #111,0 0 24px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String maastrichtBtnStyle = "font-family:'Press Start 2P',monospace;padding:14px 18px;font-size:0.82em;border:4px solid " + maastrichtBtnBorder + ";border-radius:18px;cursor:pointer;margin:0 14px 0 0;min-width:240px;background:" + maastrichtBtnBg + ";color:#fff7b3;box-shadow:0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px " + maastrichtBtnGlow + ";text-shadow:0 0 8px #000;transition:background 0.2s,color 0.2s,transform 0.15s;";
        // Timer badge — always render the element (hidden when inactive) so JS can reliably select it.
        String timerDiv = "<div id='timer' style='font-family:inherit;font-size:2em;margin-bottom:10px;color:#ffd600;background:rgba(0,0,0,0.5);border:2px solid #ffd600;padding:8px 24px;border-radius:12px;text-shadow:0 2px 8px #222;display:" + ((!error && started) ? "inline-block" : "none") + ";'></div>";
        // Banner that shows which player is currently locked in.
        String errorMsg = error ? "<h1 style='font-family:inherit;color:#ffd600;background:rgba(50,0,0,0.7);border:2px solid #d32f2f;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>" + currentPlayer.name + " gaat jilla</h1>" : "";
        // Letter prompt displayed above the category when the round starts.
        String letterDiv = showLetter ? "<h2 class='active-panel-title'>Letter: " + letter + "</h2>" : "";
        
        // Current player status line for the main game panel.
        String currentPlayerDiv = currentPlayer != null
            ? "<div class='current-player'>lock in " + escapeHtml(currentPlayer.name) + "</div>"
            : "<div class='current-player current-player-empty'>No active player selected</div>";
        // The active category prompt the players respond to.
        String categoryDiv = "<h3 style='color:#fff; background:rgba(0,0,0,0.5); border:2px solid #ffd600;display:inline-block; padding:18px 28px; border-radius:32px; text-shadow:0 2px 8px #222;'>" + category + "</h3>";
        // Running round stats displayed under the controls.
        String counterDiv = "<div style='margin:20px 0;font-size:1.2em;color:#ffd600;background:rgba(0,0,0,0.7);border:1px solid #ffd600;display:inline-block;padding:8px 24px;border-radius:12px;'>"
            + "<span>bouncers: " + bouncerCounter + "</span> &nbsp; "
            + "<span>poffen: " + pofCounter + "</span> &nbsp; "
            + "<span>palindromen: " + palindromenCounter + "</span> &nbsp; "
            + (error ? "<span>jillas: " + jillaCounter + "</span> &nbsp; " : "")
            + "</div>";
        // Total score summary shown near the bottom of the page.
        String totalDiv = "<div style='margin-top:40px;font-family:inherit;font-size:1.5em;background:rgba(0,0,0,0.8);color:#ffd600;padding:20px 40px;border-radius:24px;box-shadow:0 0 40px #222;text-shadow:0 2px 8px #222;border:3px solid #ffd600;display:inline-block;'>Pof counter: " + totalCounter + "</div>";
        // Fixed home button in the top-left corner.
        String homeBtn = "<a href='/' id='homebtn' style='position:fixed; top:24px; left:24px; z-index:10; text-decoration:none;'><button style='background:#222; border:3px solid #ffd600; border-radius:50%; width:64px; height:64px; display:flex; align-items:center; justify-content:center; box-shadow:0 4px 16px #111; cursor:pointer;'><svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='36' height='36' fill='#ffd600'><path d='M12 3l9 9-1.5 1.5L18 12.5V20a1 1 0 0 1-1 1h-4v-5H11v5H7a1 1 0 0 1-1-1v-7.5l-1.5 1.5L3 12l9-9z'/></svg></button></a>";
        StringBuilder html = new StringBuilder();
        // Document head: title, font, styles, and the gameplay scripts.
        html.append("<html><head><title>BomBomBaap</title>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("body{font-family:'Press Start 2P', monospace; text-align:center; background:#222; min-height:100vh; overflow-x:hidden; overflow-y:auto; scrollbar-width:none; -ms-overflow-style:none; padding:32px 360px 48px 32px;}");
        html.append("body::-webkit-scrollbar{width:0;height:0;}");
        html.append(".starbg{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}");
        html.append(".arcade-frame{position:relative;z-index:1;max-width:720px;margin:72px auto 0 auto;padding:44px 24px 36px 24px;background:linear-gradient(135deg,#222 60%,#333 100%);border:4px solid #ffd600;border-radius:24px;box-shadow:0 0 44px #000, 0 0 0 6px #d32f2f, 0 0 0 12px #388e3c;}");
        html.append("h1,h2,h3{font-family:inherit;}");
        html.append(".active-panel-title{margin:0 0 18px 0;font-size:0.95em;line-height:1.4;letter-spacing:2px;color:#ffd600;text-shadow:0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;}");
        html.append(".player-card.current-player-card{border:3px solid #ffd600;background:rgba(0,0,0,0.55);box-shadow:0 0 18px #ffd600, 0 0 36px #d32f2f, 0 0 0 4px #388e3c;}");
        html.append(".current-player{margin:0 0 16px 0;display:inline-block;padding:12px 20px;border:3px solid #ffd600;border-radius:16px;background:rgba(0,0,0,0.55);color:#fff7b3;font-size:0.82em;line-height:1.4;text-shadow:0 0 8px #000, 0 0 16px #ffd600;box-shadow:0 0 18px #ffd600, 0 0 36px #d32f2f, 0 0 0 4px #388e3c;}.current-player-empty{color:#ffd600;}");
        html.append(".side-panel{position:fixed; top:108px; right:24px; width:260px; max-height:calc(100vh - 156px); overflow-y:auto; scrollbar-width:none; -ms-overflow-style:none; padding:18px 14px; background:linear-gradient(135deg, #2a2a2a 0%, #1c1c1c 52%, #2f2f2f 100%); border:4px solid #ffd600; border-radius:22px; box-shadow:0 0 44px #000, 0 0 0 6px #d32f2f, 0 0 0 12px #388e3c; z-index:1; }.side-panel::-webkit-scrollbar{width:0;height:0;}.panel-title{margin:0 0 18px 0; font-size:0.95em; line-height:1.4; text-shadow:0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;}.player-list{display:flex; flex-direction:column; gap:14px;}.player-card{display:flex; align-items:center; justify-content:space-between; gap:12px; padding:14px; border:2px solid #ffd600; border-radius:16px; background:rgba(0,0,0,0.35);}.player-meta{text-align:left; min-width:0;}.player-name{font-size:0.8em; line-height:1.35; margin-bottom:8px; color:#fff7b3; word-break:break-word;}.player-elo{font-size:0.65em; color:#ffd600;}.player-add{font-family:'Press Start 2P', monospace; width:48px; height:48px; border:3px solid #ffd600; border-radius:14px; background:linear-gradient(135deg, #222 0%, #111 100%); color:#ffd600; box-shadow:0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600; cursor:pointer; font-size:1.05em; line-height:1;}.player-empty{font-size:0.7em; line-height:1.6; color:#fff7b3;}@media(max-width:1100px){body{padding-right:0;}.side-panel{position:static; width:auto; max-width:500px; margin:24px auto 0 auto;}} ");
        html.append(".player-actions{display:flex;align-items:center;gap:8px;}.player-order-buttons{display:flex;flex-direction:column;gap:6px;}.player-move{font-family:inherit;width:32px;height:32px;border:2px solid #ffd600;border-radius:10px;background:linear-gradient(135deg,#222 0%,#111 100%);color:#ffd600;box-shadow:0 2px 0 #d32f2f,0 4px 0 #388e3c,0 0 12px #ffd600;cursor:pointer;font-size:0.72em;line-height:1;padding:0;}.player-move:disabled,.player-add:disabled{opacity:0.45;cursor:not-allowed;}");
        html.append("#btnrow{display:flex; justify-content:center; align-items:center; gap:20px; margin-top:36px; z-index:2;}#btnrow button{margin:0;}@media(max-width:600px){#btnrow{flex-direction:column;}#btnrow button{margin:10px 0;}}#homebtn button:hover{background:#ffd600; color:#222;}#homebtn svg{vertical-align:middle;}");
        html.append(".arcade-btns{display:flex; flex-direction:column; gap:34px; margin-top:44px;}"
            + "button.arcade-btn{font-family:'Press Start 2P', monospace; font-size:1.2em; padding:26px 0; border:4px solid #ffd600; border-radius:16px; background:#222; color:#ffd600; box-shadow:0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600; cursor:pointer; transition:background 0.2s, color 0.2s; width:70%; max-width:400px; min-width:180px; margin:0 auto; text-shadow:0 0 8px #ffd600;}"
            + "button.arcade-btn:hover{background:#ffd600; color:#222; text-shadow:0 0 8px #222;}");
        html.append("</style>");
        html.append("<script>");
        html.append("/* Timing and starfield behavior */");
        html.append("let timer = null; let timeLeft = 30; function startTimer(){ const el = document.getElementById('timer'); if(!el) return; el.style.display='inline-block'; timeLeft = 30; el.innerText = 'Timer: ' + timeLeft + 's'; if(timer) clearInterval(timer); timer = setInterval(function(){ timeLeft--; el.innerText = 'Timer: ' + timeLeft + 's'; if(timeLeft <= 0){ clearInterval(timer); fetch('/timeout',{method:'POST'}).then(function(){ location.reload(); }); } }, 1000); }");
        html.append("function stopTimer(){ if(timer){ clearInterval(timer); timer = null; } const el = document.getElementById('timer'); if(el) el.style.display='none'; }");
        html.append("function next(){ fetch('/next',{method:'POST'}).then(function(){ location.reload(); }); }");
        html.append("function left(){ fetch('/left',{method:'POST'}).then(function(){ location.reload(); }); }");
        html.append("function right(){ fetch('/right',{method:'POST'}).then(function(){ location.reload(); }); }");
        html.append("window.onload=function(){ if(");
        html.append(started);
        html.append(" && !");
        html.append(error);
        html.append(") { startTimer(); } else { stopTimer(); } };" );
        html.append("document.addEventListener('keydown',function(e){ if(e.code==='Space' && typeof timeLeft === 'number' && timer){ timeLeft += 3; const el = document.getElementById('timer'); if(el) el.innerText = 'Timer: ' + timeLeft + 's'; } });");
        // Background starfield animation for the whole page.
        html.append("document.addEventListener('DOMContentLoaded',function(){let canvas=document.createElement('canvas');canvas.className='starbg';canvas.width=window.innerWidth;canvas.height=window.innerHeight;document.body.appendChild(canvas);let ctx=canvas.getContext('2d');let stars=[];for(let i=0;i<120;i++){stars.push({x:Math.random()*canvas.width,y:Math.random()*canvas.height,r:Math.random()*2+1,speed:Math.random()*0.7+0.3});}function drawStars(){ctx.clearRect(0,0,canvas.width,canvas.height);for(let s of stars){ctx.beginPath();ctx.arc(s.x,s.y,s.r,0,2*Math.PI);ctx.fillStyle='#ffd600';ctx.shadowColor='#fff';ctx.shadowBlur=8;ctx.fill();s.x+=s.speed;if(s.x>canvas.width){s.x=0;s.y=Math.random()*canvas.height;}}requestAnimationFrame(drawStars);}drawStars();});");
        html.append("</script></head><body>");
        // Top navigation button.
        html.append("<!-- Home button -->");
        html.append(homeBtn);
        // Main arcade frame containing the round UI.
        html.append("<div class='starbg'></div><div class='arcade-frame'>");
        // Main prompt area: error state, letter prompt, current player, timer, and controls.
        html.append("<!-- Category prompt and controls -->");
        html.append(errorMsg).append("<br>").append(letterDiv).append("<br>");

        if (!show404) {
            // Current player banner.
            html.append(currentPlayerDiv);        }
        if (started) {
            // Active round content: timer, category, buttons, and counters.
            html.append("<br>").append(timerDiv).append("<br>").append(categoryDiv).append("<br>");
            // Round action buttons.
            html.append("<div id='btnrow'>");
            html.append("<button style='");
            html.append(btnStyle);
            html.append("background:");
            html.append(leftBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\"  onclick='left()' ");
            html.append(error ? "disabled" : "");
            html.append(">bouncer</button>");
            html.append("<button style='");
            html.append(bigBtnStyle);
            html.append("background:");
            html.append(middleBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='next()'>");
            html.append(error ? "jilla" : "pof");
            html.append("</button>");
            html.append("<button style='");
            html.append(btnStyle);
            html.append("background:");
            html.append(rightBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='right()' ");
            html.append(error ? "disabled" : "");
            html.append(">palindroom</button>");
            html.append("</div>");
            // Per-round tally display.
            html.append(counterDiv);
        } else {
            // Start state: only the start button is shown.
            html.append("<br><div id='btnrow'>");
            html.append("<form action='/toggle-maastricht' method='post'>");
            html.append("<button style='");
            html.append(maastrichtBtnStyle);
            html.append("' class='arcade-btn' onmouseover=\"this.style.transform='translateY(-1px)';\" onmouseout=\"this.style.transform='translateY(0)';\" type='submit'>");
            html.append(maastrichtLabel);
            html.append("</button></form>");
            html.append("<button style='");
            html.append(bigBtnStyle);
            html.append("background:");
            html.append(middleBtnColor);
            html.append(" class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='next()'>Start</button>");
            html.append("</div>");
        }
        html.append(totalDiv);
        if (error) {
            // Error recovery button shown when a player goes jilla.
            html.append("<br><form><button type='button' style='font-family:inherit;margin-top:20px;padding:12px 36px;font-size:1.2em;border:2px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;cursor:pointer;box-shadow:0 2px 12px #111;text-shadow:0 2px 8px #222;' onclick=\"fetch('/wasoptijd',{method:'POST'}).then(()=>location.reload())\">was optijd!</button></form>");
        }
        html.append("</div>");
        // Right-side active player roster panel.
        html.append("<!-- Active player roster -->");
        html.append(Main.renderActivePlayersPanel());
        html.append("</body></html>");
        return html.toString();
    }

}
