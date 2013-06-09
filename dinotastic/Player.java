
import java.io.*; 

public class Player implements java.io.Serializable {

	private int wins;
	public String playerName;

	public Player  (int w, String name) {
		wins = w;
		playerName = name;

	}
	

} 