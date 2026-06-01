package com.bombombaap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerBase {
    
    private static Map<Integer, Player> playerBase;

    public PlayerBase() {
        playerBase = new HashMap<>();
    }

    public void copyPlayerBase(JSONArray players) {
        for (int i = 0; i < players.length(); i++) {
            JSONObject player = players.getJSONObject(i);
            String name = player.getString("name");
            int id = player.getInt("id");
            JSONObject stats = extractStats(player);
            float ELO = (float) stats.getDouble("ELO");
            int poffen = stats.optInt("poffen", 0);
            int beurtenGehad = stats.optInt("beurtenGehad", 0);
            int jillas = stats.optInt("jillas", 0);
            int bouncers = stats.optInt("bouncers", 0);
            int palindromen = stats.optInt("palindromes", 0);
            int gamesPlayed = stats.optInt("gamesPlayed", 0);

            Player newPlayer = new Player(id, name);
            playerBase.put(id, newPlayer);
            newPlayer.updateStats(poffen, beurtenGehad, bouncers, palindromen, jillas, gamesPlayed, ELO);
            System.out.println("Loaded player: " + name + " with ID: " + id);
        }
    }

    private static JSONObject extractStats(JSONObject player) {
        Object statsValue = player.get("stats");
        if (statsValue instanceof JSONArray statsArray && !statsArray.isEmpty()) {
            return statsArray.getJSONObject(0);
        }
        return (JSONObject) statsValue;
    }

    public void updateStat(int playerId, StatType stat, int value) {
        Player p = playerBase.get(playerId);

        if (p == null) return;

        switch (stat) {

            case BOUNCERS -> p.bouncers += value;
            case BEURTEN_GEHAD -> p.beurtenGehad += value;
            case PALINDROMEN -> p.palindromen += value;
            case JILLAS -> p.jillas += value;
            case GAMES_PLAYED -> p.gamesPlayed += value;
            case POFFEN -> p.poffen += value;
        }

        saveJSON();
    }

    public Player getPlayer(int playerId, String name) {
        if (playerBase.containsKey(playerId)) {
            return playerBase.get(playerId);
        } else {
            System.out.print("player does not exist");
            return null;
        }
    }


    public Player addPlayer(String name) {
        int playerId = nextPlayerId();
        if (playerBase.containsKey(playerId)){
            System.out.print("player already exists");
            return null;
        } else {
            Player newPlayer = new Player(playerId, name);
            playerBase.put(playerId, newPlayer);
            System.out.println("Added new player: " + name + " with ID: " + playerId);
            saveJSON();
            return newPlayer;
        }
    }

    private static int nextPlayerId() {
        int highestId = 0;
        for (Integer existingId : playerBase.keySet()) {
            highestId = Math.max(highestId, existingId);
        }
        return highestId + 1;
    }

    public void printPlayerBase() {
        System.out.println("Player Base:");
        for (Player p : playerBase.values()) {
            System.out.println("ID: " + p.playerId + ", Name: " + p.name + ", ELO: " + p.ELO);
        }
    }

    public Collection<Player> getPlayers() {
        return playerBase.values();
    }

    public void saveJSON() {
        JSONObject root = new JSONObject();
        JSONArray playerArray = new JSONArray();

        for (Player p : playerBase.values()) {
            p.updateELO();
            JSONObject obj = new JSONObject();

            obj.put("id", p.playerId);
            obj.put("name", p.name);

            JSONObject stats = new JSONObject();
            stats.put("poffen", p.poffen);
            stats.put("beurtenGehad", p.beurtenGehad);
            stats.put("gamesPlayed", p.gamesPlayed);
            stats.put("jillas", p.jillas);
            stats.put("bouncers", p.bouncers);
            stats.put("palindromen", p.palindromen);
            stats.put("ELO", p.ELO);

            obj.put("stats", stats);

            playerArray.put(obj);

        }
        root.put("players", playerArray);
        try {
            Files.writeString(Paths.get("players.json"), root.toString(2));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write players.json", e);
        }
    }

    public void resetPlayerBaseStats() {
        JSONObject root = new JSONObject();
        JSONArray playerArray = new JSONArray();

        for (Player p : playerBase.values()) {
            p.resetStats();
            JSONObject obj = new JSONObject();

            obj.put("id", p.playerId);
            obj.put("name", p.name);

            JSONObject stats = new JSONObject();
            stats.put("poffen", 0);
            stats.put("beurtenGehad", 0);
            stats.put("gamesPlayed", 0);
            stats.put("jillas", 0);
            stats.put("bouncers", 0);
            stats.put("palindromen", 0);
            stats.put("ELO", p.ELO);

            obj.put("stats", stats);

            playerArray.put(obj);

        }
        root.put("players", playerArray);
        try {
            Files.writeString(Paths.get("players.json"), root.toString(2));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write players.json", e);
        }
    }
}