import java.util.HashMap;

public class Money {

	public Money() {
		denominations = new HashMap<String, Integer>();
		strToVal = new HashMap<String, Double>();
		
		denominations.put("One Thousand Bill", 0);
		denominations.put("Five Hundred Bill", 0);
		denominations.put("Two Hundred Bill", 0);
		denominations.put("One Hundred Bill", 0);
		denominations.put("Fifty Bill", 0);
		denominations.put("Twenty Bill", 0);
		
		denominations.put("Twenty Coin", 0);
		denominations.put("Ten Coin", 0);
		denominations.put("Five Coin", 0);
		denominations.put("One Coin", 1.0);
		denominations.put("Twenty Five Cents", 0);
		denominations.put("Five Cents", 0);
		denominations.put("One Cent", 0);
		
		/*
		strToVal.put("One Thousand Bill", 1000.0);
		strToVal.put("Five Hundred Bill", 500.0);
		strToVal.put("Two Hundred Bill", 200.0);
		strToVal.put("One Hundred Bill", 100.0);
		strToVal.put("Fifty Bill", 50.0);
		strToVal.put("Twenty Bill", 20.0);
		
		strToVal.put("Twenty Coin", 20.0);
		strToVal.put("Ten Coin", 10.0);
		strToVal.put("Five Coin", 5.0);
		strToVal.put("One Coin", 1.0);
		strToVal.put("Twenty Five Cents", 0.25);
		strToVal.put("Five Cents", 0.05);
		strToVal.put("One Cent", 0.01);
		*/
	}
	
	public int getTotalMoney() {
		return totalMoney;
	}
	
	
	private HashMap<String, Integer> denominations;
	private int totalMoney;
	private static final HashMap<String, Double> strToVal;
	
	strToVal.put("One Thousand Bill", 1000.0);
	strToVal.put("Five Hundred Bill", 500.0);
	strToVal.put("Two Hundred Bill", 200.0);
	strToVal.put("One Hundred Bill", 100.0);
	strToVal.put("Fifty Bill", 50.0);
	strToVal.put("Twenty Bill", 20.0);
		
	strToVal.put("Twenty Coin", 20.0);
	strToVal.put("Ten Coin", 10.0);
	strToVal.put("Five Coin", 5.0);
	strToVal.put("One Coin", 1.0);
	strToVal.put("Twenty Five Cents", 0.25);
	strToVal.put("Five Cents", 0.05);
	strToVal.put("One Cent", 0.01);
}