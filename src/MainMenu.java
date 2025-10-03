import javax.swing.*;
import java.awt.*;
import java.io.File;

class MainMenu extends JPanel { 
  private GamePanel gamePanel; // อ้างอิงไปที่ GamePanel

  ImageButton playButton = new ImageButton("play");
  ImageButton howToPlay = new ImageButton("howtoplay");
  ImageButton exitButton = new ImageButton("exit");

  Image bgImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public MainMenu(GamePanel panel) {
    this.gamePanel = panel; // รับ GamePanel มาด้วย
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Button to PLAY
    playButton.addActionListener(e -> gamePanel.setState(GameState.PLAY));

    // Button to HOW TO PLAY
    howToPlay.addActionListener(e -> gamePanel.setState(GameState.OPTIONS));

  
    // Button to Exit
    exitButton.addActionListener(e -> {
        System.exit(0);
      }
    );

    // Center
    playButton.setAlignmentX(CENTER_ALIGNMENT);
    howToPlay.setAlignmentX(CENTER_ALIGNMENT);
    exitButton.setAlignmentX(CENTER_ALIGNMENT);

    add(Box.createVerticalGlue());
    add(playButton);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(howToPlay);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(exitButton);
    add(Box.createVerticalGlue());

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}