import java.awt.Image;
import java.awt.Rectangle; 

import javax.swing.ImageIcon;

public class Entity {
	
	protected int x, y; // Co-ords of top left corner
	protected int dx, dy; // Current speed components
	protected int width, height; 
	protected boolean visible;

	protected Image image;

	public int getX () { return x; }
	public int getY () { return y; }
	public Image getImage() { return image; }
	public boolean isVisible () { return visible; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

	public Image retrieveImage(String imagePath) {
		// Return image object from file path 
		ImageIcon ii = new ImageIcon(imagePath);
		return ii.getImage(); 
	}


}