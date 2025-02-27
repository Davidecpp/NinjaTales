package view;

import controller.GameController;
import model.Game;
import model.Manager;
import model.Missions;
import model.Player;
import model.objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static model.Game.*;

public class GraphicPanel extends JPanel {
    private Game game;
    public GameController gc = new GameController(this);
    public TileSetter ts;
    public Player player = new Player(this, gc);
    public int mode;
    //MODE
    public final int startMode = 0;
    public final int gameMode = 1;
    public final int inventoryMode = 2;
    public final int optionsMode = 3;
    public final int endGameMode = 4;
    public final int victoryMode = 5;
    public final int shopMode = 6;
    //CMD
    public int cmd = 0;
    public int cmdCol = 0;
    public int cmdRow = 0;
    //TIMER
    int timer = 0;
    int timerLevel = 0;
    int screenTxtTimer = 0;
    int lootTimer = 0;
    public boolean levelUp = false;
    Heart lifeImage = new Heart(game);
    Coin coinImage = new Coin(game);
    BufferedImage fullHeart, halfHeart, emptyHeart;
    public Manager manager;
    public Missions missions[] = new Missions[10];
    Color primaryBrown0 = new Color(166, 123, 91);
    Color secondaryBrown0 = new Color(250, 214, 165);
    Color primaryBlue1 = new Color( 0 , 70, 150);
    Color secondaryBlue1 = new Color( 0 , 100, 150);
    Color primaryPurple2 = new Color(90, 20, 100);
    Color secondaryPurple2 = new Color(60, 30, 80);
    Color primary, secondary;
    Color black = Color.BLACK;
    Color white = Color.WHITE;
    public int selectedColor = 1;
    public int optionsCmd = 0;
    public boolean fullScreen = false;
    public boolean chosen = false;
    public boolean boolScreenTxt = false;
    public boolean chestLoot = false;
    int chosX, chosY;
    String screenTxt = "";
    public boolean teleport = false;
    int teleportCounter = 0;
    public boolean commands = false;
    public boolean buy = false;
    public ArrayList<GameObject> shop = new ArrayList<>();
    public GraphicPanel(Game game) {
        this.game = game;
        setSize();
        ts = new TileSetter(game, this);
        manager = new Manager(game, this);
        addKeyListener(gc);
        mode = startMode;
        shop.add(new Potion(game));
        shop.add(new Falce(game));
        shop.add(new Knife(game));
        shop.add(new SwordBase(game));
        shop.add(new SuperSpada(game));
        shop.add((new Key(game)));
    }

