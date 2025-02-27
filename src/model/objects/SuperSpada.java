package model.objects;

import model.Game;

public class SuperSpada extends GameObject{
    public SuperSpada(Game game) {
        super(game);
        img = setImage("/obj/superSpada");
        //hitbox = new Rectangle(x, y,game.tileSize - 20, game.tileSize);
        name = "Super sword";
        pickable = true;
        rarity = "Legendary";
        attack = 10;
        price = 50;
    }
}
