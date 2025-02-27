package model.objects;

import model.Game;

public class SwordBase extends  GameObject{
    public SwordBase(Game game) {
        super(game);
        img = setImage("/obj/spada");
        name = "sword";
        pickable = true;
        rarity = "Rare";
        attack = 1;
        price = 5;
    }
}
