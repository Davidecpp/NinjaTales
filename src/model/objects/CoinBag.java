package model.objects;

import model.Game;

public class CoinBag extends GameObject{
    public CoinBag(Game game) {
        super(game);
        img = setImage("/obj/sacchettoCoin");
        name = "Coin Bag";
        pickable = false;
    }
}
