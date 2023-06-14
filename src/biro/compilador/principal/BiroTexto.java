package biro.compilador.principal;

import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroTexto {
  private final SimpleAttributeSet keyword = new SimpleAttributeSet();
  private JTextPane textPane;

  public BiroTexto(JTextPane textPane) {
    this.textPane = textPane;
    StyleConstants.setForeground(keyword, Color.ORANGE);
    StyleConstants.setBold(keyword, true);
  }

  public void highlightKeywords() {
    String[] keywords = {"*cad", "*dec", "*ent", "*cad[]", "*ent[]", "dec[]", "new", "function"};
    String content = textPane.getText();
    StyledDocument doc = (StyledDocument) textPane.getDocument();
    
    for (String keyword : keywords) {
      int pos = 0;
      while ((pos = content.indexOf(keyword, pos)) >= 0) {
        doc.setCharacterAttributes(pos, keyword.length(), this.keyword, false);
        pos += keyword.length();
      }
    }
    
    
    
  }
}
