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
	 * Creates VM_Regular object and inititializes the array of slots,
	 * the array list containing order history, and stock information
	 *
	 * @param name the name of the vending machine
	 * @param nOfSlots the number of slots in VM
	 * @param item_max the maximum capacity of each slot in VM
	 */
	public VM_Regular(String name, 
					  int nOfSlots, 
					  int item_max) {
		this.name = name;
		if(nOfSlots >= 8)
			slots = new VM_Slot[nOfSlots];
		else
			slots = new VM_Slot[MIN_SLOTS];
		
		for (int i = 0; i < nOfSlots; i++)
		{
			if(item_max >= 10)
				slots[i] = new VM_Slot(item_max);
			else
				slots[i] = new VM_Slot(MAX_ITEMS);
		}
		currentMoney = new Money();
		
		orderHistory = new ArrayList<Order>();
		stockedInfos = new ArrayList<VM_StockedInfo>();
		recordCurrInd = 0;

	}


	/**
	 * Adds more of a certain item to slot specified by index
	 *
	 * @param givenItem the item to be added to the specified slot
	 * @param qty the number indicating how many pieces
				  of the specified item should be added to the specified slot
	 * @param i the index of the specified slot in the slots array
	 */
	public void addItemStock(VM_Item givenItem, 
							 int qty, 
							 int i)
	{
		slots[i].addItemStock(givenItem, qty);
	}
	

	
	/**
	 * Adds more of a specified item to the specified slot
	 *
	 * @param s the name of the item to be added
	 * @param i the index of the slot that will receive the additional items
	 * @param qty the number indicating how many pieces of the specified item should be added
	 */
	public void addItemStock(String s, 
							 int i, 
							 int qty)
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
							
		else if( s.equalsIgnoreCase("Cornstarch") ) 
			addItemStock(new Cornstarch("Cornstarch", 20.00, 42), qty, i); // delete
							
		else if( s.equalsIgnoreCase("Milk") )
			addItemStock(new Milk("Milk", 27.00, 42), qty, i);
							
		else if( s.equalsIgnoreCase("Tofu") )
			addItemStock(new Tofu("Tofu", 20.00, 42), qty, i); // delete
							
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
	 * Looks for a slot associated with the specified item name,
	 * and "tells" that slot to "sell" a specified number of its item.
	 * Repeats for the other items in the order
	 *
	 * Has no input validation.
	 * Use hasEnoughStock(), deductChange(), and other methods to validate input
	 *
	 * @param order the list of items to be released from the VM,
	 * 				including how many of each should be released
	 */
	public void releaseStock(LinkedHashMap<String, Integer> order) {
		int i;
		for( String s : order.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) ) {
					slots[i].releaseStock( order.get(s) );
					break;
				}
	}
	
	/**
	 * Checks whether the machine has sufficient stock
	 * for all the items in the order
	 *
	 * @param order the list of items to be released from the VM,
					including how many of each should be released
	 * @return true if VM's stock contains all required items, false otherwise
	 */
	public boolean hasEnoughStock(LinkedHashMap<String, Integer> order) {
		int i;
		
		boolean stockHasRequiredQuantities = true; // initially true
		for( String s : order.keySet() )
		{
			for(i = 0; i < slots.length; i++)
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) )
					/* if the current slot does not hold the required quantity of its item */
					if( !( slots[i].hasEnoughStock( order.get(s) ) ) )
					{
						stockHasRequiredQuantities = false;
						break;
					}
		}		
		
		return stockHasRequiredQuantities;
	}
	
	/**
	 * Computes the total cost of an order
	 *
	 * @param order the list of items to be released from the VM,
					including how many of each should be released
	 * @return double the total cost of an order
	 */
	public double computeTotalCost(LinkedHashMap<String, Integer> order) {
		int i;
		double totalCost = 0.0;
		
		for( String s : order.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) )
				{
					totalCost += slots[i].computePartialCost( order.get(s) );
					break;
				}

		return totalCost;
	}
	
	/**
	 * Takes user's order and accepts their payment,
	 * validates inputs,
	 * and decides whether to proceed with transaction or not.
	 * 
	 * Uses text-based interface.
	 *
	 * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param order			the order object, contains the user's order
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
	 */
	public void sellingOperation(
		LinkedHashMap<String, Integer> duplicate,
		LinkedHashMap<String, Integer> payment,
		LinkedHashMap<String, Integer> change,
		Order order )
	{
		boolean transactionIsValid = true; // initially true
		boolean changeIsPossible;
		Scanner sc = new Scanner(System.in);
		double paymentTotal = 0;
		double orderTotal = 0;
		double cashReservesTotal = 0;
		double changeDue = 0;
		double denom;
		int calorieTotal = 0;
		int i;
		int qty;
		int slotNum;
		int currAmt;


		String input;
		String inputQty;


		boolean orderConfirmed = true; // intially true
	


		// order is made blank
		order = new Order();
		
		
		// display VM's initial stock
		displayAllItems();
		
		
		// setting order
		System.out.println();



		/* order request */
		System.out.println();

	

		while(true)
		try
		{
			System.out.print("What would you like to order (\033[1;33mEnter 'Y' to confirm/finish\033[0m)? \033[1;32m<slot num> <qty>\033[0m\n>> ");
			input = sc.next();
			if( input.equalsIgnoreCase("Y") )
				break;
			inputQty = sc.next();
					
				
			slotNum = Integer.parseInt(input);
			qty = Integer.parseInt(inputQty);
				
			if( slotNum >= 1 && slotNum <= slots.length )
				if(order.addOrder(slots[slotNum-1], qty))
					System.out.println("\033[1;32m-ADDED TO ORDER\033[0m");
				else
				{
					order.getPendingOrder().remove(slots[slotNum-1].getSlotItemName());
					System.out.println("\033[1;38;5;202m-ERROR: ORDERED ITEM NOT IN SUFFICENT STOCK/ITEM NAME DOES NOT MATCH. ENTER A DIFF. ITEM/QUANTITY\033[0m");
				}
			else
				System.out.println("\033[1;38;5;202m-ERROR: SLOT NUM OUT OF BOUNDS\033[0m");
		}
		catch(NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: NOT PARSABLE TO INT, Please enter slot number\033[0m");
		}
		
		/* payment */
		System.out.println();
		while(true)
		try
		{
			System.out.print("Insert payment (\033[1;33mEnter 'Y' to confirm/finish\033[0m): \033[1;32m<bill/coin num> <qty>\033[0m\n>> ");
			input = sc.next();
			if( input.equalsIgnoreCase("Y") )
				break;
			inputQty = sc.next();
			
			denom = Double.parseDouble(input);
			qty = Integer.parseInt(inputQty);
					
			if( Money.getValToStr().get(denom) != null )
				payment.put(Money.getValToStr().get(denom), qty);
			else
				System.out.println("\033[1;38;5;202m-ERROR: DENOMINATION DOES NOT EXIST\033[0m");	
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		
		/* duplicating denomination hashmap of VM, while setting change denominations to zero */
		for(String s : currentMoney.getDenominations().keySet()) {
			duplicate.put(s, currentMoney.getDenominations().get(s));
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
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) )
					break;
			orderTotal += order.getPendingOrder().get(s)*slots[i].getItem().getItemPrice();
		}
		order.setTotalCost(orderTotal);
		
		/* calculating calorie total */
		for(String s : order.getPendingOrder().keySet())
			for(i = 0; i < slots.length; i++)
				if( s.equalsIgnoreCase( slots[i].getSlotItemName() ) ) {
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
		System.out.print("Continue with order (\033[1;33mEnter Y to confirm, any other key to discontinue order\033[0m)? : ");
		input = sc.next();
		if( input.equalsIgnoreCase("Y") && orderTotal != 0 )
			orderConfirmed = true;
		else
			orderConfirmed = false;
		
		
		
		
		/* checks whether transaction is valid */
		System.out.println();

		
		/*checks whether a set of denominations can be released to meet a certain change amount */
		changeIsPossible = deductChange(changeDue, duplicate);
		
		/* transaction validation */
		if( !hasEnoughStock(order.getPendingOrder()) ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202mm-ERROR: INSUFFICIENT STOCK\033[0m");
		}
		if( paymentTotal < orderTotal ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: INSUFFICIENT PAYMENT\033[0m");
		}
		if ( cashReservesTotal < orderTotal && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: NOT ENOUGH MONEY RESERVES\033[0m");
		}
		
		if( changeDue >= 0 && !changeIsPossible ) {
			transactionIsValid = false;
			System.out.println("\033[1;38;5;202m-ERROR: CANNOT RETURN CHANGE, INSERT EXACT AMOUNT\033[0m");
		}
		
		
		/* decides whether to proceed with transaction or not */
		if( transactionIsValid && orderConfirmed )
		{
			System.out.println("\n\033[1;32mTRANSACTION PROCEEDS--------------------------\033[0m");

			for(String itemName : order.getPendingOrder().keySet())
				for(i = 0; i < slots.length; i++)
					if( itemName.equalsIgnoreCase( slots[i].getSlotItemName() ) )
					{
						currAmt = order.getPendingOrder().get(itemName);

						// Check max amount should be dispensed if order was greater than 10
						if(currAmt > slots[i].getMAX())
							System.out.println("Dispensing: " +  slots[i].getMAX() + " \033[1;33m" + slots[i].getSlotItemName() + "\033[0m");
						// Dispenses the item amount wished
						else
							System.out.println("Dispensing: " +  currAmt + " \033[1;33m" + slots[i].getSlotItemName() + "\033[0m");
					}
						
			releaseStock(order.getPendingOrder());
	

			/* computes for the change tray values based on the original cash reserves and the subtracted cash reserve duplicate */
			for( String s : change.keySet() )
				change.put( s, currentMoney.getDenominations().get(s) - duplicate.get(s) );
			
			/* updates the cash reserves */
			setDenominations(duplicate);
			acceptDenominations(payment);
			
			/* sets payment tray back to zero */
			for( String s : payment.keySet() )
				payment.put( s, 0 );
			orderHistory.add(order);
		}
		else
		{
			if( !orderConfirmed )
				System.out.println("\nTRANSACTION DISCONTINUED------------------------");
			else if( !transactionIsValid )
				System.out.println("\n\033[1;38;5;202mTRANSACTION FAILS------------------------\033[0m");
			/* returns payment to change tray */
			for( String s : payment.keySet() )
				change.put( s, payment.get(s) );
			/* sets payment tray back to 0 */
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
		for(Map.Entry<String, Integer> m : change.entrySet() )
		System.out.println(" " + m.getValue() + " " + m.getKey());
		System.out.println("\n");
		
		sc = null;
	}
	
	/**
	 * Tells currentMoney to compute and return the total of its cash reserves
	 *
	 * @return the total of all cash reserves in currentMoney
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
	 * Displays item descriptions on terminal, else displays that there is no items stocked
	 *
	 *
	 */
	public void displayAllItems() {
		int i;
		boolean isThereItem;
		System.out.println();
		System.out.println("Item Info: ");
		System.out.println();

		isThereItem = false;
		for(i = 0; i < slots.length; i++)
			if(slots[i] != null && slots[i].getItem() != null)
			{
				slots[i].displayAllItems();
				isThereItem = true;
			}
		if(!isThereItem)
			System.out.println("No item stock/label is available to display");
				
	}
	
	/**
	 * Identifies a certain denomination by its double representation,
	 * and adds to the VM a certain amount of that denomination.
	 * 
	 * @param givenValue the double representation of the denomination
	 * @param amt the number indicating how many pieces of the specified denomination should be added
	 *
	 */
	public void addBillsOrCoins(double givenValue, 
								int amt) {
		currentMoney.addBillsOrCoins(givenValue, amt);
	}

	
	/**
	 * Tells currentMoney to add a set of cash denominations to its own
	 *
	 * @param denominations the list of coins/bills to add to the cash reserves,
							and how many of each should be added
	 */
	public void acceptDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.acceptDenominations(denominations);
	}
	
	/**
	 * Tells currentMoney to replace its own set of denominations with another set of denominations
	 *
	 * @param denominations the list of coins/bills that will overwrite the VM's own cash reserves,
							and how many of each coin/bill should be added
	 */
	public void setDenominations(LinkedHashMap<String, Integer> denominations) {
		currentMoney.setDenominations(denominations);
	}
	
	/**
	 * Gets VM's slot array
	 *
	 * @return array of the VM's slots
	 */
	public VM_Slot[] getSlots() {
		return slots;
	}
	
	/**
	 * Gets a slot specified by its index
	 *
	 * @param ind the index of the slot to be returned
	 * @return the desired slot
	 */
	public VM_Slot getSlot(int ind) {
		if(ind >= 0 && ind < slots.length)
			return slots[ind];
		return null;
	}
	
	/**
	 * Gets currentMoney
	 * 
	 * @return the money tray of this VM
	 */
	public Money getCurrentMoney() {
		return currentMoney;
	}

	/**
	 * Iteratively deducts coins/bills from a duplicate of the current set of coins/bills,
	 * in order to meet a specified change amount
	 *
	 * @param amt the amount of change that must be met by the VM's cash reserves
	 * @param duplicate a duplicate of the VM's cash reserves
	 * @return true if deduction leads to zero or extremely close to zero, false otherwise
	 */
	private boolean deductChange(double amt, LinkedHashMap<String, Integer> duplicate)
	{

		amt = Math.round(amt*100)/100.0;

		// Loop repeats until it is exactly zero, indicating sufficient change
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
			// Case where the program arrives into undeterminable amt due to floating point addition, that could not ever lead to zero but close, exit loop
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
	 * Display's the VM's latest restocking history
	 * (not including initial stocking at creation)
	 *
	 *
	 */
	public void displayAllStockInfo()
	{

		double profit;
		String profitLabel;
		String denomination;
		int count;

		VM_StockedInfo tempStockInfo;
		VM_Slot tempSlot;
		LinkedHashMap<VM_Slot, Integer> slotAndStock;

		profit = 0;
		profitLabel = "";

		// Only triggers if the data is not empty
		if(recordCurrInd > 0 && !(stockedInfos.get(recordCurrInd-1).isEmptyData()))
		{
			tempStockInfo = stockedInfos.get(recordCurrInd-1);
			slotAndStock = tempStockInfo.getItemSlotsAndStock();
			
			
			System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", " Item Name ", "Item Prev Stock ", "Items Sold", " Items in Stock", "Profit Collected");
			System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
			for(Map.Entry<VM_Slot, Integer> tempEntry : slotAndStock.entrySet())
			{
				tempSlot = findSlot(tempEntry.getKey().getSlotItemName());
				if( tempSlot != null &&																	// Checks if there is no slot
					tempSlot.getItem() != null &&  														// Checks if the slot is empty
					tempEntry.getKey().getSlotItemName()	!= null &&									// Check if slot has a name
				   	tempEntry.getKey().getSlotItemName().equalsIgnoreCase(tempSlot.getSlotItemName()))		//Compares if the original item is equal to the new item
				{
					// Represents them in the order, name, prev stock, Sold, Current Stock, Profit
					System.out.printf("\t| %20s | %20s | %11s | %20s | %20s |\n", tempEntry.getKey().getSlotItemName(), tempEntry.getValue()+ "",				// Item name, stock previous
																						 tempSlot.getSlotItemSold() + "", tempSlot.getSlotItemStock() + "",				// Sold, stock of current
																						"+" + "Php " + FORMAT.format(tempSlot.getStoredProfit()));						// profit
					System.out.println("        |______________________|______________________|_____________|______________________|______________________|");
					
					// continuously add total profit
					profit += tempSlot.getStoredProfit();
				}


	


				
			}

			profitLabel = profit + "";
			if(profit != 0 && profitLabel.indexOf(".") != -1 && 			// Makes sure that the label has a decimal i.e. greater than 0
			profitLabel.substring(profitLabel.indexOf(".")).length() < 3)	// Checks if the string starting at '.' is less than ".0"
				profitLabel = profitLabel + "0";
			
			
			System.out.printf("                                                                                           |Profit: \033[1;32mPHP %10s\033[0m|\n", profitLabel);
			System.out.printf("Prev Money from prev stock: \033[1;32mPHP %.2f\033[0m\n", tempStockInfo.getMoney().getTotalMoney());


			// For each entry in the Stock info, get every denomination and count
			for(Map.Entry<String,Integer> tempEntry2 : tempStockInfo.getMoney().getDenominations().entrySet())
			{
				denomination = tempEntry2.getKey();
				count = tempEntry2.getValue();
				System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
			}
			System.out.println("_____________________________________________________________________________________________________");

			// Display differences only if they are not equal
			if(tempStockInfo.getMoney().getTotalMoney() != currentMoney.getTotalMoney())
			{
				// Display current money
				System.out.printf("Current money in stock: \033[1;32mPHP %.2f\033[0m\n", currentMoney.getTotalMoney());

				// get the string denomination and how many they are
				for(Map.Entry<String,Integer> tempEntry2 : currentMoney.getDenominations().entrySet())
				{
					denomination = tempEntry2.getKey();
					count = tempEntry2.getValue();
					System.out.println(denomination + ": \033[1;33m" + count + "\033[0m");
				}

				
			}


			
		}
		else
		{
			System.out.println("\033[1;38;5;202mThere are no stocked Info updated/ item stocks are empty!\033[0m");

			
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
			if(slots[i] != null && slots[i].getItem() != null)
				slotsCopy[i] = new VM_Slot(slots[i]);  // using the copy constructor C
		}
		return slotsCopy;
	}


	
	/**
	 * Gets MIN_SLOTS
	 *
	 * @return minimum number of slots the VM must hold
	 */
	public static int getMinSLOTS()
	{
		return MIN_SLOTS;
	}
	
	/**
	 * Gets MAX_SLOTS
	 *
	 * @return maximum no. of items that each slot can contain
	 */
	public static int getMaxITEMS()
	{
		return MAX_ITEMS;
	}
	
	/**
	 * Gets VM's name
	 *
	 * @return the VM's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets VM's name
	 *
	 * @param name the VM's new name
	 */
	public void setName(String name)
	{
		if(name != null && name.length() >= 1)
			this.name = name;
		else
			this.name = new String("defaultName");
	}

	/**
	 * Method increments the stock ind to be at a new index. Meaning
	 * The original previous will be overwritten with this current
	 */
	public void addStockInd()
	{
		stockedInfos.add(new VM_StockedInfo(this));
		recordCurrInd++;
	}
	
	

	
	/**
	 * This helper method returns a VM_Slot based on the parameters given
	 * 
	 * @param slotName name of the slot
	 * @return the VM_Slot object that the name pertains to
	 */
	private VM_Slot findSlot(String slotName)
	{
		int i;
		for(i = 0; i < slots.length; i++)
		{
			if(slots[i].getSlotItemName().equalsIgnoreCase(slotName))
				return slots[i];
		}
		return null;
	}
	
	/** the array of VM slots */
	private VM_Slot[] slots;
	/** the VM's name */
	private String name;
	/** the VM's cash reserve */
	private Money currentMoney;
	/** Format constant that would help format labels of each item prices or computations*/
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
	/** the prompt for user to use "Y" when they want to proceed to next section */
	private static final String USER_HELP = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
}