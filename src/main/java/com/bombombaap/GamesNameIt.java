package com.bombombaap;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class GamesNameIt {
private static final String[] MOVIE_PROMPTS = {
    "Noem een film met Tom Hanks", 
    "Noem een niet grappige comedy", 
    "Noem een film uit de jaren 90", 
    "Noem een film met een hond", 
    "Noem een film van James Cameron",
    "Noem een slechte horror film", 
    "Noem een klassieke Disney film", 
    "Noem een Marvel superheld", 
    "Noem een karakter uit Star Wars", 
    "Noem een film van Christopher Nolan",
    "Noem een zwart witte film", 
    "Noem een 2D geanimeerde film", 
    "Noem een film met een getal in de titel", 
    "Noem een romcon", 
    "Noem een film met aliens", 
    "Noem een 3D geanimeerde film",
    "Noem een film die zich afspeelt in New York", 
    "Noem een kerst film", 
    "Noem een film gebasseerd op een echt gebeurd verhaal", 
    "Noem een superhero movie", 
    "Noem een film met Brad Pitt",
    "Noem een film die een Oscar heeft gewonnen", 
    "Noem een film met een plot twist einde", 
    "Noem een buitenlandse film (niet Engels)", 
    "Noem een opvolger met een '2' in de titel", 
    "Noem een film met een draak", 
    "Noem een opvolger die géén '2' in de titel heeft", 
    "Noem een film die jullie samen gekeken hebben", 
    "Noem een film met een seksscène",
    "Noem een musical", 
    "Noem een oorlogsfilm", 
    "Noem een bekende film quote", 
    "Noem een film waarin het hoofdkarakter dood gaat", 
    "raak een minderjarige aan"
};

private static final String[] MUSIC_PROMPTS = {
    "Noem een Beatles lied", 
    "Noem een blanke rapper", 
    "Noem een liedje met 'love' in de titel", 
    "Noem een jazzmuzikant", 
    "Noem een boyband",
    "Noem een lied met een gitaarsolo", 
    "Noem een country zanger(es)", 
    "Noem een klassieke componist", 
    "Noem een lied uit de jaren 70",
    "Noem een artiest die een Grammy heeft gewonnen", 
    "Noem een punk band", 
    "Noem een lied met een kleur in de titel", 
    "Noem een muzikaal duo", 
    "Noem een lied met maar 1 woord als titel",
    "Noem een muziek festival waar je niet geweest bent", 
    "Noem een concert waar je geweest bent", 
    "Noem een hip hop groep", 
    "Noem een buitenlandse DJ", 
    "Noem een album dat is uitgekomen in de 20e eeuw",
    "Noem een zangeres uit de jaren 80", 
    "Noem een Taylor Swift lied", 
    "Noem een live album", 
    "Noem een depressief lied",
     "Noem een techno / uptempo lied",
    "Noem een lied met een dier in de titel", 
    "Noem een lied uit een film soundtrack", 
    "Noem een duet", 
    "Noem een lied met een stad in de titel",
    "Noem de zanger van een band", 
    "Noem een niet-zanger van een band", 
    "Noem een album met een mooie albumcover", 
    "Noem een nummer met een mooie betekenis",
    "zeg het n woord harde R"
};

private static final String[] COUNTRY_PROMPTS = {
    "Noem een land in Noord Afrika", 
    "Noem een land met een >50% groene vlag", 
    "Noem een land dat begint met een G", 
    "Noem een country with a coastline",
    "Noem een stad in Zuid Amerika", 
    "Noem 2 grenzende landen in Azië", 
    "Noem een Europese niet-hoofdstad", 
    "Noem een land dat Spaans spreekt",
    "Noem een land met meer dan 100 millioen mensen", 
    "Noem een land dat een eiland is", 
    "Noem een land dat grenst aan Turkije", 
    "Noem een land met een woestijn",
    "Noem een land dat de Olympische Spelen heeft gehost", 
    "Noem een Europese munt buiten de Euro", 
    "Noem een land met een monarchie",
    "Noem een land met een >50% blauwe vlag", 
    "Noem een land dat niet eindigt op '-land'", 
    "Noem een land dat eindigt op een E", 
    "Noem een land in het Midden Oosten",
    "Noem een land met een jungle", 
    "Noem een land dat het WK heeft gehost", 
    "Noem een land dat grenst aan China", 
    "Noem een van de Verenigde Staten", 
    "Noem een van de TVTAS eilanden"
};

private static final String[] DRUG_PROMPTS = {
    "Noem een legale drug",
    "Noem een pijnstiller",
    "Noem een recreatieve drug",
    "Noem een drug die in ziekenhuizen wordt gebruikt",
    "Noem een drug die begint met een A",
    "Noem een vrij verkrijgbare drug",
    "Noem een drug tegen angst",
    "Noem een receptplichtige drug",
    "Noem een stimulerende drug",
    "Noem een kalmerende drug",
    "Noem een psychedelische drug",
    "Noem een soort antibioticum",
    "Noem een chemotherapie-medicijn",
    "Noem een ontstekingsremmer",
    "Noem een drug die je moet injecteren",
    "Noem een medicijn tegen hoge bloeddruk",
    "Noem een anticonceptiemiddel",
    "Noem een slaappil",
    "Noem een drug die eindigt op '-ine'",
    "Noem een drug die vaak wordt misbruikt",
    "Noem een drug die begint met een M",
    "Noem een drug die je in een EHBO-kit kunt vinden",
    "Noem een medicijn tegen depressie",
    "Noem een vaccin",
    "Noem een hoestremmer",
    "Noem een medicijn tegen diabetes",
    "Noem een drug die wordt gebruikt bij operaties",
    "Noem een verboden drug",
    "Noem een medicijn voor mentale gezondheid",
    "Noem een drug die je bij de apotheek kunt kopen"
};

    private static final Random rand = new Random();
    private static String currentPrompt = "";
    private static String currentCategory = "";
    private static boolean show404 = false;
    private static int nextCounter = 0;
    // Removed unused variable tryAgainCounter
    private static int totalCounter = 0;
    private static boolean started = false;
    private static boolean showCategories = true;
    private static boolean autoMode = false;
    // Track prompts used during the application's lifetime (until shutdown)
    private static final Set<String> usedPrompts = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void registerRoutes() {
        get("/nameit", (req, res) -> {
            res.type("text/html");
            return htmlPage(currentPrompt, show404, started, showCategories, currentCategory);
        });
        post("/toggleauto", (req, res) -> {
            // Only toggle autoMode, do not reset timer or game state
            autoMode = !autoMode;
            // Do NOT touch started, showCategories, currentPrompt, currentCategory, or counters
            return "ok";
        });
        post("/selectcategory", (req, res) -> {
            started = true;
            showCategories = false;
            if (autoMode) {
                String[] cats = {"Films", "Muziek", "Landen", "Drugs"};
                currentCategory = cats[rand.nextInt(cats.length)];
            } else {
                currentCategory = req.queryParams("category");
            }
            currentPrompt = randomPromptForCategory(currentCategory);
            return "ok";
        });
        post("/nextnameit", (req, res) -> {
            if (show404) {
                show404 = false;
                started = false;
                showCategories = true;
                currentPrompt = "";
                currentCategory = "";
                // keep counters unchanged
                return "ok";
            }
            showCategories = true;
            started = false;
            nextCounter++;
            totalCounter += 1;
            currentPrompt = "";
            currentCategory = "";
            return "ok";
        });
        post("/resetnameit", (req, res) -> {
            nextCounter = 0;
            totalCounter = 0;
            show404 = false;
            started = false;
            showCategories = true;
            currentPrompt = "";
            currentCategory = "";
            return "ok";
        });
        post("/timeoutnameit", (req, res) -> {
            show404 = true;
            return "timeout";
        });
    }

    private static String randomPromptForCategory(String category) {
        String[] pool;
        switch (category) {
            case "Films":
                pool = MOVIE_PROMPTS; break;
            case "Muziek":
                pool = MUSIC_PROMPTS; break;
            case "Landen":
                pool = COUNTRY_PROMPTS; break;
            case "Drugs":
                pool = DRUG_PROMPTS; break;
            default:
                return "Name something";
        }

        // Try to pick an unused prompt; if all used, inform the player
        // Attempt a bounded number of random tries for efficiency
        int attempts = 0;
        int maxAttempts = pool.length * 2;
        while (attempts < maxAttempts) {
            String candidate = pool[rand.nextInt(pool.length)];
            if (!usedPrompts.contains(candidate)) {
                usedPrompts.add(candidate);
                return candidate;
            }
            attempts++;
        }

        // Fallback: scan linearly for any unused, else report exhaustion
        for (String p : pool) {
            if (!usedPrompts.contains(p)) {
                usedPrompts.add(p);
                return p;
            }
        }
        return "No prompts left in " + category + ". Reset the app to start over.";
    }
    private static String htmlPage(String prompt, boolean error, boolean started, boolean showCategories, String currentCategory) {
        String autoSwitch = "<div style='margin-bottom:18px;display:flex;justify-content:center;align-items:center;'><label style='font-family:inherit;font-size:1.1em;color:#ffd600;display:flex;align-items:center;gap:12px;'>"
            + "<span style='display:inline-block;width:32px;height:32px;position:relative;'>"
            + "<input type='checkbox' id='autoSwitch' style='appearance:none;width:32px;height:32px;background:#222;border:3px solid #ffd600;border-radius:8px;box-shadow:0 2px 8px #ffd600;outline:none;cursor:pointer;position:absolute;top:0;left:0;'" + (autoMode ? " checked" : "") + ">"
            + "<span id='retroCheck' style='position:absolute;top:0;left:0;width:32px;height:32px;pointer-events:none;display:" + (autoMode ? "flex" : "none") + ";justify-content:center;align-items:center;'>"
            + "<svg width='32' height='32' style='display:block;margin:0 auto;'><rect x='0' y='0' width='32' height='32' rx='8' fill='none'/><polyline points='10,18 16,24 24,10' style='fill:none;stroke:#ffd600;stroke-width:4;stroke-linecap:round;stroke-linejoin:round;'/></svg>"
            + "</span></span>Auto Mode</label></div>"
            + "<script>document.addEventListener('DOMContentLoaded',function(){var sw=document.getElementById('autoSwitch');var retro=document.getElementById('retroCheck');if(sw){sw.onchange=function(){fetch('/toggleauto',{method:'POST'}).then(function(){location.reload();});};sw.addEventListener('input',function(){if(sw.checked){retro.style.display='flex';}else{retro.style.display='none';}});}});</script>";
        String timerDiv = (!error && started) ? "<div id='timer' style='font-family:inherit;font-size:2em;margin-bottom:10px;color:#ffd600;background:rgba(0,0,0,0.5);border:2px solid #ffd600;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #222;'></div>" : "";
        String errorMsg = error ? "<h1 style='font-family:inherit;color:#ffd600;background:rgba(50,0,0,0.7);border:2px solid #d32f2f;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>404 Error: Time's up!</h1>" : "";
        String totalDiv = "<div style='margin-top:40px;font-family:inherit;font-size:1.5em;background:rgba(0,0,0,0.8);color:#ffd600;padding:20px 40px;border-radius:24px;box-shadow:0 0 40px #222;text-shadow:0 2px 8px #222;border:3px solid #ffd600;display:inline-block;'>Pof counter: "+totalCounter+"</div>";
        String homeBtn = "<a href='/' id='homebtn' style='position:fixed;top:24px;left:24px;z-index:10;text-decoration:none;'><button style='background:#222;border:3px solid #ffd600;border-radius:50%;width:64px;height:64px;display:flex;align-items:center;justify-content:center;box-shadow:0 4px 16px #111;cursor:pointer;'><svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='36' height='36' fill='#ffd600'><path d='M12 3l9 9-1.5 1.5L18 12.5V20a1 1 0 0 1-1 1h-4v-5H11v5H7a1 1 0 0 1-1-1v-7.5l-1.5 1.5L3 12l9-9z'/></svg></button></a>";
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Name It</title>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("body{font-family:'Press Start 2P',monospace;text-align:center;background:#222;min-height:100vh;overflow-x:hidden;}");
        html.append(".starbg{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}");
        html.append(".arcade-frame{position:relative;z-index:1;max-width:500px;margin:60px auto 0 auto;padding:40px 20px 32px 20px;background:linear-gradient(135deg,#222 60%,#333 100%);border:8px solid #ffd600;border-radius:32px;box-shadow:0 0 60px #000,0 0 0 12px #d32f2f,0 0 0 24px #388e3c;}");
        html.append("}");
        html.append("h1,h2,h3{font-family:inherit;}");
        html.append("#btnrow{display:flex;justify-content:center;align-items:center;margin-top:30px;z-index:2;}#btnrow button{margin:0 10px;}@media(max-width:600px){#btnrow{flex-direction:column;}#btnrow button{margin:10px 0;}}#homebtn button:hover{background:#ffd600;color:#222;}#homebtn svg{vertical-align:middle;}.catrow{display:flex;justify-content:center;align-items:center;gap:40px;margin-top:60px;z-index:2;}.catbtn{background:#222;border:3px solid #ffd600;border-radius:20px;padding:32px 40px;display:flex;flex-direction:column;align-items:center;cursor:pointer;transition:background 0.2s,color 0.2s;box-shadow:0 2px 16px #111;}.catbtn:hover{background:#ffd600;color:#222;}.caticon{font-size:3em;margin-bottom:16px;}.catlabel{font-size:1.2em;font-weight:bold;letter-spacing:2px;}");
        html.append(".arcade-btns2{display:flex;flex-direction:column;gap:32px;margin-top:40px;}"
                + "button.arcade-btn{font-family:'Press Start 2P',monospace;font-size:1.2em;padding:24px 0;border:4px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;box-shadow:0 4px 0 #d32f2f,0 8px 0 #388e3c,0 0 16px #ffd600;cursor:pointer;transition:background 0.2s, color 0.2s;width:70%;max-width:400px;min-width:180px;margin:0 auto;text-shadow:0 0 8px #ffd600;}"
                + "button.arcade-btn:hover{background:#ffd600;color:#222;text-shadow:0 0 8px #222;}");
        html.append("</style>");
        html.append("<script>");
        html.append("let timer;let timeLeft=15;function startTimer(){document.getElementById('timer').innerText='Timer: '+timeLeft+'s';timer=setInterval(function(){timeLeft--;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';if(timeLeft<=0){clearInterval(timer);fetch('/timeoutnameit',{method:'POST'}).then(function(){location.reload();});}},1000);}function next(){fetch('/nextnameit',{method:'POST'}).then(function(){location.reload();});}function selectCategory(cat){fetch('/selectcategory',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},body:'category='+cat}).then(function(){location.reload();});}window.onload=function(){if(");
        html.append(started);
        html.append("&&!" );
        html.append(error);
        html.append("){startTimer();}};");
        html.append("document.addEventListener('keydown',function(e){if(e.code==='Space'&&typeof timeLeft==='number'&&timer){timeLeft+=3;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';}});");
        // Arcade star background animation and shooting stars
        html.append("document.addEventListener('DOMContentLoaded',function(){"
            + "let canvas=document.createElement('canvas');canvas.className='starbg';canvas.width=window.innerWidth;canvas.height=window.innerHeight;document.body.appendChild(canvas);"
            + "let ctx=canvas.getContext('2d');let stars=[];for(let i=0;i<120;i++){stars.push({x:Math.random()*canvas.width,y:Math.random()*canvas.height,r:Math.random()*2+1,speed:Math.random()*0.7+0.3});}"
            + "function drawStars(){ctx.clearRect(0,0,canvas.width,canvas.height);for(let s of stars){ctx.beginPath();ctx.arc(s.x,s.y,s.r,0,2*Math.PI);ctx.fillStyle='#ffd600';ctx.shadowColor='#fff';ctx.shadowBlur=8;ctx.fill();s.x+=s.speed;if(s.x>canvas.width){s.x=0;s.y=Math.random()*canvas.height;}}requestAnimationFrame(drawStars);}drawStars();"
            // Shooting star logic
            + "function shootStar(dir){var shoot=document.createElement('div');shoot.className='shooting-star';shoot.style.position='fixed';shoot.style.width='60px';shoot.style.height='4px';shoot.style.background='linear-gradient(90deg,#fff,#ffd600 80%)';shoot.style.borderRadius='2px';shoot.style.opacity='0.8';shoot.style.zIndex='2';shoot.style.boxShadow='0 0 16px #ffd600';var start,end,duration=1200;var angle=0;if(dir==0){shoot.style.top=Math.random()*80+'vh';shoot.style.left='-60px';start={left:'-60px',opacity:0.8};end={left:'110vw',opacity:0};}else if(dir==1){shoot.style.left=Math.random()*80+'vw';shoot.style.top='-20px';shoot.style.width='4px';shoot.style.height='60px';start={top:'-20px',opacity:0.8};end={top:'110vh',opacity:0};}else if(dir==2){shoot.style.top='-20px';shoot.style.left='-60px';shoot.style.width='60px';shoot.style.height='4px';angle=45;shoot.style.transform='rotate(45deg)';start={top:'-20px',left:'-60px',opacity:0.8};end={top:'110vh',left:'110vw',opacity:0};}else if(dir==3){shoot.style.top='-20px';shoot.style.left='calc(100vw - 60px)';shoot.style.width='60px';shoot.style.height='4px';angle=-45;shoot.style.transform='rotate(-45deg)';start={top:'-20px',left:'calc(100vw - 60px)',opacity:0.8};end={top:'110vh',left:'-60px',opacity:0};}else if(dir==4){shoot.style.left='-60px';shoot.style.top=Math.random()*80+'vh';shoot.style.width='60px';shoot.style.height='4px';start={left:'-60px',opacity:0.8};end={left:'110vw',opacity:0};}else{shoot.style.left=Math.random()*80+'vw';shoot.style.top='-20px';shoot.style.width='4px';shoot.style.height='60px';start={top:'-20px',opacity:0.8};end={top:'110vh',opacity:0};}document.body.appendChild(shoot);shoot.animate([start,end],{duration:duration,easing:'ease-out'});setTimeout(function(){shoot.remove();},duration+100);}"
            // 3 independent timers, min 2s between spawns, max 5s
            + "function starTimer(){let min=2000,max=5000;let t=function(){shootStar(Math.floor(Math.random()*6));setTimeout(t,Math.random()*(max-min)+min);};setTimeout(t,Math.random()*(max-min)+min);}for(let i=0;i<3;i++){starTimer();}"
            // Click anywhere outside arcade-frame to spawn extra star
            + "document.body.addEventListener('mousedown',function(e){var frame=document.querySelector('.arcade-frame');if(!frame||!frame.contains(e.target)){shootStar(Math.floor(Math.random()*6));}});"
            + "});"
        );
        html.append("</script></head><body>");
        html.append(homeBtn);
        html.append("<div class='arcade-frame'>");
        html.append("<div class='arcade-btns'>");
        html.append(errorMsg).append("<br>");
        if (showCategories && !autoMode) {
            html.append("<form><button class='arcade-btn' type='button' onclick=\"selectCategory('Movies')\">Movies</button></form>");
            html.append("<form><button class='arcade-btn' type='button' onclick=\"selectCategory('Music')\">Music</button></form>");
            html.append("<form><button class='arcade-btn' type='button' onclick=\"selectCategory('Countries')\">Countries</button></form>");
            html.append("<form><button class='arcade-btn' type='button' onclick=\"selectCategory('Drugs')\">Drugs</button></form>");
        } else if (started || (showCategories && autoMode)) {
            // If autoMode is enabled and showCategories is true, immediately pick a category and start
            if (showCategories && autoMode) {
                html.append("<script>fetch('/selectcategory',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},body:'category=auto'}).then(function(){location.reload();});</script>");
            }
            html.append("<br>").append(timerDiv).append("<br>");
            html.append("<h2 style='color:#ffd600;text-shadow:0 0 8px #222;'>");
            html.append(currentCategory);
            html.append("</h2>");
            html.append("<br><h3 style='color:#fff;background:rgba(0,0,0,0.5);border:2px solid #ffd600;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>");
            html.append(prompt);
            html.append("</h3><br>");
            html.append("<form><button class='arcade-btn' type='button' onclick='next()'>Next</button></form>");
        }
        html.append("</div>");
        html.append(autoSwitch);
        html.append(totalDiv);
        if (error) {
            html.append("<br><form><button type='button' style='font-family:inherit;margin-top:20px;padding:12px 36px;font-size:1.2em;border:2px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;cursor:pointer;box-shadow:0 2px 12px #111;text-shadow:0 2px 8px #222;' onclick=\"fetch('/resetnameit',{method:'POST'}).then(()=>location.reload())\">Reset</button></form>");
            html.append("<form><button class='arcade-btn' type='button' style='margin-top:10px;' onclick=\"fetch('/nextnameit',{method:'POST'}).then(()=>location.reload())\">Next</button></form>");
        }
        html.append("</div>");
        html.append("</body></html>");
        return html.toString();
    }
}
