package com.bombombaap;

import java.util.HashMap;
import java.util.Map;

public class DeBoys {
    
    private static Map<String, Player> players;

    public DeBoys() {
        players = new HashMap<>();
    }

    public static Player getPlayer(String playerId, String name) {
        if (players.containsKey(playerId)) {
            return players.get(playerId);
        } else {
            System.out.print("player does not exist");
            return null;
        }
    }

    public static Player addPlayer(String playerId, String name) {
        if (players.containsKey(playerId)){
            System.out.print("player already exists");
            return null;
        } else {
            Player newPlayer = new Player(playerId, name);
            players.put(playerId, newPlayer);
            return newPlayer;
        }
    }
}