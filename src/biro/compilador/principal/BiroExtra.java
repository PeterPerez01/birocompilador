package biro.compilador.principal;
import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroExtra {
  private final SimpleAttributeSet x = new SimpleAttributeSet();
  private JTextPane textPane;

  public BiroExtra(JTextPane textPane) {
    this.textPane = textPane;
    StyleConstants.setForeground(x, Color.WHITE);
  }

  public void highlight() {
    String content = textPane.getText();
    StyledDocument doc = (StyledDocument) textPane.getDocument();
    String[] words = content.split("\\s");
    for (String word : words) {
        int pos = content.indexOf(word);
        doc.setCharacterAttributes(pos, word.length(), x, false);
    }
  }
}
