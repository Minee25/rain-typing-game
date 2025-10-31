import javax.swing.*;
import java.awt.*;

enum GameState {
  MAIN_MENU,
  PLAY,
  MULTIPLAYER_MENU,
  HOST_MENU,
  JOIN_MENU,
  HOW_TO_PLAY,
  GAMEOVER
}

public class GamePanel extends JPanel {
  private Timer timer;
  private boolean gameRunning = false;

  private MainMenu mainMenu;
  private HowToPlay howToPlay;
  private PlayScene playScene;
  private MultiplayerMenu multiplayerMenu;
  private HostMenu hostMenu;
  private JoinMenu joinMenu;
  private GameOverScreen gameOverScreen;

  public GamePanel() {
    setLayout(new BorderLayout());

    mainMenu = new MainMenu(this);
    howToPlay = new HowToPlay(this);
    playScene = new PlayScene(this);
    multiplayerMenu = new MultiplayerMenu(this);
    hostMenu = new HostMenu(this);
    joinMenu = new JoinMenu(this);
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
      case MULTIPLAYER_MENU:
        add(BorderLayout.CENTER, multiplayerMenu); 
        break;
      case HOST_MENU:
        add(BorderLayout.CENTER, hostMenu); 
        break;
      case JOIN_MENU:
        add(BorderLayout.CENTER, joinMenu); 
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
