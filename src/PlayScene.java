import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class PlayScene extends JPanel implements KeyListener {
  private final GamePanel gamePanel;
  private final Random random = new Random();
  private final Timer gameTimer;
  Timer spawnTimer;

  private final java.util.List<String> wordList = new ArrayList<>();
  private final java.util.List<Word> words = new ArrayList<>();

  String wordListPath = "../assets/words/english.txt";
  String soundWrongPath = "../assets/sound/wrong-effect.wav";
  String soundCorrectPath = "../assets/sound/bubble-effect.wav";
  double minSpeed = 2.0;
  double maxSpeed = 5.0;
  double delayWordSpawn = 1.5;
  int score = 0;
  int missedWords = 0;
  int maxMissedWords = 5;
  String currentInput = "";

  private Image backgroundImage = new ImageIcon(System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public PlayScene(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(null);
    setFocusable(true);
    addKeyListener(this);
    setFocusTraversalKeysEnabled(false);

    loadWordFromFile(wordListPath);
    gameTimer = new Timer(16, e -> gameLoop());
    spawnTimer = new Timer((int) (delayWordSpawn * 1000), e -> spawnWord());
  }

  public void startGame() {
    words.clear();
    score = 0;
    missedWords = 0;
    currentInput = "";
    gameTimer.start();
    spawnTimer.start();
  }

  public void stopGame() {
    gameTimer.stop();
    spawnTimer.stop();
  }

  private void gameLoop() {
    updateGame();
    repaint();
  }

  // โหลดคำจากไฟล์
  private void loadWordFromFile(String path) {
    File file = new File(path);
    if (!file.exists()) {
      System.err.println("File not found: " + file.getAbsolutePath());
      return;
    }

    try {
      // อ่านข้อความจากไฟล์
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();

        if (!line.isEmpty()) {
          wordList.add(line);
        }
      }

      System.out.println("Loaded " + wordList.size() + " words from file");
    } catch (Exception error) {
      error.printStackTrace();
    }
  }

  // สุ่มคำจากรายการ
  private void spawnWord() {
    if (wordList.isEmpty()) {
      return;
    }

    // ถ้าคำที่แสดงอยู่ครบทุกคำแล้วไม่ต้องสุ่มเพิ่ม
    if (words.size() >= wordList.size()) {
      return;
    }

    String word;
    int tries = 0;
    boolean duplicate;

    // เช็คไม่ให้ซ้ำกับคำที่อยู่บนจอ
    do {
      word = wordList.get(random.nextInt(wordList.size()));
      duplicate = false;

      for (Word w : words) {
        if (w.text.equals(word)) {
          duplicate = true;
          break;
        }
      }

      tries++;
    } while (duplicate && tries < 50);

    // ถ้ายังมีคำซ้ำให้ข้ามการสร้างคำ
    if (duplicate) {
      return;
    }

    double randomSpeed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
    int x = random.nextInt(getWidth() - 100);
    if (x < 1) {
      x = 1;
    }
    words.add(new Word(word, x, 0, randomSpeed));
  }

  // อัปเดตตำแหน่งคำ
  private void updateGame() {
    for (int i = words.size() - 1; i >= 0; i--) {
      Word word = words.get(i);
      word.y += (int) word.speed;
      if (word.y > getHeight()) {
        words.remove(i);
        PlaySound.play(soundWrongPath);
        missedWords++;
        if (missedWords >= maxMissedWords) {
          gameOver();
          return;
        }
      }
    }
  }

  private void gameOver() {
    gameTimer.stop();
    spawnTimer.stop();
    gamePanel.setState(GameState.GAMEOVER);
  }

  static class Word {
    String text;
    int x, y;
    double speed;

    Word(String t, int x, int y, double s) {
      this.text = t;
      this.x = x;
      this.y = y;
      this.speed = s;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    char c = e.getKeyChar();
    if (!Character.isISOControl(c)) {
      if (c == ' ' && currentInput.isEmpty()) {
        return;
      }

      currentInput += c;
      checkWord();
    } else if (c == '\b' && currentInput.length() > 0) {
      currentInput = currentInput.substring(0, currentInput.length() - 1);
    }
  }

  private void checkWord() {
    for (int i = words.size() - 1; i >= 0; i--) {
      Word word = words.get(i);
      if (word.text.equals(currentInput)) {
        words.remove(i);
        score++;
        PlaySound.play(soundCorrectPath);
        currentInput = "";
        break;
      }
    }
  }
  
  public int getFinalScore() {
    return score;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
      currentInput = "";
      repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

    g.setFont(ItimFont.itimFont(48));
    g.setColor(Color.WHITE);
    for (Word word : words) {
      g.drawString(word.text, word.x, word.y);
    }

    g.drawString("คะแนน: " + score, 20, 40);
    g.drawString("พลาด: " + missedWords + "/" + maxMissedWords, 20, 90);
    g.drawString(currentInput, getWidth() / 2, getHeight() - 50);
  }
}
