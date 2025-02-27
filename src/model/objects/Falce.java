package model.objects;

import model.Game;

public class Falce extends GameObject{
    public Falce(Game game) {
        super(game);
        img = setImage("/obj/falce");
        name = "Falce";
        pickable = true;
        rarity = "Epic";
        attack = 5;
        price = 20;
    }
}
