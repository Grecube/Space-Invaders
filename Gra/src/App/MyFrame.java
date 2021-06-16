package App;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
    
    MyFrame() {
        Font font = new Font("Verdana", Font.PLAIN, 18);
        Label label = new Label("Hello (cruel) Windows World!");
        label.setFont(font);
        add(label);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        

        pack();
        setVisible(true);
    }
    
}
