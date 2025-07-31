package com.bombombaap;

import static spark.Spark.get;

public class GamesHitOrStand {
    public static void registerRoutes() {
        get("/hitorstand", (req, res) -> {
            String p1 = getRandomCard();
            String p2 = getRandomCard();
            String dealer = getRandomCard();
            return renderGamePage(p1, p2, dealer, 0, 0, "");
        });
        spark.Spark.post("/hitorstand", (req, res) -> {
            String p1 = req.queryParams("p1");
            String p2 = req.queryParams("p2");
            String dealer = req.queryParams("dealer");
            String action = req.queryParams("action");
            int correct = Integer.parseInt(req.queryParams("correct"));
            int wrong = Integer.parseInt(req.queryParams("wrong"));
            String expected = getStrategyResult(p1, p2, dealer);
            String feedback;
            if (action != null && action.equals(expected)) {
                correct++;
                feedback = "<span style='color:#ffd600;'>✔ Correct!</span>";
            } else {
                wrong++;
                feedback = "<span style='color:#d32f2f;'>✖ Wrong! Correct: " + expected + "</span>";
            }
            // New round
            String newP1 = getRandomCard();
            String newP2 = getRandomCard();
            String newDealer = getRandomCard();
            return renderGamePage(newP1, newP2, newDealer, correct, wrong, feedback);
        });
    }
    public static String getRandomCard() {
        String[] cards = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        int idx = (int)(Math.random() * cards.length);
        return cards[idx];
    }
    public static String getStrategyResult(String p1, String p2, String dealer) {
        return BlackjackStrategy.getAction(p1, p2, dealer);
    }
    public static String renderGamePage(String p1, String p2, String dealer, int correct, int wrong, String feedback) {
        String result = getStrategyResult(p1, p2, dealer);
        return "<html><head><title>Hit or Stand</title>"
            + "<link href='https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap' rel='stylesheet'>"
            + "<style>body{font-family:'Press Start 2P',monospace;text-align:center;background:#222;min-height:100vh;overflow-x:hidden;color:#ffd600;}"
            + ".starbg{position:fixed;top:0;left:0;width:100vw;height:100vh;z-index:0;pointer-events:none;}"
            + ".arcade-frame{position:relative;z-index:1;max-width:800px;margin:60px auto 0 auto;padding:40px 20px 32px 20px;background:linear-gradient(135deg,#222 60%,#333 100%);border:8px solid #ffd600;border-radius:32px;box-shadow:0 0 60px #000,0 0 0 12px #d32f2f,0 0 0 24px #388e3c;}"
            + "h1{font-size:2em;margin-bottom:24px;text-shadow:0 0 8px #ffd600;}"
            + ".card{display:inline-block;font-size:2em;padding:12px 24px;margin:0 8px;background:#333;border:2px solid #ffd600;border-radius:12px;color:#ffd600;box-shadow:0 2px 8px #222;}"
            + ".arcade-btns{display:flex;justify-content:center;align-items:center;gap:24px;margin-top:32px;}"
            + ".arcade-btn{font-family:'Press Start 2P',monospace;font-size:1.2em;padding:18px 36px;border:4px solid #ffd600;border-radius:16px;background:#222;color:#ffd600;box-shadow:0 4px 0 #d32f2f,0 8px 0 #388e3c,0 0 16px #ffd600;cursor:pointer;transition:background 0.2s, color 0.2s;}"
            + ".arcade-btn:hover{background:#ffd600;color:#222;text-shadow:0 0 8px #222;}"
            + ".counter{margin-top:32px;font-size:1.3em;background:rgba(0,0,0,0.8);color:#ffd600;padding:12px 32px;border-radius:16px;box-shadow:0 0 24px #222;text-shadow:0 2px 8px #222;border:3px solid #ffd600;display:inline-block;}"
            + "</style>"
            + "</head><body>"
            + "<a href='/' id='homebtn' style='position:fixed;top:24px;left:24px;z-index:10;text-decoration:none;'><button style='background:#222;border:3px solid #ffd600;border-radius:50%;width:64px;height:64px;display:flex;align-items:center;justify-content:center;box-shadow:0 4px 16px #111;cursor:pointer;'><svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='36' height='36' fill='#ffd600'><path d='M12 3l9 9-1.5 1.5L18 12.5V20a1 1 0 0 1-1 1h-4v-5H11v5H7a1 1 0 0 1-1-1v-7.5l-1.5 1.5L3 12l9-9z'/></svg></button></a>"
            + "<div class='arcade-frame'>"
            + "<h1>Hit or Stand</h1>"
            + "<div style='margin:32px 0;'>"
            + "<span class='card' id='p1'>" + p1 + "</span>"
            + "<span class='card' id='p2'>" + p2 + "</span>"
            + "<span style='font-size:1.2em;margin:0 12px;'>vs Dealer</span>"
            + "<span class='card' id='dealer'>" + dealer + "</span>"
            + "</div>"
            + "<form method='post' action='/hitorstand'>"
            + "<input type='hidden' name='p1' value='" + p1 + "'>"
            + "<input type='hidden' name='p2' value='" + p2 + "'>"
            + "<input type='hidden' name='dealer' value='" + dealer + "'>"
            + "<input type='hidden' name='correct' value='" + correct + "'>"
            + "<input type='hidden' name='wrong' value='" + wrong + "'>"
            + "<div class='arcade-btns'>"
            + "<button class='arcade-btn' name='action' value='DOUBLE' type='submit'>Double</button>"
            + "<button class='arcade-btn' name='action' value='HIT' type='submit'>Hit</button>"
            + "<button class='arcade-btn' name='action' value='STAND' type='submit'>Stand</button>"
            + "<button class='arcade-btn' name='action' value='SPLIT' type='submit'>Split</button>"
            + "</div>"
            + "</form>"
            + "<div class='counter'><span>Correct: <span id='correct'>" + correct + "</span></span> &nbsp; <span>Wrong: <span id='wrong'>" + wrong + "</span></span></div>"
            + "<div id='feedback' style='margin-top:18px;font-size:1.2em;'>" + feedback + "</div>"
            + "</div>"
            + "</body></html>";
    }
}

