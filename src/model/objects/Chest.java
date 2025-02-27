package model.objects;

import model.Game;

public class Chest extends GameObject{
    public Chest(Game game) {
        super(game);
        img = setImage("/obj/chest");
        img2 = setImage("/obj/chestOpen");
        solid = true;
        name = "Chest";

    }
    public void setLoot(GameObject obj){
        this.loot = obj;
    }
    public GameObject getLoot(){
        return loot;
    }
}
