import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Rectangle; 
import java.awt.event.KeyEvent;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Dino dino;

    //private ArrayList<Platforms> platforms = new ArrayList<Platforms>();

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        dino = new Dino();

        timer = new Timer(5, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        // Draw what should be showing up
        g2d.drawImage(dino.getImage(), dino.getX(), dino.getY(), this);


        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }


    public void actionPerformed(ActionEvent e) {

        dino.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {

    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            dino.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            dino.keyPressed(e);
        }
    }
}
