
import java.awt.Image;
import java.awt.Rectangle; 
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;



public class Craft {

    private String initial_craft = "walk-right-1.png";

    private Image[] walkLeftImages = new Image[4];
    private Image[] walkRightImages = new Image[4];
    ImageIcon ii;
    private Boolean falling = true ; 
    private int dx;
    private int dy = 1;
    private int x;
    private int y;
    private Image image;
    private int width;
    private int height;

    private ArrayList<Missile> missiles;

    private final int CRAFT_SIZE = 10;

    public Craft() {

        for (int i =0; i<4; i++) {
            ii = new ImageIcon("walk-left-" + (i+1) + ".png");
            walkLeftImages[i] = ii.getImage();

            ii = new ImageIcon("walk-right-" + (i+1) + ".png");
            walkRightImages[i] = ii.getImage();
        }


        ImageIcon ii = new ImageIcon(this.getClass().getResource(initial_craft));
        image = ii.getImage();
        missiles = new ArrayList<Missile>();
        x = 40;
        y = 60;

        width = image.getWidth(null);
        height = image.getHeight(null);
    }


    public void move() {


        if ( x >= 0 && x <= 520)
            x += dx;
        if (falling) {
            y += dy;
        }  

        if (y>450) {
            falling = false;
            dy = 0;
            // DEAD = TRUE
        }

        if (x < 0)
            x = 0   ;
        if (x > 520)
            x = 520; 
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
                ImageIcon ii = new ImageIcon(this.getClass().getResource("walk-left-1.png"));
                image = ii.getImage();
                dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("walk-right-1.png"));
            image = ii.getImage();
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            jump();
        }

    }

    public void fire() {
        missiles.add(new Missile(x + CRAFT_SIZE, y + CRAFT_SIZE/2));
    }

    public void jump() {
        y=y-40;
        falling = true;
        dy = 1;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            // JUMP
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}