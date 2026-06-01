package com.bombombaap;

public class Player {

    public final int playerId;
    public String name;
    public float ELO;

    public int poffen = 0;
    public int bouncers = 0;
    public int palindromen = 0;
    public int jillas = 0;
    public int gamesPlayed = 0;
    public int beurtenGehad = 0;

    public boolean inJilla = false;

    public static final float POFFEN_WEIGHT = 4.2f;
    public static final float BOUNCER_WEIGHT = 15f;
    public static final float PALINDROMEN_WEIGHT = 30.0f;
    public static final float JILLAS_WEIGHT = 17.5f;

    public Player(int playerId, String name){
        this.playerId = playerId;
        this.name = name;
        this.ELO = 1000;
    }

    public void updateStats(int poffen, int beurtenGehad,int bouncers, int palindromen, int jillas, int gamesPlayed, float ELO){
        this.poffen += poffen;
        this.beurtenGehad += beurtenGehad;
        this.bouncers += bouncers;
        this.palindromen += palindromen;
        this.jillas += jillas;
        this.gamesPlayed += gamesPlayed;
        this.ELO = ELO;
    }

    public void addPof(int poffen){
        this.poffen += poffen;
    }

    public void addBeurt(int beurtenGehad) {
        this.beurtenGehad += beurtenGehad;
    }

    public void addBouncer(int bouncers) {
        this.bouncers += bouncers;
    }

    public void addPalindromen(int palindromen) {
        this.palindromen += palindromen;
    }

    public void addJilla(int jillas) {
        this.jillas += jillas;
    }

    public void addGamesPlayed(int gamesPlayed) {
        this.gamesPlayed += gamesPlayed;
    }

    public void updateELO(){
        float weightedScore = (poffen * POFFEN_WEIGHT)
            + (bouncers * BOUNCER_WEIGHT)
            + (palindromen * PALINDROMEN_WEIGHT)
            - (jillas * JILLAS_WEIGHT);
        this.ELO = Math.max(0f, 1000f + weightedScore);
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed += gamesPlayed;
    }

    public void setInJilla(boolean inJilla) {
        this.inJilla = inJilla;
    }

    public boolean isInJilla() {
        return inJilla;
    }

    public void resetStats() {
        this.poffen = 0;
        this.bouncers = 0;
        this.palindromen = 0;
        this.jillas = 0;
        this.gamesPlayed = 0;
        this.beurtenGehad = 0;
        this.inJilla = false;
        this.ELO = 1000;
    }
    
}
