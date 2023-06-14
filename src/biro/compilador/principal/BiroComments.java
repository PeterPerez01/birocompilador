package biro.compilador.principal;
import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroComments {
	private final SimpleAttributeSet dotWord = new SimpleAttributeSet();
	private JTextPane textPane;

  public BiroComments(JTextPane textPane) {
    this.textPane = textPane;
    StyleConstants.setForeground(dotWord, Color.LIGHT_GRAY);
    StyleConstants.setBold(dotWord, true);
  }

  public void comentar() {
	  String content = textPane.getText();
	    StyledDocument doc = (StyledDocument) textPane.getDocument();
	    String[] lines = content.split("\n");
	    for (String line : lines) {
	      if (line.matches("^\\s*(/\\*.*\\*/|//.*)\\s*$")) {
	        int pos = content.indexOf(line);
	        doc.setCharacterAttributes(pos, line.length(), dotWord, false);
      }
    }
  }
}
