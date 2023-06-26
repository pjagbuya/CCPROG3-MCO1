import java.util.LinkedHashMap;

public class Money {

	public Money() {
		denominations = new LinkedHashMap<String, Integer>();
		
		denominations.put("One Thousand Bill", 0);
		denominations.put("Five Hundred Bill", 0);
		denominations.put("Two Hundred Bill", 0);
		denominations.put("One Hundred Bill", 0);
		denominations.put("Fifty Bill", 0);
		denominations.put("Twenty Bill", 0);
		
		denominations.put("Twenty Coin", 0);
		denominations.put("Ten Coin", 0);
		denominations.put("Five Coin", 0);
		denominations.put("One Coin", 0);
		denominations.put("Twenty Five Cents", 0);
		denominations.put("Five Cents", 0);
		denominations.put("One Cent", 0);
		
		
		strToVal = new LinkedHashMap<String, Double>();
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

		valToStr = new LinkedHashMap<Double, String>();
		valToStr.put(1000.0, "One Thousand Bill");
		valToStr.put(500.0, "Five Hundred Bill");
		valToStr.put(200.0, "Two Hundred Bill");
		valToStr.put(100.0, "One Hundred Bill");
		valToStr.put(50.0, "Fifty Bill");
		valToStr.put(20.0, "Twenty Bill");

		valToStr.put(20.0, "Twenty Coin");
		valToStr.put(10.0, "Ten Coin");
		valToStr.put(5.0, "Five Coin");
		valToStr.put(1.0, "One Coin");
		valToStr.put(0.25, "Twenty Five Cents");
		valToStr.put(0.05, "Five Cents");
		valToStr.put(0.01, "One Cent");
	}

	public void addBillsOrCoins(double givenValue, int qty)
	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) + qty);
			}	
		}
	}
	
	public boolean subtractBillsOrCoins(double givenValue, int qty)
	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) - qty);
				return true;
			}
			
			
		}
		return false;

	}
	
	public double getTotalMoney() {
		double total = 0.0;
		for( String s : denominations.keySet() )
			total += strToVal.get(s)*denominations.get(s);
		return total;
	}
	

	
	public void acceptDenominations(LinkedHashMap<String, Integer> denominations) {
		for(String s : denominations.keySet())
			this.denominations.put(s, this.denominations.get(s) + denominations.get(s));
	}
	
	public void acceptDenominations(Money money) {
		for(String s : money.getDenominations().keySet())
			this.denominations.put(s , this.denominations.get(s) + money.getDenominations().get(s));
	}
	
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		for(String s : denominations.keySet())
			this.denominations.put(s , denominations.get(s));
	}
	
	public LinkedHashMap<String, Integer> getDenominations() {
		return denominations;
	}
	
	public static LinkedHashMap<String, Double> getStrToVal() {
		return strToVal;
	}
	
	public static LinkedHashMap<Double, String> getValToStr() {
		return valToStr;
	}
	
	private LinkedHashMap<String, Integer> denominations;
	private static LinkedHashMap<String, Double> strToVal;	//change back to priv
	private static LinkedHashMap<Double, String> valToStr;
}