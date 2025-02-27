package  model;
import javax.sound.sampled.*;

public class Sound {
    public Clip[] sounds = new Clip[30];
    public Sound(){
        setSound(0,"/sounds/key.wav");
        setSound(1,"/sounds/coin.wav");
        setSound(2,"/sounds/levelUp.wav");
        setSound(3,"/sounds/hitMonster.wav");
        setSound(4,"/sounds/door_opening.wav");
        setSound(5,"/sounds/damage3.wav");
    }

    public void setSound(int index, String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filename));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            sounds[index] = clip;
        } catch (Exception e) {
            System.out.println("NO");
        }
    }

    public void playSound(int index) {
        if (sounds[index] != null) {
            sounds[index].setFramePosition(0);
            sounds[index].start();
        }
    }

    public void stopSound(int index) {
        if (sounds[index] != null) {
            sounds[index].stop();
        }
    }
}
