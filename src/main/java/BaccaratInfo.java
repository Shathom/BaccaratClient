import java.io.Serializable;
import java.util.ArrayList;

public class BaccaratInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String bettingType;
	public double bettingAmount;
	public boolean quit;
	public ArrayList<String> playerHand = new ArrayList<String>();
	public ArrayList<String> bankerHand = new ArrayList<String>();
	public boolean naturalWin;
    public boolean playerDraw;
	public boolean bankerDraw;
	public double totalWinnings;
	public double currentWinnings;
	public String gameResult;
	
	BaccaratInfo() {
	}

}
