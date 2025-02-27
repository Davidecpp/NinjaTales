package model;

import model.enemies.*;
import model.objects.*;
import view.GraphicPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Manager {//SETTA E GESTISCE INTERAZIONI
    Game game;
    GraphicPanel gp;
    public Sound soundManager = new Sound();
    boolean contact = false;
    public int timer = 0;
    public int killCount = 0;
    public Manager(Game game, GraphicPanel gp){
        this.game = game;
        this.gp = gp;
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
    private void setObjects(){
        if(game.gp.ts.realMap == 0){
            int i = 0;
            game.obj[i] = new Key(game);
            game.obj[i].setPosition(23 * game.tileSize, 7 * game.tileSize);
            i++;
            game.obj[i] = new Key(game);
            game.obj[i].setPosition(37 * game.tileSize, 39 * game.tileSize);
            i++;
            game.obj[i] = new Potion(game);
            game.obj[i].setPosition(22 * game.tileSize, 7 * game.tileSize);
            i++;
            game.obj[i] = new Potion(game);
            game.obj[i].setPosition(38 * game.tileSize, 23 * game.tileSize);
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(34 * game.tileSize, 7 * game.tileSize);
            game.obj[i].setLoot(new SwordBase(game));
            i++;
            game.obj[i] = new CoinBag(game);
            game.obj[i].setPosition(24 * game.tileSize, 41 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(27 * game.tileSize, 11 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(28 * game.tileSize, 11 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(29 * game.tileSize, 7 * game.tileSize);
            i++;
        }
        if(game.gp.ts.realMap == 1){
            int i = 0;
            game.obj[i] = new Key(game);
            game.obj[i].setPosition(33 * game.tileSize, 20 * game.tileSize);
            i++;
            game.obj[i] = new Heart(game);
            game.obj[i].setPosition(10 * game.tileSize, 19 * game.tileSize);
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(12 * game.tileSize, 7 * game.tileSize);
            game.obj[i].setLoot(new Potion(game));
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(12 * game.tileSize, 43 * game.tileSize);
            game.obj[i].setLoot(new Falce(game));
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(10 * game.tileSize, 29 * game.tileSize);
            game.obj[i].setLoot(new Potion(game));
            i++;
            game.obj[i] = new Key(game);
            game.obj[i].setPosition(23 * game.tileSize, 41 * game.tileSize);
            i++;
            game.obj[i] = new Heart(game);
            game.obj[i].setPosition(35 * game.tileSize, 13 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(29 * game.tileSize, 9 * game.tileSize);
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(39 * game.tileSize, 8 * game.tileSize);
            game.obj[i].setLoot(new Potion(game));
            i++;
        }
        if(game.gp.ts.realMap == 2){
            int i = 0;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(27 * game.tileSize, 25 * game.tileSize);
            game.obj[i].setLoot(new Potion(game));
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(32 * game.tileSize, 33 * game.tileSize);
            game.obj[i].setLoot(new Key(game));
            i++;
            game.obj[i] = new Chest(game);
            game.obj[i].setPosition(27 * game.tileSize, 25 * game.tileSize);
            game.obj[i].setLoot(new Knife(game));
            i++;
            game.obj[i] = new Coin(game);
            game.obj[i].setPosition(18 * game.tileSize, 19 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(39 * game.tileSize, 41 * game.tileSize);
            i++;
            game.obj[i] = new Door(game);
            game.obj[i].setPosition(35 * game.tileSize, 9 * game.tileSize);
            i++;
        }

    }
    private void setEnemies(){
        if(game.gp.ts.realMap == 0){
            int i = 0;
            game.enemies[i] = new Slime(game,22*game.tileSize, 6 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,25*game.tileSize, 8 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,24*game.tileSize, 13 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,21*game.tileSize, 16 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,12*game.tileSize, 18 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,20*game.tileSize, 35 * game.tileSize);i++;

            game.enemies[i] = new Butterfly(game,17*game.tileSize, 18 * game.tileSize);i++;
            game.enemies[i] = new Butterfly(game,11*game.tileSize, 25 * game.tileSize);i++;
            game.enemies[i] = new Butterfly(game,19*game.tileSize, 25 * game.tileSize);i++;
            game.enemies[i] = new Butterfly(game,37*game.tileSize, 18 * game.tileSize);i++;

            game.enemies[i] = new Ball(game,20*game.tileSize, 33 * game.tileSize);i++;
            game.enemies[i] = new Ball(game,13*game.tileSize, 41 * game.tileSize);i++;
            game.enemies[i] = new Ball(game,37*game.tileSize, 37 * game.tileSize);i++;
        }
        if(game.gp.ts.realMap == 1){
            int i = 0;
            game.enemies[i] = new Slime(game,14*game.tileSize, 8 * game.tileSize);i++;
            game.enemies[i] = new Slime(game,15*game.tileSize, 24 * game.tileSize);i++;
            game.enemies[i] = new Butterfly(game,32*game.tileSize, 21 * game.tileSize);i++;
            game.enemies[i] = new Butterfly(game,23*game.tileSize, 29 * game.tileSize);i++;
            game.enemies[i] = new BlueOrc(game,23*game.tileSize, 39 * game.tileSize);i++;
        }
        if(game.gp.ts.realMap == 2){
            int i = 0;
            game.enemies[44] = new KingKong(game,16*game.tileSize, 40 * game.tileSize);
            game.enemies[i] = new Ball(game,32*game.tileSize, 21 * game.tileSize);i++;
            game.enemies[i] = new BlueOrc(game,29*game.tileSize, 23 * game.tileSize);i++;
            game.enemies[i] = new Ball(game,15*game.tileSize, 13 * game.tileSize);i++;
            game.enemies[i] = new BlueOrc(game,12*game.tileSize, 28 * game.tileSize);i++;
        }


    }
    public void setProj(){
        int i = 0;
        game.proj[i] = new Fireball(game);
        switch (game.gp.player.direction) {
            case "W" -> game.proj[i].setPosition(gp.player.x, gp.player.y - 2);
            case "A" -> game.proj[i].setPosition(gp.player.x - 2, gp.player.y);
            case "S" -> game.proj[i].setPosition(gp.player.x, gp.player.y + 2);
            case "D" -> game.proj[i].setPosition(gp.player.x + 2, gp.player.y);
        }

        game.proj[i].direction = gp.player.direction;
    }
    private void setMissions(){
        int i = 0;
        gp.missions[i] = new Missions(gp, "Intro", 3);
        gp.missions[i].txt = "Sconfiggi 3 mostri.";
        gp.missions[i].locked = false;
        i++;
        gp.missions[i] = new Missions(gp, "Missione 2", 5);
        gp.missions[i].txt = "Sconfiggi 5 mostri.";
        gp.missions[i].locked = true;
        i++;
        gp.missions[i] = new Missions(gp, "Missione 3", 10);
        gp.missions[i].txt = "Sconfiggi 10 mostri.";
        gp.missions[i].locked = true;
        i++;
    }
    public void setStuff(){
        setObjects();
        setEnemies();
        setMissions();
    }
    public void damagePlayer(int i, Graphics2D g2){
        if(gp.player.hitbox.intersects(game.enemies[i].hitbox)){contact = true;}
        if(contact) {
            timer++;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            if (timer <= 1) {
                soundManager.playSound(5);
                gp.player.life -= game.enemies[i].attack;
                gp.setTxt("Life -"+game.enemies[i].attack);
            }
            else if (timer > 300){
                contact = false;
                timer = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            }
        }
    }
    public void kill(int i){
        if(game.enemies[i].life <= 0){
            game.gp.player.exp += game.enemies[i].exp;
            spawnLoot(game.enemies[i]);
            game.enemies[i] = null;
            killCount++;
        }
    }
    private int getObjSize(){
        int val = 0;
        for(int i = 0; i < game.obj.length; i++){
            if(game.obj[i]!=null){
                val++;
            }
        }
        return val;
    }
    public void spawnLoot(Creature creature){
        Random random = new Random();
        int r = random.nextInt(0,101);
        int l = getObjSize();

        if (game.obj[l] != null) {
            l++;
        }
        if(creature.name.equals("Slime")){
            if(r <= 80){
                game.obj[l] = new Coin(game);
                game.obj[l].setPosition(creature.x, creature.y);
            }
            else if(r > 80){
                game.obj[l] = new Heart(game);
                game.obj[l].setPosition(creature.x, creature.y);
            }
        } else if(creature.name.equals("Butterfly")){
            if(r <= 50){
                game.obj[l] = new CoinBag(game);
                game.obj[l].setPosition(creature.x, creature.y);
            }
        }else if(creature.name.equals("Ball")){
            if(r <= 70){
                game.obj[l] = new SwordBase(game);
                game.obj[l].setPosition(creature.x, creature.y);
            }

        }
        else if(creature.name.equals("KingKong")){
            game.obj[l] = new Crown(game);
            game.obj[l].setPosition(creature.x, creature.y);
        }

    }
    public void solidObjCollision(Creature creature){
        for(int i = 0; i < game.obj.length; i++){
            if(game.obj[i]!=null && game.obj[i].solid && creature.hitbox!=null && game.obj[i].hitbox!=null){
                if(creature.hitbox.intersects(game.obj[i].hitbox)){
                    switch (creature.direction){
                        case "W" -> creature.y+= creature.speed;
                        case "A" -> creature.x+= creature.speed;
                        case "S" -> creature.y-= creature.speed;
                        case "D" -> creature.x-= creature.speed;
                    }
                }
            }
        }
    }

}
