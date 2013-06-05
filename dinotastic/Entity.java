import java.awt.Image;
import java.awt.Rectangle; 

import javax.swing.ImageIcon;

public class Entity {
	
	protected double x, y; // Co-ords of top left corner
	protected double dx, dy; // Current speed components
	protected int width, height; 
	protected boolean visible;

	protected boolean falling = true; 

	protected Image image;

	public int getX () { return (int) x; }
	public int getY () { return (int) y; }
	public double getDX () { return dx; }
	public double getDY () { return dy; }
	public void setDX (double udx) { dx = udx; }
	public void setDY (double udy) { dy = udy; }
	public Image getImage() { return image; }
	public boolean isVisible () { return visible; }

	public void move() {
		x = x + .7*dx;
		if (falling) {  
			y=y + .5*dy;
			//`dy += .02;
		}

	}	

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

	public Image retrieveImage(String imagePath) {
		// Return image object from file path 
		ImageIcon ii = new ImageIcon(imagePath);
		return ii.getImage(); 
	}

	public void isFalling(boolean f) {
		falling = f;
	} 

}