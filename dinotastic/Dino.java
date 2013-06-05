import java.awt.event.KeyEvent;
import java.awt.Rectangle; 

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;


import java.awt.Image;

public class Dino extends Entity {

	private boolean jumping=false;
		private Image leftImage;
		private Image rightImage; 
	public Dino() {
        	


		leftImage = retrieveImage("artwork/dino-left.png");	
		rightImage = retrieveImage("artwork/dino-right.png");

		image = leftImage;
        width = image.getWidth(null);
        height = image.getHeight(null);
        dx=0;
        dy=1;	
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
			image = leftImage;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
			image = rightImage;
		}
		if (key == KeyEvent.VK_UP){
			if (!jumping){
				dy = -3;
				jumping=true;
				new Thread(new jumpThread()).start();
			} 
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {dx=0;}
		if (key == KeyEvent.VK_RIGHT) {dx=0;}
	}

	public void mouseMoved(MouseEvent me) {
		x = me.getX();
	}

	public Rectangle getBounds() {
        return new Rectangle((int)x, (int) y+height-1, width, 1);
    }

    public class jumpThread implements Runnable {

    	@Override
    	public void run() {
    		try {
	    		Thread.sleep(200);
	    		dy = 1;
	    		Thread.sleep(700);
	    		jumping=false;
    		} catch (Exception e){}
    	}
    }
}