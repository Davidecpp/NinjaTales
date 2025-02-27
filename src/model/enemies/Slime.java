package model.enemies;

import model.Creature;
import model.Game;
import java.awt.*;
public class Slime extends Creature {
    Game game;
    public Slime(Game game, int x, int y) {
        super(game,x,y);
        this.game = game;
        speed = 1;
        maxLife = 8;
        life = maxLife;
        exp = 3;
        name = "Slime";
        type = enemyType;
        attack = 1;
        hitbox = new Rectangle( game.tileSize - 18, game.tileSize/2);
        getImages();
    }
    @Override
    public void update() {
        super.update();
        setAction();
    }

    @Override
    public void setAction() {
        if(hitted){
            direction = game.gp.player.direction;
        }else{
            randomMovement(240);
        }
    }

    public void getImages(){
        up = setImage("/monsters/redSlime");
        up2 = setImage("/monsters/redSlime2");
        down = setImage("/monsters/redSlime");
        down2 = setImage("/monsters/redSlime2");
        left2 = setImage("/monsters/redSlime");
        left = setImage("/monsters/redSlime2");
        right2 = setImage("/monsters/redSlime");
        right = setImage("/monsters/redSlime2");
    }

}
