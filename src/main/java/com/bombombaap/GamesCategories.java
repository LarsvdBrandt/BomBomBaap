package com.bombombaap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;

public class GamesCategories {

    public static Map<Integer, Player> playerBase = new HashMap<>();

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
        "Iets dat jou breekt",
        "Iets dat kapot gaat als je ernaar kijkt",
        "Iets dat elektrisch is",
        "Iets dat werkt zonder dat iemand weet hoe",
        "Iets dat nooit doet wat het moet doen",
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





    private static final Random rand = new Random();
    private static String currentLetter = randomLetter();
    private static String currentCategory = randomCategory();
    private static boolean show404 = false;
    private static int leftCounter = 0;
    private static int middleCounter = 0;
    private static int rightCounter = 0;
    private static int tryAgainCounter = 0;
    private static int totalCounter = 0;
    private static boolean showLetter = true;
    private static boolean started = false;

    public static void registerRoutes() {
        get("/categories", (req, res) -> {
            res.type("text/html");
            return htmlPage(currentLetter, currentCategory, show404, showLetter, started);
        });
        post("/next", (req, res) -> {
            if (!started) {
                started = true;
                showLetter = false;
                return "ok";
            }
            if (show404) {
                show404 = false;
                showLetter = false;
                tryAgainCounter++;
                middleCounter++;
                totalCounter += 1;
                currentCategory = randomCategory(); // Only new category, do not restart
            } else {
                showLetter = false;
                middleCounter++;
                totalCounter += 1;
                currentCategory = randomCategory();
            }
            return "ok";
        });
        post("/reset", (req, res) -> {
            leftCounter = 0;
            middleCounter = 0;
            rightCounter = 0;
            tryAgainCounter = 0;
            totalCounter = 0;
            show404 = false;
            showLetter = true;
            started = false;
            currentLetter = randomLetter();
            currentCategory = randomCategory();
            return "ok";
        });
        post("/left", (req, res) -> {
            leftCounter++;
            totalCounter += 2;
            showLetter = false;
            currentCategory = randomCategory();
            return "ok";
        });
        post("/right", (req, res) -> {
            rightCounter++;
            totalCounter += 3;
            showLetter = false;
            currentCategory = randomCategory();
            return "ok";
        });
        post("/timeout", (req, res) -> {
            show404 = true;
            return "timeout";
        });
    }

