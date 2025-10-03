import java.io.File;
import javax.swing.*;
import java.awt.*;

public class RainTypingWindows extends JFrame {
  public RainTypingWindows() {
    setTitle("Rain Typeing Game");
    setSize(1280, 729);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    Image logo = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/logo64.png").getImage();
    setIconImage(logo);

    GamePanel panel = new GamePanel();
    add(panel);
    panel.startGame();
  }
}
