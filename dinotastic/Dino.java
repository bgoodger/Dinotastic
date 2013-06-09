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
	private Image walkLeftImage;
	private Image walkRightImage; 
	private Image jumpLeftImage;
	private Image jumpRightImage;
	private Image deadImage;
	private boolean facingLeft=true;
	private boolean canJump=false;
	private int healthLevel = 500;
	private double deathTime;

	public Dino() {
        	
      
		leftImage = retrieveImage("artwork/dino-left.png");	
		rightImage = retrieveImage("artwork/dino-right.png");
		walkLeftImage = retrieveImage("artwork/dino-walk-left.gif");	
		walkRightImage = retrieveImage("artwork/dino-walk-right.gif");
		jumpLeftImage = retrieveImage("artwork/jump-left.png");	
		jumpRightImage = retrieveImage("artwork/jump-right.png");
		deadImage = retrieveImage("artwork/dead.png");
		falling = true;
		image = leftImage;
        width = image.getWidth(null);
        height = image.getHeight(null);
        dy=1;	
        ody=dy;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -3;
			image = walkLeftImage;
			facingLeft=true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 3;
			image = walkRightImage;
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

		if (key == KeyEvent.VK_LEFT) {
			dx=0;
			image = leftImage;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx=0;
			image = rightImage;
		}
	}

	public void mouseMoved(MouseEvent me) {
		double xx = me.getX();

		if (xx<320){
			dx = (((320-xx)/320) * -3);
			image = leftImage;
			facingLeft=true;
		} else if (xx>320){
			dx = (((xx-320)/320) * 3);
			image = rightImage;
			facingLeft=false;
		}

	}

	public void loseHealth(int amount) {
		healthLevel -= amount;
		//hurtSound.start();
		
		if (healthLevel <= 0) {
			healthLevel = 0;
			dead();	
		}
	}

	public void mouseClicked (MouseEvent me) {
		//if (me.getButton() == MouseEvent.BUTTON1) {
			if (!jumping && canJump){

				new Thread(new jumpThread()).start();
			} 
		//}
	}

	public int getHealth() {
		return  healthLevel;
	}

	public void canJump (boolean cJ) {
		canJump = cJ;
	}

	public Rectangle getBounds() {
        return new Rectangle((int)x, (int) y+height-1, width, 1);
    }

    public void dead() {
    	if(!isDead) { // Check this isn't creating duplicate threads for this dino
    		new Thread(new SoundMaker("bison.wav")).start();
    	}
    	isDead = true;	
    	deathTime = System.currentTimeMillis();
    	healthLevel = 0;
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
    			new Thread(new SoundMaker("jump.wav")).start();
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