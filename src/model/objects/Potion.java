package model.objects;

import model.Game;

public class Potion extends GameObject{
    public Potion(Game game) {
        super(game);
        img = setImage("/obj/healthPotion");
        name = "Potion";
        pickable = true;
        price = 8;
    }
}
