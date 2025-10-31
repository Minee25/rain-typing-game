import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainMenu extends JPanel {
  private GamePanel gamePanel;
  ButtonImage playButton = new ButtonImage(ButtonType.PLAY);
  ButtonImage multiPlayerButton = new ButtonImage(ButtonType.MULTIPLAYER);
  ButtonImage howToPlay = new ButtonImage(ButtonType.HOW_TO_PLAY);
  ButtonImage exitButton = new ButtonImage(ButtonType.EXIT);

  Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();
  Image logoImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/banner.png").getImage();
  JLabel logoLabel = new JLabel(new ImageIcon(logoImage));

  public MainMenu(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    playButton.addActionListener(e -> {
      gamePanel.setState(GameState.PLAY);
    });

    multiPlayerButton.addActionListener(e -> {
      gamePanel.setState(GameState.MULTIPLAYER_MENU);
    });

    howToPlay.addActionListener(e -> {
      gamePanel.setState(GameState.HOW_TO_PLAY);
    });

    exitButton.addActionListener(e -> {
      System.exit(0);
    });

    // ปรับให้อยู่ตรงกลาง
    playButton.setAlignmentX(CENTER_ALIGNMENT);
    multiPlayerButton.setAlignmentX(CENTER_ALIGNMENT);
    howToPlay.setAlignmentX(CENTER_ALIGNMENT);
    exitButton.setAlignmentX(CENTER_ALIGNMENT);
    logoLabel.setAlignmentX(CENTER_ALIGNMENT);

    add(Box.createVerticalGlue());
    add(logoLabel);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(Box.createVerticalGlue());
    add(playButton);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(multiPlayerButton);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(howToPlay);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(exitButton);
    add(Box.createVerticalGlue());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }
}
