package model.enemies;

import model.Creature;
import model.Game;

import java.awt.*;

public class BlueOrc extends Creature {
    public BlueOrc(Game game, int x, int y) {
        super(game, x, y);
        speed = 1;
        maxLife = 30;
        life = maxLife;
        exp = 20;
        name = "Orco blu";
        type = enemyType;
        attack = 3;
        hitbox = new Rectangle( game.tileSize - 18, game.tileSize/2 + 5);
        getImages();
    }

    @Override
    public void update() {
        randomMovement(120);
        super.update();
    }

    public void getImages(){
        up = setImage("/monsters/blueOrcUp");
        up2 = setImage("/monsters/blueOrcUp2");
        down = setImage("/monsters/blueOrcDown");
        down2 = setImage("/monsters/blueOrcDown2");
        left2 = setImage("/monsters/blueOrcLeft");
        left = setImage("/monsters/blueOrcLeft2");
        right2 = setImage("/monsters/blueOrcRight");
        right = setImage("/monsters/blueOrcRight2");
    }
}
