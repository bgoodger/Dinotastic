public class Platform extends Entity {

	public static String NORMAL_PLATFORM = "platform-normal";
	public static String BONE_PLATFORM = "platform-bone";
	public static String SPIKE_PLATFORM = "platform-spike";

	private String platformType;


	public Platform (int xx, int yy, double platSpeed, String platType) { 
		image = retrieveImage("artwork/" + platType +".png");	
        width = image.getWidth(null);
        height = image.getHeight(null);
        dx=0;
        x=xx;
        y=yy;
        falling = false;

        dy=platSpeed;
        ody=platSpeed; // Initial acc if needs to be reset later

        platformType = platType;

    }

    public boolean doesDamage() {
    	if (platformType == SPIKE_PLATFORM) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public void interact() {
    	if (platformType == BONE_PLATFORM) {
    		new Thread(new fallTimer()).start();
    	}
    }

    public class fallTimer implements Runnable {

        @Override
        public void run() {
            try {	
                Thread.sleep(500);
                falling = true;
            } catch (Exception e){}
        }
    }

}