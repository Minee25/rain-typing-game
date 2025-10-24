import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import javax.sound.sampled.*;

enum ButtonType {
  START,
  OPTION,
  BACK,
  EXIT,
  HOW_TO_PLAY,
  PLAY_AGAIN,
  BACK_TO_MENU
}

public class ImageButton extends JButton {
  private Image img;
  File soundFile = new File("../assets/sound/click2.wav");

  // Scale hover effect
  private double scale = 0.95;
  private final double normalScale = 0.95;
  private final double hoverScale = 1.0;

  public ImageButton(ButtonType type) {
    String path;
    switch (type) {
      case START:
        path = "../assets/img/button-play.png";
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

    this.img = new ImageIcon(System.getProperty("user.dir") + File.separator + path).getImage();

    // ตั้งขนาดปุ่มตามขนาดรูป
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
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

    // Hover effect
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

    addActionListener(e -> {
      playSound(soundFile);
    });
  }

  private void playSound(File soundFile) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();

      clip.addLineListener(e -> {
        if (e.getType() == LineEvent.Type.STOP) {
          clip.close();
        }
      });
    } catch (Exception error) {
      error.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g.create();

    int newWidth = (int) (img.getWidth(null) * scale);
    int newHeight = (int) (img.getHeight(null) * scale);

    // Center
    int x = (getWidth() - newWidth) / 2;
    int y = (getHeight() - newHeight) / 2;

    g2.drawImage(img, x, y, newWidth, newHeight, this);
    g2.dispose();
  }
}
