import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameOverScreen extends JPanel {
  private final GamePanel gamePanel;
  private final PlayScene playScene;
  private JLabel scoreLabel;

  ButtonImage playAgainButton = new ButtonImage(ButtonType.PLAY_AGAIN);
  ButtonImage backToMenuButton = new ButtonImage(ButtonType.BACK_TO_MENU);
  Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public GameOverScreen(GamePanel gamePanel, PlayScene playScene) {
    this.gamePanel = gamePanel;
    this.playScene = playScene;
    setLayout(new BorderLayout());
    createGameOverUI();
  }

  private void createGameOverUI() {
    // title
    ImageIcon gameOverBanner = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/text-game-over.png");
    JLabel bannerLabel = new JLabel(gameOverBanner);

    // Score
    scoreLabel = new JLabel("Last Score: 0", JLabel.CENTER);
    scoreLabel.setFont(ItimFont.itimFont(48));
    scoreLabel.setForeground(Color.WHITE);

    // Button
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setOpaque(false);

    playAgainButton.addActionListener(e -> {
      playScene.startGame();
      gamePanel.setState(GameState.PLAY);
    });

    backToMenuButton.addActionListener(e -> {
      gamePanel.setState(GameState.MAIN_MENU);
    });

    buttonPanel.add(backToMenuButton);
    buttonPanel.add(playAgainButton);

    // ใส่ลงใน Panel
    add(bannerLabel, BorderLayout.NORTH);
    add(scoreLabel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  public void updateScore() {
    int finalSocre = playScene.getFinalScore();
    scoreLabel.setText("Last Score: " + finalSocre);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
  }
}
