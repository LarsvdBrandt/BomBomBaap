package com.bombombaap;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
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

        PlayerBase playerBase = new PlayerBase();
        playerBase.copyPlayerBase(players.getJSONArray("players"));
        playerBase.printPlayerBase();

        get("/", (req, res) -> {
            res.type("text/html");
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
                            overflow: hidden;
                            color: #ffd600;
                            min-height: 100vh;
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
                            font-size: 0.9em;
                            padding: 14px 18px;
                            border: 3px solid #ffd600;
                            border-radius: 14px;
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
                            max-width: 560px;
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
                            width: 70%;
                            max-width: 400px;
                            min-width: 180px;
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
                    <a class='top-right-link' href='/addplayer'><button type='button'>Add Player</button></a>
                    <div class='arcade-frame'>
                        <h1 class='arcade-title'>BomBomBaap</h1>
                        <p class='subtitle'>Choose a game or add a player to the roster</p>
                        <div class='arcade-btns'>
                            <form action='/categories' method='get'><button class='arcade-btn' type='submit'>Categories</button></form>
                            <form action='/nameit' method='get'><button class='arcade-btn' type='submit'>Name It</button></form>
                            <form action='/hitorstand' method='get'><button class='arcade-btn' type='submit'>Hit or Stand</button></form>
                        </div>
                    </div>
                </body>
                </html>
                """;
        });

        get("/addplayer", (req, res) -> {
            res.type("text/html");
            return """
                <html>
                <head>
                    <title>Add Player</title>
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
                        <h1>Add Player</h1>
                        <form action='/addplayer' method='post'>
                            <input name='name' placeholder='Player Name' required>
                            <br><button type='submit'>Create Player</button>
                        </form>
                        <a href='/'>Back To Home</a>
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

        GamesCategories.registerRoutes();
        GamesNameIt.registerRoutes();
        GamesHitOrStand.registerRoutes();
    }

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
}
