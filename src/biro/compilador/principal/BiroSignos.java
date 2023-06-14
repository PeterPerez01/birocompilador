package biro.compilador.principal;
import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class BiroSignos {
	private JTextPane textPane;
	private final SimpleAttributeSet keyword = new SimpleAttributeSet();

	public BiroSignos(JTextPane textPane) {
	    this.textPane = textPane;
	    StyleConstants.setForeground(keyword, Color.RED);
	    StyleConstants.setBold(keyword, true);
	  }

  public void señalizar() {
	  String[] keywords = {"=", "/", "+", "-", "&&", "||", ";", "%"};
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
