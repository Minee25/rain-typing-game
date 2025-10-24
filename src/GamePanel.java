import javax.swing.*;
import java.awt.*;

enum GameState {
  MENU, PLAY, OPTIONS, HOW_TO_PLAY, GAMEOVER
}

public class GamePanel extends JPanel implements Runnable {
  private Thread gameThread;
  private boolean running = false;
  private GameState currentState = GameState.MENU;

  private MainMenu mainMenu;
  private HowToPlay howToPlay;
  private PlayScene playScene;
  private GameOverScreen gameOverScreen;

  public GamePanel() {
    setPreferredSize(new Dimension(1280, 720));
    setLayout(new BorderLayout());

    mainMenu = new MainMenu(this);
    howToPlay = new HowToPlay(this);
    playScene = new PlayScene(this);
    gameOverScreen = new GameOverScreen(this, playScene);
    add(BorderLayout.CENTER, mainMenu);
  }

  public void startPlayScene() {
    playScene.startGame(); // เรียกเริ่มเกมจริง
  }

  public void setState(GameState state) {
    currentState = state;
    removeAll();
    revalidate();
    repaint();

    if (state == GameState.MENU) {
      add(BorderLayout.CENTER, mainMenu);
    } else if (state == GameState.PLAY) {
      add(BorderLayout.CENTER, playScene);
      playScene.requestFocusInWindow();
    } else if (state == GameState.OPTIONS) {
      add(BorderLayout.CENTER, new JLabel("⚙ Options Page"));
    } else if (state == GameState.HOW_TO_PLAY) {
      add(BorderLayout.CENTER, howToPlay);
    } else if (state == GameState.GAMEOVER) {
      gameOverScreen.updateScore();
      add(BorderLayout.CENTER, gameOverScreen);
    }
  }


  public void startGame() {
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double nsPerFrame = 16; // 60 FPS
    double delta = 0;

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / nsPerFrame;
      lastTime = now;

      if (delta >= 1) {
        repaint();
        delta--;
      }

      try {
        Thread.sleep(1); // Small sleep to prevent excessive CPU usage
      } catch (InterruptedException e) {
      }
    }
  }
}
