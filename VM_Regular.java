import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.InputMismatchException;

/** This class represents a Regular Vending Machine
  * and its methods and attributes
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Regular {
	
	/**
	 * Creates VM_Regular object and inititializes the array of slots, the array lists of order history and stock information
	 *
	 * @param nOfSlots the maximum number of slots that VM slots will be allowed to contain
	 * @param itemMax the maximum number of items that VM slots will be allowed to contain
	 */
	public VM_Regular( int nOfSlots, int item_max) {
		if(nOfSlots >= 8)
			slots = new VM_Slot[nOfSlots];
		else
			slots = new VM_Slot[MIN_SLOTS];
		
		for (int i = 0; i < nOfSlots; i++)
		{
			slots[i] = new VM_Slot(item_max);
		}
		currentMoney = new Money();
		
		orderHistory = new ArrayList<Order>();
		stockedInfos = new ArrayList<VM_StockedInfo>();
		recordCurrInd = 0;
	}


	/**
	 * Adds more of a specified item to the specified slot
	 *
	 * @param givenItem the item to be added to the specified slot
	 * @param qty the number indicating how many pieces of the specified item should be added to the specified slot
	 * @param i the array index of the specified slot
	 */
	public void addItemStock(VM_Item givenItem, int qty, int i)
	{
		slots[i].addItemStock(givenItem, qty);
	}
	
	/**
	 * Adds more of a specified item to the appropriate slot
	 *
	 * @param itemName the name of the item to be added
	 * @param qty the number indicating how many pieces of the specified item should be added
	 */
	public void addItemStock(String itemName, int qty)
	{
		int i;
		for(i = 0; i < slots.length; i++)
			if(slots[i].getSlotItemName().equalsIgnoreCase(itemName)) {
				if(slots[i].addItemStock(qty))
					recordCurrInd++;
				break;
			}
			
		if(i >= slots.length)
			System.out.println("Slot Name Not Found -------------");
	}
	
	/**
	 * Adds more of a specified item to the appropriate slot
	 *
	 * @param s the name of the item to be added
	 * @param i the index of the slot that will receive the additional items
	 * @param qty the number indicating how many pieces of the specified item should be added
	 */
	public void addItemStock(String s, int i, int qty)
	{
		if( s.equalsIgnoreCase("Cheese") )
			addItemStock(new Cheese("Cheese", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Cocoa") )
			addItemStock(new Cocoa("Cocoa", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Cream") )
			addItemStock(new Cream("Cream", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Egg") )
			addItemStock(new Egg("Egg", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Kangkong") )
			addItemStock(new Kangkong("Kangkong", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Lemon") ) 
			addItemStock(new Lemon("Lemon", 20.00, 42), qty, i); // delete
							
		else if( s.equalsIgnoreCase("Milk") )
			addItemStock(new Milk("Milk", 27.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Powder") )
			addItemStock(new Powder("Powder", 20.00, 42), qty, i); // delete
							
		else if( s.equalsIgnoreCase("Salt") )
			addItemStock(new Salt("Salt", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Sugar") )
			addItemStock(new Sugar("Sugar", 20.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Chicken") )
			addItemStock(new Chicken("Chicken", 20.00, 42), qty, i);// add
							
		else if( s.equalsIgnoreCase("BBQ") )
			addItemStock(new BBQ("BBQ", 20.00, 42), qty, i); // add
							
		else if( s.equalsIgnoreCase("Flour") )
			addItemStock(new Flour("Flour", 20.00, 42), qty, i); // add		
	}
	
	
	/**
	 * Provides for replacing the items in a slot, or for filling up a slot with a null name
	 *
	 * @param possibleItems	the list of all possible items in the program
	 */
	public void replaceItemStock(LinkedHashMap<String, Integer> possibleItems)
	{
		int i;
		int qty;
		Scanner sc = new Scanner(System.in);
		String input;
		
		try
		{
			System.out.println("Replace Stock of Slot No: ");
			i = sc.nextInt();
			i--;
			
			if(i >= 0 && i < slots.length)
			{
				try
				{
					System.out.println("Replace with this: ");
					input = sc.next();
					qty = sc.nextInt();
					if(possibleItems.get(input.toUpperCase()) != null && qty > 0)
						addItemStock(input, i, qty);
					else
						System.out.println("-ERROR: NON-EXISTENT CLASS/INVALID QUANTITY");
				}
				catch (InputMismatchException e)
				{
					System.out.println("-ERROR: NON-INTEGER INPUT");
				}
			}
			else
				System.out.println("-ERROR: NON-INTEGER INPUT");
		}
		catch (InputMismatchException e)
		{
			System.out.println("-ERROR: SLOT NUMBER OUT OF BOUNDS");
		}
		
		sc = null;
	}
	

	/**
	 * Looks for a slot associated with the specified item name, and "tells" that slot to "sell" a specified number of its item. Repeats for the other items in the order
	 * Has no input validation. Use hasEnoughStock(), deductChange(), and other methods to validate input
	 *
	 * @param order the list of items to be released from the VM, including how many of each should be released
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
	 * Checks whether the machine has sufficient stock for all the items in the order
	 *
	 * @param order the list of items to be released from the VM, including how many of each should be released
	 *
	 * @return true if VM's stock contains all required items, false otherwise
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
	 * Computes the total cost of an order
	 *
	 * @param order the list of items to be released from the VM, including how many of each should be released
	 *
	 * @return double the total cost of an order
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
	 * @param duplicate		a duplicate set of the VM's current denominations	 
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
		String input;
		double denom;
		int qty;
		int slotNum;
		boolean orderConfirmed = true; // intially true
		
		/* order is made blank */
		order = new Order();
		
		
		/* display VM's initial stock */
		displayAllItems();
		
		System.out.println();
		do
		{
			System.out.print("What would you like to order?\n>> ");
			input = sc.next();
				
			if( !input.equalsIgnoreCase("Y") )
			{
				try
				{
					qty = sc.nextInt();
					slotNum = Integer.parseInt(input);
					if( slotNum >= 1 && slotNum <= slots.length )
						if(order.addOrder(slots[slotNum-1], qty))
							System.out.println("-ADDED TO ORDER");
						else
						{
							order.getPendingOrder().remove(slots[slotNum-1]);
							System.out.println("-ERROR: ORDERED ITEM NOT IN SUFFICENT STOCK/ITEM NAME DOES NOT MATCH. ENTER A DIFF. ITEM/QUANTITY");
						}
					else
						System.out.println("-ERROR: SLOT NUM OUT OF BOUNDS");
				}
				catch (InputMismatchException e)
				{
					System.out.println("-ERROR: NON-INTEGER INPUT");
				}
				catch(NumberFormatException e)
				{
					System.out.println("-ERROR: NOT PARSABLE TO INT");
				}
			}
		} while ( !input.equalsIgnoreCase("Y") );
		
		/* payment */
		System.out.println();
		do
		{
			System.out.print("Insert payment\n>> ");
			input = sc.next();
			
			if( !input.equalsIgnoreCase("Y") ) {
				try
				{
					qty = sc.nextInt();
					denom = Double.parseDouble(input);
					
					if( Money.getValToStr().get(denom) != null )
						payment.put(Money.getValToStr().get(denom), qty);
					else
						System.out.println("-ERROR: DENOMINATION DOES NOT EXIST");	
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
				catch (InputMismatchException e)
				{
					e.printStackTrace();
				}	
			}
		} while ( !input.equalsIgnoreCase("Y") );
		
		
		/* duplicating denomination hashmap of VM, while setting change denominations to zero */
		for(String s : getDenominations().keySet()) {
			duplicate.put(s, getDenominations().get(s));
			change.put(s, 0);
		}
		
		cashReservesTotal = getTotalOfMoneyReserves();
		System.out.println("\nCash Reserves Total: " + FORMAT.format(cashReservesTotal) + " PHP");
		
		/* calculating payment total */
		for(String s : payment.keySet())
			paymentTotal += payment.get(s)*Money.getStrToVal().get(s);
		
		/* calculating order total */
		for(String s : order.getPendingOrder().keySet()) {
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) )
					break;
			orderTotal += order.getPendingOrder().get(s)*slots[i].getItem().getItemPrice();
		}
		order.setTotalCost(orderTotal);
		
		/* calculating calorie total */
		for(String s : order.getPendingOrder().keySet())
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) ) {
					calorieTotal = order.getPendingOrder().get(s)*slots[i].getItem().getItemCalories();
					break;
				}
		
		
		/* calculating total of cash reserves in the machine */
		cashReservesTotal = getTotalOfMoneyReserves();
		
		/* calculating change due */
		changeDue = paymentTotal - orderTotal;
		
		/* display total of order, total of payment received, and change due */
		System.out.println();
		System.out.println("Order Total: " + orderTotal + " PHP");
		System.out.println("Payment Received: " + paymentTotal + " PHP");
		System.out.println("Change Due: " + changeDue + " PHP");
		System.out.println("Calorie Total: " + calorieTotal + " kCal\n");
		
		
		
		/* asks user to confirm order */
		System.out.print("Continue with order? : ");
		input = sc.next();
		if( input.equalsIgnoreCase("Y") )
			orderConfirmed = true;
		else
			orderConfirmed = false;
		
		
		
		
		/* checks whether transaction is valid */
		
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
		if( changeDue >= 0 && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("-ERROR: CANNOT RETURN CHANGE, INSERT EXACT AMOUNT");
		}
		
		/* decides whether to proceed with transaction or not */
		if( transactionIsValid && orderConfirmed )
		{
			System.out.println("\nTRANSACTION PROCEEDS--------------------------");
			releaseStock(order.getPendingOrder());
			for( String s : change.keySet() )
				change.put( s, getDenominations().get(s) - duplicate.get(s) );
			setDenominations(duplicate);
			acceptDenominations(payment);
			for( String s : payment.keySet() )
				payment.put( s, 0 );
			orderHistory.add(order);
		}
		else
		{
			if( !orderConfirmed )
				System.out.println("\nTRANSACTION DISCONTINUED------------------------");
			else if( !transactionIsValid )
				System.out.println("\nTRANSACTION FAILS------------------------");
			for( String s : payment.keySet() )
				change.put( s, payment.get(s) );
			for( String s : payment.keySet() )
				payment.put( s, 0 );
		}
		
		
		
		displayAllItems();
		System.out.println();
		
		
		/* recalculating total of cash reserves in the machine */
		cashReservesTotal = getTotalOfMoneyReserves();
		
		
		/* clearing payment tray */
		for( String s : payment.keySet() )
			payment.put(s, 0);
		
		/* display change */
		System.out.println();
		System.out.println("CHANGE RETURNED:");
		for( Map.Entry m : change.entrySet() )
			System.out.println(" " + m.getValue() + " " + m.getKey());
		System.out.println("\n");
		
		sc = null;
	}
	
	/**
	 * getter, tells currentMoney to compute the total of its cash reserves
	 *
	 * @return the total of all cash reserves in the VM's Money attribute
	 */
	public double getTotalOfMoneyReserves() {
		return currentMoney.getTotalMoney();
	}
	
	
	/**
	 * Replaces the current record of order history with a blank new one
	 *
	 */
	public void emptyOrderHistory() {
		orderHistory = new ArrayList<Order>();
	}
	
	
	/**
	 * Displays item descriptions on terminal
	 *
	 *
	 */
	public void displayAllItems() {
		int i;
		System.out.println();
		for(i = 0; i < slots.length; i++)
			slots[i].displayAllItems();
	}
	
	/**
	 * Another method for adding cash into currentMoney's cash reserves
	 * 
	 * @param givenValue the double representation of the denomination
	 * @param amt the number indicating how many pieces of the specified denomination should be added
	 *
	 */
	public void addBillsOrCoins(double givenValue, int amt) {
		currentMoney.addBillsOrCoins(givenValue, amt);
	}
	
	/**
	 * getter, tells VM's currentMoney to return its LinkedHashMap of cash denominations
	 *
	 * @return the LinkedHashMap representing VM's current cash reserves
	 */
	public LinkedHashMap<String, Integer> getDenominations() {
		return currentMoney.getDenominations();
	}
	
	/**
	 * tells currentMoney to add a set of cash denominations to its own set of cash denominations
	 *
	 * @param denominations the list of coins/bills to add to the cash reserves, and how many of each should be added
	 */
	public void acceptDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.acceptDenominations(denominations);
	}
	
	/**
	 * tells currentMoney to replace its own set of denominations with another set of denominations
	 *
	 * @param denominations the list of coins/bills that will overwrite the VM's own cash reserves, and how many of each coin/bill should be added
	 */
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.setDenominations(denominations);
	}
	
	/**
	 * getter, returns VM's slot array
	 *
	 * @return array of the VM's slots
	 */
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	/**
	 * getter, returns VM_Slot based on index
	 *
	 * @param the index of the slot to be returned
	 *
	 * @return the desired slot
	 */
	public VM_Slot getSlot(int ind) {
		if(ind >= 0)
			return slots[ind];
		return null;
	}

	/**
	 * Iteratively deducts coins/bills from a duplicate of the current set of coins/bills, in order to meet a specified change amount
	 *
	 * @param amt the amount of change that must be met by the VM's cash reserves
	 * @param duplicate a duplicate of the VM's cash reserves
	 *
	 * @return true if deduction leads to zero, false otherwise
	 */
	private boolean deductChange(double amt, LinkedHashMap<String, Integer> duplicate)
	{
		amt = Math.round(amt*100)/100.0;
		//System.out.println("Change Due in machine: " + amt + " PHP");
		
		while(amt != 0)
		{
			if(amt >= 1000.0 && duplicate.get("One Thousand Bill") > 0) {
				duplicate.put("One Thousand Bill", duplicate.get("One Thousand Bill") - 1);
				amt -= 1000;
			}
			else if(amt >= 500.0 && duplicate.get("Five Hundred Bill") > 0) {
				duplicate.put("Five Hundred Bill", duplicate.get("Five Hundred Bill") - 1);
				amt-= 500;
			}
			else if(amt >= 200.0 && duplicate.get("Two Hundred Bill") > 0) 
			{
				duplicate.put("Two Hundred Bill", duplicate.get("Two Hundred Bill") - 1);
				amt -= 200;
			}
			else if(amt >= 100.0 && duplicate.get("One Hundred Bill") > 0) {
				duplicate.put("One Hundred Bill", duplicate.get("One Hundred Bill") - 1);
				amt -= 100;
			}
			else if(amt >= 50.0 && duplicate.get("Fifty Bill") > 0) {
				duplicate.put("Fifty Bill", duplicate.get("Fifty Bill") - 1);
				amt -= 50;
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Bill") > 0) {
				duplicate.put("Twenty Bill", duplicate.get("Twenty Bill") - 1);
				amt-= 20;
			}
			else if(amt >= 20.0 && duplicate.get("Twenty Coin") > 0) {
				duplicate.put("Twenty Coin", duplicate.get("Twenty Coin") - 1);
				amt -= 20;
			}
			else if(amt >= 10.0 && duplicate.get("Ten Coin") > 0) {
				
				duplicate.put("Ten Coin", duplicate.get("Ten Coin") - 1);
				amt -= 10;
			}
			else if(amt >= 5.0 && duplicate.get("Five Coin") > 0) {
				duplicate.put("Five Coin", duplicate.get("Five Coin") - 1);
				amt -= 5;
			}
			else if(amt >= 1.0 && duplicate.get("One Coin") > 0) {
				duplicate.put("One Coin", duplicate.get("One Coin") - 1);
				amt -= 1;
			}
			else if(amt >= 0.25 && duplicate.get("Twenty Five Cents") > 0) {
				duplicate.put("Twenty Five Cents", duplicate.get("Twenty Five Cents") - 1);
				amt -= 0.25;
			}
			else if(amt >= 0.05 && duplicate.get("Five Cents") > 0) {
				duplicate.put("Five Cents", duplicate.get("Five Cents") - 1);
				amt -= 0.05;
			}
			else if(amt >= 0.01 && duplicate.get("One Cent") > 0) {
				duplicate.put("One Cent", duplicate.get("One Cent") - 1);
				amt -= 0.01;
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
	 * Checks whether each slot of the vending machine is filled with an item
	 * 
	 * @return true if all slots contain more than zero stock, false otherwise
	 */
	public boolean isThisValid()
	{
		for(VM_Slot tempSlot : slots)
			if(tempSlot.getItem() == null || tempSlot.getSlotItemStock() == 0)
				return false;
		return true;
	}
	
	/**
	 * Provides for manual replenishing of the VM's cash reserves
	 *
	 *
	 */
	public void replenishDenominations() {
		Scanner sc = new Scanner(System.in);
		String input;
		double denom;
		int qty;
		//inserting payment
		do
		{
			System.out.print("Replenish Denominations: ");
			input = sc.next();
			
			if( !input.equalsIgnoreCase("Y") )
			{
				try
				{
					qty = sc.nextInt();
					denom = Double.parseDouble(input);
						
					if( Money.getValToStr().get(denom) != null )
						addBillsOrCoins(denom, qty);
					else
						System.out.println("-ERROR: DENOMINATION DOES NOT EXIST");	
					}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
				catch (InputMismatchException e)
				{
					e.printStackTrace();
				}	
			}
		} while ( !input.equalsIgnoreCase("Y") );
		
		sc = null;
	}
	
	/**
	 * Provides for manual restocking of the VM's sellable items
	 *
	 *
	 */
	public void restockItems() {
		int j;
		int qty;
		Scanner sc = new Scanner(System.in);
		String input;
		int slotNum;
		boolean anItemIsRestocked = false; // initially false


		System.out.println("Restock item: ");
		input = sc.next();
		
		if(!input.equalsIgnoreCase("Y"))
		while(true)
		try
		{
			qty = sc.nextInt();
			slotNum = Integer.parseInt(input);
			if( slots[slotNum-1].getSlotItemName() != null ) {
				if ( !anItemIsRestocked ) {
						updateStockedInfos();
						recordCurrInd++;
						anItemIsRestocked = true;
				}
				slots[slotNum-1].addItemStock(qty);
			}
			else
				System.out.println("-ERROR: SLOT DOES NOT HOLD THIS ITEM. ENTER A DIFF. SLOT NUM");		
		}
		catch (InputMismatchException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
	
	
		
		sc = null;
	}
	
	/**
	 * Provides for repricing of the VM's items
	 *
	 *
	 */
	public void repriceItems() {
		int j;
		double amt;
		Scanner sc = new Scanner(System.in);
		String input;
		boolean itemIsRepriced = false; // initially false
		int slotNum;
		
		
		System.out.println("Reprice item: ");
		input = sc.next();
			
		try
		{
			amt = sc.nextDouble();
			slotNum = Integer.parseInt(input);
			if(slotNum >= 1 && slotNum <= slots.length)
				if( slots[slotNum-1].getSlotItemName() != null ) {
					if( !itemIsRepriced ) {
					updateStockedInfos();
					recordCurrInd++;
					itemIsRepriced = true;	
					}
					slots[slotNum-1].repriceItem(amt);
				}
				else
					System.out.println("-ERROR: SLOT DOES NOT HOLD THIS ITEM. ENTER A DIFF. SLOT NUM");		
			else
				System.out.println("-ERROR: SLOT NUM OUT OF BOUNDS");
		}	
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (InputMismatchException e)
		{
			e.printStackTrace();
		}
		
		sc = null;
	}
	
	/**
	 * Display's the VM's transaction history (not including failed transactions)
	 * starting from the last restocking or repricing
	 *
	 *
	 */
	public void displayOrderHistory() {
		int i;
		
		for(i = 0; i < orderHistory.size(); i++) {
			System.out.println("\nORDER NO. " + (i+1));
			for(String s : orderHistory.get(i).getPendingOrder().keySet())
				System.out.println(
					orderHistory.get(i).getTotalCost() +
					" PHP " +
					orderHistory.get(i).getPendingOrder().get(s) +
					" pc(s). " +
					s +
					"\n" );
		}
	}
	
	
	/**
	 * Display's the VM's latest restocking history (not including initial stocking at creation)
	 *
	 *
	 */
	public void displayAllStockInfo()
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
																						 /*(tempEntry.getKey().getSlotItemStock() - getSlot(i).getSlotItemStock())*/ getSlot(i).getSlotItemSold() + "", getSlot(i).getSlotItemStock(),
																						"+" + "Php " + /*(getSlot(i).getSlotItemSold()*getSlot(i).getItem().getItemPrice() -
																										tempEntry.getKey().getSlotItemSold()*tempEntry.getKey().getItem().getItemPrice())*/ getSlot(i).getItem().getItemPrice()*getSlot(i).getSlotItemSold());
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
	
	/**
	 * Generates a copy of the VM's slot array
	 *
	 * @return a copy of the VM's slot array
	 */
	public VM_Slot[] getSlotsCopy()
	{
		VM_Slot[] slotsCopy = new VM_Slot[slots.length];
		for (int i = 0; i < slots.length; i++) {
			slotsCopy[i] = new VM_Slot(slots[i]);  // using the copy constructor 
		}
		return slotsCopy;
	}
	
	/**
	 * Creates a copy of the current VM's slots and items and saves them in a new VM_StockedInfo object.
	 * Saves that VM_StockedInfo object as the next entry in the stockedInfos arraylist
	 *
	 */
	public void updateStockedInfos() {
		int i;
		
		stockedInfos.add(new VM_StockedInfo(this));
		
		for(i = 0; i < slots.length; i++)
			slots[i].setSlotItemSold(0); // resets no. of sold items per slot back to
	}
	
	/**
	 * getter for MIN_SLOTS
	 *
	 * @return minimum number of slots the VM must hold
	 */
	public static int getMinSLOTS()
	{
		return MIN_SLOTS;
	}
	
	/**
	 * getter for MAX_SLOTS
	 *
	 * @return maximum number of items each slot can hold
	 */
	public static int getMaxITEMS()
	{
		return MAX_ITEMS;
	}
	
	/**
	 * getter for VM's name
	 *
	 * @return the VM's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * setter for VM's name
	 *
	 * @param the VM's new name
	 */
	public void setName(String name)
	{
		if(name != null && name.length() >= 1)
			this.name = name;
		else
			this.name = new String("defaultName");
	}
	
	
	/** the array of VM slots */
	private VM_Slot[] slots;
	/** the VM's name */
	private String name;
	/** the VM's cash reserve */
	private Money currentMoney;
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
	/** the list of inventory records, with each record being taken right before each restocking or repricing */
	private ArrayList<VM_StockedInfo> stockedInfos;
	/** the list of successful transaction records starting from the last restocking */
	private ArrayList<Order> orderHistory;
	/** the index right after the tail of stockedInfos */ 
	private int recordCurrInd;
	/** the minimum number of slots a machine must hold */
	private static final int MIN_SLOTS = 8;
	/** the maximum number of items a slot can hold */
	private static final int MAX_ITEMS = 10;
}