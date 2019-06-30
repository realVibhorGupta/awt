/*
 * Beeper.java is a 1.2/1.3/1.4 example that requires
 * no other files.
 */

import javax.swing.JApplet;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Beeper extends JApplet 
                    implements ActionListener {
    JButton button;

    public void init() {
        button = new JButton("Click Me");
        getContentPane().add(button, BorderLayout.CENTER);
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().beep();
    }
}