    private void setSize() {
        Dimension D = new Dimension(gameWidth, gameHeight);
        setPreferredSize(D);
    }
    private void chooseColor(Color p, Color s){
        primary = p;
        secondary = s;
    }
    private String getStyleName(){
        String name = "";
        switch (selectedColor){
            case 0: name = "Sand"; break;
            case 1: name = "Ocean"; break;
            case 2: name = "Demoniac"; break;
        }
        return name;
    }
    private void setColorStyle(){
        switch (selectedColor){
            case 0: chooseColor(primaryBrown0, secondaryBrown0); break;
            case 1: chooseColor(primaryBlue1, secondaryBlue1); break;
            case 2: chooseColor(primaryPurple2, secondaryPurple2); break;
        }
    }
    private void setFullScreen() {
        if (fullScreen) {
            gameWidth = 1440;
            gameHeight = 960;
            game.window.window.setSize(gameWidth, gameHeight);
        } else {
            gameWidth = 1200;
            gameHeight = 800;
            game.window.window.setSize(gameWidth, gameHeight);
        }
        game.window.window.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
        setColorStyle();
        if (mode == startMode) {
            startOptions(g2);
            player.setStartPlayer();
            manager.setStuff();
        }
        else if (mode == gameMode) {
            ts.draw(g2);
            drawObjects(g2);
            drawLife(g2);
            drawLevel(g2);
            drawEnemies(g2);
            drawProj(g2);
            player.drawPlayer(g2);
            drawLevelUp(g2);
            drawMissions(g2);
            drawScreenTxt(g2);
            drawLoot(g2);
            drawTeleport(g2);
        } else if (mode == inventoryMode) {
            ts.draw(g2);
            drawEnemies(g2);
            drawInventory(g2);
        } else if(mode == optionsMode){
            ts.draw(g2);
            drawMenu(g2);
            if(commands){
                drawCommands(g2);
            }
        } else if(mode == endGameMode){
            drawEndGame(g2);
        }else if(mode == victoryMode){
            drawVictory(g2);
        }else if(mode == shopMode){
            ts.draw(g2);
            drawShop(g2);
        }

        drawInfo(g2);
    }
    public void changeMap(){
        if(ts.realMap == 0) {
            clearArrayEnemies();
            clearArrayObj();
            manager.setStuff();
        }
        if(ts.realMap == 1){
            clearArrayEnemies();
            clearArrayObj();
            manager.setStuff();
        }
        if(ts.realMap == 2){
            clearArrayEnemies();
            clearArrayObj();
            manager.setStuff();
        }
    }
    private void clearArrayEnemies(){
        for(int i = 0; i < game.enemies.length; i++){
            game.enemies[i] = null;
        }
    }
    private void clearArrayObj(){
        for(int i = 0; i < game.obj.length; i++){
            game.obj[i] = null;
        }
    }
    private void restart(){
        ts.realMap = 0;
        clearArrayObj();
        clearArrayEnemies();
        player.setStartPlayer();
        manager.setStuff();
    }
    private void drawEndGame(Graphics2D g2) {
        int width = 400;
        int height = 400;
        int x = gameWidth/2-width/2;
        int y = gameHeight/2-height/2;

        if(cmd < 0){cmd = 1;}
        else if(cmd >1){cmd = 0;}

        ts.draw(g2);
        drawBorderRect(g2,x, y, width, height);y+= 60;
        g2.setFont(getFont().deriveFont(Font.BOLD, 50));
        invText(g2, "GAME OVER",x + 35, y, white, black);y+=100;

        g2.setFont(getFont().deriveFont(Font.BOLD, 30));
        invText(g2,">", x + 100, y + cmd*50, white, black);
        invText(g2, "RESTART",x + 125, y, white, black);y+=50;
        invText(g2, "EXIT",x + 155, y, white, black);

        if(chosen){
            chosen = false;
            if(cmd == 0){
                restart();
                mode = gameMode;
            }
            if(cmd == 1){
                mode = startMode;
                cmd = 0;
            }
        }
    }
    public void drawObjects(Graphics2D g2) {
        for (int i = 0; i < game.obj.length; i++) {
            if (game.obj[i] != null) {
                game.obj[i].drawObj(g2, gc.info);
                game.obj[i].pickObj(i);

                if (game.obj[i] != null) {
                    game.obj[i].interact(i);
                }
            }
        }
    }
    public void drawEnemies(Graphics2D g2) {
        for (int i = 0; i < game.enemies.length; i++) {
            if (game.enemies[i] != null) {
                game.enemies[i].draw(g2, gc.info);
                manager.damagePlayer(i, g2);
                manager.kill(i);
            }
        }
    }
    public void drawProj(Graphics2D g2) {
        if(player.shot){
            if(player.usable){
                manager.setProj();
                player.usable = false;
            }
            for (int i = 0; i < game.proj.length; i++) {
                if (game.proj[i] != null && player.shot) {
                    game.proj[i].draw(g2, gc.info);
                    game.proj[i].projDamage(g2);
                    if(game.proj[i].collision){
                        game.proj[i]=null;
                    }
                }
            }
            timer++;
            if(timer > 40){
                timer = 0;
                player.usable = true;
                player.shot = false;
            }
        }
    }
    private void drawMissions(Graphics2D g2) {
        g2.setFont(getFont().deriveFont(Font.PLAIN, 25));
        int x = 50;
        int y = 250;
        for (int i = 0; i < missions.length; i++) {
            if (missions[i] != null && !missions[i].locked && !missions[i].completed) {
                drawBorderRect(g2, x, y, game.tileSize * 4 + 15, game.tileSize + 10);
                invText(g2, missions[i].name, x, y, black, Color.orange);
                invText(g2, missions[i].txt, x, y + 30, white, black);
                invText(g2, manager.killCount + "/" + missions[i].target, x + game.tileSize * 4 - 60, y + 30,white, black);
                missions[i].setCompleted(i);
            }
        }
    }
    public void drawBorderRect(Graphics2D g2, int x, int y, int width, int height){
        g2.setColor(secondary);
        g2.fillRoundRect(x - 15, y - 35, width, height, 20, 20);
        g2.setColor(primary);
        g2.fillRoundRect(x - 10, y - 30, width - 10, height - 10, 20, 20);
        g2.setColor(Color.orange);
    }
    private void titolo(Graphics2D g2) {
        int width = 710;
        int x = (gameWidth / 2) - width / 2;
        g2.setFont(getFont().deriveFont(Font.BOLD, 100));
        g2.setColor(Color.black);
        g2.fillRoundRect(x, 90, width, 150, 30, 30);
        g2.setColor(new Color(246, 230, 11));
        g2.fillRoundRect(x + 5, 95, width - 10, 140, 30, 30);
        invText(g2,"NINJA TALES", x + 30, 200, black, white);
    }
    private void startOptions(Graphics2D g2) {
        setBackground(primary);
        titolo(g2);
        g2.setFont(getFont().deriveFont(Font.BOLD, 60));
        g2.setColor(secondary);
        int xRect, yRect, widthRect = 0, heightRect = 90;

        if(cmd < 0){cmd = 2;}
        else if(cmd>2){cmd = 0;}

        switch (cmd) {
            case 0 -> widthRect = 400;
            case 1 -> widthRect = 360;
            case 2 -> widthRect = 186;
        }
        xRect = (gameWidth / 2) - widthRect / 2;
        yRect = gameHeight/5*2 + cmd * 100;
        g2.fillRoundRect(xRect, yRect, widthRect, heightRect, 20, 20);
        //TESTI
        int yTxt = gameHeight/5*2 + heightRect*2/3+8;
        int xTxt = gameWidth/2 - 165;
        invText(g2,"NEW GAME", xTxt , yTxt, white, black);yTxt+=100;
        invText(g2,"OPTIONS", xTxt + 30, yTxt, white, black);yTxt+=100;
        invText(g2,"EXIT", xTxt + 100, yTxt, white, black);yTxt+=100;

        g2.setFont(getFont().deriveFont(Font.PLAIN, 20));
        invText(g2,"ENTER TO CONTINUE", gameWidth/2 - 100, gameHeight - 70,white,black);
    }
    //GAME INFO
    private void drawInfo(Graphics2D g2) {
        int space = 100;
        int x = gameWidth - 300;
        if (gc.info) {
            g2.setColor(Color.white);
            g2.setFont(getFont().deriveFont(Font.BOLD, 30));

            if(game.enemies[0]!=null){
                g2.drawString("direction nemico: " + game.enemies[0].direction, x, space);space += 30;
            }
            g2.drawString("map: " + ts.realMap, x, space);space += 30;
            g2.drawString("chestLoot: " + chestLoot, x, space);space += 30;
            g2.drawString("lootTimer: " + lootTimer, x, space);space += 30;
            g2.drawString("chosen: " + chosen, x, space);space += 30;
            g2.drawString("chosX: " + chosX, x, space);space += 30;
            g2.drawString("chosY: " + chosY, x, space);space += 30;
            g2.drawString("CmdCol: " + cmdCol, x, space);space += 30;
            g2.drawString("CmdRow: " + cmdRow, x, space);space += 30;
            g2.drawString("usable: " + player.usable, x, space); space += 30;
            g2.drawString("shot: " + player.shot, x, space);space += 30;
            g2.drawString("timer: " + manager.timer, x, space);space += 30;
            g2.drawString("x: " + player.x / game.tileSize, x, space);space += 30;
            g2.drawString("y: " + player.y / game.tileSize, x, space);space += 30;
            g2.drawString("Mode: " + mode, x, space);space += 30;
            g2.drawString("Direction: " + player.direction, x, space);space += 30;
            g2.drawString("Cmd: " + cmd, x, space);space += 30;
            g2.drawString("FPS: " + game.FPS, x, space);space += 30;
            g2.drawString("attackX: " + player.weaponHitbox.x / game.tileSize, x, space);space += 30;
            g2.drawString("attackY: " + player.weaponHitbox.y / game.tileSize, x, space);space += 30;
            g2.drawString("moving:"+gc.moving, x, space);space += 30;
            g2.drawString("attacking: "+gc.attacking, x, space);space += 30;
            g2.drawString("collision: "+player.collision, x, space);

            g2.setColor(Color.red);
            g2.drawLine(gameWidth/2, 0, gameWidth/2,gameHeight);//VERTICALE
            g2.drawLine(0, gameHeight/2, gameWidth,gameHeight/2);//ORIZZONTALE
        }
    }
    public void update() {
        player.update();
        for (int i = 0; i < game.enemies.length; i++) {
            if (game.enemies[i] != null) {
                game.enemies[i].update();
            }
        }
        for(int i = 0; i < game.proj.length; i++){
            if(game.proj[i]!=null){
                game.proj[i].update();
            }
        }
        setFullScreen();
    }
    private void drawInventory(Graphics2D g2) {
        int width = 600;
        int height = 600;
        int xRect = game.gameWidth / 2 - width / 2;
        int yRect = game.gameHeight / 2 - height / 2;


        g2.setColor(primary);
        g2.fillRoundRect(xRect - 3, yRect - 3, width + 6, height + 6, 20, 20);
        g2.setColor(secondary);
        g2.fillRoundRect(xRect, yRect, width, height, 20, 20);
        g2.setColor(primary);
        g2.fillRoundRect(xRect + 10, yRect + 10, 250, 580, 20, 20);
        g2.fillRoundRect(xRect + 300, yRect + 10, 280, 400, 20, 20);
        g2.fillRoundRect(xRect + 300, yRect + 430, 280, 140, 20, 20);
        drawObjInfo(g2, xRect + 310,yRect + 460);
        drawCursor(g2, yRect + 15);
        drawInvObj(g2, yRect + 15);
        //TEXTS
        xRect += 30;
        yRect += 20;
        g2.setColor(Color.white);
        g2.setFont(getFont().deriveFont(Font.BOLD, 30));
        yRect += 40;

        g2.drawString("Life: " + player.life + "/" + player.maxLife, xRect, yRect);
        g2.setColor(Color.black);
        g2.drawString("Life: " + player.life + "/" + player.maxLife, xRect - 1, yRect - 1);
        g2.drawImage(lifeImage.img, xRect + 130, yRect - 32, game.tileSize / 2, game.tileSize / 2, null);

        yRect += 40;
        invText(g2, "Attack: " + player.getAttack(), xRect, yRect,white,black);yRect += 40;
        invText(g2, "exp: " + player.exp, xRect, yRect,white,black);yRect += 40;
        invText(g2, "Next level: " + (player.expNeeded - player.exp), xRect, yRect,white,black);yRect += 40;
        invText(g2, "Level: " + player.level, xRect, yRect,white,black); yRect+=40;
        invText(g2,"Weapon: ", xRect, yRect,white,black);
        g2.drawImage(player.selectedWeapon.img, xRect + 130, yRect - 32, game.tileSize / 2, game.tileSize / 2, null);

        yRect += 300;
        invText(g2,"Coin: "+player.coin, xRect, yRect,white,black);
        g2.drawImage(coinImage.img, xRect + 120, yRect - 32, game.tileSize / 2, game.tileSize / 2, null);
        xRect += 160;
    }
    public void setTxt(String txt){
        screenTxt = txt;
        boolScreenTxt = true;
    }
    private void drawScreenTxt(Graphics2D g2){
        if(boolScreenTxt){
            invText(g2, screenTxt, gameWidth/4, gameHeight/2, white, black);
            screenTxtTimer++;
            if(screenTxtTimer > 60){
                screenTxtTimer = 0;
                boolScreenTxt = false;
                screenTxt = "";
            }
        }

    }
    private void invText(Graphics2D g2, String txt, int xRect, int yRect, Color p, Color s) {
        g2.setColor(p);
        g2.drawString(txt, xRect, yRect);
        g2.setColor(s);
        switch (txt) {
            case "Rare" -> g2.setColor(Color.green);
            case "Normal" -> g2.setColor(Color.gray);
            case "Epic" -> g2.setColor(new Color(100, 0, 130));
            case "Legendary" -> g2.setColor(Color.orange);
        }
        g2.drawString(txt, xRect - 1, yRect - 1);
    }
    private void drawCursor(Graphics2D g2, int yInv) {
        int xInv = game.gameWidth / 2 + 5;
        int val = game.tileSize * 2 / 3;
        g2.setStroke(new BasicStroke(5));

        if (cmdCol > 4) {cmdCol = 0;}
        else if (cmdCol < 0) {cmdCol = 4;}
        if (cmdRow > 6) {cmdRow = 0;}
        else if (cmdRow < 0) {cmdRow = 6;}

        xInv += cmdCol * (val);
        if (cmdRow != 0) {
            yInv += cmdRow * val + 8;
        }
        if(cmdRow >= 2){yInv+=4 + cmdRow*3;}
        if(cmdRow >= 4){yInv+=5;}
        if(chosen){

            chosen = false;
            int index = cmdCol + cmdRow*5;
            if(player.inventory.get(index).name.equals("Potion")){
                player.life+=2;
                player.inventory.remove(index);
            }
            if(player.inventory.get(index).attack!=0){
                chosX = xInv;
                chosY = yInv;
                player.selectedWeapon = player.inventory.get(index);
            }

        }
        if(chosX==0){
            g2.setColor(new Color(0, 100, 80));
            g2.fillRoundRect(game.gameWidth / 2 + 5, yInv, val, val, 30, 30);
        }
        else{
            g2.setColor(new Color(0, 100, 80));
            g2.fillRoundRect(chosX, chosY, val, val, 30, 30);
        }

        g2.setColor(Color.black);
        g2.drawRoundRect(xInv, yInv, val, val, 5, 5);
        g2.setStroke(new BasicStroke(1));
    }
    private void drawInvObj(Graphics2D g2, int yInv) {
        int xInv = game.gameWidth / 2 + 5;
        int c = 0;
        for (int i = 0; i < player.inventory.size(); i++) {
            if (player.inventory.get(i) != null) {
                if (player.inventory.get(i).pickable) {
                    g2.drawImage(player.inventory.get(i).img, xInv, yInv, game.tileSize * 2 / 3, game.tileSize * 2 / 3, null);
                    drawRarity(g2, i, xInv, yInv);
                    c++;
                    xInv += 52;
                    if (c == 5) {
                        yInv += 60;
                        xInv = game.gameWidth / 2 + 5;
                        c = 0;
                    }
                }
            }
        }
    }
    private void drawObjInfo(Graphics2D g2, int x, int y){
        g2.setFont(getFont().deriveFont(Font.BOLD, 28));
        int index = cmdCol + cmdRow*5;
        if(index >= 0 && index < player.inventory.size()){
            invText(g2,"["+player.inventory.get(index).name+"]", x, y,white,black);y+=30;
            invText(g2,"Damage: "+player.inventory.get(index).attack, x, y,white,black);y+=30;
            invText(g2,player.inventory.get(index).rarity, x, y,white,black);y+=30;
        }

    }
    private void drawRarity(Graphics2D g2, int i, int x, int y){
        if(player.inventory.get(i).rarity.equals("Normal")){
            g2.setColor(Color.gray);
            g2.fillOval(x+30, y+30, 20, 20);
        }
        else if(player.inventory.get(i).rarity.equals("Rare")){
            g2.setColor(Color.green);
            g2.fillOval(x+30, y+30, 20, 20);
        }
        else if(player.inventory.get(i).rarity.equals("Epic")){
            g2.setColor(new Color(100, 0, 130));
            g2.fillOval(x+30, y+30, 20, 20);
        }
        else if(player.inventory.get(i).rarity.equals("Legendary")){
            g2.setColor(Color.orange);
            g2.fillOval(x+30, y+30, 20, 20);
        }
        g2.setColor(Color.black);
    }
    private void drawLife(Graphics2D g2) {
        fullHeart = manager.setImage("/obj/heart");
        emptyHeart = manager.setImage("/obj/heartEmpty");
        halfHeart = manager.setImage("/obj/heartHalf");
        int x = 50;
        int x2 = 50;

        for (int i = 0; i < player.maxLife / 2; i++) {
            g2.drawImage(emptyHeart, x, 50, game.tileSize, game.tileSize, null);
            x += game.tileSize;
        }
        for (int i = 0; i < player.life / 2; i++) {
            g2.drawImage(fullHeart, x2, 50, game.tileSize, game.tileSize, null);
            x2 += game.tileSize;
        }
        if (player.life % 2 == 1) {
            g2.drawImage(halfHeart, x2, 50, game.tileSize, game.tileSize, null);
        }

    }
    private void drawLevel(Graphics2D g2) {
        int x = 50;
        int width = 400;
        int expBar = width / player.expNeeded;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x - 2, 128, 404, 44, 50, 50);
        g2.setColor(new Color(0, 140, 220));
        g2.setStroke(new BasicStroke(1));
        g2.fillRoundRect(x, 130, player.exp * expBar, 40, 50, 50);
        g2.setFont(getFont().deriveFont(Font.PLAIN, 50));
        g2.setColor(Color.black);
        g2.drawString(String.valueOf(player.level+1), x + width + 8, 168);
        g2.setColor(new Color(0, 140, 220));
        g2.drawString(String.valueOf(player.level+1), x + width + 10, 170);
    }
    private void drawLevelUp(Graphics2D g2) {
        int x = gameWidth / 2 - 150;
        int y = gameHeight / 2;
        g2.setFont(getFont().deriveFont(Font.BOLD, 100));
        if(levelUp){
            manager.soundManager.playSound(2);
            invText(g2, "Level " + player.level, x, y,white,black);
            sparks(g2, Color.yellow, x, y, timerLevel);
            timerLevel++;
            if(timerLevel > 80){
                timerLevel = 0;
                player.expNeeded*=2;
                levelUp = false;
            }
        }
    }
    private void sparks(Graphics2D g2, Color color, int x, int y, int timer){
        g2.setColor(color);
        int width = 30;
        int height = 10;
        if(timer % 4 == 0){
            g2.fillRect(x , y - 50, width, height);
            g2.fillRect(x + 10, y-60, height, width);

            g2.fillRect(x + 250, y - 50, width, height);
            g2.fillRect(x + 260, y-60, height, width);
        }
        else if(timer % 5==0){
            g2.setColor(Color.orange);
            g2.fillRect(x , y - 50, width-5, height-5);
            g2.fillRect(x + 10, y-60, height-5, width-5);

            g2.fillRect(x + 250, y - 50, width-5, height);
            g2.fillRect(x + 260, y-60, height-5, width-5);
        }
        else if(timer % 3 == 0){
            g2.fillRect(x + 100, y - 30, width, height);
            g2.fillRect(x + 110, y-40, height, width);

            g2.fillRect(x + 300, y - 10, width, height);
            g2.fillRect(x + 310, y-20, height, width);
        }
    }
    private void drawMenu(Graphics2D g2){
        int width = 550;
        int height = 600;
        int x = gameWidth/2-width/2;
        int y = gameHeight/2-height/2;
        drawBorderRect(g2, x, y, width, height);y+=100;
        g2.setFont(getFont().deriveFont(Font.PLAIN, 60));
        invText(g2, "OPTIONS", x + width/5, y,white,black);y+= 120;
        x += 30;
        g2.setFont(getFont().deriveFont(Font.BOLD, 35));
        if(optionsCmd < 0){optionsCmd = 4;}
        if(optionsCmd > 4){optionsCmd = 0;}
        g2.setColor(secondary);
        g2.fillRoundRect(x - 25, y-40+50*optionsCmd, 280, 50, 20, 20);
        invText(g2, ">", x - 20, y + 50*optionsCmd,white,black);
        invText(g2, "WINDOW          "+game.gameWidth+"x"+game.gameHeight, x, y,white,black); y+= 50;
        invText(g2, "COMMANDS", x, y,white,black); y+= 50;
        invText(g2, "COLOR STYLE   <"+getStyleName()+">", x, y,white,black); y+= 50;
        invText(g2, "EXIT", x, y,white,black); y+= 50;
    }
    private void drawCommands(Graphics2D g2){
        int width = 550;
        int height = 600;
        int x = gameWidth/2-width/2;
        int y = gameHeight/2-height/2;
        drawBorderRect(g2, x, y, width, height);y+=100;
        g2.setFont(getFont().deriveFont(Font.PLAIN, 60));
        invText(g2, "COMMANDS", x + width/5, y,white,black);y+= 120;
        x += 30;
        g2.setFont(getFont().deriveFont(Font.BOLD, 35));
        invText(g2, "MOVE -> W, A, S, D", x, y,white,black); y+= 50;
        invText(g2, "ATTACK -> ENTER", x, y,white,black); y+= 50;
        invText(g2, "SHOOT -> F   <"+getStyleName()+">", x, y,white,black); y+= 50;
        invText(g2, "INFO -> I", x, y,white,black); y+= 50;
        invText(g2, "INVENTORY -> E", x, y,white,black); y+= 50;
        invText(g2, "SHOP -> H", x, y,white,black); y+= 50;
    }
    public void drawChestLoot(int index, Graphics2D g2){
        if(chestLoot){
            int size = 100;
            int rectX = gameWidth/2-size/2 + 12;
            int rectY = gameHeight/3 - 20;
            lootTimer++;
            drawBorderRect(g2, rectX,rectY+size/2,size,size);
            g2.drawImage(game.obj[index].getLoot().img, rectX - 5 ,rectY + 25, game.tileSize, game.tileSize,null);

            if(lootTimer >= 60){
                lootTimer = 0;
                chestLoot = false;
            }
        }
    }
    private void drawShop(Graphics2D g2){
        int width = 250;
        int height = 200;
        int x = gameWidth/2-width/2;
        int y = gameHeight/2-height/2;

        drawBorderRect(g2, x,y,width,height);
        drawShopCursor(g2, x,y);
        drawShopObj(g2, y, x);
        int index = cmdCol + cmdRow*5;
        if(buy){
            if(player.coin >= shop.get(index).price){
                player.inventory.add(shop.get(index));
                player.coin -= shop.get(index).price;
                setTxt("Hai comprato!!");
            }
            buy = false;
        }
        g2.setFont(getFont().deriveFont(Font.BOLD, 20));
        if(index >= 0 && index < shop.size()){
            invText(g2,"Price: "+shop.get(index).price, x, y,white,black);y+=30;
            g2.drawImage(coinImage.img, x + 80, y - 45, 15, 15, null);
        }

    }
    private void drawShopCursor(Graphics2D g2, int xInv, int yInv) {
        int val = game.tileSize * 2 / 3;
        g2.setStroke(new BasicStroke(5));

        if (cmdCol >3) {cmdCol = 0;}
        else if (cmdCol < 0) {cmdCol = 4;}
        if (cmdRow > 3) {cmdRow = 0;}
        else if (cmdRow < 0) {cmdRow = 4;}

        xInv += cmdCol * (val);
        if (cmdRow != 0) {
            yInv += cmdRow * val + 8;
        }
        if(cmdRow >= 2){yInv+=4 + cmdRow*3;}
        if(cmdRow >= 3){yInv+=5;}

        g2.setColor(Color.black);
        g2.drawRoundRect(xInv, yInv, val, val, 5, 5);
        g2.setStroke(new BasicStroke(1));
    }
    private void drawShopObj(Graphics2D g2, int yInv, int xInv) {
        int x = xInv;
        int c = 0;
        for (int i = 0; i < shop.size(); i++) {
            if (shop.get(i) != null) {
                if (shop.get(i).pickable) {
                    g2.drawImage(shop.get(i).img, xInv, yInv, game.tileSize * 2 / 3, game.tileSize * 2 / 3, null);
                    c++;
                    xInv += 52;
                    if (c == 3) {
                        yInv += 60;
                        xInv = x;
                        c = 0;
                    }
                }
            }
        }
    }
    private void drawLoot(Graphics2D g2){
        for(int i = 0; i < game.obj.length; i++){
            if(game.obj[i]!=null && game.obj[i].name.equals("Chest")){
                if(game.obj[i].opened){
                    drawChestLoot(i, g2);
                }
            }
        }
    }

    private void drawTeleport(Graphics2D g2){
        if(teleport){
            teleportCounter++;
            g2.setColor(black);
            g2.fillRect(0, 0, gameWidth, gameHeight);

            if(teleportCounter>= 40){
                teleportCounter = 0;
                teleport = false;
            }
        }
    }
    private void drawVictory(Graphics2D g2){
        int width = 400;
        int height = 100;
        int x = gameWidth/2-width/2;
        int y = gameHeight/2-height/2;
        drawBorderRect(g2, x, y, width, height);
        g2.setFont(getFont().deriveFont(Font.BOLD, 60));
        invText(g2,"VICTORY",x + 50,y + 30,white,black);

    }
}
