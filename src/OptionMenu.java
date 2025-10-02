import javax.swing.*;
import java.awt.*;

public class OptionMenu extends JPanel {
  public OptionMenu() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    JLabel label = new JLabel("Option Menu");
    label.setFont(new Font("SansSerif", Font.BOLD, 32));
    label.setAlignmentX(CENTER_ALIGNMENT);

    PrettyButton backBtn = new PrettyButton("Back", new Color(37, 99, 235), new Color(29, 78, 216)).setFontSize(28);
    backBtn.setAlignmentX(CENTER_ALIGNMENT);

    add(Box.createVerticalGlue());
    add(label);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(backBtn);
    add(Box.createVerticalGlue());
  }
}
