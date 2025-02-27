package model.objects;

import model.Game;

public class Knife extends GameObject{
    public Knife(Game game) {
        super(game);
        img = setImage("/obj/knife");
        name = "Knife";
        pickable = true;
        rarity = "Normal";
        attack = 1;
        price = 10;
    }
}
