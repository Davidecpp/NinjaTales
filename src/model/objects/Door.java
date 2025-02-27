package model.objects;

import model.Game;

public class Door extends  GameObject{
    public Door(Game game) {
        super(game);
        img = setImage("/obj/double_door");
        solid = true;
        name = "Door";
    }
}
