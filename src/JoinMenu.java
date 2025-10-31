import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JoinMenu extends JPanel {
  private GamePanel gamePanel;
  ButtonImage joinButton = new ButtonImage(ButtonType.JOIN); 
  ButtonImage backButton = new ButtonImage(ButtonType.BACK); 
  JTextField ipInput = new JTextField("xx.xx.xx.xx");
  JTextArea instructions = new JTextArea();
  JLabel label = new JLabel("กรอก IP Server", SwingConstants.CENTER);

  Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public JoinMenu(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    label.setAlignmentX(CENTER_ALIGNMENT);
    label.setFont(ItimFont.itimFont(24));
    label.setForeground(Color.WHITE);

    ipInput.setMaximumSize(new Dimension(200, 40));
    ipInput.setPreferredSize(new Dimension(100, 50));
    ipInput.setFont(ItimFont.itimFont(24));
    ipInput.setAlignmentX(CENTER_ALIGNMENT);
 
    instructions.setText( 
      "*กรอก IP ของเพื่อนคุณ กรอกเสร็จแล้วกด Join ได้เลย"
    );
    instructions.setEditable(false);
    instructions.setOpaque(false);
    instructions.setFont(ItimFont.itimFont(24));
    instructions.setMaximumSize(new Dimension(550, 300));
    instructions.setForeground(Color.WHITE);
    instructions.setAlignmentX(CENTER_ALIGNMENT);

    joinButton.setAlignmentX(CENTER_ALIGNMENT);
    joinButton.addActionListener(e -> {
      String text = ipInput.getText();
      for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i);
        if (!Character.isDigit(c) && c != '.') {
          JOptionPane.showMessageDialog(this, "Invalid IP Server");
          return;
        }
      }
    });

    backButton.setAlignmentX(CENTER_ALIGNMENT);
    backButton.addActionListener(e -> gamePanel.setState(GameState.MAIN_MENU));

    add(Box.createVerticalGlue());
    add(instructions);
    add(Box.createRigidArea(new Dimension(0, 50)));
    add(label);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(ipInput);
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
