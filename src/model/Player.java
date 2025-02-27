package model;

import controller.GameController;
import model.objects.*;
import view.GraphicPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Creature{
    private Game game;
    public int xScreen, yScreen;
    public GameController gc;
    GraphicPanel gp;
    public Rectangle weaponHitbox;
    public int level;
    public int coin = 20;
    public boolean shot = false;
    public ArrayList<GameObject> inventory = new ArrayList<>();
    public int inventorySize = 30;
    public GameObject selectedWeapon;

    public Player(GraphicPanel gp, GameController gc){
        super(null);
        this.gp = gp;
        this.gc = gc;
        setStartPlayer();
        getImages();
        attackingImages();
        selectedWeapon = new Knife(game);
        inventory.add(selectedWeapon);
        inventory.add(new Key(game));
    }
    public void setStartPlayer(){
        x = game.tileSize*10;
        y = game.tileSize*6;
        hitbox = new Rectangle(game.tileSize/2, game.tileSize - 33);
        weaponHitbox = new Rectangle(game.tileSize/2, game.tileSize/2);
        level = 1;
        exp = 0;
        expNeeded = 10;
        attack = 1;
        speed = 3;
        maxLife = 6;
        life = maxLife;
    }
    private void getCenterScreen(){
        xScreen= game.gameWidth/2 - 38;
        yScreen = game.gameHeight/2 - 38;
    }
    public void collisionCheckTiles(){
        int predW = (hitbox.y - speed)/game.tileSize;
        int predA = (hitbox.x - speed)/game.tileSize;
        int predS = (hitbox.y + hitbox.height+ speed)/game.tileSize;
        int predD = (hitbox.x + hitbox.width + speed)/game.tileSize;
        int upLeft = gp.ts.map[predA][predW];
        int upRight= gp.ts.map[predD][predW];
        int downLeft = gp.ts.map[predA][predS];
        int downRight = gp.ts.map[predD][predS];

        switch (direction){
            /*case "W":
                if(gp.ts.tiles[upLeft].collision){
                    y += speed;
                    collision = true;
                }
                else if(gp.ts.tiles[upRight].collision){
                    y += speed;
                    collision = true;
                }break;*/
            /*case "S":
                if(gp.ts.tiles[downLeft].collision){
                    y -= speed;
                    collision = true;
                }
                else if(gp.ts.tiles[downRight].collision){
                    y -= speed;
                    collision = true;
                }break;*/
            case "A":
                if(gp.ts.tiles[upLeft].collision){
                    x += speed;
                    collision = true;
                }
                else if(gp.ts.tiles[downLeft].collision){
                    x += speed;
                    collision = true;
                }break;
            case "D":
                if(gp.ts.tiles[upRight].collision){
                    x -= speed;
                    collision = true;
                }
                else if(gp.ts.tiles[downRight].collision){
                    x -= speed;
                    collision = true;
                }break;
        }
    }

    public void update(){
        if(gp.mode == gp.gameMode){
            setTeleport();
            collision = false;
            collisionCheckTiles();
            gp.manager.solidObjCollision(this);
            checkExp();
            if(life>maxLife){life=maxLife;}
            if(life <= 0){gp.mode = gp.endGameMode;}
            if(gc.attacking){
                gc.moving = false;
                attack();
            }
            else{
                gc.moving = false;
                if(gc.left && !gc.right){
                    gc.moving = true;
                    direction = "A";
                    x -= speed;
                }
                else if(gc.right && !gc.left){
                    gc.moving = true;
                    direction = "D";
                    x += speed;
                }
                else if(gc.up && !gc.down && !gc.left && !gc.right){
                    gc.moving = true;
                    direction = "W";
                    y -= speed;
                }
                else if(gc.down && !gc.up && !gc.left && !gc.right){
                    gc.moving = true;
                    direction = "S";
                    y += speed;
                }

                animationCounter++;
                if(animationCounter >= 30){
                    if(animation == 1){
                        animation = 2;
                    }
                    else if(animation == 2){
                        animation = 1;
                    }
                    animationCounter = 0;
                }
            }
            hitbox.x = x + 15;
            hitbox.y = y + 15;
        }
    }
    public void drawPlayer(Graphics2D g2){
        BufferedImage img = down;
        int width = game.tileSize * 2, height = game.tileSize;
        getCenterScreen();

        switch (direction) {
            case "W" -> {
                if (gc.attacking) {
                    if (animation == 1) {img = attUp;}
                    if (animation == 2) {img = attUp2;}
                } else {
                    if (animation == 1) {img = up;}
                    if (animation == 2) {img = up2;}
                }
            }
            case "A" -> {
                if (gc.attacking) {
                    if (animation == 1) {img = attLeft;}
                    if (animation == 2) {img = attLeft2;}
                } else {
                    if (animation == 1) {img = left;}
                    if (animation == 2) {img = left2;}
                }
            }
            case "S" -> {
                if (gc.attacking) {
                    if (animation == 1) {img = attDown;}
                    if (animation == 2) {img = attDown2;}
                } else {
                    if (animation == 1) {img = down;}
                    if (animation == 2) {img = down2;}
                }
            }
            case "D" -> {
                if (gc.attacking) {
                    if (animation == 1) {img = attRight;}
                    if (animation == 2) {img = attRight2;}
                } else {
                    if (animation == 1) {img = right;}
                    if (animation == 2) {img = right2;}
                }
            }
        }

        if(gc.attacking){
            switch (direction) {
                case "D" -> {
                    g2.drawImage(img.getScaledInstance(width, height, 0), xScreen - 30, yScreen, null);
                    if (gc.info)
                        g2.drawRect(xScreen + weaponHitbox.width * 2, yScreen, weaponHitbox.width, weaponHitbox.height);
                }
                case "A" -> {
                    g2.drawImage(img.getScaledInstance(width, height, 0), xScreen - 40, yScreen, null);
                    if (gc.info)
                        g2.drawRect(xScreen - weaponHitbox.width, yScreen, weaponHitbox.width, weaponHitbox.height);
                }
                case "W" -> {
                    g2.drawImage(img.getScaledInstance(height, width, 0), xScreen, yScreen - 50, null);
                    if (gc.info)
                        g2.drawRect(xScreen, yScreen - weaponHitbox.height, weaponHitbox.width, weaponHitbox.height);
                }
                case "S" -> {
                    g2.drawImage(img.getScaledInstance(height, width, 0), xScreen, yScreen, null);
                    if (gc.info)
                        g2.drawRect(xScreen + weaponHitbox.width / 2, yScreen + weaponHitbox.height * 2, weaponHitbox.width, weaponHitbox.height);
                }
            }
        }
        else {
            g2.drawImage(img.getScaledInstance(height, height, 0), xScreen, yScreen, null);
        }
        if(gc.info){
            g2.setColor(Color.yellow);
            g2.drawRect(xScreen +15, yScreen +15, hitbox.width, hitbox.height);
        }
    }
    public void attack(){
        animationCounter++;

        switch (direction) {
            case "W" -> {
                weaponHitbox.x = x;
                weaponHitbox.y = y - weaponHitbox.height;
            }
            case "S" -> {
                weaponHitbox.x = x + weaponHitbox.width / 2;
                weaponHitbox.y = y + weaponHitbox.height * 2;
            }
            case "A" -> {
                weaponHitbox.x = x - weaponHitbox.width;
                weaponHitbox.y = y;
            }
            case "D" -> {
                weaponHitbox.x = x + weaponHitbox.width * 2;
                weaponHitbox.y = y;
            }
        }

        if(animationCounter <= 10){
            animation = 1;
        }
        if(animationCounter > 10 && animationCounter <= 60) {
            animation = 2;
        }
        if(animationCounter > 60){
            gc.attacking = false;
            animationCounter = 0;
        }
    }
    public void getImages(){
        up = setImage("/player/ninjaUp");
        up2 = setImage("/player/ninjaUp2");
        down = setImage("/player/ninjaDown");
        down2 = setImage("/player/ninjaDown2");
        left2 = setImage("/player/ninjaLeft");
        left = setImage("/player/ninjaLeft2");
        right2 = setImage("/player/ninjaRight");
        right = setImage("/player/ninjaRight2");
    }
    public void attackingImages(){
        attUp = setImage("/player/ninjaAttUp");
        attUp2 = setImage("/player/ninjaAttUp2");
        attDown = setImage("/player/ninjaAttDown");
        attDown2 = setImage("/player/ninjaAttDown2");
        attLeft = setImage("/player/ninjaAttLeft2");
        attLeft2 = setImage("/player/ninjaAttLeft");
        attRight = setImage("/player/ninjaAttRight2");
        attRight2 = setImage("/player/ninjaAttRight");
    }
    public int getAttack(){
        return attack + selectedWeapon.attack;
    }
    public void teleport(int posx, int posy, int newX, int newY, int map){
        if(x/game.tileSize == posx/game.tileSize && y/game.tileSize == posy/game.tileSize){
            gp.teleport = true;
            if(gp.ts.realMap != map){
                gp.ts.realMap = map;
                gp.changeMap();
            }
            x = newX;
            y = newY;


        }
    }
    private void setTeleport(){
        teleport(8*game.tileSize, 42*game.tileSize, 39*game.tileSize, 42*game.tileSize,1);
        teleport(8*game.tileSize, 41*game.tileSize, 39*game.tileSize, 42*game.tileSize,1);

        teleport(40*game.tileSize, 43*game.tileSize, 11*game.tileSize, 43*game.tileSize,0);
        teleport(40*game.tileSize, 42*game.tileSize, 11*game.tileSize, 43*game.tileSize,0);

        teleport(22*game.tileSize, 23*game.tileSize, 39*game.tileSize, 8*game.tileSize,2);
        teleport(22*game.tileSize, 24*game.tileSize, 39*game.tileSize, 8*game.tileSize,2);
    }
    private void checkExp(){
        if(exp >= expNeeded){
            gp.levelUp = true;
            level++;
            attack++;
            life+=2;
            if(level%5==0){
                maxLife+=2;
            }
            exp-=expNeeded;
        }
    }


}
