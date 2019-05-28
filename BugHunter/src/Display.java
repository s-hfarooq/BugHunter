import java.awt.EventQueue;
import javax.swing.JFrame;

public class Display extends JFrame {
	
	private final int SCALE_FACTOR = 5;
	
	public Display() {
		initUI();
	}

	private void initUI() {
		add(new Main());
		
		// Sets window characteristics
		setSize(192 * SCALE_FACTOR, 108 * SCALE_FACTOR);
		setTitle("Bug Hunter v0.0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
  }

	public static void main(String[] args) {
		// Centers the thing on the screen
		EventQueue.invokeLater(() -> {
			Display ex = new Display();
			ex.setVisible(true);
		});
	}
}