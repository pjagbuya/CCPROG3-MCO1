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
		denominations.put("One Coin", 0);
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
	
	public HashMap<String, Integer> getDenominations() {
		return denominations;
	}

	
	public boolean canGiveChange(double amt, HashMap<String, Integer> duplicateOfDenomMap) {
		if(amt > 1000.0 && denominations.get("One Thousand Bill") > 0) {
			duplicateOfDenomMap.put("One Thousand Bill", duplicateOfDenomMap.get("One Thousand Bill") - 1);
			return canGiveChange(amt-1000.0, duplicateOfDenomMap);
		}
		else if(amt > 500.0 && denominations.get("") > 0) {
			duplicateOfDenomMap.put("Five Hundred Bill", duplicateOfDenomMap.get("Five Hundred Bill") - 1);
			return canGiveChange(amt-500.0, duplicateOfDenomMap);
		}
		else if(amt > 200.0 && denominations.get("Two Hundred Bill") > 0) {
			duplicateOfDenomMap.put("Two Hundred Bill", duplicateOfDenomMap.get("Two Hundred Bill") - 1);
			return canGiveChange(amt-200.0, duplicateOfDenomMap);
		}
		else if(amt > 100.0 && denominations.get("One Hundred Bill") > 0) {
			duplicateOfDenomMap.put("One Hundred Bill", duplicateOfDenomMap.get("One Hundred Bill") - 1);
			return canGiveChange(amt-100.0, duplicateOfDenomMap);
		}
		else if(amt > 50.0 && denominations.get("Fifty Bill") > 0) {
			duplicateOfDenomMap.put("Fifty Bill", duplicateOfDenomMap.get("Fifty Bill") - 1);
			return canGiveChange(amt-50.0, duplicateOfDenomMap);
		}
		else if(amt > 20.0 && denominations.get("Twenty Bill") > 0) {
			duplicateOfDenomMap.put("Twenty Bill", duplicateOfDenomMap.get("Twenty Bill") - 1);
			return canGiveChange(amt-20.0, duplicateOfDenomMap);
		}
		else if(amt > 20.0 && denominations.get("Twenty Coin") > 0) {
			duplicateOfDenomMap.put("Twenty Coin", duplicateOfDenomMap.get("Twenty Coin") - 1);
			return canGiveChange(amt-20.0, duplicateOfDenomMap);
		}
		else if(amt > 10.0 && denominations.get("Ten Coin") > 0) {
			duplicateOfDenomMap.put("Ten Coin", duplicateOfDenomMap.get("Ten Coin") - 1);
			return canGiveChange(amt-10.0, duplicateOfDenomMap);
		}
		else if(amt > 5.0 && denominations.get("Five Coin") > 0) {
			duplicateOfDenomMap.put("Five Coin", duplicateOfDenomMap.get("Five Coin") - 1);
			return canGiveChange(amt-5.0, duplicateOfDenomMap);
		}
		else if(amt > 1.0 && denominations.get("One Coin") > 0) {
			duplicateOfDenomMap.put("One Coin", duplicateOfDenomMap.get("One Coin") - 1);
			return canGiveChange(amt-1.0, duplicateOfDenomMap);
		}
		else if(amt > 0.25 && denominations.get("Twenty Five Cents") > 0) {
			duplicateOfDenomMap.put("Twenty Five Cents", duplicateOfDenomMap.get("Twenty Five Cents") - 1);
			return canGiveChange(amt-0.25, duplicateOfDenomMap);
		}
		else if(amt > 0.05 && denominations.get("Five Cents") > 0) {
			duplicateOfDenomMap.put("Five Cents", duplicateOfDenomMap.get("Five Cents") - 1);
			return canGiveChange(amt-0.05, duplicateOfDenomMap);
		}
		else if(amt > 0.01 && denominations.get("One Cent") > 0) {
			duplicateOfDenomMap.put("One Cent", duplicateOfDenomMap.get("One Cent") - 1);
			return canGiveChange(amt-0.01, duplicateOfDenomMap);
		}
		else if(amt == 0)
			return true;
		else
			return false;
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