package model.objects;
import model.Game;

public class Key extends GameObject {
    public Key(Game game) {
        super(game);
        img = setImage("/obj/key");
        name = "Key";
        pickable = true;
        price = 5;
    }

}
