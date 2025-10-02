import javax.swing.*;

public class Main extends JFrame {
  public Main() {
    setTitle("Rain Typeing Game");
    setSize(1280, 729);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    add(new MainMenu());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Main().setVisible(true);
    });
  }
}