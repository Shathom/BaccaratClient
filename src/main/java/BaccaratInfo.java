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
	public int playerHandTotal;
	public int bankerHandTotal;
	
	BaccaratInfo() {
	}
	
//	public String toString() {
//		
//		System.out.println("In client toString");
//		
//		System.out.println("currentWinnings: " + currentWinnings);
//		System.out.println("gameResult :" + gameResult);
//		System.out.println("bettingType :" + bettingType);
//		System.out.println("bettingAmount" + bettingAmount);
//		System.out.println("playerHandTotal: + playerHandTotal");
//		System.out.println("bankerHandTotal: + bankerHandTotal");
//		
//		return "";
//	}

}
