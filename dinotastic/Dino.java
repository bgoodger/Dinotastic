import java.awt.event.KeyEvent;

public class Dino extends Entity {

	public Dino() {
        
		image = retrieveImage("artwork/dino-main.png");	
        width = image.getWidth(null);
        height = image.getHeight(null);
	}

	public void move() {

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
}