import java.io.File;
import javax.sound.sampled.*;

public class PlaySound {
  public static void play(String path) {
    try {
      File soundFile = new File(path);
      if (!soundFile.exists()) {
        System.err.println("Sound not found: " + soundFile.getAbsolutePath());
        return;
      }
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start();
      clip.addLineListener(e -> {
        if (e.getType() == LineEvent.Type.STOP)
          clip.close();
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
