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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
    private JLabel endLabel;
    private long startTime;  
    private long endTime; 
    private long pauseTime;
    private Timer timer;
    private Dino dinoA;
    private Dino dinoB;
    private boolean twoPlayer;
    private boolean paused = false;
    private JButton startButton;
    private JButton returnToMenuButton;
    private JPanel mB;
    private JPanel menuBar;
    private boolean isTrainingMode;
    private boolean makePlatforms=true;
    private boolean gameOver=false;
    private int levelCounter;


    // Level info

    private int currentLevel = 0;
    private int maxPlatSpeed = -3;
    private int minPlatSpeed = -1;
    private int maxPlatVariation = 20;
    private ArrayList<String> availablePlats;

    private ArrayList<Platform> platforms;

    public PlayBoard() {};

    public PlayBoard(JPanel cardBoard, boolean twoP, boolean trainingMode) {


        menuBar = new JPanel( new FlowLayout(FlowLayout.CENTER,5,0));
        this.setLayout(new BorderLayout());
        BorderLayout bl = (BorderLayout)(this.getLayout());
        bl.setVgap(0);

        mB = cardBoard;
        twoPlayer = twoP;
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        isTrainingMode = trainingMode;
        
        returnToMenuButton = new JButton("Return to Main Menu");
        startButton = new JButton("Play Game");
       
        startButton.addActionListener(this);
        returnToMenuButton.addActionListener(this);
        menuBar.add(startButton);
        menuBar.add(returnToMenuButton);
        menuBar.setBackground(Color.WHITE);
        add(menuBar, BorderLayout.PAGE_START);
        dinoA = new Dino();
        if (twoPlayer) {dinoB = new Dino();}

        platforms = new ArrayList<Platform>();
        platforms.add(new Platform(0,550,-1, Platform.NORMAL_PLATFORM));
        platforms.add(new Platform(240,550,-1, Platform.NORMAL_PLATFORM));
        platforms.add(new Platform(550,550,-1, Platform.NORMAL_PLATFORM));
        
        availablePlats = new ArrayList<String>();
        availablePlats.add(Platform.NORMAL_PLATFORM);

        timer = new Timer(5, this);

    }

    public void start() {
        timeLabel = new JLabel(String.valueOf(timePassed()));
        pauseLabel = new JLabel("PAUSED");
        pauseLabel.setVisible(false);
        returnToMenuButton.setVisible(false);

        p1HealthLabel = new JLabel(String.valueOf(dinoA.getHealth()));
        
        menuBar.add(pauseLabel);
        menuBar.add(timeLabel);        
        menuBar.add(p1HealthLabel);
        if (twoPlayer) {
            p2HealthLabel = new JLabel(String.valueOf(dinoB.getHealth()));
            menuBar.add(p2HealthLabel);
        }
        


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
       
       if (!gameOver) {
            endTime = timePassed();
            makePlatforms = false;
            for (int i = 0; i<platforms.size(); i++) {
                Platform plat = (Platform) platforms.get(i);
                plat.setFalling(true);
            }

            returnToMenuButton.setVisible(true);
           
            endLabel = new JLabel(String.valueOf("Final Score: " + endTime));
            menuBar.add(endLabel);
            endLabel.setVisible(true);
            timeLabel.setVisible(false);
            gameOver=true;
        }

    }

    public void increaseLevel() {
        
        currentLevel++;
        if (currentLevel == 1) {
            maxPlatSpeed -= 1;
            maxPlatSpeed -= 1;
            maxPlatVariation = 20;
        } else if (currentLevel == 2) {
            availablePlats.add(Platform.BONE_PLATFORM);
        } else if (currentLevel == 3) {
            availablePlats.add(Platform.SPIKE_PLATFORM);
        } else if (currentLevel >= 4) {

        }

    }

    public long timePassed() {
        return System.currentTimeMillis() - startTime;
    }

    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(new ImageIcon("artwork/bg.png").getImage(), 0, 30, this);

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
            timeLabel.setText("Score:  " + String.valueOf(this.timePassed())); 
            p1HealthLabel.setText("Player One Health:  " + String.valueOf(dinoA.getHealth())); 
            if (twoPlayer) {
                p2HealthLabel.setText(String.valueOf("Player Two Health:  " + dinoB.getHealth())); 
            }
        } catch (Exception e){}
        
        if (!isTrainingMode){
           levelCounter++;
           if (levelCounter > 1000) {
            increaseLevel();
            levelCounter =0;
           }
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {
            this.start();
            startButton.setVisible(false);
        } else if( e.getSource() ==returnToMenuButton) {
            CardLayout cl = (CardLayout)(mB.getLayout()); 
            cl.show(mB,"menu");
        } else {

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
                dinoB.loseHealth(1);
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

        boolean jumpFlagA = false;
        boolean jumpFlagB = false;
        for (int i = 0; i<platforms.size(); i++) {
            Platform plat = (Platform) platforms.get(i);
            Rectangle platBox = plat.getBounds();
            if (dinoABox.intersects(platBox)) {
                jumpFlagA = true;
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
                    jumpFlagB = true;
                    plat.interact();
                    if (plat.doesDamage()) {
                        dinoB.loseHealth(1);
                    }
                    dinoB.resetDY();
                    dinoB.setY(plat.getY()-dinoB.getHeight()); 
                } 
            }
        }

        dinoA.canJump(jumpFlagA);
        if (twoPlayer) { dinoB.canJump(jumpFlagB);}

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
                    Thread.sleep(2000);
                    platforms.add(new Platform((int)(10 + (Math.random() * maxPlatVariation)) ,550, (Math.random() * (maxPlatSpeed- minPlatSpeed) + minPlatSpeed), availablePlats.get( (int) (Math.random() * availablePlats.size()) )));
                    platforms.add(new Platform((int)(280 + (Math.random() * maxPlatVariation)),550,(Math.random() * (maxPlatSpeed- minPlatSpeed) + minPlatSpeed), availablePlats.get( (int) (Math.random() * availablePlats.size()) )));
                    platforms.add(new Platform((int)(500 + (Math.random() * maxPlatVariation)),550,(Math.random() * (maxPlatSpeed- minPlatSpeed) + minPlatSpeed), availablePlats.get( (int) (Math.random() * availablePlats.size()) )));
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
