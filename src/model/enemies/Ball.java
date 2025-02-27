package model.enemies;

import model.Creature;
import model.Game;

import java.awt.*;

public class Ball extends Creature {
    Game game;
    public Ball(Game game, int x, int y) {
        super(game,x,y);
        this.game = game;
        speed = 2;
        maxLife = 20;
        life = maxLife;
        exp = 10;
        name = "Ball";
        type = enemyType;
        attack = 2;
        hitbox = new Rectangle( game.tileSize - 18, game.tileSize/2 + 5);
        getImages();
    }
    @Override
    public void update() {
        setAction();
        super.update();
    }
    public void setAction() {
        if(hitted){
            switch (game.gp.player.direction){
                case "W": direction = "S";break;
                case "A": direction = "D";break;
                case "S": direction = "W";break;
                case "D": direction = "A";break;
            }
        }else{
            randomMovement(180);
        }
    }
    public void getImages(){
        up = setImage("/monsters/pallaUp");
        up2 = setImage("/monsters/pallaUp2");
        down = setImage("/monsters/pallaDown");
        down2 = setImage("/monsters/pallaDown2");
        left2 = setImage("/monsters/pallaLeft");
        left = setImage("/monsters/pallaLeft2");
        right2 = setImage("/monsters/pallaRight");
        right = setImage("/monsters/pallaRight2");
    }
}
