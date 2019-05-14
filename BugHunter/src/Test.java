import java.awt.EventQueue;
import javax.swing.JFrame;

// Makes a doughnut shaped thing
public class Test extends JFrame {

    public Test() {
        initUI();
    }

    private void initUI() {
        add(new Main());

        setSize(330, 330);

        setTitle("Donut");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Test ex = new Test();
            ex.setVisible(true);
        });
    }
}
