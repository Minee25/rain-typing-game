import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameOverScreen extends JPanel {
  private final GamePanel gamePanel;
  private final PlayScene playScene;
  private JLabel scoreLabel;
  Image bgImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg")
      .getImage();

  public GameOverScreen(GamePanel gamePanel, PlayScene playScene) {
    this.gamePanel = gamePanel;
    this.playScene = playScene;
    setLayout(new BorderLayout());
    createGameOverUI();
  }

  private void createGameOverUI() {
    // Game Over Title
    ImageIcon gameOverIcon = new ImageIcon(
        System.getProperty("user.dir") + File.separator + "../assets/img/text-game-over.png");
    JLabel titleLabel = new JLabel(gameOverIcon);

    // Final Score - will be updated when shown
    scoreLabel = new JLabel("Last Score: 0", JLabel.CENTER);
    Font f;
    try {
      f = Font.createFont(Font.TRUETYPE_FONT, new File("../assets/fonts/Itim-Regular.ttf")).deriveFont(Font.BOLD, 42);
    } catch (Exception e) {
      f = new Font("SansSerif", Font.BOLD, 42);
    }
    scoreLabel.setFont(f.deriveFont(Font.PLAIN, 48));

    scoreLabel.setForeground(Color.WHITE);

    // Button Panel
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setOpaque(false);

    ImageButton restartButton = new ImageButton(ButtonType.PLAY_AGAIN);
    restartButton.addActionListener(e -> {
      playScene.resetGame();
      gamePanel.setState(GameState.PLAY);
    });

    ImageButton menuButton = new ImageButton(ButtonType.BACK_TO_MENU);
    menuButton.addActionListener(e -> gamePanel.setState(GameState.MENU));

    buttonPanel.add(menuButton);
    buttonPanel.add(restartButton);

    // Add components to panel
    add(titleLabel, BorderLayout.NORTH);
    add(scoreLabel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  public void updateScore() {
    int finalScore = playScene.getFinalScore();
    scoreLabel.setText("Last Score: " + finalScore);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
  }
}
