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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class PlayBoard extends Board {

    private Timer timer;
    private Dino dinoA;
    private Dino dinoB;
    private boolean twoPlayer;
    private JButton startButton;

    private boolean isTrainingMode;

    private ArrayList<Platform> platforms;

    public PlayBoard() {};

    public PlayBoard(JPanel cardBoard, boolean twoP, boolean trainingMode) {

        twoPlayer = twoP;
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        isTrainingMode = trainingMode;
        startButton = new JButton("Play Game");
        startButton.addActionListener(this);
        add(startButton);
        dinoA = new Dino();
        if (twoPlayer) {dinoB = new Dino();}

        platforms = new ArrayList<Platform>();
        platforms.add(new Platform(100,550));
        platforms.add(new Platform(250,550));
        platforms.add(new Platform(350,550));
        timer = new Timer(5, this);

        System.out.println("is training  " + isTrainingMode);

    }

    public void start() {

        this.addKeyListener(new KAdapter(this));
        this.addMouseMotionListener(this);
        Toolkit.getDefaultToolkit().sync();
        timer.start();

    }


    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        // Draw what should be showing up
        g2d.drawImage(dinoA.getImage(), dinoA.getX(), dinoA.getY(), this);
        if (twoPlayer) {
            g2d.drawImage(dinoB.getImage(), dinoB.getX(), dinoB.getY(), this);
        }
          
        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            g2d.drawImage(plat.getImage(), plat.getX(), plat.getY(), this);
        }

        timeLabel.setText(timer.toString()); 
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {
            this.start();
            startButton.setVisible(false);
        }


        for (int i = 0; i < platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            plat.move();
        }

        dinoA.move();
        if (twoPlayer) {dinoB.move();}
        checkCollisions();
        repaint();  
    }


    public void checkBounds () {

    }

    public void checkCollisions() {

        Rectangle dinoABox = dinoA.getBounds();

        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            Rectangle platBox = plat.getBounds();

            if (dinoABox.intersects(platBox)) {
                dinoA.setDY(plat.getDY());
            } else {
               // dinoA.setDY(1);
            }
            
            if (twoPlayer) { 
                Rectangle dinoBBox = dinoB.getBounds();
                if (dinoBBox.intersects(platBox)) {
                    dinoB.setDY(plat.getDY());
                } else {
                   // dinoB.setDY(1);
                }
            }
        }

    }

    public void pause() {
        timer.stop();
        System.out.println("pause");
    }

    public void mouseMoved(MouseEvent me) { 
        if (twoPlayer) {dinoB.mouseMoved(me);}
    } 



    private class KAdapter  extends KeyAdapter {

        private PlayBoard caller; 
 
        public KAdapter (PlayBoard c) {
            caller = c;
        }

        public void keyReleased(KeyEvent e) {
            dinoA.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                caller.pause();
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                caller.start();
            }

            dinoA.keyPressed(e);
             

        }
    }
}
