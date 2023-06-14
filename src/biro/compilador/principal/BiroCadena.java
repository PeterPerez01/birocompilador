package biro.compilador.principal;
import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroCadena {
  private final SimpleAttributeSet keyword = new SimpleAttributeSet();
  private JTextPane textPane;

  public BiroCadena(JTextPane textPane) {
    this.textPane = textPane;
    StyleConstants.setForeground(keyword, Color.GREEN);
    StyleConstants.setBold(keyword, true);
  }

  public void highlightQuotedWords() {
    String content = textPane.getText();
    StyledDocument doc = (StyledDocument) textPane.getDocument();
    int pos = 0;
    while (pos < content.length()) {
      int start = content.indexOf("\"", pos);
      if (start >= 0) {
        int end = content.indexOf("\"", start + 1);
        if (end >= 0) {
          doc.setCharacterAttributes(start, end - start + 1, keyword, false);
          pos = end + 1;
        } else {
          break;
        }
      } else {
        break;
      }
    }
  }
}
