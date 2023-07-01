import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;


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
	 * Looks for a slot associated with the specified item name,
	 * and "tells" that slot to "sell" a specified number of its item.
	 * Repeats for the other items in the order
	 *
	 * Has no input validation.
	 * Use hasEnoughStock(), deductChange(), and other methods to be validated by SellingOperator
	 *
	 * @param order item containing the list of items to be released from the VM,
	 * 				including how many of each should be released
	 */
	public void releaseStock(Order order) {
		int i;
		LinkedHashMap<String, Integer> orders;
		
		orders = order.getPendingOrder();

		for( String s : orders.keySet() )
			for(i = 0; i < slots.length; i++)
				if( s.equals( slots[i].getSlotItemName() ) ) {
					slots[i].releaseStock( orders.get(s) );
					break;
				}
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
	 * This method gets currentMoney containing denominations of this vending machine
	 * 
	 * @return the money tray of this VM
	 */
	public Money getCurrentMoney() {
		return currentMoney;
	}


	/**
	 * This method gets the order history of the VM.
	 * 
	 * @return the array list instance of order history of the Vending machine
	 */
	public ArrayList<Order> getOrderHistory() {
		return orderHistory;
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
	 * This method Gets the VM's name
	 *
	 * @return the VM's name
	 */
	public String getName()
	{
		return name;
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

}