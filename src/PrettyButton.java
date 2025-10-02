import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import javax.sound.sampled.*;

public class PrettyButton extends JButton {
  private int fontSize = 16;
  File soundFile = new File("../assets/sound/click2.wav");

  public PrettyButton(String text, Color bgColor, Color bgHover) {
    super(text);
    setFont(new Font("sans-serif", Font.BOLD, fontSize));
    setForeground(Color.WHITE);
    setBackground(bgColor);
    setFocusPainted(false);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setOpaque(false);
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setMargin(new Insets(10, 30, 10, 30));

    // Hover effect
    addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setBackground(bgHover);
        repaint();
      }

      public void mouseExited(MouseEvent e) {
        setBackground(bgColor);
        repaint();
      }
    });

    // Sound click
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

      clip.addLineListener(event -> {
        if (event.getType() == LineEvent.Type.STOP) {
          clip.close();
        }
      });
    } catch (Exception error) {
      error.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    super.paintComponent(g2);
    g2.dispose();
  }

  // Overload auto bgHover
  public PrettyButton(String text, Color bgColor) {
    this(text, bgColor, bgColor.darker());
  }

  // Overload deault theme
  public PrettyButton(String text) {
    this(text, new Color(37, 99, 235), new Color(29, 78, 216));
  }

  // Set Font size
  public PrettyButton setFontSize(int size) {
    this.fontSize = size;
    setFont(getFont().deriveFont((float) fontSize));
    revalidate();
    repaint();
    return this;
  }
}
