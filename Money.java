import java.util.LinkedHashMap;

/** This class represents a vending machine's cash reserves.
  * Contains methods and attributes for working with the "cash."
  * This program's denomination set is based
  * on the current series of Philippine coins and banknotes.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class Money {
	/**
	 * Creates Money object and initializes all denominations to zero pieces.
	 * Also intializes strToVal and valToStr.
	 *
	 */
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
	
	/**
	 * Adds a specified number of coins/bills to the cash reserves
	 * 
	 * @param givenValue the double representation of the denomination
	 * @param qty the indicator of how many pieces of the specified denomination should be added
	 */
	public void addBillsOrCoins(double givenValue, int qty)
	{
		for(double tempVal : valToStr.keySet())
			if(	givenValue == tempVal &&
				qty > 0 )
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) + qty);
			}	
	}
	
	/**
	 * Removes a specified number of coins/bills from the cash reserves
	 * 
	 * @param givenValue the double representation of the denomination
	 * @param qty the indicator of how many pieces of the specified denomination
				  should be removed
	 * 
	 * @return true if 1.) the specified denomination is part of the standard set,
					and 2.) the specified number of pieces of that denomination
					was removed from the cash reserves,
					false otherwise
	 */
	public boolean subtractBillsOrCoins(double givenValue, int qty)
	{
		for(double tempVal : valToStr.keySet())
			if(	givenValue == tempVal &&
				qty > 0 &&
				(denominations.get(valToStr.get(tempVal)) - qty) >= 0)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) - qty);
				return true;
			}
		return false;

	}
	
	
	/**
	 * Computes for the total value of all cash reserves
	 * 
	 * @return the current total of all cash reserves
	 **/
	public double getTotalMoney() {
		double total = 0.0;
		for( String s : denominations.keySet() )
			total += strToVal.get(s)*denominations.get(s);
		return total;
	}
	

	/**
	 * Adds a set of denominations to the object's own set of denominations
	 * 
	 * @param denominations the list of bills and coins to add to the cash reserves
	 **/
	public void acceptDenominations(LinkedHashMap<String, Integer> denominations) {
		for(String s : denominations.keySet())
			this.denominations.put(s, this.denominations.get(s) + denominations.get(s));
	}
	
	/**
	 * Adds the cash reserves of another Money object to its own cash reserves
	 * 
	 * @param money another instance of class Money
	 **/
	public void acceptDenominations(Money money) {
		acceptDenominations(money.getDenominations());
	}
	
	/**
	 * Overwrite the cash reserves
	 *
	 * @param denominations a list of new denominations
	 **/
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		for(String s : denominations.keySet())
			this.denominations.put(s , denominations.get(s));
	}
	
	/**
	 * Gets cash reserves
	 *
	 * @return the current set of cash reserves by denomination
	 **/
	public LinkedHashMap<String, Integer> getDenominations() {
		return denominations;
	}
	
	/**
	 * Gets strToVal, for converting from a denomination's
	 * String representation to its double representation
	 *
	 * @return the strToVal hashmap of the Money class
	 **/
	public static LinkedHashMap<String, Double> getStrToVal() {
		return strToVal;
	}
	
	/**
	 * Gets valToStr, converting from a denomination's
	 * double representation to its String representation
	 *
	 * @return the valToStr hashmap of the Money class
	 **/
	public static LinkedHashMap<Double, String> getValToStr() {
		return valToStr;
	}
	
	/** represents cash reserves, inluding the currently stored number of pieces of each denomination */
	private LinkedHashMap<String, Integer> denominations;
	/** class hashmap for converting from the String to the double representation of a denomination */
	private static LinkedHashMap<String, Double> strToVal;
	/** class hashmap for converting from the double to the String representation of a denomination */
	private static LinkedHashMap<Double, String> valToStr;
}