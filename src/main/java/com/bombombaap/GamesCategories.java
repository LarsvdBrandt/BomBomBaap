package com.bombombaap;

import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;

public class GamesCategories {
    private static final String[] CATEGORIES = {
        "Things that are soft", "Things that are hard", "Things that are expensive", "Things that are cheap", "Things that are fast",
        "Things that are slow", "Things that are loud", "Things that are quiet", "Things that are sweet", "Things that are sour",
        "Things that are salty", "Things that are bitter", "Things that are spicy", "Things that are green", "Things that are red",
        "Things that are yellow", "Things that are purple", "Things that are orange", "Things that are black", "Things that are white",
        "Things that are brown", "Things that are pink", "Things that are silver", "Things that are gold", "Things that are transparent",
        "Things that are sticky", "Things that are slippery", "Things that are fluffy", "Things that are shiny", "Things that are dull",
        "Things that are square", "Things that are triangular", "Things that are rectangular", "Things that are cylindrical",
        "Things that are electronic", "Things that are mechanical", "Things that are natural", "Things that are artificial",
        "Things you find at the beach", "Things you find in the forest", "Things you find in the desert", "Things you find in the city",
        "Things you find in the countryside", "Things you find in space", "Things you find underwater", "Things you find underground",
        "Things you find in a school", "Things you find in a hospital", "Things you find in a supermarket", "Things you find in a restaurant",
        "Things you find in a hotel", "Things you find in a zoo", "Things you find in a museum", "Things you find in a library",
        "Things you find in a park", "Things you find in a playground", "Things you find in a gym", "Things you find in a stadium",
        "Things you find in a church", "Things you find in a temple", "Things you find in a mosque", "Things you find in a bank",
        "Things you find in an airport", "Things you find in a train station", "Things you find in a bus station", "Things you find in a garage",
        "Things you find in a factory", "Things you find in an office", "Things you find in a farm", "Things you find in a zoo",
        "Things you find in a circus", "Things you find in a theater", "Things you find in a cinema", "Things you find in a casino",
        "Things you find in a prison", "Things you find in a police station", "Things you find in a fire station", "Things you find in a post office",
        "Things you find in a shopping mall", "Things you find in a hair salon", "Things you find in a tattoo shop", "Things you find in a bakery",
        "Things you find in a butcher shop", "Things you find in a pharmacy", "Things you find in a pet shop", "Things you find in a toy store",
        "Things you find in a hardware store", "Things you find in a clothing store", "Things you find in a shoe store", "Things you find in a jewelry store",
        "Things you find in a furniture store", "Things you find in a electronics store", "Things you find in a book store", "Things you find in a music store",
        "Things you find in a sports store", "Things you find in a car dealership", "Things you find in a gas station", "Things you find in a laundromat",
        "Things you find in a recycling center", "Things you find in a dump", "Things you find in a construction site", "Things you find in a warehouse",
        "Things you find in a laboratory", "Things you find in a observatory", "Things you find in a power plant", "Things you find in a water treatment plant",
        "Things you find in a wind farm", "Things you find in a solar farm", "Things you find in a nuclear plant", "Things you find in a military base",
        "Things you find in a embassy", "Things you find in a consulate", "Things you find in a parliament", "Things you find in a court",
        "Things you find in a jail", "Things you find in a morgue", "Things you find in a crematorium", "Things you find in a cemetery"
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
        return CATEGORIES[rand.nextInt(CATEGORIES.length)];
    }
    private static String htmlPage(String letter, String category, boolean error, boolean showLetter, boolean started) {
        String leftBtnColor = error ? "#888" : "#d32f2f";
        String middleBtnColor = error ? "#d32f2f" : "#ffd600";
        String rightBtnColor = error ? "#888" : "#ffd600";
        String btnStyle = "font-family:'Press Start 2P',monospace;padding:18px 36px;font-size:1.3em;border:4px solid #ffd600;border-radius:16px;cursor:pointer;margin:0 16px;min-width:160px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 4px 24px #111,0 0 16px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String bigBtnStyle = "font-family:'Press Start 2P',monospace;padding:28px 56px;font-size:2.2em;border:4px solid #ffd600;border-radius:20px;cursor:pointer;margin:0 16px;min-width:200px;background:linear-gradient(135deg,#ffd600 0%,#d32f2f 50%,#1a7f2b 100%);color:#222;box-shadow:0 8px 32px #111,0 0 24px #ffd600;text-shadow:0 2px 8px #222;transition:background 0.2s,color 0.2s;outline:3px solid #ffd600;";
        String timerDiv = (!error && started) ? "<div id='timer' style='font-family:inherit;font-size:2em;margin-bottom:10px;color:#ffd600;background:rgba(0,0,0,0.5);border:2px solid #ffd600;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #222;'></div>" : "";
        String errorMsg = error ? "<h1 style='font-family:inherit;color:#ffd600;background:rgba(50,0,0,0.7);border:2px solid #d32f2f;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>404 Error: Time's up!</h1>" : "";
        String letterDiv = showLetter ? "<h2 style='color:#ffd600;text-shadow:0 0 8px #222;'>Letter: " + letter + "</h2>" : "";
        String categoryDiv = "<h3 style='color:#fff;background:rgba(0,0,0,0.5);border:2px solid #ffd600;display:inline-block;padding:12px 32px;border-radius:16px;text-shadow:0 2px 8px #222;'>Category: " + category + "</h3>";
        String counterDiv = "<div style='margin:20px 0;font-size:1.2em;color:#ffd600;background:rgba(0,0,0,0.7);border:1px solid #ffd600;display:inline-block;padding:8px 24px;border-radius:12px;'>"
            + "<span>Bouncer: " + leftCounter + "</span> &nbsp; "
            + "<span>Next: " + middleCounter + "</span> &nbsp; "
            + "<span>Palindrome: " + rightCounter + "</span> &nbsp; "
            + (error ? "<span>Try Again: " + tryAgainCounter + "</span> &nbsp; " : "")
            + "</div>";
        String totalDiv = "<div style='margin-top:40px;font-family:inherit;font-size:1.5em;background:rgba(0,0,0,0.8);color:#ffd600;padding:20px 40px;border-radius:24px;box-shadow:0 0 40px #222;text-shadow:0 2px 8px #222;border:3px solid #ffd600;display:inline-block;'>Pof counter: " + totalCounter + "</div>";
        String homeBtn = "<a href='/' id='homebtn' style='position:fixed;top:24px;left:24px;z-index:10;text-decoration:none;'><button style='background:#222;border:3px solid #ffd600;border-radius:50%;width:64px;height:64px;display:flex;align-items:center;justify-content:center;box-shadow:0 4px 16px #111;cursor:pointer;'><svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='36' height='36' fill='#ffd600'><path d='M12 3l9 9-1.5 1.5L18 12.5V20a1 1 0 0 1-1 1h-4v-5H11v5H7a1 1 0 0 1-1-1v-7.5l-1.5 1.5L3 12l9-9z'/></svg></button></a>";
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>BomBomBaap</title>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("body{font-family:'Press Start 2P',monospace;text-align:center;background:#222;min-height:100vh;overflow-x:hidden;}");
        html.append(".starbg{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}");
        html.append(".arcade-frame{position:relative;z-index:1;max-width:800px;margin:60px auto 0 auto;padding:40px 20px 32px 20px;background:linear-gradient(135deg,#222 60%,#333 100%);border:8px solid #ffd600;border-radius:32px;box-shadow:0 0 60px #000,0 0 0 12px #d32f2f,0 0 0 24px #388e3c;}");
        html.append("h1,h2,h3{font-family:inherit;}");
        html.append("#btnrow{display:flex;justify-content:center;align-items:center;margin-top:30px;z-index:2;}#btnrow button{margin:0 10px;}@media(max-width:600px){#btnrow{flex-direction:column;}#btnrow button{margin:10px 0;}}#homebtn button:hover{background:#ffd600;color:#222;}#homebtn svg{vertical-align:middle;}");
        html.append(".arcade-btns{display:flex;flex-direction:column;gap:32px;margin-top:40px;}"
                + "button.arcade-btn{font-family:'Press Start 2P',monospace;font-size:1.2em;padding:24px 0;border:4px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;box-shadow:0 4px 0 #d32f2f,0 8px 0 #388e3c,0 0 16px #ffd600;cursor:pointer;transition:background 0.2s, color 0.2s;width:70%;max-width:400px;min-width:180px;margin:0 auto;text-shadow:0 0 8px #ffd600;}"
                + "button.arcade-btn:hover{background:#ffd600;color:#222;text-shadow:0 0 8px #222;}");
        html.append("</style>");
        html.append("<script>");
        html.append("let timer;let timeLeft=15;function startTimer(){document.getElementById('timer').innerText='Timer: '+timeLeft+'s';timer=setInterval(function(){timeLeft--;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';if(timeLeft<=0){clearInterval(timer);fetch('/timeout',{method:'POST'}).then(function(){location.reload();});}},1000);}function next(){fetch('/next',{method:'POST'}).then(function(){location.reload();});}function left(){fetch('/left',{method:'POST'}).then(function(){location.reload();});}function right(){fetch('/right',{method:'POST'}).then(function(){location.reload();});}window.onload=function(){if(");
        html.append(started);
        html.append("&&!" );
        html.append(error);
        html.append("){startTimer();}};" );
        html.append("document.addEventListener('keydown',function(e){if(e.code==='Space'&&typeof timeLeft==='number'&&timer){timeLeft+=3;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';}});");
        // Arcade star background animation
        html.append("document.addEventListener('DOMContentLoaded',function(){let canvas=document.createElement('canvas');canvas.className='starbg';canvas.width=window.innerWidth;canvas.height=window.innerHeight;document.body.appendChild(canvas);let ctx=canvas.getContext('2d');let stars=[];for(let i=0;i<120;i++){stars.push({x:Math.random()*canvas.width,y:Math.random()*canvas.height,r:Math.random()*2+1,speed:Math.random()*0.7+0.3});}function drawStars(){ctx.clearRect(0,0,canvas.width,canvas.height);for(let s of stars){ctx.beginPath();ctx.arc(s.x,s.y,s.r,0,2*Math.PI);ctx.fillStyle='#ffd600';ctx.shadowColor='#fff';ctx.shadowBlur=8;ctx.fill();s.x+=s.speed;if(s.x>canvas.width){s.x=0;s.y=Math.random()*canvas.height;}}requestAnimationFrame(drawStars);}drawStars();});");
        html.append("</script></head><body>");
        html.append(homeBtn);
        html.append("<div class='starbg'></div><div class='arcade-frame'>");
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
            html.append(">Bouncer</button>");
            html.append("<button style='");
            html.append(bigBtnStyle);
            html.append("background:");
            html.append(middleBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='next()'>");
            html.append(error ? "Try Again" : "Next");
            html.append("</button>");
            html.append("<button style='");
            html.append(btnStyle);
            html.append("background:");
            html.append(rightBtnColor);
            html.append(";' class='arcade-btn' onmouseover=\"this.style.background='#ffd600';this.style.color='#222';\" onmouseout=\"this.style.background='#222';this.style.color='#ffd600';\" onclick='right()' ");
            html.append(error ? "disabled" : "");
            html.append(">Palindrome</button>");
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
        html.append("</body></html>");
        return html.toString();
    }
}
