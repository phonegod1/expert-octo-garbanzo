package me.ac;

import java.util.UUID;

public class playerbets {
    private UUID player;
    private Color c;
    private Double amount;
    public enum Color
    {
        RED, BLACK, GREEN;
    }

    public playerbets(UUID player, Double amount, Color c){
        this.player=player;this.amount=amount;this.c=c;
    }

    public UUID getplayer(){return player;}
    public Double getamount(){return amount;}
    public Color getcolor(){return c;}

}
