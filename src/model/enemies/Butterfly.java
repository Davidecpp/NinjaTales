package model.enemies;

import model.Creature;
import model.Game;
import java.awt.*;

public class Butterfly extends Creature {
    public Butterfly(Game game,int x,int y) {
        super(game, x, y);
        speed = 2;
        maxLife = 6;
        life = maxLife;
        exp = 10;
        name = "Butterfly";
        type = enemyType;
        attack = 2;
        hitbox = new Rectangle( game.tileSize - 18, game.tileSize/2 + 5);
        getImages();
    }

    @Override
    public void update() {
        randomMovement(120);
        super.update();
    }

    public void getImages(){
        up = setImage("/monsters/flyUp");
        up2 = setImage("/monsters/flyUp2");
        down = setImage("/monsters/flyDown");
        down2 = setImage("/monsters/flyDown2");
        left2 = setImage("/monsters/flyLeft");
        left = setImage("/monsters/flyLeft2");
        right2 = setImage("/monsters/flyRight");
        right = setImage("/monsters/flyRight2");
    }
}
