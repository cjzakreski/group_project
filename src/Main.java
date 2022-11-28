import javax.swing.*;
import java.awt.event.*;

class ButtonListener implements ActionListener{
    String cat = null;

    public ButtonListener(String fileName){
        cat = fileName;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Categories c = new Categories(cat);
    }
}
public class Main {
    public static void main(String[] args) {
        JFrame startMenu = new JFrame("Start Menu");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.getContentPane().setLayout(new BoxLayout(startMenu.getContentPane(), BoxLayout.Y_AXIS));

        JLabel categories = new JLabel("Select a category");
        JButton animals = new JButton("Animals");
        animals.addActionListener(new ButtonListener("animals.txt"));
        JButton colors = new JButton("Colors");
        colors.addActionListener(new ButtonListener("colors.txt"));
        JButton food = new JButton("Food");
        food.addActionListener(new ButtonListener("food.txt"));
        JButton countries = new JButton("Countries");
        countries.addActionListener(new ButtonListener("countries.txt"));
        JButton sports = new JButton("Sports");
        food.addActionListener(new ButtonListener("sports.txt"));
        JButton hobbies = new JButton("Hobbies");
        food.addActionListener(new ButtonListener("hobbies.txt"));
        JButton umw = new JButton("UMW");
        umw.addActionListener(new ButtonListener("umw.txt"));
        JButton challenge = new JButton("Challenge");
        challenge.addActionListener(new ButtonListener("challenge.txt.txt"));

        startMenu.getContentPane().add(categories);
        startMenu.getContentPane().add(animals);
        startMenu.getContentPane().add(colors);
        startMenu.getContentPane().add(food);
        startMenu.getContentPane().add(countries);
        startMenu.getContentPane().add(sports);
        startMenu.getContentPane().add(hobbies);
        startMenu.getContentPane().add(umw);
        startMenu.getContentPane().add(challenge);

        startMenu.pack();
        startMenu.setVisible(true);
    }
}
