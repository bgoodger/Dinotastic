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
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class PlayBoard extends Board {

    private JLabel timeLabel; 
    private JLabel p1HealthLabel;  
    private JLabel p2HealthLabel; 
    private JLabel pauseLabel; 
    private long startTime;  
    private long pauseTime;
    private Timer timer;
    private Dino dinoA;
    private Dino dinoB;
    private boolean twoPlayer;
    private boolean paused = false;
    private JButton startButton;
    private JPanel mB;
    private boolean isTrainingMode;
    private boolean makePlatforms=true;

    private ArrayList<Platform> platforms;

    public PlayBoard() {};

    public PlayBoard(JPanel cardBoard, boolean twoP, boolean trainingMode) {

        mB = cardBoard;
        twoPlayer = twoP;
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        isTrainingMode = trainingMode;
        startButton = new JButton("Play Game");
        startButton.addActionListener(this);
        add(startButton);
        dinoA = new Dino();
        if (twoPlayer) {dinoB = new Dino();}

        platforms = new ArrayList<Platform>();
        platforms.add(new Platform(0,550,-1, Platform.NORMAL_PLATFORM));
        platforms.add(new Platform(240,550,-1, Platform.NORMAL_PLATFORM));
        platforms.add(new Platform(550,550,-1, Platform.NORMAL_PLATFORM));
        timer = new Timer(5, this);
        
        System.out.println("is training  " + isTrainingMode);


    }

    public void start() {
        timeLabel = new JLabel(String.valueOf(timePassed()));
        pauseLabel = new JLabel("PAUSED");
        pauseLabel.setVisible(false);

        p1HealthLabel = new JLabel(String.valueOf(dinoA.getHealth()));
        if (twoPlayer) {p2HealthLabel = new JLabel(String.valueOf(dinoB.getHealth()));}
        this.add(timeLabel);        
        this.add(pauseLabel);
        this.add(p1HealthLabel);

        this.addKeyListener(new KAdapter(this));
        this.addMouseMotionListener(this);

        this.addMouseListener(this);            
        Toolkit.getDefaultToolkit().sync();
        timer.start();
        startTime = System.currentTimeMillis();

        new Thread(new platformMaker()).start();        

    }

    public void pause() {
        paused = true;
        pauseLabel.setVisible(true);
        timer.stop();
        pauseTime = System.currentTimeMillis();
        
    }

    public void restart() {
        paused = false;
        pauseLabel.setVisible(false);
        timer.start();
        startTime = ( System.currentTimeMillis() - pauseTime ) + startTime;
    }

    public void gameOver() {
        makePlatforms = false;
        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            plat.setFalling(true);
        }

    }


    public long timePassed() {
        return System.currentTimeMillis() - startTime;
    }

    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(new ImageIcon("artwork/bg.png").getImage(), 0, -10, this);

        // Draw what should be showing up
        g2d.drawImage(dinoA.getImage(), dinoA.getX(), dinoA.getY(), this);
        if (twoPlayer) {
            g2d.drawImage(dinoB.getImage(), dinoB.getX(), dinoB.getY(), this);
        }
          
        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            g2d.drawImage(plat.getImage(), plat.getX(), plat.getY(), this);
        }
        try {
        timeLabel.setText(String.valueOf(this.timePassed())); 
        p1HealthLabel.setText(String.valueOf(dinoA.getHealth())); 
        if (twoPlayer) {p2HealthLabel.setText(String.valueOf(this.timePassed())); }
        } catch (Exception e){}
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
        checkBounds();
        checkGameOver();
        repaint();  
    }


    public void checkBounds() {
        Rectangle dinoABox = dinoA.getBounds();

        // Remove platforms that have moved off the screen
        // Descending order to avoid double counting
        for (int i = platforms.size()-1; i <= 0; i--) {
            Platform plat = (Platform) platforms.get(i);
            if (plat.getY() < 0) {
                platforms.remove(i);
            }
        }


        if (dinoA.getY() + dinoA.getHeight() > 640) {
            dinoA.dead();
        } else if (dinoA.getY() < 50) {
            dinoA.loseHealth(1);
            dinoA.setY(52);
        }

        if (dinoA.getX() <= 0) {
            dinoA.setX(1);
        } else if ((dinoA.getX() + dinoA.getWidth() >= 640)) {
            dinoA.setX(639-dinoA.getWidth());
        }


        if (twoPlayer) { 
            if (dinoB.getY() + dinoB.getHeight() > 640) {
                dinoB.dead();
            } else if (dinoB.getY() < 50) {
                // Decrease life
                dinoB.setY(52);
            }

            if (dinoB.getX() <= 0) {
                dinoB.setX(1);
            } else if ((dinoB.getX() + dinoB.getWidth() >= 640)) {
                dinoB.setX(639-dinoB.getWidth());
            }
        }
    }

    public void checkCollisions() {

        Rectangle dinoABox = dinoA.getBounds();

        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            Rectangle platBox = plat.getBounds();

            if (dinoABox.intersects(platBox)) {
                plat.interact();
                if (plat.doesDamage()) {
                        dinoA.loseHealth(1);
                }
                dinoA.resetDY();
                dinoA.setY(plat.getY()-dinoA.getHeight()); 
            }  
            
            if (twoPlayer) { 
                Rectangle dinoBBox = dinoB.getBounds();
                if (dinoBBox.intersects(platBox)) {
                    plat.interact();
                    if (plat.doesDamage()) {
                        dinoB.loseHealth(1);
                    }
                    dinoB.resetDY();
                    dinoB.setY(plat.getY()-dinoB.getHeight()); 
                } 
            }
        }

    }

    public void checkGameOver() {
        if (dinoA.isDead()) {
            if (twoPlayer) {
                if(dinoB.isDead()){
                    this.gameOver();
                }
            } else {
                this.gameOver();
            }
        }
    }


    @Override
    public void mouseMoved(MouseEvent me) { 
        if (twoPlayer) {dinoB.mouseMoved(me);}
    } 
    @Override
    public void mouseReleased(MouseEvent me) {
        if (twoPlayer) {dinoB.mouseClicked(me);}
    }
    

    public class platformMaker implements Runnable {

        @Override
        public void run() {
            try {
                
                while(makePlatforms) {
                    Thread.sleep(3000);
                    platforms.add(new Platform(10,550,-3, Platform.NORMAL_PLATFORM));
                    platforms.add(new Platform(280,550,-1, Platform.BONE_PLATFORM));
                    platforms.add(new Platform(500,550,-2, Platform.NORMAL_PLATFORM));
                }
            } catch (Exception e){}
        }
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
                if (!paused) {
                    caller.pause();
                } else {
                    caller.restart();
                }
            }
            dinoA.keyPressed(e);
        }
    }
}
