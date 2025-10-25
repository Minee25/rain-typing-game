import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HowToPlay extends JPanel {
  private final GamePanel gamePanel;
  ButtonImage backButton = new ButtonImage(ButtonType.BACK);
  Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public HowToPlay(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(new BorderLayout());

    JPanel top = new JPanel();
    top.setOpaque(false);
    top.setLayout(new FlowLayout(FlowLayout.LEFT));

    backButton.addActionListener(e -> {
      gamePanel.setState(GameState.MAIN_MENU);
    });

    top.add(backButton);

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("วิธีการเล่น");
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(ItimFont.itimFont(36));
    title.setForeground(Color.WHITE);

    JTextArea instructions = new JTextArea(
      "1. คำศัพท์จะค่อย ๆ ตกลงมาจากด้านบนของหน้าจอ\n"
      + "2. ผู้เล่นต้องพิมพ์คำศัพท์ที่เห็นให้ถูกต้อง ก่อนที่คำจะถึงขอบล่าง\n"
      + "3. เมื่อพิมพ์ถูกต้องคำศัพท์คำนั้นจะหายไปทันทีโดยอัตโนมัติและได้คะแนน\n"
      + "4. ความเร็วในการตกของคำจะเพิ่มขึ้นเรื่อย ๆ เมื่อเวลาผ่านไป\n"
      + "5. หากต้องการลบคำที่พิมพ์อยู่ ให้ใช้ปุ่ม Backspace, กด Enter หรือ Tab เพื่อเคลียร์\n"
      + "6. ถ้าปล่อยให้คำตกถึงล่างจอโดยไม่พิมพ์ถูก จะนับเป็น 'พลาด'\n"
      + "7. หากพลาดครบ 5 ครั้ง เกมจะจบลงทันที\n\n"
      + "ตั้งใจพิมพ์ให้ไว! ฝึกฝนเพื่อคะแนนสูงสุด"
    );
 
    instructions.setFont(ItimFont.itimFont(24));

    instructions.setOpaque(false);
    instructions.setEditable(false);
    instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
    instructions.setForeground(Color.WHITE);
    instructions.setMaximumSize(new Dimension(960, 1080));

    content.add(Box.createVerticalStrut(40));
    content.add(title);
    content.add(Box.createVerticalStrut(20));
    content.add(instructions);

    add(top, BorderLayout.NORTH);
    add(content, BorderLayout.CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }
}