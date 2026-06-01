package com.bombombaap;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    
    // private static final Set<Integer> activePlayerIds = new LinkedHashSet<>();
    public static final List<Player> activePlayers = new ArrayList<>();
    public static Integer currentActivePlayerId = null;
    public static PlayerBase playerBase;

    public static List<Player> getActivePlayers() {
        synchronized (activePlayers) {
            return Collections.unmodifiableList(new ArrayList<>(activePlayers));
        }
    }

    public static void setActivePlayers(List<Player> updatedPlayers) {
        synchronized (activePlayers) {
            activePlayers.clear();
            if (updatedPlayers == null) {
                return;
            }
            for (Player p : updatedPlayers) {
                if (p != null && !activePlayers.contains(p)) {
                    activePlayers.add(p);
                }
            }
        }
    }

    public static void saveData() {
        playerBase.saveJSON();
    }

    public static void setCurrentActivePlayer(Player player) {
        currentActivePlayerId = player == null ? null : player.playerId;
    }

    public static Integer getCurrentActivePlayerId() {
        return currentActivePlayerId;
    }

    public static void main(String[] args) throws IOException {
        int basePort = 3004;
        String portEnv = System.getenv("PORT");
        int desiredPort = basePort;
        if (portEnv != null && !portEnv.isBlank()) {
            try {
                desiredPort = Integer.parseInt(portEnv.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid PORT environment variable, falling back to default " + basePort);
            }
        }

        int selectedPort = -1;
        for (int p = desiredPort; p < desiredPort + 6; p++) {
            if (isPortAvailable(p)) {
                selectedPort = p;
                break;
            }
        }
        if (selectedPort == -1) {
            System.err.println("No available port found in range " + desiredPort + "-" + (desiredPort + 5) + ", attempting to use " + desiredPort);
            selectedPort = desiredPort;
        }

        port(selectedPort);
        System.out.println("Using port: " + selectedPort);

        String jsonString = loadPlayersJSON();
        JSONObject players = new JSONObject(jsonString);

        playerBase = new PlayerBase();
        playerBase.copyPlayerBase(players.getJSONArray("players"));
        playerBase.printPlayerBase();
        //playerBase.resetPlayerBaseStats();

        get("/", (req, res) -> {
            res.type("text/html");
            return renderHomePage(playerBase);
        });

        get("/addplayer", (req, res) -> {
            res.type("text/html");
            return """
                <html>
                <head>
                    <title>nieuwe legend</title>
                    <style>
                        @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                        body {
                            font-family: 'Press Start 2P', monospace;
                            text-align: center;
                            background: radial-gradient(circle at top, #242424 0, #111 45%, #060606 100%);
                            color: #ffd600;
                            margin: 0;
                            min-height: 100vh;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                        }
                        .panel {
                            max-width: 560px;
                            width: calc(100% - 32px);
                            padding: 36px 24px;
                            background: linear-gradient(135deg, #222 60%, #333 100%);
                            border: 8px solid #ffd600;
                            border-radius: 28px;
                            box-shadow: 0 0 60px #000, 0 0 0 12px #d32f2f, 0 0 0 24px #388e3c;
                        }
                        input {
                            font-family: inherit;
                            width: 100%;
                            max-width: 360px;
                            padding: 16px 14px;
                            margin: 12px 0;
                            border: 3px solid #ffd600;
                            border-radius: 12px;
                            background: #111;
                            color: #ffd600;
                        }
                        button {
                            font-family: inherit;
                            padding: 16px 24px;
                            border: 3px solid #ffd600;
                            border-radius: 12px;
                            background: #ffd600;
                            color: #222;
                            cursor: pointer;
                            margin-top: 14px;
                        }
                        a {
                            color: #ffd600;
                            text-decoration: none;
                            display: inline-block;
                            margin-top: 18px;
                        }
                    </style>
                </head>
                <body>
                    <div class='panel'>
                        <h1>nieuwe legend</h1>
                        <form action='/addplayer' method='post'>
                            <input name='name' placeholder='hoe heet je broer' required>
                            <br><button type='submit'>bro is on the team</button>
                        </form>
                        <a href='/'>terug</a>
                    </div>
                </body>
                </html>
                """;
        });

        post("/addplayer", (req, res) -> {
            String name = req.queryParams("name");
            if (name == null || name.isBlank()) {
                res.status(400);
                return "Player Name Required";
            }

            playerBase.addPlayer(name.trim());
            res.redirect("/");
            return "";
        });

        post("/activate-player", (req, res) -> {
            String playerId = req.queryParams("playerId");
            if (playerId != null && !playerId.isBlank()) {
                try {
                    int id = Integer.parseInt(playerId.trim());
                    Player p = playerBase.getPlayer(id, "");
                    if (p != null && !activePlayers.contains(p)) {
                        activePlayers.add(p);
                        p.setGamesPlayed(1);
                    }
                } catch (NumberFormatException ignored) {
                    // ignore malformed ids
                }
            }
            redirectBack(req.headers("Referer"), res);
            return "";
        });

        post("/deactivate-player", (req, res) -> {
            String playerId = req.queryParams("playerId");
            if (playerId != null && !playerId.isBlank()) {
                try {
                    int id = Integer.parseInt(playerId.trim());
                    Player p = playerBase.getPlayer(id, "");
                    if (p != null) {
                        activePlayers.remove(p);
                        p.setGamesPlayed(-1);
                    }
                } catch (NumberFormatException ignored) {
                    // Ignore malformed ids.
                }
            }
            redirectBack(req.headers("Referer"), res);
            return "";
        });
        post("/move-active-player", (req, res) -> {
            String playerId = req.queryParams("playerId");
            String direction = req.queryParams("direction");
            if (playerId != null && !playerId.isBlank() && direction != null && !direction.isBlank()) {
                try {
                    int id = Integer.parseInt(playerId.trim());
                    moveActivePlayer(id, direction.trim());
                } catch (NumberFormatException ignored) {
                    // ignore malformed ids
                }
            }
            redirectBack(req.headers("Referer"), res);
            return "";
        });

        post("/reset-all-stats", (req, res) -> {
            playerBase.resetPlayerBaseStats();
            activePlayers.clear();
            currentActivePlayerId = null;
            res.redirect("/");
            return "";
        });

        try {
            GamesCategories.registerRoutes();
        } catch (Throwable t) {
            System.err.println("Failed to register GamesCategories routes:");
            t.printStackTrace();
        }
        try {
            GamesNameIt.registerRoutes();
        } catch (Throwable t) {
            System.err.println("Failed to register GamesNameIt routes:");
            t.printStackTrace();
        }
        try {
            GamesHitOrStand.registerRoutes();
        } catch (Throwable t) {
            System.err.println("Failed to register GamesHitOrStand routes:");
            t.printStackTrace();
        }
    };

    private static boolean isPortAvailable(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static String loadPlayersJSON() throws IOException {
        try (InputStream resource = Main.class.getResourceAsStream("/players.json")) {
            if (resource != null) {
                return new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

        if (Files.exists(Paths.get("players.json"))) {
            return Files.readString(Paths.get("players.json"));
        }

        return """
        {
            "player": {
                "name": "Player",
                "id": 1,
                "stats": {
                    "gamesPlayed": 0,
                    "bouncers": 0,
                    "palindromes": 0,
                    "ELO": 1000
                }
            }
        }
        """;
    }

    private static String renderHomePage(PlayerBase playerBase) {
        return """
            <html>
            <head>
                <title>BomBomBaap</title>
                <style>
                    @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
                    body {
                        font-family: 'Press Start 2P', monospace;
                        text-align: center;
                        background: radial-gradient(circle at top, #242424 0, #111 45%, #060606 100%);
                        margin: 0;
                        overflow-x: hidden;
                        overflow-y: auto;
                        scrollbar-width: none;
                        -ms-overflow-style: none;
                        color: #ffd600;
                        min-height: 100vh;
                    }
                    body::-webkit-scrollbar {
                        width: 0;
                        height: 0;
                    }
                    .top-left-link {
                        position: fixed;
                        top: 20px;
                        left: 20px;
                        z-index: 10;
                        text-decoration: none;
                    }
                    .top-left-link button {
                        font-family: 'Press Start 2P', monospace;
                        font-size: 0.78em;
                        padding: 16px 20px;
                        border: 4px solid #ffd600;
                        border-radius: 16px;
                        background: linear-gradient(135deg, #222 0%, #111 100%);
                        color: #ffd600;
                        box-shadow: 0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600;
                        cursor: pointer;
                        transition: transform 0.15s, background 0.2s, color 0.2s;
                    }
                    .top-left-link button:hover {
                        background: #ffd600;
                        color: #222;
                        transform: translateY(-1px);
                    }
                    .side-panel {
                        position: fixed;
                        top: 108px;
                        width: 260px;
                        max-height: calc(100vh - 156px);
                        overflow-y: auto;
                        scrollbar-width: none;
                        -ms-overflow-style: none;
                        padding: 18px 14px;
                        background: linear-gradient(135deg, #2a2a2a 0%, #1c1c1c 52%, #2f2f2f 100%);
                        border: 4px solid #ffd600;
                        border-radius: 22px;
                        box-shadow: 0 0 44px #000, 0 0 0 6px #d32f2f, 0 0 0 12px #388e3c;
                        z-index: 1;
                    }
                    .side-panel::-webkit-scrollbar {
                        }
                    .left-panel {
                        left: 24px;
                    }
                    .right-panel {
                        right: 24px;
                    }
                    .panel-title {
                        margin: 0 0 18px 0;
                        font-size: 0.95em;
                        line-height: 1.4;
                        color: #ffd600;
                        text-shadow: 0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;
                    }
                    .active-panel-title {
                        margin: 0 0 18px 0;
                        font-size: 0.95em;
                        line-height: 1.4;
                        letter-spacing: 2px;
                        color: #ffd600;
                        text-shadow: 0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;
                    }
                    .player-list {
                        display: flex;  
                        flex-direction: column;
                        gap: 12px;
                    }
                    .player-card {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        gap: 12px;
                        padding: 12px;
                        border: 2px solid #ffd600;
                        border-radius: 16px;
                        background: rgba(0, 0, 0, 0.35);
                    }
                    .player-card.current-player-card {
                        border: 3px solid #ffd600;
                        background: rgba(0, 0, 0, 0.55);
                        box-shadow: 0 0 18px #ffd600, 0 0 36px #d32f2f, 0 0 0 4px #388e3c;
                    }
                    .player-meta {
                        text-align: left;
                        min-width: 0;
                    }
                    .player-name {
                        font-size: 0.8em;
                        line-height: 1.35;
                        margin-bottom: 8px;
                        color: #fff7b3;
                        word-break: break-word;
                    }
                    .player-elo {
                        font-size: 0.65em;
                        color: #ffd600;
                    }
                    .player-add {
                        font-family: 'Press Start 2P', monospace;
                        width: 48px;
                        height: 48px;
                        border: 3px solid #ffd600;
                        border-radius: 14px;
                        background: linear-gradient(135deg, #222 0%, #111 100%);
                        color: #ffd600;
                        box-shadow: 0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600;
                        cursor: pointer;
                        font-size: 1.05em;
                        line-height: 1;
                    }
                    .player-actions {
                        display: flex;
                        align-items: center;
                        gap: 8px;
                    }
                    .player-order-buttons {
                        display: flex;
                        flex-direction: column;
                        gap: 6px;
                    }
                    .player-move {
                        font-family: inherit;
                        width: 32px;
                        height: 32px;
                        border: 2px solid #ffd600;
                        border-radius: 10px;
                        background: linear-gradient(135deg, #222 0%, #111 100%);
                        color: #ffd600;
                        box-shadow: 0 2px 0 #d32f2f, 0 4px 0 #388e3c, 0 0 12px #ffd600;
                        cursor: pointer;
                        font-size: 0.72em;
                        line-height: 1;
                        padding: 0;
                    }      
                    .player-move:disabled,
                    .player-add:disabled {
                        opacity: 0.45;
                        cursor: not-allowed;
                    }
                    .player-empty {
                        font-size: 0.7em;
                        line-height: 1.6;
                        color: #fff7b3;
                    }
                    .stars {
                        position: fixed;
                        top: 0;
                        left: 0;
                        width: 100vw;
                        height: 100vh;
                        z-index: 0;
                        pointer-events: none;
                        overflow: hidden;
                    }
                    .star {
                        position: absolute;
                        width: 2px;
                        height: 2px;
                        background: #ffd600;
                        border-radius: 50%;
                        animation: twinkle 2s infinite alternate;
                        box-shadow: 0 0 8px #ffd600, 0 0 16px #ffd600;
                    }
                    @keyframes twinkle {
                        0% { opacity: 0.5; }
                        100% { opacity: 1; }
                    }
                    .arcade-frame {
                        position: relative;
                        z-index: 1;
                        max-width: 620px;
                        margin: 72px auto 0 auto;
                        padding: 44px 24px 36px 24px;
                        background: linear-gradient(135deg, #2a2a2a 0%, #1c1c1c 52%, #2f2f2f 100%);
                        border: 8px solid #ffd600;
                        border-radius: 34px;
                        box-shadow: 0 0 60px #000, 0 0 0 12px #d32f2f, 0 0 0 24px #388e3c;
                        backdrop-filter: blur(2px);
                    }
                    h1.arcade-title {
                        font-size: 2.8em;
                        margin: 0 0 20px 0;
                        letter-spacing: 4px;
                        text-shadow: 0 0 8px #ffd600, 0 0 24px #d32f2f, 0 0 32px #388e3c;
                    }
                    .subtitle {
                        font-size: 0.85em;
                        opacity: 0.9;
                        margin: 0 0 24px 0;
                        color: #fff7b3;
                        text-shadow: 0 0 8px #000;
                    }
                    .arcade-btns {
                        display: flex;
                        flex-direction: column;
                        gap: 30px;
                        margin-top: 34px;
                        align-items: center;
                        width: 100%;
                    }
                    .arcade-btns form {
                        width: 100%;
                        display: flex;
                        justify-content: center;
                    }
                    button.arcade-btn {
                        font-family: 'Press Start 2P', monospace;
                        font-size: 1.15em;
                        padding: 22px 0;
                        border: 4px solid #ffd600;
                        border-radius: 16px;
                        background: linear-gradient(135deg, #222 0%, #111 100%);
                        color: #ffd600;
                        box-shadow: 0 4px 0 #d32f2f, 0 8px 0 #388e3c, 0 0 16px #ffd600;
                        cursor: pointer;
                        transition: background 0.2s, color 0.2s, transform 0.15s;
                        width: 100%;
                        max-width: 620px;
                        min-width: 280px;
                        margin: 0 auto;
                        text-shadow: 0 0 8px #ffd600;
                    }
                    button.arcade-btn:hover {
                        background: #ffd600;
                        color: #222;
                        transform: translateY(-1px);
                        text-shadow: 0 0 8px #222;
                    }
                </style>
                <script>
                    document.addEventListener('DOMContentLoaded', function() {
                        var s = document.createElement('div');
                        s.className = 'stars';
                        for (var i = 0; i < 120; i++) {
                            var star = document.createElement('div');
                            star.className = 'star';
                            star.style.top = Math.random() * 100 + 'vh';
                            star.style.left = Math.random() * 100 + 'vw';
                            var size = (Math.random() < 0.7 ? 2 : Math.floor(Math.random() * 6 + 3));
                            star.style.width = size + 'px';
                            star.style.height = size + 'px';
                            star.style.opacity = (0.5 + Math.random() * 0.5);
                            star.style.animationDuration = (1 + Math.random() * 2) + 's';
                            s.appendChild(star);
                        }
                        document.body.appendChild(s);

                        function shootStar() {
                            var shoot = document.createElement('div');
                            shoot.className = 'shooting-star';
                            shoot.style.position = 'fixed';
                            shoot.style.width = '60px';
                            shoot.style.height = '4px';
                            shoot.style.background = 'linear-gradient(90deg,#fff,#ffd600 80%)';
                            shoot.style.borderRadius = '2px';
                            shoot.style.opacity = '0.8';
                            shoot.style.zIndex = '2';
                            shoot.style.boxShadow = '0 0 16px #ffd600';
                            var angle = Math.random() * 180;
                            var startX = Math.random() * window.innerWidth, startY = Math.random() * window.innerHeight;
                            var length = window.innerWidth * 1.2;
                            shoot.style.top = startY + 'px';
                            shoot.style.left = startX + 'px';
                            shoot.style.transform = 'rotate(' + angle + 'deg)';
                            var rad = angle * Math.PI / 180;
                            var endX = startX + Math.cos(rad) * length;
                            var endY = startY + Math.sin(rad) * length;
                            var duration = 1400;
                            document.body.appendChild(shoot);
                            shoot.animate([{ left: startX + 'px', top: startY + 'px', opacity: 0.8 }, { left: endX + 'px', top: endY + 'px', opacity: 0 }], { duration: duration, easing: 'ease-out' });
                            setTimeout(function() { shoot.remove(); }, duration + 100);
                        }

                        function starTimer() {
                            var min = 2000, max = 5000;
                            var t = function() {
                                shootStar();
                                setTimeout(t, Math.floor(Math.random() * (max - min + 1)) + min);
                            };
                            setTimeout(t, Math.floor(Math.random() * (max - min + 1)) + min);
                        }
                        for (var i = 0; i < 3; i++) { starTimer(); }
                        document.body.addEventListener('mousedown', function(e) {
                            var frame = document.querySelector('.arcade-frame');
                            if (!frame || !frame.contains(e.target)) { shootStar(); }
                        });
                    });
                </script>
            </head>
            <body>
                <!-- Top actions -->
                <a class='top-left-link' href='/addplayer'><button type='button'>nieuwe legend</button></a>
                <form class='top-left-link' action='/reset-all-stats' method='post' style='top:88px;'>
                    <button type='submit' onclick="return confirm('Reset stats of all players?')" style='background:linear-gradient(135deg,#8b1e1e 0%,#3a0d0d 100%);border-color:#ff5252;'>reset stats</button>
                </form>
                """
                + renderAllPlayersPanel(playerBase)
                + """
                <!-- Main menu -->
                <div class='arcade-frame'>
                    <h1 class='arcade-title'>BomBomBaap</h1>
                    <p class='subtitle'>we gaan moeilijk stoned worden</p>
                    <div class='arcade-btns'>
                        <form action='/categories' method='get'><button class='arcade-btn' type='submit'>Categories</button></form>
                        <form action='/nameit' method='get'><button class='arcade-btn' type='submit'>Name It</button></form>
                        <form action='/hitorstand' method='get'><button class='arcade-btn' type='submit'>Hit or Stand</button></form>
                    </div>
                </div>
                <!-- Active roster -->
                """ + renderActivePlayersPanel() + """
            </body>
            </html>
            """;
    }

    private static String renderAllPlayersPanel(PlayerBase playerBase) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='side-panel left-panel'>");
        html.append("<h2 class='panel-title'>alle boys</h2>");
        html.append("<div class='player-list'>");
        for (Player player : playerBase.getPlayers()) {
            html.append("<div class='player-card");
            if (player.isInJilla()) {
                html.append(" jilla-card");
                html.append("' style='border-color:#ff5252;background:rgba(80,0,0,0.42);box-shadow:0 0 18px #ff5252,0 0 30px rgba(255,82,82,0.45);'>");
            } else {
                html.append("'>");
            }
            html.append("<div class='player-meta'>");
            html.append("<div class='player-name'>").append(escapeHtml(player.name));
            if (player.isInJilla()) {
                html.append(" <span style='display:inline-block;margin-left:8px;padding:3px 8px;border-radius:999px;background:#ff5252;color:#111;font-size:0.55em;vertical-align:middle;box-shadow:0 0 10px #ff5252;'>JILLA</span>");
            }
            html.append("</div>");
            html.append("<div class='player-elo'>ELO: ").append(String.format("%.2f", player.ELO)).append("</div>");
            html.append("</div>");
            html.append("<form action='/activate-player' method='post'>");
            html.append("<input type='hidden' name='playerId' value='").append(player.playerId).append("'>");
            html.append("<button class='player-add' type='submit'>+</button>");
            html.append("</form>");
            html.append("</div>");
        }
        html.append("</div>");
        html.append("</div>");
        return html.toString();
    }

    static String renderActivePlayersPanel() {
        StringBuilder html = new StringBuilder();
        html.append("<div class='side-panel right-panel'>");
        html.append("<h2 class='active-panel-title'>currently baked</h2>");
        html.append("<div class='player-list'>");
        
        List<Player> activePlayersList;
        synchronized (activePlayers) {
            activePlayersList = new ArrayList<>(activePlayers);
        }
        if (activePlayersList.isEmpty()) {
            html.append("<div class='player-empty'>niemand durft mee te doen<br>kom boys 1 potje</div>");
        } else {
            for (int i = 0; i < activePlayersList.size(); i++) {
                Player player = activePlayersList.get(i);
                if (player == null) {
                    continue;
                }
                html.append("<div class='player-card");
                if (getCurrentActivePlayerId() != null && getCurrentActivePlayerId() == player.playerId) {
                    html.append(" current-player-card");
                }
                if (player.isInJilla()) {
                    html.append(" jilla-card");
                }
                html.append("'");
                if (player.isInJilla()) {
                    html.append(" style='border-color:#ff5252;background:rgba(80,0,0,0.42);box-shadow:0 0 18px #ff5252,0 0 30px rgba(255,82,82,0.45);'");
                }
                html.append(">");
                html.append("<div class='player-meta'>");
                html.append("<div class='player-name'>").append(escapeHtml(player.name));
                if (player.isInJilla()) {
                    html.append(" <span style='display:inline-block;margin-left:8px;padding:3px 8px;border-radius:999px;background:#ff5252;color:#111;font-size:0.55em;vertical-align:middle;box-shadow:0 0 10px #ff5252;'>JILLA</span>");
                }
                html.append("</div>");
                html.append("<div class='player-elo'>ELO: ").append(String.format("%.2f", player.ELO)).append("</div>");
                html.append("</div>");
                // up and down buttons for active players
                html.append("<div class='player-actions'>");
                html.append("<div class='player-order-buttons'>");
                html.append("<form action='/move-active-player' method='post'>");
                html.append("<input type='hidden' name='playerId' value='").append(player.playerId).append("'>");
                html.append("<input type='hidden' name='direction' value='up'>");
                html.append("<button class='player-move' type='submit' ");
                if (i == 0) {
                    html.append("disabled");
                }
                html.append(">&#9650</button>");
                html.append("</form>");
                html.append("<form action='/move-active-player' method='post'>");
                html.append("<input type='hidden' name='playerId' value='").append(player.playerId).append("'>");
                html.append("<input type='hidden' name='direction' value='down'>");
                html.append("<button class='player-move' type='submit' ");
                if (i == activePlayersList.size() - 1) {
                    html.append("disabled");
                }
                html.append(">&#9660</button>");
                html.append("</form>");
                html.append("</div>");
                html.append("<form action='/deactivate-player' method='post'>");
                html.append("<input type='hidden' name='playerId' value='").append(player.playerId).append("'>");
                html.append("<button class='player-add' type='submit'>-</button>");
                html.append("</form>");
                html.append("</div>");
                html.append("</div>");
            }
        }

        html.append("</div>");
        html.append("</div>");
        return html.toString();
    }

    private static String escapeHtml(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    private static void redirectBack(String referer, spark.Response res) {
        if (referer != null && !referer.isBlank()) {
            int protocolIdx = referer.indexOf("://");
            if (protocolIdx >= 0) {
                int pathStart = referer.indexOf('/', protocolIdx + 3);
                if (pathStart >= 0) {
                    res.redirect(referer.substring(pathStart));
                    return;
                }
            } else if (referer.startsWith("/")) {
                res.redirect(referer);
                return;
            }
        }
        res.redirect("/");
    }

    private static void moveActivePlayer(int playerId, String direction) {
        synchronized (activePlayers) {
            int index = -1;
            for (int i = 0; i < activePlayers.size(); i++) {
                Player player = activePlayers.get(i);
                if (player != null && player.playerId == playerId) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                return;
            }

            if (direction.equals("up") && index > 0) {
                java.util.Collections.swap(activePlayers, index, index - 1);
            } else if (direction.equals("down") && index < activePlayers.size() - 1) {
                java.util.Collections.swap(activePlayers, index, index + 1);
            }
        }
    }

}
