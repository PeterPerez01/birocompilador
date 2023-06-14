package biro.compilador.principal;
import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroPuntos {
  private final SimpleAttributeSet dotWord = new SimpleAttributeSet();
  private JTextPane textPane;

  public BiroPuntos(JTextPane textPane) {
    this.textPane = textPane;
    StyleConstants.setForeground(dotWord, Color.CYAN);
    StyleConstants.setBold(dotWord, true);
  }

  public void highlightDotWords() {
    String content = textPane.getText();
    StyledDocument doc = (StyledDocument) textPane.getDocument();
    String[] words = content.split("\\s");
    for (String word : words) {
      if (word.startsWith(".")) {
        int pos = content.indexOf(word);
        doc.setCharacterAttributes(pos, word.length(), dotWord, false);
      }
    }
  }
}
