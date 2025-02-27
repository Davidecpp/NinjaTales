package view;

import model.Game;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileSetter {
    Game game;
    GraphicPanel gp;
    public Tile[] tiles;
    public int map[][];
    public int realMap = 0;

    public TileSetter(Game game, GraphicPanel gp){
        this.game = game;
        this.gp = gp;
        tiles = new Tile[50];
        map = new int[game.mapCols][game.mapRows];
        setTilesImages();
    }
    public void chooseMap(){
        if(realMap == 0){
            mapLoader("/maps/worldMap0.txt");
        }if(realMap == 1){
            mapLoader("/maps/mapRoc.txt");
        }
        if(realMap == 2){
            mapLoader("/maps/mapRoc2.txt");
        }
    }


    public void setTilesImages(){
        //PLACE HOLDER PER COMIDITA' NELLA CREAZIONE DELLA MAPPA
        setImage(0, "erba", false);
        setImage(1, "erba", false);
        setImage(2, "erba", false);
        setImage(3, "erba", true);
        setImage(4, "erba", false);
        setImage(5, "erba", true);
        setImage(6, "erba", false);
        setImage(7, "erba", false);
        setImage(8, "erba", true);
        setImage(9, "erba", true);
        setImage(10, "grass", false);
        setImage(11, "albero", true);
        // ACQUA
        setImage(12, "acqua2", true);
        setImage(13, "acquaUp", true);
        setImage(14, "acquaDown", true);
        setImage(15, "acquaLeft", true);
        setImage(16, "acquaRight", true);
        setImage(17, "acquaUpLeft", true);
        setImage(18, "acquaUpRight", true);
        setImage(19, "acquaDownLeft", true);
        setImage(20, "acquaDownRight", true);

        // TERRA
        setImage(21, "terraVerticale", false);
        setImage(22, "terraOrizzontale", false);
        setImage(23, "terraVerticaleUp", false);
        setImage(24, "terraVerticaleDown", false);
        setImage(25, "terraOrizzontaleLeft", false);
        setImage(26, "terraOrizzontaleRight", false);

        setImage(27, "grass2", false);

        setImage(28, "pavCasetta", false);
        setImage(29, "casetta", false);
        setImage(30, "table", true);

        setImage(31, "terraRoc", false);
        setImage(32, "muroRoc", true);
        setImage(33, "muroRocUp", true);
        setImage(34, "muroRocLeft", true);
        setImage(35, "muroRocUpLeft", true);
        setImage(36, "muroRocRight", true);
        setImage(37, "muroRocUpRight", true);
        setImage(38, "muroRocDown", true);
        setImage(39, "muroRocDownLeft", true);
        setImage(40, "muroRocDownRight", true);
        setImage(41, "muroRocUpRightEst", true);
        setImage(42, "muroRocDownRightEst", true);
        setImage(43, "muroRocUpLeftEst", true);
        setImage(44, "muroRocDownLeftEst", true);
        setImage(45, "bucoRockLeft", false);
        setImage(46, "bucoRockRight", false);
        setImage(47, "saveTile", false);
    }
    public BufferedImage setImage(int i, String name, boolean collision){
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+name+".png"));
            tiles[i] = new Tile();
            tiles[i].img = image;
            tiles[i].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
        }
        return  image;
    }
    public void mapLoader(String path){
        try {
            InputStream readMap = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(readMap));
            int col = 0, row = 0;
            while(col < game.mapCols && row < game.mapRows){
                String line = br.readLine();
                while (col < game.mapCols){
                    String pos[] = line.split(" ");
                    int val = Integer.parseInt(pos[col]);
                    map[col][row] = val;
                    col++;
                }
                if(col == game.mapCols){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e){
        }
    }
    public void draw(Graphics2D g2){
        chooseMap();
        int col = 0, row = 0;

        while(col < game.mapCols && row < game.mapRows){
            int val = map[col][row];
            int tileX =  (col * game.tileSize - game.gp.player.x + game.gp.player.xScreen);
            int tileY =  (row * game.tileSize - game.gp.player.y + game.gp.player.yScreen);

            if (col * game.tileSize + game.tileSize > game.gp.player.x - game.gp.player.xScreen &&
                col * game.tileSize - game.tileSize < game.gp.player.x + game.gp.player.xScreen &&
                row * game.tileSize + game.tileSize > game.gp.player.y - game.gp.player.yScreen &&
                row * game.tileSize - game.tileSize < game.gp.player.y + game.gp.player.xScreen) {
                g2.drawImage(tiles[val].img, tileX, tileY, game.tileSize, game.tileSize, null);
            }
            col++;
            if(col == game.mapCols){
                col = 0;
                row++;
            }
        }
    }

}
