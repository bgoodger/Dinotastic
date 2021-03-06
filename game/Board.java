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
import javax.swing.ImageIcon;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        craft = new Craft();

        timer = new Timer(5, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);


        ImageIcon ii = new ImageIcon(this.getClass().getResource("top.png"));
        g2d.drawImage((ii.getImage()),0,0,this);

        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++ ) {
            Missile m = (Missile) ms.get(i);
            g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }


    public void actionPerformed(ActionEvent e) {
        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }

        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {

    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}
