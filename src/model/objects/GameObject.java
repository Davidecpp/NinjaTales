package model.objects;

import model.Game;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameObject {
    Game game;
    public int x, y;
    public String rarity = "";
    public BufferedImage img, img2;
    public boolean pickable;
    public boolean picked = false;
    public boolean opened = false;
    public String name = "";
    public Rectangle hitbox;
    public int attack;
    public GameObject loot;
    public int price;
    public boolean solid = false;
    public GameObject(Game game){
        this.game = game;
    }

    public void setImage(){}
    public void setPosition(int posX, int posY){
        x = posX;
        y = posY;
        hitbox = new Rectangle(x, y, game.tileSize - 18, game.tileSize);
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
    public void drawObj(Graphics2D g2, boolean info){
        int objX = x - game.gp.player.x + game.gp.player.xScreen;
        int objY = y - game.gp.player.y + game.gp.player.yScreen;

        if (x + game.tileSize > game.gp.player.x - game.gp.player.xScreen &&
                x - game.tileSize < game.gp.player.x + game.gp.player.xScreen &&
                y + game.tileSize > game.gp.player.y - game.gp.player.yScreen &&
                y - game.tileSize < game.gp.player.y + game.gp.player.xScreen) {
            if(opened){
                g2.drawImage(img2, objX, objY, game.tileSize, game.tileSize, null);
            }
            else{
                g2.drawImage(img, objX, objY, game.tileSize, game.tileSize, null);
            }

            if(info){
                g2.setColor(Color.BLUE);
                g2.drawRect(objX + 7, objY, hitbox.width, hitbox.height);
            }

        }

    }
    public void pickObj(int index){
        if(game.gp.player.hitbox.intersects(game.obj[index].hitbox) && !game.obj[index].solid) {
            if (!game.obj[index].picked && game.obj[index].name == "coin") {
                game.gp.manager.soundManager.playSound(1);
                game.gp.player.coin++;
                game.obj[index].picked = true;
                game.gp.setTxt("Coin +1");
            }
            else if(!game.obj[index].picked && game.obj[index].name == "heart") {
                game.gp.manager.soundManager.playSound(0);
                game.gp.player.life+=2;
                game.obj[index].picked = true;
            }
            else if(!game.obj[index].picked && game.obj[index].name == "Coin Bag") {
                game.gp.manager.soundManager.playSound(1);
                game.gp.player.coin+=5;
                game.obj[index].picked = true;
                game.gp.setTxt("Coin +5");
            }else if(!game.obj[index].picked && game.obj[index].name == "Crown") {
                game.gp.mode = game.gp.victoryMode;
            }
            if(game.obj[index].pickable && index<=game.gp.player.inventorySize)
                game.gp.player.inventory.add(game.obj[index]);

            game.obj[index] = null;
        }
    }
    public void interact(int index){
        if(game.gp.player.weaponHitbox.intersects(game.obj[index].hitbox) && game.obj[index].solid && !game.obj[index].opened){
            if(game.gp.gc.attacking){
                game.gp.gc.attacking = false;
                if(game.obj[index].name.equals("Chest")){
                    game.gp.chestLoot = true;
                    game.gp.player.inventory.add(game.obj[index].getLoot());
                    game.obj[index].opened = true;
                }
                else if(game.obj[index].name.equals("Door")){
                    checkKey(index);
                }
            }
        }
    }
    private void checkKey(int index){
        for(int i = 0; i < game.gp.player.inventory.size(); i++){
            if(game.gp.player.inventory.get(i)!=null){
                if(game.gp.player.inventory.get(i).name.equals("Key")){
                    game.gp.manager.soundManager.playSound(4);
                    game.gp.player.inventory.remove(i);
                    game.obj[index] = null;
                    return;
                }
            }
        }
        game.gp.setTxt("Ti serve una chiave!");
    }

    public void setLoot(GameObject obj){
    }
    public GameObject getLoot(){
        return null;
    }
}



