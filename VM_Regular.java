import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;

public class VM_Regular {
	public VM_Regular( int nOfSlots, int item_max) {
		recordCurrInd = 0;								//added
		stockedInfos = new ArrayList<VM_StockedInfo>();	// added
		slots = new VM_Slot[nOfSlots];

		for (int i = 0; i < nOfSlots; i++)
		{
			slots[i] = new VM_Slot(item_max);
		}
		currentMoney = new Money();

	}


	// Double check for some unrecalled conditions
	public void setSlot(VM_Item item, int stock, int ind)
	{
		for (int i = 0; i < slots.length; i++)
			if(ind == i && slots[i].getItem() == null)
				slots[i].addItemStock(item, stock);
		
	}

	/**
	 * Looks for a slot associated with a given item name, and "tells" that slot to "sell" its items a specified quantity
	 *
	 * Has no input validation (use the hasEnoughStock() and hasEnoughChange() methods prior to this)
	 *
	 */
	public void releaseStock(LinkedHashMap<String, Integer> order) {
		int i;
		for( String s : order.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) ) {
					slots[i].releaseStock( order.get(s) );
					break;
				}
	}
	
	/**
	 * Looks for a slot associated with a given item name, and "asks" that slot whether it has enough pieces of that item
	 *
	 *
	 *
	 */
	public boolean hasEnoughStock(LinkedHashMap<String, Integer> order) {
		int i;
		
		boolean stockHasRequiredQuantities = true; // initially true
		for( String s : order.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) )
					
					// if the current slot does not hold the required quantity of its item
					if( !( slots[i].hasEnoughStock( order.get(s) ) ) ) {
					stockHasRequiredQuantities = false;
					break;
					}
		
		return stockHasRequiredQuantities;
	}
	
	/**
	 * Looks for a slot associated with a given item name, and "tells" that slot to compute the total cost of a specified quantity of its items
	 *
	 *
	 */
	public double computeTotalCost(LinkedHashMap<String, Integer> order) {
		int i;
		double totalCost = 0.0;
		
		for( String s : order.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) ) {
					totalCost += slots[i].computePartialCost( order.get(s) );
					break;
				}

		return totalCost;
	}
	
	/**
	 * Takes user's order and accepts their payment, validates inputs, and decides whether to proceed with transaction or not. Uses text-based interface
	 *
	 * @param duplicate		a duplicate of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities(>= 0)
	 * @param order			the order object, contains the user's order
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities (>= 0)
	 */
	public void sellingOperation(
		LinkedHashMap<String, Integer> duplicate,
		LinkedHashMap<String, Integer> payment,
		LinkedHashMap<String, Integer> change,
		Order order )
		{
		
		Scanner sc = new Scanner(System.in);
		double paymentTotal = 0;
		double orderTotal = 0;
		double cashReservesTotal = 0;
		double changeDue = 0;
		int calorieTotal = 0;
		boolean transactionIsValid = true; // initially true
		boolean changeIsPossible;
		int i;
		int j;
		String itemName;
		int itemQty;
		
		
		// setting order
		System.out.println("What would you like to order?");
		i = 0;
		do
		{
			itemName = sc.next();
			
			if( !itemName.equalsIgnoreCase("Y") )
			{
				itemQty = sc.nextInt();
				for(j = 0; j < slots.length; j++)
					if( itemName.equals(slots[j].getSlotItemName()) ) {
						break;
					}
				if( j >= slots.length || !order.addOrder(slots[j], itemQty) )
					System.out.println("-ERROR: ORDERED ITEM NOT IN SUFFICENT STOCK/ITEM NAME DOES NOT EXIST. ENTER A DIFF. ITEM/QUANTITY");
			}
			else
				i++;
		} while ( !itemName.equalsIgnoreCase("Y") && i < slots.length );
		
		
		/*
		for(i = 0; i < getSlots().length; i++) {
			System.out.print( " " + getSlots()[i].getSlotItemName() + ": " );
			order.put( getSlots()[i].getSlotItemName(), sc.nextInt() );
		}
		*/
		
		// inserting payment
		System.out.println();
		System.out.println("Insert payment:");
		for( String s : getDenominations().keySet() )
			payment.put( s, 0 );
		for( String s : payment.keySet() ) {
			System.out.print(" " + s + " ");
			payment.put( s, sc.nextInt() );
		}
		
		
		
		// duplicating denomination hashmap of VM, while setting change denominations to zero
		for(String s : getDenominations().keySet()) {
			duplicate.put(s, getDenominations().get(s));
			change.put(s, 0);
		}
		
		//	display duplicate of denomination hashmap of VM, then calculate total of cash reserves, and display that
		System.out.println();
		System.out.println("CASH RESERVES IN VM");
		for( Map.Entry m : duplicate.entrySet() ) {
			System.out.println(" " + m.getKey() + " " + m.getValue());
		}
		cashReservesTotal = getTotalOfMoneyReserves();
		System.out.println("Cash Reserves Total: " + FORMAT.format(cashReservesTotal) + " PHP");
		
		//calculating payment total
		for(String s : payment.keySet())
			paymentTotal += payment.get(s)*Money.getStrToVal().get(s);
		
		//calculating order total
		for(String s : order.getPendingOrder().keySet()) {
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) )
					break;
			orderTotal += order.getPendingOrder().get(s)*slots[i].getItem().getItemPrice();
		}
		order.setTotalCost(orderTotal);
		
		//calculating calorie total
		for(String s : order.getPendingOrder().keySet())
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) ) {
					calorieTotal = order.getPendingOrder().get(s)*slots[i].getItem().getItemCalories();
					break;
				}
		
		
		//calculating total of cash reserves in the machine
		cashReservesTotal = getTotalOfMoneyReserves();
		
		//calculating change due
		changeDue = paymentTotal - orderTotal;
		
		// display total of order, total of payment received, and change due
		System.out.println();
		System.out.println("Order Total: " + orderTotal + " PHP");
		System.out.println("Payment Received: " + paymentTotal + " PHP");
		System.out.println("Change Due: " + changeDue + " PHP");
		System.out.println("Calorie Total: " + calorieTotal + " kCal");
		
		
		
		
		// checks whether transaction is valid
		
		System.out.println();
		
		if( !hasEnoughStock(order.getPendingOrder()) ) {
			transactionIsValid = false;
			System.out.println("-ERROR: INSUFFICIENT STOCK");
		}
		if( paymentTotal < orderTotal ) {
			transactionIsValid = false;
			System.out.println("-ERROR: INSUFFICIENT PAYMENT");
		}
		if ( !(cashReservesTotal >= orderTotal) ) {
			transactionIsValid = false;
			System.out.println("-ERROR: NOT ENOUGH MONEY RESERVES");
		}
		changeIsPossible = deductChange(changeDue, duplicate);
		System.out.println("changeIsPossible: " + changeIsPossible);
		System.out.println();
		if( changeDue >= 0 && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("-ERROR: CANNOT RETURN CHANGE, INSERT EXACT AMOUNT");
		}
		
		// decides whether to proceed with transaction
		if( transactionIsValid )
		{
			System.out.println("\nTRANSACTION PROCEEDS--------------------------\n");
			releaseStock(order.getPendingOrder());
			for( String s : change.keySet() )
				change.put( s, getDenominations().get(s) - duplicate.get(s) );
			setDenominations(duplicate);
			acceptDenominations(payment);
			for( String s : payment.keySet() )
				payment.put( s, 0 );
		}
		else
		{
			System.out.println("\nTRANSACTION FAILS------------------------\n");
			for( String s : change.keySet() )
				change.put( s, payment.get(s) );
			for( String s : payment.keySet() )
				payment.put( s, 0 );
		}
		
		displayAllItems();
		
		// display cash in VM right after transation success/failure
		System.out.println("CASH RESERVES IN VM:");
		for( Map.Entry m : getDenominations().entrySet() )
			System.out.println(" " + m.getKey() + " " + m.getValue());
		
		// recalculating total of cash reserves in the machine, and display
		cashReservesTotal = getTotalOfMoneyReserves();
		System.out.println("Cash Reserves Total: " + FORMAT.format(cashReservesTotal) + " PHP");
		
		// display payment tray
		System.out.println();
		System.out.println("PAYMENT TRAY:");
		for( Map.Entry m : payment.entrySet() )
			System.out.println(" " + m.getKey() + " " + m.getValue());
		
		// display change
		System.out.println();
		System.out.println("CHANGE RETURNED:");
		for( Map.Entry m : change.entrySet() )
			System.out.println(" " + m.getKey() + " " + m.getValue());
	}
	
	/**
	 * getter, tells currentMoney to compute the total of its cash reserves, and return
	 *
	 *
	 */
	public double getTotalOfMoneyReserves() {
		return currentMoney.getTotalMoney();
	}
	
	/*
	public boolean canGiveChange(double amt, LinkedHashMap<String, Integer> duplicateOfDenomMap) {
		return execGiveChange(amt, duplicateOfDenomMap);
	}
	*/
	
	/**
	 * prints item descriptions onto terminal
	 *
	 *
	 */
	public void displayAllItems() {
		int i;
		for(i = 0; i < slots.length; i++)
			slots[i].displayAllItems();
	}
	
	/**
	 * another method for adding cash into currentMoney's cash reserves
	 *
	 *
	 */
	public void addBillsOrCoins(double givenValue, int amt) {
		currentMoney.addBillsOrCoins(givenValue, amt);
	}
	
	/**
	 * getter, tells currentMoney to return its LinkedHashMap of cash denominations
	 *
	 *
	 */
	public LinkedHashMap<String, Integer> getDenominations() {
		return currentMoney.getDenominations();
	}
	
	/**
	 * tells currentMoney to add a set of cash denominations to its own set of cash denominations
	 *
	 *
	 */
	public void acceptDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.acceptDenominations(denominations);
	}
	
	/**
	 * tells currentMoney to replace its set of denominations with another set of denominations
	 *
	 *
	 */
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.setDenominations(denominations);
	}
	



	public VM_Slot[] getSlotsCopy()					// added
	{
		VM_Slot[] slotsCopy = new VM_Slot[slots.length];
		for (int i = 0; i < slots.length; i++) {
			slotsCopy[i] = new VM_Slot(slots[i]);  // using the copy constructor
		}
		return slotsCopy;
	}
	/**
	 * getter, returns VM's slot array
	 *
	 *
	 */
	public VM_Slot[] getSlots() {
		return slots;
	}

	/**
	 * getter, returns VM_Slot based on index
	 *
	 *
	 */
	public VM_Slot getSlot(int ind) {	// added
		if(ind >= 0)
			return slots[ind];
		return null;
	}

	/**
	 * deducts denominations from a duplicate of the current set of denominations, in order to meet a specified change amount
	 *
	 * @return true if deduction leads exactly to zero, false otherwise
	 *
	 */
	private boolean deductChange(double amt, LinkedHashMap<String, Integer> duplicate)
	{
		amt = Math.round(amt*100)/100.0;
		System.out.println("Change Due in machine: " + amt + " PHP");
		
		while(amt != 0)
		{
			if(amt >= 1000.0 && duplicate.get("One Thousand Bill") > 0) {
				duplicate.put("One Thousand Bill", duplicate.get("One Thousand Bill") - 1);
				amt -= 1000;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 500.0 && duplicate.get("Five Hundred Bill") > 0) {
				duplicate.put("Five Hundred Bill", duplicate.get("Five Hundred Bill") - 1);
				amt-= 500;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 200.0 && duplicate.get("Two Hundred Bill") > 0) 
			{
				duplicate.put("Two Hundred Bill", duplicate.get("Two Hundred Bill") - 1);
				amt -= 200;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 100.0 && duplicate.get("One Hundred Bill") > 0) {
				duplicate.put("One Hundred Bill", duplicate.get("One Hundred Bill") - 1);
				amt -= 100;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 50.0 && duplicate.get("Fifty Bill") > 0) {
				duplicate.put("Fifty Bill", duplicate.get("Fifty Bill") - 1);
				amt -= 50;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Bill") > 0) {
				duplicate.put("Twenty Bill", duplicate.get("Twenty Bill") - 1);
				amt-= 20;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Coin") > 0) {
				duplicate.put("Twenty Coin", duplicate.get("Twenty Coin") - 1);
				amt -= 20;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 10.0 && duplicate.get("Ten Coin") > 0) {
				
				duplicate.put("Ten Coin", duplicate.get("Ten Coin") - 1);
				amt -= 10;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 5.0 && duplicate.get("Five Coin") > 0) {
				duplicate.put("Five Coin", duplicate.get("Five Coin") - 1);
				amt -= 5;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 1.0 && duplicate.get("One Coin") > 0) {
				duplicate.put("One Coin", duplicate.get("One Coin") - 1);
				amt -= 1;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 0.25 && duplicate.get("Twenty Five Cents") > 0) {
				duplicate.put("Twenty Five Cents", duplicate.get("Twenty Five Cents") - 1);
				amt -= 0.25;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 0.05 && duplicate.get("Five Cents") > 0) {
				duplicate.put("Five Cents", duplicate.get("Five Cents") - 1);
				amt -= 0.05;
				System.out.println("amt: " + amt);
			}
			else if(amt >= 0.01 && duplicate.get("One Cent") > 0) {
				duplicate.put("One Cent", duplicate.get("One Cent") - 1);
				amt -= 0.01;
				System.out.println("amt: " + amt);
			}
			else if(amt >= -0.000001907348633 && amt <= 0.000001907348633) {    /*  0.0.000001907348633 == (1/2)^19   */
 				break;
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if each slot of the vending machine is filled with an item. If not
	 * it will return false
	 * 
	 * @return true if all slots contain an item with more than 0 in stock, otherwise false
	 */
	public boolean isThisValid()
	{
		for(VM_Slot tempSlot : slots)
			if(tempSlot.getItem() == null || tempSlot.getSlotItemStock() == 0)
				return false;
		return true;
	}

	public void addToStockedInfo() // added
	{
		VM_StockedInfo stockInfo = new VM_StockedInfo(this);
		stockedInfos.add(stockInfo);
		recordCurrInd += 1;

	}

	public void displayAllStockInfo()	// added
	{
		int i;	// index for slot start
		boolean isThereNew;

		isThereNew = false;
		if(recordCurrInd > 0)
		{
			VM_StockedInfo tempStockInfo = stockedInfos.get(recordCurrInd-1);
			LinkedHashMap<VM_Slot, Integer> slotAndStock = tempStockInfo.getItemSlotsAndStock();
			i = 0;
			System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", " Item Name ", "Item Prev Stock ", "Items Sold", " Items in Stock", "Profit Collected");
			System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
			for(Map.Entry<VM_Slot, Integer> tempEntry : slotAndStock.entrySet())
			{
				if( getSlot(i) != null &&																	// Checks if there is no slot
					getSlot(i).getItem() != null && 														// Checks if the slot is empty
				   tempEntry.getKey().getSlotItemName().equalsIgnoreCase(getSlot(i).getSlotItemName()))		//Compares if the original item is equal to the new item
				{

					System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", tempEntry.getKey().getSlotItemName(), tempEntry.getValue()+ "",
																						 (tempEntry.getKey().getSlotItemStock() - getSlot(i).getSlotItemStock()) + "", getSlot(i).getSlotItemStock(),
																						"+" + "Php " + (getSlot(i).getSlotItemSold()*getSlot(i).getItem().getItemPrice() -
																										tempEntry.getKey().getSlotItemSold()*tempEntry.getKey().getItem().getItemPrice()) );
					System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
				}
				
				else if(getSlot(i) != null &&																	// Checks if there is no slot
						getSlot(i).getItem() != null && 														// Checks if the slot is empty
						!tempEntry.getKey().getSlotItemName().equalsIgnoreCase(getSlot(i).getSlotItemName()))	//Compares if the original item is equal to the new item
					isThereNew = true;
				i++;
			}
			if(isThereNew)
			{
				System.out.println();
				System.out.println("NEWLY ADDED");
				i = 0;
				//checks new items

				System.out.printf("\t| %20s | %20s | %11s  |\n", " Item Name ", " Items in Stock", "Profit Collected");
				for(Map.Entry<VM_Slot, Integer> tempEntry : slotAndStock.entrySet())
				{
					if(getSlot(i) != null &&
					getSlot(i).getItem() != null && 															// Checks if the slot is empty
					!tempEntry.getKey().getSlotItemName().equalsIgnoreCase(getSlot(i).getSlotItemName()))		//Compares if the original item is equal to the new item
					{

						System.out.printf("\t| %20s | %20s | %11s  |\n", tempEntry.getKey().getSlotItemName(), tempEntry.getValue()+ "",
																			tempEntry.getKey().getSlotItemSold() + "", getSlot(i).getSlotItemStock(),
																			"+" + getSlot(i).getSlotItemStock()*getSlot(i).getItem().getItemPrice());
						System.out.println("        |______________________|______________________|_____________|______________________|______________________|");

					}
					i++;
				}	

			}

			
		}
		
	}




	private VM_Slot[] slots;
	private Money currentMoney;
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
	//private ArrayList<VM_StockedInfo> inventoryRecords = new ArrayList<VM_StockedInfo>();
	private int recordCurrInd;
	private static final int MIN_SLOTS = 8;
	private static final int MIN_ITEMS = 10;

	ArrayList<VM_StockedInfo> stockedInfos; // added
}