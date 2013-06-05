import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {

    JPanel mainBoard; 
    MenuBoard menuBoard;
    PlayBoard gameBoard;

    public Main() {

        mainBoard = new JPanel(new CardLayout());
        mainBoard.add( new MenuBoard (mainBoard), "menu" );
        this.add (mainBoard);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 580);
        setLocationRelativeTo(null);
        setTitle("Dinotastic");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        Main main = new Main(); 
    }

}