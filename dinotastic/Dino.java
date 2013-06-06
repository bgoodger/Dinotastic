import java.awt.event.KeyEvent;
import java.awt.Rectangle; 

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;


import java.awt.Image;

public class Dino extends Entity {

	private boolean isDead = false;
	private boolean jumping=false;
	private Image leftImage;
	private Image rightImage; 
	private Image jumpLeftImage;
	private Image jumpRightImage;
	private Image deadImage;
	private boolean facingLeft=true;

	private int healthLevel = 100;

	public Dino() {
        	
		leftImage = retrieveImage("artwork/dino-left.png");	
		rightImage = retrieveImage("artwork/dino-right.png");
		jumpLeftImage = retrieveImage("artwork/jump-left.png");	
		jumpRightImage = retrieveImage("artwork/jump-right.png");
		deadImage = retrieveImage("artwork/dead.png");
		falling = true;
		image = leftImage;
        width = image.getWidth(null);
        height = image.getHeight(null);
        //dx=0;
        dy=1;	
        ody=dy;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -3;
			image = leftImage;
			facingLeft=true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 3;
			image = rightImage;
			facingLeft=false;
		}
		if (key == KeyEvent.VK_UP){
			if (!jumping){
				new Thread(new jumpThread()).start();
			} 
		}
	}

	public void setDY (double udy) { 
		if (!jumping){
			dy = udy;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {dx=0;}
		if (key == KeyEvent.VK_RIGHT) {dx=0;}
	}

	public void mouseMoved(MouseEvent me) {
		double xx = me.getX();

		if (xx<340){
			dx = (((340-xx)/340) * -3);
			image = leftImage;
			facingLeft=true;
		} else if (xx>340){
			dx = (((xx-340)/340) * 3);
			image = rightImage;
			facingLeft=false;
		}

	}

	public void loseHealth(int amount) {
		healthLevel -= amount;

		if (healthLevel <= 0) {
			dead();	
		}
	}

	public void mouseClicked (MouseEvent me) {
		//if (me.getButton() == MouseEvent.BUTTON1) {
			if (!jumping){
				new Thread(new jumpThread()).start();
			} 
		//}
	}

	public int getHealth() {
		return  healthLevel;
	}

	public Rectangle getBounds() {
        return new Rectangle((int)x, (int) y+height-1, width, 1);
    }

    public void dead() {
    	isDead = true;	
    	image = deadImage;
    	falling = true;
    }

    public boolean isDead() {
    	return isDead;	
    }

    public class jumpThread implements Runnable {

    	@Override
    	public void run() {
    		try {
    			dy = -6;
				jumping=true;
				if (facingLeft){
					image = jumpLeftImage;
				} else {
					image = jumpRightImage;
				}
	    		Thread.sleep(200);
	    		dy = ody;
	    		Thread.sleep(700);
	    		jumping=false;
	    		if (facingLeft){
					image = leftImage;
				} else {
					image = rightImage;
				}

    		} catch (Exception e){}
    	}
    }
}