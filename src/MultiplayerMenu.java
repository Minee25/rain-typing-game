import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MultiplayerMenu extends JPanel {
  private GamePanel gamePanel;
  ButtonImage backButton = new ButtonImage(ButtonType.BACK);
  ButtonImage hostButton = new ButtonImage(ButtonType.HOST);
  ButtonImage joinButton = new ButtonImage(ButtonType.JOIN);
  JTextArea instructions = new JTextArea();

  Image backgroundImage = new ImageIcon(
      System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public MultiplayerMenu(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    instructions.setText(
        "*เลือก Host หากต้องการเล่นใน Server ของตัวเอง\n" +
        "*เลือก Join หากต้องการเล่นใน Server ของเพื่อน"
    );
    instructions.setEditable(false);
    instructions.setOpaque(false);
    instructions.setFont(ItimFont.itimFont(24));
    instructions.setMaximumSize(new Dimension(550, 300));
    instructions.setForeground(Color.WHITE);
    instructions.setAlignmentX(CENTER_ALIGNMENT);

    hostButton.setAlignmentX(CENTER_ALIGNMENT);
    hostButton.addActionListener(e -> gamePanel.setState(GameState.HOST_MENU));

    joinButton.setAlignmentX(CENTER_ALIGNMENT);
    joinButton.addActionListener(e -> gamePanel.setState(GameState.JOIN_MENU));

    backButton.setAlignmentX(CENTER_ALIGNMENT);
    backButton.addActionListener(e -> gamePanel.setState(GameState.MAIN_MENU));

    add(Box.createVerticalGlue());
    add(instructions);
    add(Box.createRigidArea(new Dimension(0, 50)));
    add(hostButton);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(joinButton);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(backButton);
    add(Box.createVerticalGlue());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }
}
