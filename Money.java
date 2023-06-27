import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map;
/** The class Money represents an item
  * that is built within a slot that is
  * inside the vending machine 
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class Money {

	public Money(boolean value)
	{
		this();
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
		

	}
	public Money(Money givenMoney)
	{
		this();
		LinkedHashMap<String, Integer> givenDenoms = givenMoney.getDenominations();
		for(String tempDenom : givenDenoms.keySet())
		{
			denominations.put(tempDenom, denominations.get(tempDenom));
		}

	}
	public Money() {
		denominations = new LinkedHashMap<String, Integer>();
		
		denominations.put("One Thousand Bill", 10);
		denominations.put("Five Hundred Bill", 10);
		denominations.put("Two Hundred Bill", 100);
		denominations.put("One Hundred Bill", 100);
		denominations.put("Fifty Bill", 100);
		denominations.put("Twenty Bill", 100);
		
		denominations.put("Twenty Coin", 100);
		denominations.put("Ten Coin", 500);
		denominations.put("Five Coin", 500);
		denominations.put("One Coin", 500);
		denominations.put("Twenty Five Cents", 500);
		denominations.put("Five Cents", 500);
		denominations.put("One Cent", 500);
		
		
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

	public boolean isEmpty()
	{
		for(Map.Entry<String, Integer> tempEntry : denominations.entrySet())
		{
			if(tempEntry.getValue() > 0)
			{
				return false;
			}
		}
		return true;

	}
	public void addBillsOrCoins(double givenValue, int amt)

	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) + amt);
				return true;
			}
			
			
		}
		return false;

	}

	public boolean subtractBillsOrCoins(double givenValue, int amt)
	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) - amt);
				return true;
			}
			
			
		}
		return false;

	}
	public boolean setBillsOrCoins(double givenValue, int amt)
	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)));
				return true;
			}
			
			
		}
		return false;

	}
	// public Money giveDifferenceMoney(Money given)
	// {
	// 	Money tempMoney = new Money(true);
	// 	for(String tempKey : given.getDenominations().keySet())
	// 	{
	// 		if(given.getDenominations().get(tempKey) - target.getDenominations().get(tempKey) > 0)
	// 			tempMoney.addBillsOrCoins(moneyStringToValue(tempKey), given.getDenominations().get(tempKey) - this.getDenominations().get(tempKey));
	// 	}

	// 	return tempMoney;
		

	// }
	
	public boolean subtractBillsOrCoins(double givenValue, int amt)
	{
		for(double tempVal : valToStr.keySet())
		{
			if(givenValue == tempVal)
			{
				// valToStr.get(tempVal) - converts value to string word equivalent, thus used as key for denominations
				//denominations.get(valToStr.get(tempVal)) - gets the amount currently in denominations
				denominations.put(valToStr.get(tempVal), denominations.get(valToStr.get(tempVal)) - amt);
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
		for(String i : this.denominations.keySet())
			this.denominations.put(i, this.denominations.get(i) + denominations.get(i));
	}
	

	public void acceptDenominations(Money money) 
{
		for(String i : money.getDenominations().keySet())
			this.denominations.put(i , this.denominations.get(i) + money.getDenominations().get(i));

	}
	
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		for(String i : denominations.keySet())
			this.denominations.put(i , denominations.get(i));
	}

	public void promptDenomination()
	{
		Scanner sc = new Scanner(System.in);

		int newValue;
		for(String tempEntry : denominations.keySet()){
            

            System.out.print("How many " + tempEntry + ": ");
            newValue = sc.nextInt();
			sc.nextLine();
            denominations.put(tempEntry, newValue);
        }
		sc.close();
	}

	public static double moneyStringToValue(String name)
	{
		return strToVal.get(name);
	}
	
	public LinkedHashMap<String, Integer> getDenominations() {
		return denominations;
	}
	
	public static LinkedHashMap<String, Double> getStrToVal() {
		return strToVal;
	}
	
	
	private LinkedHashMap<String, Integer> denominations;
	private static LinkedHashMap<String, Double> strToVal;	//change back to priv
	private static LinkedHashMap<Double, String> valToStr;

}