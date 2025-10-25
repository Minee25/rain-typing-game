import javax.swing.*;
import java.awt.*;

enum GameState {
  MAIN_MENU,
  PLAY,
  HOW_TO_PLAY,
  GAMEOVER
}

public class GamePanel extends JPanel {
  private Timer timer;
  private boolean gameRunning = false;

  private MainMenu mainMenu;
  private HowToPlay howToPlay;
  private PlayScene playScene;
  private GameOverScreen gameOverScreen;

  public GamePanel() {
    setLayout(new BorderLayout());

    mainMenu = new MainMenu(this);
    howToPlay = new HowToPlay(this);
    playScene = new PlayScene(this);
    gameOverScreen = new GameOverScreen(this, playScene);

    // แสดงเมนูหลักในตอนแรก
    add(BorderLayout.CENTER, mainMenu);

    // Timer ให้เรียก repaint() ทุก 16 ms
    timer = new Timer(16, e -> repaint());
  }

  public void setState(GameState state) {
    removeAll();

    switch (state) {
      case MAIN_MENU:
        add(BorderLayout.CENTER, mainMenu);
        break;
      case PLAY:
        add(BorderLayout.CENTER, playScene);
        playScene.requestFocusInWindow();
        playScene.startGame();
        break;
      case HOW_TO_PLAY:
        add(BorderLayout.CENTER, howToPlay);
        break;
      case GAMEOVER:
        add(BorderLayout.CENTER, gameOverScreen);
        gameOverScreen.updateScore();
        break;
      default:
        break;
    }

    revalidate();
    repaint();
  }

  public void startGame() {
    if (!gameRunning) {
      gameRunning = true;
      timer.start();
    }
  }

  public void stopGame() {
    if (gameRunning) {
      gameRunning = false;
      timer.stop();
    }
  }
}
