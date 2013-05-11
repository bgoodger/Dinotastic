import java.awt.Image;
import java.awt.Rectangle; 

public class Entity {
	
	private int x, y; // Co-ords of top left corner
	private int dx, dy; // Current speed components
	private int width, height; 
	private boolean visible;

	private Image image;

	public int getX () { return x; }
	public int getY () { return y; }
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