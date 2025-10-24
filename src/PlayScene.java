import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PlayScene extends JPanel implements KeyListener, Runnable {
  private final GamePanel gamePanel;
  private Thread gameThread;
  private boolean running = false;
  File soundWrongFile = new File("../assets/sound/wrong.wav");

  private final ArrayList<Word> words = new ArrayList<>();
  private String wordListPath = "../assets/words/english.txt";
  private final Random random = new Random();
  private final Font thaiFont;
  private final java.util.List<String> wordList = new ArrayList<>();
  private Double minSpeed = 1.0;
  private Double maxSpeed = 1.2;
  private int score = 0;
  private int missedWords = 0;
  private final int MAX_MISSED_WORDS = 5;
  private String currentInput = "";
  private Image bgImage = new ImageIcon(
      System.getProperty("user.dir") + File.separator + "../assets/img/background.jpg").getImage();

  public PlayScene(GamePanel panel) {
    this.gamePanel = panel;
    setLayout(null);
    setFocusable(true);
    addKeyListener(this);

    // เพื่อให้ TAB ส่ง KeyEvent ได้
    setFocusTraversalKeysEnabled(false);

    // โหลดฟอนต์
    Font f;
    try {
      f = Font.createFont(Font.TRUETYPE_FONT, new File("../assets/fonts/Itim-Regular.ttf")).deriveFont(Font.BOLD, 42);
    } catch (Exception e) {
      f = new Font("SansSerif", Font.BOLD, 42);
    }
    thaiFont = f;

    loadWordsFromFile(wordListPath);
  }

  /** เริ่มเกม */
  public void startGame() {
    words.clear();
    score = 0;
    missedWords = 0;
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  /** หยุดเกม (ตอน Game Over หรือออกจากฉาก) */
  public void stopGame() {
    running = false;
  }

  /** โหลดคำจากไฟล์ครั้งเดียว */
  private void loadWordsFromFile(String path) {
    File file = new File(path);
    if (!file.exists()) {
      System.err.println("File not found: " + file.getAbsolutePath());
      return;
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
          for (String part : line.split("\\s+")) {
            wordList.add(part.trim());
          }
        }
      }
      System.out.println("✅ Loaded " + wordList.size() + " words from file");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** สุ่มคำจากรายการ */
  private void spawnWord() {
    if (wordList.isEmpty())
      return;
    String w = wordList.get(random.nextInt(wordList.size()));
    words.add(new Word(w, (int) random.nextDouble(Math.max(1, getWidth() - 100)), 0,
        (int) (minSpeed + random.nextDouble(maxSpeed))));
  }

  /** ลูปเกมหลัก */
  @Override
  public void run() {
    long lastSpawnTime = System.currentTimeMillis();
    long lastFrame = System.nanoTime();
    double nsPerFrame = 16; // 60 FPS
    double delta = 0;

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastFrame) / nsPerFrame;
      lastFrame = now;

      if (delta >= 1) {
        updateGame();
        repaint();
        delta--;
      }

      // สุ่มคำใหม่ทุก 1 วินาที
      if (System.currentTimeMillis() - lastSpawnTime >= 1000) {
        spawnWord();
        lastSpawnTime = System.currentTimeMillis();
      }

      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /** อัปเดตตำแหน่งคำ */
  private void updateGame() {
    Iterator<Word> iterator = words.iterator();
    while (iterator.hasNext()) {
      Word word = iterator.next();
      word.y += word.speed;
      if (word.y > getHeight()) {
        iterator.remove();
        try {
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundWrongFile);
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
        missedWords++;
        if (missedWords >= MAX_MISSED_WORDS) {
          gameOver();
          return;
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

    g.setFont(thaiFont);
    g.setColor(Color.WHITE);
    for (Word word : words) {
      g.drawString(word.text, word.x, word.y);
    }

    g.setFont(thaiFont.deriveFont(Font.PLAIN, 48));
    g.drawString("คะแนน: " + score, 20, 40);
    g.drawString("พลาด: " + missedWords + "/" + MAX_MISSED_WORDS, 20, 90);
    g.drawString(currentInput, 900, getHeight() - 50);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    char c = e.getKeyChar();
    if (Character.isLetterOrDigit(c) || Character.isAlphabetic(c)) {
      currentInput += c;
      checkWord();
    } else if (c == '\b' && currentInput.length() > 0) {
      currentInput = currentInput.substring(0, currentInput.length() - 1);
    }
  }

  private void checkWord() {
    Iterator<Word> iterator = words.iterator();
    while (iterator.hasNext()) {
      Word word = iterator.next();
      if (word.text.equals(currentInput)) {
        iterator.remove();
        score += 1;
        playSound("../assets/sound/bubble-effect.wav");
        currentInput = "";
        break;
      }
    }
  }

  private void playSound(String path) {
    try {
      File soundFile = new File(path);
      if (!soundFile.exists()) {
        System.err.println("Sound not found: " + soundFile.getAbsolutePath());
        return;
      }
      javax.sound.sampled.AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(soundFile);
      javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
      clip.open(audioIn);
      clip.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_TAB) {
      currentInput = "";
      repaint();
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      currentInput = "";
      repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  private void gameOver() {
    running = false;
    gamePanel.setState(GameState.GAMEOVER);
  }

  public void resetGame() {
    score = 0;
    missedWords = 0;
    currentInput = "";
    words.clear();
    startGame(); // restart thread
  }

  public int getFinalScore() {
    return score;
  }

  static class Word {
    String text;
    int x, y, speed;

    Word(String t, int x, int y, int s) {
      this.text = t;
      this.x = x;
      this.y = y;
      this.speed = s;
    }
  }
}
