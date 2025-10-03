import javax.swing.*;
import java.awt.*;

enum GameState {
  MENU, PLAY, OPTIONS, GAMEOVER
}

public class GamePanel extends JPanel implements Runnable {
  private Thread gameThread;
  private boolean running = false;
  private GameState currentState = GameState.MENU;

  private MainMenu menuPanel;

  public GamePanel() {
    setPreferredSize(new Dimension(1280, 720));
    setLayout(new BorderLayout());

    // สร้างเมนูโดยส่ง this ไป
    menuPanel = new MainMenu(this);
    add(menuPanel, BorderLayout.CENTER);
  }

  public void setState(GameState state) {
    currentState = state;
    removeAll(); // ลบ component เก่า
    revalidate();
    repaint();

    if (state == GameState.MENU) {
      add(menuPanel, BorderLayout.CENTER);
    } else if (state == GameState.PLAY) {
      add(new JLabel("🎮 Playing Game..."), BorderLayout.CENTER);
    } else if (state == GameState.OPTIONS) {
      add(new JLabel("⚙ Options Page"), BorderLayout.CENTER);
    } else if (state == GameState.GAMEOVER) {
      add(new JLabel("💀 Game Over"), BorderLayout.CENTER);
    }
  }

  public void startGame() {
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    while (running) {
      repaint();
      try {
        Thread.sleep(16);
      } catch (InterruptedException e) {
      }
    }
  }
}
