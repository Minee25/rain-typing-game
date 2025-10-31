import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.*;

public class HostMenu extends JPanel {
  private GamePanel gamePanel;
  ButtonImage playButton = new ButtonImage(ButtonType.PLAY); 
  ButtonImage backButton = new ButtonImage(ButtonType.BACK); 
  JTextArea instructions = new JTextArea(); 

  Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public HostMenu(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    instructions.setEditable(false);
    instructions.setOpaque(false);
    instructions.setFont(ItimFont.itimFont(24));
    instructions.setMaximumSize(new Dimension(550, 300));
    instructions.setForeground(Color.WHITE);
    instructions.setAlignmentX(CENTER_ALIGNMENT);

    try {
      InetAddress localHost = InetAddress.getLocalHost();
      instructions.setText(
        "\t\tIP ของฉัน: " + localHost.getHostAddress() + "\n\n" +
        "*ให้เพื่อนกรอก IP ของคุณ\n" +
        "รอให้เพื่อน Join จนครบก่อนแล้วกดปุ่ม Play\n"
        );
    } catch (Exception e) {
        e.printStackTrace();
    }
      
    playButton.setAlignmentX(CENTER_ALIGNMENT);

    backButton.setAlignmentX(CENTER_ALIGNMENT); 
    backButton.addActionListener(e -> gamePanel.setState(GameState.MAIN_MENU));

    add(Box.createVerticalGlue());
    add(instructions);
    add(Box.createRigidArea(new Dimension(0, 50)));
    add(playButton);
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
