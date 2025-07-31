package com.bombombaap;

import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;

public class GamesNameIt {
private static final String[] MOVIE_PROMPTS = {
    "Name a movie with Tom Hanks", "Name a comedy movie", "Name a movie from the 90s", "Name a movie with a dog", "Name a movie set in space",
    "Name a horror movie", "Name a Disney movie", "Name a Marvel movie", "Name a movie with time travel", "Name a movie directed by Christopher Nolan",
    "Name a black and white movie", "Name an animated movie", "Name a movie with a number in the title", "Name a romantic movie", "Name a movie with aliens",
    "Name a movie set in New York", "Name a Christmas movie", "Name a movie based on a true story", "Name a superhero movie", "Name a movie with Brad Pitt",
    "Name a movie that won an Oscar", "Name a movie with a twist ending", "Name a foreign-language movie", "Name a sequel", "Name a movie with a dragon",
    "Name a musical", "Name a zombie movie", "Name a movie about high school", "Name a war movie", "Name a movie with a famous quote"
};

private static final String[] MUSIC_PROMPTS = {
    "Name a Beatles song", "Name a rap artist", "Name a song with 'love' in the title", "Name a jazz musician", "Name a famous band",
    "Name a rock song", "Name a country artist", "Name a classical composer", "Name a song from the 80s", "Name a female singer",
    "Name a Grammy-winning artist", "Name a punk band", "Name a song with a color in the title", "Name a music duo", "Name a song with one word in the title",
    "Name a music festival", "Name a boy band", "Name a hip hop group", "Name a famous DJ", "Name an album released in the 2000s",
    "Name a song from the 70s", "Name a song by Taylor Swift", "Name a live album", "Name a sad song", "Name a fast-paced song",
    "Name a song with an animal in the title", "Name a protest song", "Name a duet", "Name a soundtrack song", "Name a song with a city in the title"
};

private static final String[] COUNTRY_PROMPTS = {
    "Name a country in Africa", "Name a country with a red flag", "Name a country starting with S", "Name a country with mountains", "Name a country with a coastline",
    "Name a landlocked country", "Name a country in South America", "Name a country in Asia", "Name a country in Europe", "Name a country that speaks Spanish",
    "Name a country with more than 100 million people", "Name a small island nation", "Name a country that borders Russia", "Name a country in the EU", "Name a country with a desert",
    "Name a cold country", "Name a tropical country", "Name a country that hosted the Olympics", "Name a country that uses the euro", "Name a country with a monarchy",
    "Name a country with a blue flag", "Name a country that ends with 'land'", "Name a country that starts with B", "Name a country without a military", "Name a country in the Middle East",
    "Name a country with a jungle", "Name a country that is an island", "Name a country that has hosted the World Cup", "Name a country that borders China", "Name a country that is part of NATO"
};

private static final String[] DRUG_PROMPTS = {
    "Name a legal drug", "Name a painkiller", "Name a recreational drug", "Name a drug used in hospitals", "Name a drug starting with A",
    "Name an over-the-counter drug", "Name a drug used to treat anxiety", "Name a prescription drug", "Name a stimulant", "Name a sedative",
    "Name a psychedelic drug", "Name a type of antibiotic", "Name a chemotherapy drug", "Name an anti-inflammatory drug", "Name a drug you inject",
    "Name a drug for high blood pressure", "Name a birth control drug", "Name a sleeping pill", "Name a drug that ends with -ine", "Name a drug commonly abused",
    "Name a drug that starts with M", "Name a drug found in a first-aid kit", "Name a drug that treats depression", "Name a vaccine", "Name a cough suppressant",
    "Name a diabetes medication", "Name a drug used in surgery", "Name a banned drug", "Name a drug used in mental health", "Name a drug found in a pharmacy"
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
                String[] cats = {"Movies", "Music", "Countries", "Drugs"};
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
        switch (category) {
            case "Movies": return MOVIE_PROMPTS[rand.nextInt(MOVIE_PROMPTS.length)];
            case "Music": return MUSIC_PROMPTS[rand.nextInt(MUSIC_PROMPTS.length)];
            case "Countries": return COUNTRY_PROMPTS[rand.nextInt(COUNTRY_PROMPTS.length)];
            case "Drugs": return DRUG_PROMPTS[rand.nextInt(DRUG_PROMPTS.length)];
            default: return "Name something";
        }
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
