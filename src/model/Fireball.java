package model;

import java.awt.*;

public class Fireball extends  Creature{
    public Fireball(Game game) {
        super(game);
        speed = 6;
        maxLife = 1;
        life = maxLife;
        attack = 1;
        hitbox = new Rectangle(game.tileSize - 20, game.tileSize/2 - 5);
        type = projType;
        getImages();
    }
    public void getImages(){
        up = setImage("/monsters/fireUp");
        up2 = setImage("/monsters/fireUp2");
        down = setImage("/monsters/fireDown");
        down2 = setImage("/monsters/fireDown2");
        left2 = setImage("/monsters/fireLeft");
        left = setImage("/monsters/fireLeft2");
        right2 = setImage("/monsters/fireRight");
        right = setImage("/monsters/fireRight2");
    }

}
