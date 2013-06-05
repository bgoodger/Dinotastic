public class Platform extends Entity {

	public Platform (int xx, int yy) { 
		image = retrieveImage("artwork/platform.png");	
        width = image.getWidth(null);
        height = image.getHeight(null);
        dx=0;
        dy=-1;
        x=xx;
        y=yy;
    }

}