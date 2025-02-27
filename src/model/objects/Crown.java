package model.objects;

import model.Game;

public class Crown extends GameObject{
    public Crown(Game game) {
        super(game);
        img = setImage("/obj/crown");
        name = "Crown";
        pickable = true;
    }
}