    private static String randomLetter() {
        return String.valueOf((char) ('A' + rand.nextInt(26)));
    }
    private static String randomCategory() {
        return categorien[rand.nextInt(categorien.length)];
    }
    private static String htmlPage(String letter, String category, boolean error, boolean showLetter, boolean started) {
        String leftBtnColor = error ? "#888" : "#d32f2f";
        String middleBtnColor = error ? "#d32f2f" : "#ffd600";
        String rightBtnColor = error ? "#888" : "#ffd600";
        String btnStyle = "font-family:'Press Start 2P',monospace; padding:18px 36px; font-size:1.3em; border:4px solid #ffd600; border-radius:16px; cursor:pointer; margin:0 16px;min-width:160px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 4px 24px #111,0 0 16px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String bigBtnStyle = "font-family:'Press Start 2P',monospace;padding:28px 56px;font-size:2.2em;border:4px solid #ffd600;border-radius:20px;cursor:pointer;margin:0 16px;min-width:200px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 8px 32px #111,0 0 24px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String timerDiv = (!error && started) ? "<div id='timer' style='font-family:inherit;font-size:2em;margin-bottom:10px;color:#ffd600;background:rgba(0,0,0,0.5);border:2px solid #ffd60<PASSWORD>;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:<PASSWORD>;'></div>" : "";
        String errorMsg = error ? "<h1 style='font-family:inherit;color:#ffd600;background:rgba(50,0,0,0.7);border:2px solid #d32f2f;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>404 Error: Time's up!</h1>" : "";
        String letterDiv = showLetter ? "<h2 class='active-panel-title'>Letter: " + letter + "</h2>" : "";
        String categoryDiv = "<h3 style='color:#fff; background:rgba(0,0,0,0.5); border:2px solid #ffd600;display:inline-block; padding:18px 28px; border-radius:32px; text-shadow:0 2px 8px #222;'>" + category + "</h3>";
        String counterDiv = "<div style='margin:20px 0;font-size:1.2em;color:#ffd600;background:rgba(0,0,0,0.7);border:1px solid #ffd600;display:inline-block;padding:8px 24px;border-radius:12px;'>"
            + "<span>bouncers: " + leftCounter + "</span> &nbsp; "
            + "<span>poffen: " + middleCounter + "</span> &nbsp; "
            + "<span>palindromen: " + rightCounter + "</span> &nbsp; "
            + (error ? "<span>Try Again: " + tryAgainCounter + "</span> &nbsp; " : "")
            + "</div>";
        String totalDiv = "<div style='margin-top:40px;font-family:inherit;font-size:1.5em;background:rgba(0,0,0,0.8);color:#ffd600;padding:20px 40px;border-radius:24px;box-shadow:0 0 40px #222;text-shadow:0 2px 8px #222;border:3px solid #ffd600;display:inline-block;'>Pof counter: " + totalCounter + "</div>";
        String homeBtn = "<a href='/' id='homebtn' style='position:fixed; top:24px; left:24px; z-index:10; text-decoration:none;'><button style='background:#222; border:3px solid #ffd600; border-radius:50%; width:64px; height:64px; display:flex; align-items:center; justify-content:center; box-shadow:0 4px 16px #111; cursor:pointer;'><svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='36' height='36' fill='#ffd600'><path d='M12 3l9 9-1.5 1.5L18 12.5V20a1 1 0 0 1-1 1h-4v-5H11v5H7a1 1 0 0 1-1-1v-7.5l-1.5 1.5L3 12l9-9z'/></svg></button></a>";
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>BomBomBaap</title>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("body{font-family:'Press Start 2P', monospace; text-align:center; background:#222; min-height:100vh; overflow-x:hidden; overflow-y:auto; scrollbar-width:none; -ms-overflow-style:none; padding:32px 360px 48px 32px;}");
        html.append("body::-webkit-scrollbar{width:0;height:0;}");
        html.append(".starbg{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}");
        html.append(".arcade-frame{position:relative;z-index:1;max-width:720px;margin:72px auto 0 auto;padding:44px 24px 36px 24px;background:linear-gradient(135deg,#222 60%,#333 100%);border:4px solid #ffd600;border-radius:24px;box-shadow:0 0 44px #000, 0 0 0 6px #d32f2f, 0 0 0 12px #388e3c;}");
        html.append("h1,h2,h3{font-family:inherit;}");
        html.append(".active-panel-title{margin:0 0 18px 0;font-size:0.95em;line-height:1.4;letter-spacing:2px;color:#ffd600;text-shadow:0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;}");
        html.append(".side-panel{position:fixed; top:108px; right:24px; width:260px; max-height:calc(100vh - 156px); overflow-y:auto; scrollbar-width:none; -ms-overflow-style:none; padding:18px 14px; background:linear-gradient(135deg, #2a2a2a 0%, #1c1c1c 52%, #2f2f2f 100%); border:4px solid #ffd600; border-radius:22px; box-shadow:0 0 44px #000, 0 0 0 6px #d32f2f, 0 0 0 12px #388e3c; z-index:1; }.side-panel::-webkit-scrollbar{width:0;height:0;}.panel-title{margin:0 0 18px 0; font-size:0.95em; line-height:1.4; text-shadow:0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;}.player-list{display:flex; flex-direction:column; gap:14px;}.player-card{display:flex; align-items:center; justify-content:space-between; gap:12px; padding:14px; border:2px solid #ffd600; border-radius:16px; background:rgba(0,0,0,0.35);}.player-meta{text-align:left; min-width:0;}.player-name{font-size:0.8em; line-height:1.35; margin-bottom:8px; color:#fff7b3; word-break:break-word;}.player-elo{font-size:0.65em; color:#ffd600;}.player-add{font-family:'Press Start 2P', monospace; width:48px; height:48px; border:3px solid #ffd600; border-radius:14px; background:linear-gradient(135deg, #222 0%, #111 100%); color:#ffd600; box-shadow:0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600; cursor:pointer; font-size:1.05em; line-height:1;}.player-empty{font-size:0.7em; line-height:1.6; color:#fff7b3;}@media(max-width:1100px){body{padding-right:0;}.side-panel{position:static; width:auto; max-width:500px; margin:24px auto 0 auto;}} ");
        html.append("#btnrow{display:flex; justify-content:center; align-items:center; gap:20px; margin-top:36px; z-index:2;}#btnrow button{margin:0;}@media(max-width:600px){#btnrow{flex-direction:column;}#btnrow button{margin:10px 0;}}#homebtn button:hover{background:#ffd600; color:#222;}#homebtn svg{vertical-align:middle;}");
        html.append(".arcade-btns{display:flex; flex-direction:column; gap:34px; margin-top:44px;}"
            + "button.arcade-btn{font-family:'Press Start 2P', monospace; font-size:1.2em; padding:26px 0; border:4px solid #ffd600; border-radius:16px; background:#222; color:#ffd600; box-shadow:0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600; cursor:pointer; transition:background 0.2s, color 0.2s; width:70%; max-width:400px; min-width:180px; margin:0 auto; text-shadow:0 0 8px #ffd600;}"
            + "button.arcade-btn:hover{background:#ffd600; color:#222; text-shadow:0 0 8px #222;}");
        html.append("</style>");
        html.append("<script>");
        html.append("/* Timing and starfield behavior */");
        html.append("let timer;let timeLeft=30;function startTimer(){document.getElementById('timer').innerText='Timer: '+timeLeft+'s';timer=setInterval(function(){timeLeft--;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';if(timeLeft<=0){clearInterval(timer);fetch('/timeout',{method:'POST'}).then(function(){location.reload();});}},1000);}function next(){fetch('/next',{method:'POST'}).then(function(){location.reload();});}function left(){fetch('/left',{method:'POST'}).then(function(){location.reload();});}function right(){fetch('/right',{method:'POST'}).then(function(){location.reload();});}window.onload=function(){if(");
        html.append(started);
        html.append("&&!" );
        html.append(error);
        html.append("){startTimer();}};" );
        html.append("document.addEventListener('keydown',function(e){if(e.code==='Space'&&typeof timeLeft==='number'&&timer){timeLeft+=3;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';}});");
        // Arcade star background animation
        html.append("document.addEventListener('DOMContentLoaded',function(){let canvas=document.createElement('canvas');canvas.className='starbg';canvas.width=window.innerWidth;canvas.height=window.innerHeight;document.body.appendChild(canvas);let ctx=canvas.getContext('2d');let stars=[];for(let i=0;i<120;i++){stars.push({x:Math.random()*canvas.width,y:Math.random()*canvas.height,r:Math.random()*2+1,speed:Math.random()*0.7+0.3});}function drawStars(){ctx.clearRect(0,0,canvas.width,canvas.height);for(let s of stars){ctx.beginPath();ctx.arc(s.x,s.y,s.r,0,2*Math.PI);ctx.fillStyle='#ffd600';ctx.shadowColor='#fff';ctx.shadowBlur=8;ctx.fill();s.x+=s.speed;if(s.x>canvas.width){s.x=0;s.y=Math.random()*canvas.height;}}requestAnimationFrame(drawStars);}drawStars();});");
        html.append("</script></head><body>");
        html.append("<!-- Home button -->");
        html.append(homeBtn);
        html.append("<div class='starbg'></div><div class='arcade-frame'>");
        html.append("<!-- Category prompt and controls -->");
        html.append(errorMsg).append("<br>").append(letterDiv);
        if (started) {
            html.append("<br>").append(timerDiv).append("<br>").append(categoryDiv).append("<br>");
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
            html.append(error ? "Try Again" : "pof");
            html.append("</button>");
            html.append("<button style='");
            html.append(btnStyle);
            html.append("background:");
            html.append(rightBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='right()' ");
            html.append(error ? "disabled" : "");
            html.append(">palindroom</button>");
            html.append("</div>");
            html.append(counterDiv);
        } else {
            html.append("<br><div id='btnrow'>");
            html.append("<button style='");
            html.append(bigBtnStyle);
            html.append("background:");
            html.append(middleBtnColor);
            html.append(" class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='next()'>Start</button>");
            html.append("</div>");
        }
        html.append(totalDiv);
        if (error) {
            html.append("<br><form><button type='button' style='font-family:inherit;margin-top:20px;padding:12px 36px;font-size:1.2em;border:2px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;cursor:pointer;box-shadow:0 2px 12px #111;text-shadow:0 2px 8px #222;' onclick=\"fetch('/reset',{method:'POST'}).then(()=>location.reload())\">Reset</button></form>");
        }
        html.append("</div>");
        html.append("<!-- Active player roster -->");
        html.append(Main.renderActivePlayersPanel());
        html.append("</body></html>");
        return html.toString();
    }

    private static void resetStats() {
        leftCounter = 0;
        middleCounter = 0;
        rightCounter = 0;
        tryAgainCounter = 0;
        totalCounter = 0;
    }
}