class BlackjackStrategy {
    private static final String[] TEN_CARDS = {"10", "J", "Q", "K"};
    public static int cardValue(String card) {
        for (String ten : TEN_CARDS) {
            if (ten.equals(card)) return 10;
        }
        if ("A".equals(card)) return 11;
        return Integer.parseInt(card);
    }
    public static String normalizeCard(String card) {
        for (String ten : TEN_CARDS) {
            if (ten.equals(card)) return "10";
        }
        if ("A".equals(card)) return "A";
        return card;
    }
    public static String hardStrategy(int total, String dealer) {
        int d = "A".equals(dealer) ? 11 : Integer.parseInt(dealer);
        if (total <= 8) return "HIT";
        if (total == 9) return (d >= 3 && d <= 6) ? "DOUBLE" : "HIT";
        if (total == 10) return (d >= 2 && d <= 9) ? "DOUBLE" : "HIT";
        if (total == 11) return (d <= 10) ? "DOUBLE" : "HIT";
        if (total == 12) return (d >= 4 && d <= 6) ? "STAND" : "HIT";
        if (total >= 13 && total <= 16) return (d >= 2 && d <= 6) ? "STAND" : "HIT";
        return "STAND";
    }
    public static String softStrategy(int otherCardValue, String dealer) {
        int d = "A".equals(dealer) ? 11 : Integer.parseInt(dealer);
        switch (otherCardValue) {
            case 2:
            case 3:
                return (d >= 5 && d <= 6) ? "DOUBLE" : "HIT";
            case 4:
            case 5:
                return (d >= 4 && d <= 6) ? "DOUBLE" : "HIT";
            case 6:
                return (d >= 3 && d <= 6) ? "DOUBLE" : "HIT";
            case 7:
                return (d >= 3 && d <= 6) ? "DOUBLE" : (d == 2 || d == 7 || d == 8) ? "STAND" : "HIT";
            case 8:
            case 9:
                return "STAND";
            default:
                return null;
        }
    }
    public static String pairStrategy(String card, String dealer) {
        int d = "A".equals(dealer) ? 11 : Integer.parseInt(dealer);
        switch (card) {
            case "A": return "SPLIT";
            case "10":
            case "J":
            case "Q":
            case "K": return "STAND";
            case "9": return (d == 7 || d == 10 || d == 11) ? "STAND" : "SPLIT";
            case "8": return "SPLIT";
            case "7": return (d <= 7) ? "SPLIT" : "HIT";
            case "6": return (d >= 2 && d <= 6) ? "SPLIT" : "HIT";
            case "5": return (d >= 2 && d <= 9) ? "DOUBLE" : "HIT";
            case "4": return (d == 5 || d == 6) ? "SPLIT" : "HIT";
            case "3":
            case "2": return (d >= 2 && d <= 7) ? "SPLIT" : "HIT";
            default: return null;
        }
    }
    public static String getAction(String playerCard1, String playerCard2, String dealerCard) {
        String dealer = normalizeCard(dealerCard);
        if (playerCard1.equals(playerCard2)) {
            String result = pairStrategy(playerCard1, dealer);
            if (result != null) return result;
        }
        if (playerCard1.equals("A") || playerCard2.equals("A")) {
            String otherCard = playerCard1.equals("A") ? playerCard2 : playerCard1;
            String result = softStrategy(cardValue(otherCard), dealer);
            if (result != null) return result;
        }
        int total = cardValue(playerCard1) + cardValue(playerCard2);
        return hardStrategy(total, dealer);
    }
}
