package com.bombombaap;

public class Player {

    public final int playerId;
    public String name;
    public float ELO;

    public int bouncers = 0;
    public int palindromen = 0;
    public int jillas = 0;
    public int gamesPlayed = 0;

    public Player(int playerId, String name){
        this.playerId = playerId;
        this.name = name;
        this.ELO = 1000;
    }

    public void updateStats(int bouncers, int palindromen, int jillas, int gamesPlayed, float ELO){
        this.bouncers += bouncers;
        this.palindromen += palindromen;
        this.jillas += jillas;
        this.gamesPlayed += gamesPlayed;
        this.ELO = ELO;
    }
    
}
