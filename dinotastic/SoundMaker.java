import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class SoundMaker implements Runnable {

    private AudioInputStream audio; 

    public SoundMaker (String filePath)  {

        try {
            audio = AudioSystem.getAudioInputStream(new File(filePath));
        } catch (Exception e) {}
    }


    @Override
    public void run() {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
    
        catch(IOException ioe) {
            System.out.println(ioe);
        }
        catch(LineUnavailableException lua) {
            System.out.println(lua);
        }
}
}