import javax.swing.JButton;
import javax.swing.KeyStroke;

public class Buttons extends JButton {
	
	private KeyStroke k;
	private int changeX;
	
	public Buttons(String name, int num, int changeX) {
		super(name);
		k = KeyStroke.getKeyStroke(name, 0);
	}
}
