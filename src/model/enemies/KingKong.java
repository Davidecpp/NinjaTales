package model.enemies;

import model.Creature;
import model.Game;
import java.awt.*;

public class KingKong extends Creature {
    public KingKong(Game game, int x, int y) {
        super(game, x, y);
        speed = 1;
        maxLife = 20;
        life = maxLife;
        exp = 50;
        name = "KingKong";
        type = enemyType;
        boss = true;
        attack = 4;
        hitbox = new Rectangle( game.tileSize*3, game.tileSize*3 - 30);
        getImages();
    }

    @Override
    public void update() {
        randomMovement(120);
        super.update();
    }

    public void getImages(){
        up = setImage("/monsters/gorillaUp");
        up2 = setImage("/monsters/gorillaUp2");
        down = setImage("/monsters/gorillaDown");
        down2 = setImage("/monsters/gorillaDown2");
        left2 = setImage("/monsters/gorillaLeft");
        left = setImage("/monsters/gorillaLeft2");
        right2 = setImage("/monsters/gorillaRight");
        right = setImage("/monsters/gorillaRight2");
    }
}
