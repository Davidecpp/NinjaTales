package model.objects;

import model.Game;
import java.awt.*;
public class Heart extends GameObject{
    public Heart(Game game) {
        super(game);
        img = setImage("/obj/heart");
        hitbox = new Rectangle(x, y,game.tileSize - 20, game.tileSize);
        name = "heart";
        pickable = false;
    }
}
