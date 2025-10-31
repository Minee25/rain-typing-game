import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

enum ButtonType {
  PLAY,
  MULTIPLAYER,
  HOST,
  JOIN,
  OPTION,
  BACK,
  EXIT,
  HOW_TO_PLAY,
  PLAY_AGAIN,
  BACK_TO_MENU
}

public class ButtonImage extends JButton {
  private Image buttonImage;
  String clickSoundPath = "../assets/sound/click2.wav";

  // ขนาดของปุ่มเมื่อ Hover
  private double scale = 0.95;
  private final double normalScale = 0.95;
  private final double hoverScale = 1.0;

  public ButtonImage(ButtonType type) {
    // ตำแหน่งรูปของภาพ
    String path;
    switch (type) {
      case PLAY:
        path = "../assets/img/button-play.png";
        break;
      case MULTIPLAYER:
        path = "../assets/img/button-multiplayer.png";
        break;
      case JOIN:
        path = "../assets/img/button-join.png";
        break;
      case HOST:
        path = "../assets/img/button-host.png";
        break;
      case OPTION:
        path = "../assets/img/button-option.png";
        break;
      case BACK:
        path = "../assets/img/button-back.png";
        break;
      case EXIT:
        path = "../assets/img/button-exit.png";
        break;
      case HOW_TO_PLAY:
        path = "../assets/img/button-how-to-play.png";
        break;
      case PLAY_AGAIN:
        path = "../assets/img/button-play-again.png";
        break;
      case BACK_TO_MENU:
        path = "../assets/img/button-back-to-menu.png";
        break;
      default:
        throw new IllegalArgumentException("Unknown button type: " + type);
    }

    this.buttonImage = new ImageIcon(System.getProperty("user.dir") + File.separator + path).getImage();

    // ตั้งขนาดปุ่มตามขนาดรูป
    Dimension size = new Dimension(buttonImage.getWidth(null), buttonImage.getHeight(null));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);

    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setOpaque(false);
    setMargin(new Insets(0, 0, 0, 0));

    // เอฟเฟกต์เมื่อ Hover
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        scale = hoverScale;
        repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        scale = normalScale;
        repaint();
      }
    });

    // เอฟเฟกต์เมื่อคลิก
    addActionListener(e -> {
      PlaySound.play(clickSoundPath);
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g.create();

    int newWidth = (int) (buttonImage.getWidth(null) * scale);
    int newHeight = (int) (buttonImage.getHeight(null) * scale);

    // ปรับให้อยู่ตรงกลาง
    int x = (getWidth() - newWidth) / 2;
    int y = (getHeight() - newHeight) / 2;

    g2.drawImage(buttonImage, x, y, newWidth, newHeight, this);
    g2.dispose();
  }
}
