package model.objects;
import model.Game;

public class Coin extends GameObject{
    public Coin(Game game) {
        super(game);
        img = setImage("/obj/coin");
        //hitbox = new Rectangle(x, y,game.tileSize - 20, game.tileSize);
        name = "coin";
        pickable = false;
    }
}
