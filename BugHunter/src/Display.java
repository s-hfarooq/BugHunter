import java.awt.EventQueue;
import javax.swing.JFrame;

public class Display extends JFrame {
  public Display() {
    initUI();
  }

  private void initUI() {
    add(new Main());

    // Sets window characteristics
    setSize(250, 200);
    setTitle("Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  public static void main(String[] args) {
    // Centers the thing on the screen
    EventQueue.invokeLater(() -> {
      Display ex = new Display();
      ex.setVisible(true);
    });
  }
}
