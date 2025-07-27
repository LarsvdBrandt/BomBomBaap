package com.bombombaap;

import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    private static final String[] CATEGORIES = {
        "Things that are blue", "Animals", "Countries", "Fruits", "Sports", "Cities", "Movies", "Foods",
        "Things that are round", "Vegetables", "Car brands", "Famous people", "Mountains", "Rivers", "Planets", "Languages",
        "Things you wear", "Musical instruments", "Board games", "Video games", "Cartoon characters", "TV shows", "Songs", "Bands",
        "Things in a kitchen", "Things in a bathroom", "Things in a garden", "Types of trees", "Types of flowers", "Birds", "Insects",
        "Things that fly", "Things that swim", "Things that crawl", "Things that jump", "Things that are hot", "Things that are cold",
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

    public static void main(String[] args) {
        port(3004); // Set to 3004 to match README and instructions
        get("/", (req, res) -> {
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
        // Button colors
        String leftBtnColor = error ? "#888" : "#d32f2f"; // red or grey
        String middleBtnColor = error ? "red" : "#388e3c"; // red or dark green
        String rightBtnColor = error ? "#888" : "#ffd600"; // yellow or grey
        String btnStyle = "padding:10px 20px;font-size:1.2em;border:1px solid black;border-radius:10px;cursor:pointer;margin:0 10px;min-width:120px;";
        String bigBtnStyle = "padding:20px 40px;font-size:2em;border:1px solid black;border-radius:10px;cursor:pointer;margin:0 10px;min-width:160px;";
        String timerDiv = "<div id='timer' style='font-size:2em;margin-bottom:10px;color:#fff;background:rgba(0,0,0,0.3);border:1px solid black;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #000;'></div>";
        String errorMsg = error ? "<h1 style='color:#fff;background:rgba(0,0,0,0.5);border:1px solid black;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #000;'>404 Error: Time's up!</h1>" : "";
        String letterDiv = showLetter ? "<h2 style='color:#fff;background:rgba(0,0,0,0.3);border:1px solid black;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #000;'>Letter: "+letter+"</h2>" : "";
        String categoryDiv = "<h3 style='color:#fff;background:rgba(0,0,0,0.3);border:1px solid black;display:inline-block;padding:8px 24px;border-radius:12px;text-shadow:0 1px 2px #000;'>Category: " + category + "</h3>";
        String counterDiv = "<div style='margin:20px 0;font-size:1.2em;color:#fff;background:rgba(0,0,0,0.7);border:1px solid black;display:inline-block;padding:8px 24px;border-radius:12px;'>"
            + "<span>Bouncer: " + leftCounter + "</span> &nbsp; "
            + "<span>Next: " + middleCounter + "</span> &nbsp; "
            + "<span>Palindrome: " + rightCounter + "</span> &nbsp; "
            + (error ? "<span>Try Again: " + tryAgainCounter + "</span> &nbsp; " : "")
            + "</div>";
        String totalDiv = "<div style='margin-top:40px;font-size:1.5em;background:rgba(0,0,0,0.7);color:#fff;padding:20px;border-radius:20px;box-shadow:0 0 30px #222;'>Pof counter: "+totalCounter+"</div>";
        String rastafariBg = "body{font-family:sans-serif;text-align:center;"
            + "background: repeating-linear-gradient(135deg, #222 0px, #222 40px, #1a7f2b 40px, #1a7f2b 80px, #ffd600 80px, #ffd600 120px, #d32f2f 120px, #d32f2f 160px, #222 160px, #222 200px), url('https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80');background-size:cover;min-height:100vh;}";
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>BomBomBaap</title>");
        html.append("<style>").append(rastafariBg)
            .append("h1{margin-top:40px;}#btnrow{display:flex;justify-content:center;align-items:center;margin-top:30px;}#btnrow button{margin:0 10px;}@media(max-width:600px){#btnrow{flex-direction:column;}#btnrow button{margin:10px 0;}}")
            .append("</style>");
        html.append("<script>");
        html.append("let timer;let timeLeft=10;function startTimer(){document.getElementById('timer').innerText='Timer: '+timeLeft+'s';timer=setInterval(function(){timeLeft--;document.getElementById('timer').innerText='Timer: '+timeLeft+'s';if(timeLeft<=0){clearInterval(timer);fetch('/timeout',{method:'POST'}).then(function(){location.reload();});}},1000);}function next(){fetch('/next',{method:'POST'}).then(function(){location.reload();});}function left(){fetch('/left',{method:'POST'}).then(function(){location.reload();});}function right(){fetch('/right',{method:'POST'}).then(function(){location.reload();});}window.onload=function(){if(")
            .append(started)
            .append("&&!")
            .append(error)
            .append("){startTimer();}};");
        html.append("</script></head><body>");
        html.append(errorMsg).append("<br>").append(letterDiv);
        if (started) {
            html.append("<br>").append(timerDiv).append("<br>").append(categoryDiv).append("<br>");
            html.append("<div id='btnrow'>");
            html.append("<button style='").append(btnStyle).append("background:").append(leftBtnColor).append(";' onclick='left()' ").append(error ? "disabled" : "").append(">Bouncer</button>");
            html.append("<button style='").append(bigBtnStyle).append("background:").append(middleBtnColor).append(";' onclick='next()'>").append(error ? "Try Again" : "Next").append("</button>");
            html.append("<button style='").append(btnStyle).append("background:").append(rightBtnColor).append(";' onclick='right()' ").append(error ? "disabled" : "").append(">Palindrome</button>");
            html.append("</div>");
            html.append(counterDiv);
        } else {
            html.append("<br><div id='btnrow'>");
            html.append("<button style='").append(bigBtnStyle).append("background:").append(middleBtnColor).append(";' onclick='next()'>Start</button>");
            html.append("</div>");
        }
        html.append(totalDiv);
        if (error) {
            html.append("<br><form><button type='button' style='margin-top:20px;padding:10px 30px;font-size:1.1em;border:1px solid black;border-radius:10px;background:#222;color:#fff;cursor:pointer;' onclick=\"fetch('/reset',{method:'POST'}).then(()=>location.reload())\">Reset</button></form>");
        }
        html.append("</body></html>");
        return html.toString();
    }
}
