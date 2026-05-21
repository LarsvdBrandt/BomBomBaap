package com.bombombaap;

public class Player {

    public final String playerId;
    public String name;
    public float ELO;

    public int bouncers = 0;
    public int palindromen = 0;
    public int jillas = 0;
    public int gamesPlayed = 0;

    public Player(String playerId, String name){
        this.playerId = playerId;
        this.name = name;
        this.ELO = 1000;
    }
    
}
