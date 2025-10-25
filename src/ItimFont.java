import java.awt.*;
import java.io.File;

public class ItimFont {
  private static String itimFontPath = "../assets/fonts/Itim-Regular.ttf";

  public static Font itimFont(int size) {
    try {
      Font itim = Font.createFont(Font.TRUETYPE_FONT, new File(itimFontPath));
      return itim.deriveFont(Font.PLAIN, size);
    } catch (Exception error) {
      error.printStackTrace();
      return new Font("SansSerif", Font.PLAIN, size);
    }
  }
}
