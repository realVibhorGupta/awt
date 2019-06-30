import javax.swing.SpringLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Container;

public class SpringDemo1 {
    public static void main(String[] args) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("SpringDemo1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new SpringLayout());
        contentPane.add(new JLabel("Label: "));
        contentPane.add(new JTextField("Text field", 15));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
