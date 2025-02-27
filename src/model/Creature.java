package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Creature {
    Game game;
    public int x, y;
    public int life, maxLife;
    public int speed, exp, expNeeded;
    public int attack, defense;
    public boolean collision, hitted;
    public String direction = "";
    public String name;
    public BufferedImage up, up2, down, down2, right, right2, left, left2;
    public BufferedImage attUp, attUp2, attDown, attDown2, attRight, attRight2, attLeft, attLeft2;
    public int animationCounter = 0;
    public int animation = 1;
    public Rectangle hitbox = new Rectangle();
    public boolean usable = true;
    public int type;
    public final int playerType = 0;
    public final int enemyType = 1;
    public final int bossType = 2;
    public final int projType = 3;
    int timer = 0;
    int hitProjTimer = 0;
    public boolean boss = false;
    int hitTimer = 0;
    public Creature(Game game){
        this.game = game;
    }
    public Creature(Game game,int x, int y){
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void getImages(){

    }
    public BufferedImage setImage(String imagePath){
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return  image;
    }
    public void update(){
        collision = false;
        collisionCheckTiles();
        game.gp.manager.solidObjCollision(this);

        if(game.gp.gc.attacking && game.gp.player.weaponHitbox.intersects(this.hitbox)){
            hitted = true;
            takeDamage(game.gp.player.getAttack());
        }
        switch (direction) {
            case "W" -> y -= speed;
            case "A" -> x -= speed;
            case "S" -> y += speed;
            case "D" -> x += speed;
        }
        animationCounter++;
        if(animationCounter >= 60){
            if(animation == 1){
                animation = 2;
            }
            else if(animation == 2){
                animation = 1;
            }
            animationCounter = 0;
        }

    }
    protected void randomMovement(int timeSpan){
        Random random = new Random();
        timer++;

        if(timer > timeSpan){
            int r = random.nextInt(0,40);

            if(r < 10){direction = "W";}
            else if(r >= 10 && r < 20){direction = "S";}
            else if(r >= 20 && r < 30){direction = "D";}
            else if(r >= 30 && r < 40){direction = "A";}
            timer = 0;
        }

    }
    public void draw(Graphics2D g2, boolean info){
        BufferedImage img = down;
        int monX = x - game.gp.player.x + game.gp.player.xScreen;
        int monY = y - game.gp.player.y + game.gp.player.yScreen;

        if (x + game.tileSize*2 > game.gp.player.x - game.gp.player.xScreen &&
                x - game.tileSize*2 < game.gp.player.x + game.gp.player.xScreen &&
                y + game.tileSize*2 > game.gp.player.y - game.gp.player.yScreen &&
                y - game.tileSize*2 < game.gp.player.y + game.gp.player.xScreen) {
            switch (direction) {
                case "W" -> {
                    if (animation == 1) {img = up;}
                    if (animation == 2) {img = up2;}
                }
                case "A" -> {
                    if (animation == 1) {img = left;}
                    if (animation == 2) {img = left2;}}
                case "S" -> {
                    if (animation == 1) {img = down;}
                    if (animation == 2) {img = down2;}
                }
                case "D" -> {
                    if (animation == 1) {img = right;}
                    if (animation == 2) {img = right2;}
                }
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            ifHitted(g2);
            if(boss){
                g2.drawImage(img, monX, monY, game.tileSize*3, game.tileSize*3, null);
            }
            else{
                g2.drawImage(img, monX, monY, game.tileSize, game.tileSize, null);
            }
            drawLifeRect(g2, monX, monY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            hitbox.x = x + 10;
            hitbox.y = y + 15;
            if(info){
                g2.setColor(Color.red);
                g2.drawRect(monX + 10, monY + 15, hitbox.width, hitbox.height);
            }
        }
    }
    public void setPosition(int posX, int posY){
        x = posX;
        y = posY;
    }
    public void collisionCheckTiles(){
        int predW = (hitbox.y - speed)/game.tileSize;
        int predA = (hitbox.x - speed)/game.tileSize;
        int predS = (hitbox.y + hitbox.height+ speed)/game.tileSize;
        int predD = (hitbox.x + hitbox.width + speed)/game.tileSize;
        int upLeft = game.gp.ts.map[predA][predW];
        int upRight= game.gp.ts.map[predD][predW];
        int downLeft = game.gp.ts.map[predA][predS];
        int downRight = game.gp.ts.map[predD][predS];

        switch (direction) {
            case "W" -> {
                if (game.gp.ts.tiles[upLeft].collision || game.gp.ts.tiles[upRight].collision) {
                    if (type == projType) {
                        y += speed;
                    } else {
                        direction = "S";
                    }
                    collision = true;
                }
            }
            case "S" -> {
                if (game.gp.ts.tiles[downLeft].collision || game.gp.ts.tiles[downRight].collision) {
                    if (type == projType) {
                        y += speed;
                    } else {
                        direction = "W";
                    }
                    collision = true;
                }
            }
            case "A" -> {
                if (game.gp.ts.tiles[upLeft].collision || game.gp.ts.tiles[downLeft].collision) {
                    if (type == projType) {
                        y += speed;
                    } else {
                        direction = "D";
                    }
                    collision = true;
                }
            }
            case "D" -> {
                if (game.gp.ts.tiles[upRight].collision || game.gp.ts.tiles[downRight].collision) {
                    if (type == projType) {
                        y += speed;
                    } else {
                        direction = "A";
                    }
                    collision = true;
                }
            }
        }
    }
    public void chasePlayer(){
        int newX = game.gp.player.x*game.tileSize - x*game.tileSize;
        int newY = game.gp.player.y*game.tileSize - y*game.tileSize;

        if(newX <8 || newY < 8){

        }
    }
    public void ifHitted(Graphics2D g2){
        if(hitted){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            hitTimer++;
            if(hitTimer > 50){
                hitted = false;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                hitTimer = 0;

            }
        }
    }
    public void takeDamage(int damage){
        if(hitted){
            game.gp.manager.soundManager.playSound(3);
            hitTimer++;
            if(hitTimer <= 1){
                life-= damage;
                game.gp.setTxt("Damage "+damage);
            }
            else if(hitTimer > 50){
                hitted = false;
                hitTimer = 0;
            }
        }

    }
    public void projDamage(Graphics2D g2){
        for(int i = 0; i < game.enemies.length; i++){
            if(game.enemies[i]!=null){
                if(hitbox.intersects(game.enemies[i].hitbox)){
                    hitProjTimer++;
                    collision = true;
                    game.enemies[i].hitted = true;
                    if(hitTimer <= 1){
                        game.enemies[i].life -= attack;
                    }
                    else if(hitProjTimer > 20){
                        hitProjTimer = 0;
                    }
                }
            }
        }
    }
    private void drawLifeRect(Graphics2D g2, int x , int y){
        x+=10;
        int width = 60;
        int bar = width/maxLife;
        if(boss){
            width =200;
        }
        if(type!=projType){
            g2.setColor(Color.black);
            g2.drawRect(x - 1, y - 1,width+2, 12);
            g2.setColor(Color.red);
            g2.fillRect(x, y, life*bar, 10);
        }

    }
    public void setAction(){}

}
