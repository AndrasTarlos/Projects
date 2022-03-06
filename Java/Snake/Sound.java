import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class Sound implements Runnable {
    protected Path currentRelativePath = Paths.get("");
    @Override
    public abstract void run();

    public void playSound(String fileName) {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
            while (clip.isOpen()) {
                clip.start();
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void playMusic(String fileName) {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
            while (clip.isOpen()) {
                clip.start();
                if (!GamePanel.running) {
                    clip.close();
                }
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}
