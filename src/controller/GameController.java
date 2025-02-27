package controller;
import view.GraphicPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.System.exit;
public class GameController implements KeyListener {
    private GraphicPanel gp;
    public boolean moving = false;
    public boolean attacking = false;
    public boolean info = false;
    public boolean up, left, down, right;

    public GameController(GraphicPanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_I){
            if(!info){info = true;}
            else{info = false;}
        }
        if(gp.mode == gp.startMode){startCommand(e);}
        else if (gp.mode == gp.gameMode){playerCommands(e);}
        else if(gp.mode == gp.inventoryMode){inventoryCommands(e);}
        else if(gp.mode == gp.optionsMode){optionsCommand(e);}
        else if(gp.mode == gp.endGameMode){endModeCommand(e);}
        else if(gp.mode == gp.shopMode){shopCommands(e);}
    }

    private void endModeCommand(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_ESCAPE: gp.mode = gp.optionsMode;break;
            case KeyEvent.VK_W: gp.cmd--;break;
            case KeyEvent.VK_S: gp.cmd++;break;
            case KeyEvent.VK_ENTER: gp.chosen = true;break;
        }
    }

    private void inventoryCommands(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_D: gp.cmdCol++;break;
            case KeyEvent.VK_A: gp.cmdCol--;break;
            case KeyEvent.VK_S: gp.cmdRow++;break;
            case KeyEvent.VK_W: gp.cmdRow--;break;
            case KeyEvent.VK_E: gp.mode = gp.gameMode;break;
            case KeyEvent.VK_ESCAPE: gp.mode = gp.optionsMode;break;
            case KeyEvent.VK_ENTER: gp.chosen = true;break;
        }
    }
    private void shopCommands(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_D: gp.cmdCol++;break;
            case KeyEvent.VK_A: gp.cmdCol--;break;
            case KeyEvent.VK_S: gp.cmdRow++;break;
            case KeyEvent.VK_W: gp.cmdRow--;break;
            case KeyEvent.VK_H: gp.mode = gp.gameMode;break;
            case KeyEvent.VK_ESCAPE: gp.mode = gp.optionsMode;break;
            case KeyEvent.VK_ENTER: gp.buy = true;break;
        }
    }
    private void startCommand(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W:
                gp.cmd--;
                break;
            case KeyEvent.VK_S:
                gp.cmd++;
                break;
            case KeyEvent.VK_ENTER:
                down = false;
                if(gp.cmd == 0){gp.mode = gp.gameMode;}
                if(gp.cmd == 1){gp.mode = gp.optionsMode;}
                if(gp.cmd == 2){exit(0);}
                break;
        }
    }
    private void optionsCommand(KeyEvent e){
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_ESCAPE: gp.mode = gp.gameMode;break;
            case KeyEvent.VK_W: gp.optionsCmd--;break;
            case KeyEvent.VK_S: gp.optionsCmd++;break;
            case KeyEvent.VK_ENTER:
                if(gp.optionsCmd == 0){
                    if(gp.fullScreen){gp.fullScreen = false;}
                    else{gp.fullScreen = true;}
                }
                else if(gp.optionsCmd == 1){
                    if(!gp.commands){
                        gp.commands = true;
                    }else{
                        gp.commands = false;
                    }
                }
                else if(gp.optionsCmd == 2){
                    gp.selectedColor++;
                    if(gp.selectedColor > 3){
                        gp.selectedColor = 0;
                    }
                }else if(gp.optionsCmd == 3){
                    gp.mode = gp.startMode;
                }
        }
    }
    private void playerCommands(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W:
                up = true;
                moving = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                moving = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                moving = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                moving = true;
                break;
            case KeyEvent.VK_ENTER:
                attacking = true; break;
            case KeyEvent.VK_ESCAPE:
                gp.mode = gp.optionsMode;
                break;
            case KeyEvent.VK_E:
                gp.mode = gp.inventoryMode;
                break;
            case KeyEvent.VK_H:
                gp.mode = gp.shopMode;
                break;
            case KeyEvent.VK_F:
                gp.player.shot = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            up = false;
        }
        else if(key == KeyEvent.VK_A){
            left = false;
        }
        else if(key == KeyEvent.VK_S){
            down = false;
        }
        else if(key == KeyEvent.VK_D){
            right = false;
        }

    }
}
