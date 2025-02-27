package model;
import view.GraphicPanel;

public class Missions {
    GraphicPanel gp;
    public String name = "";
    public String txt = "";
    public boolean locked = true;
    public boolean completed = false;
    public int target;
    public Missions(GraphicPanel gp, String name, int target){
        this.gp = gp;
        this.name = name;
        this.target = target;
    }
    public void setCompleted(int index){
        if(gp.manager.killCount >= gp.missions[index].target){
            completed = true;
            if(gp.missions[index+1]!=null){
                gp.missions[index+1].locked = false;
            }
        }
    }

}
