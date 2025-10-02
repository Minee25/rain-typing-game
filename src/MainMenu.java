import javax.swing.*;
import java.awt.*;
import java.io.File;

class MainMenu extends JPanel {
  PrettyButton startBtn = new PrettyButton("Start", new Color(37, 99, 235), new Color(29, 78, 216)).setFontSize(28);
  PrettyButton optionBtn = new PrettyButton("Option", new Color(37, 99, 235), new Color(29, 78, 216)).setFontSize(28);
  PrettyButton exitBtn = new PrettyButton("Exit", new Color(37, 99, 235), new Color(29, 78, 216)).setFontSize(28);

  Image bgImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public MainMenu() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Click button for exit
    exitBtn.addActionListener(e -> {
        System.exit(0);
      }
    );

    // Center
    startBtn.setAlignmentX(CENTER_ALIGNMENT);
    optionBtn.setAlignmentX(CENTER_ALIGNMENT);
    exitBtn.setAlignmentX(CENTER_ALIGNMENT);

    add(Box.createVerticalGlue());
    add(startBtn);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(optionBtn);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(exitBtn);
    add(Box.createVerticalGlue());

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}