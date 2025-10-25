import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RainTypingWindows extends JFrame {
  public RainTypingWindows() {
    setTitle("Rain Typing Game");
    setSize(1920, 1080);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    Image logo = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/logo64.png").getImage();
    setIconImage(logo);
    add(new GamePanel());
  }
}
