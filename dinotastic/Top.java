public class Top extends Entity {

	public Top () { 
		image = retrieveImage("artwork/platform.png");	
        width = image.getWidth(null);
        height = image.getHeight(null);
        dx=0;
        dy=0;
    }

}
