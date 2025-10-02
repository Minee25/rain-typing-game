import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame {
  public Main() {
    setTitle("Rain Typeing Game");
    setSize(1280, 729);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    Image logo = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/logo64.png").getImage();
    setIconImage(logo);

    add(new MainMenu());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Main().setVisible(true);
    });
  }
}