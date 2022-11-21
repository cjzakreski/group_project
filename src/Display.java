import javax.swing.*;
import java.awt.*;
public class Display {
    public Display() {
        JFrame startMenu = new JFrame("Start Menu");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.getContentPane().setLayout(new BoxLayout(startMenu.getContentPane(), BoxLayout.Y_AXIS));

        JLabel categories = new JLabel("Select a category");
        JButton animals = new JButton("Animals");

        startMenu.getContentPane().add(categories);
        startMenu.getContentPane().add(animals);

        startMenu.pack();
        startMenu.setVisible(true);
    }
}
