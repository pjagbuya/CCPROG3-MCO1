import java.util.Scanner;



/** The class VM_Slot represents a slot of the VM
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class VM_Slot {


    /**
     * This instructor Initializes a slot's item and capacity based on the given parameters.
     * Every slot can contain only one actual copy of the item it is set to hold
     * 
     * @param capacity the maximum no. of items that this slot can hold
     */
    public VM_Slot(int capacity){

        storedProfit = 0;
        slotItemName = null;
        item = null;
		
        slotItemStock = 0;
		slotItemSold = 0;

        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
    }
	
	
	/**
     * This copy constructor initializes itself with another VM_Slot and inherit
     * its attributes and data.
     * 
     * @param copy another VM_Slot object
     * 
     */
	public VM_Slot(VM_Slot copy)
    {
        slotItemSold = copy.getSlotItemSold();
        storedProfit = copy.getStoredProfit();
		
        // Sets the item as a new item copy to be assigned as the attribute of this slot
		if(copy.getItem() != null)
			item = new VM_Item(copy.getSlotItemName(), copy.getItem().getItemPrice(), copy.getItem().getItemCalories());
        // When the given copy is nothing set it as just null named item
		else
			item = new VM_Item(copy.getSlotItemName(), 0, 0);

        // when this slot has no item in, set the new item as this slot's name
        if(item != null)
            slotItemName = item.getItemName();
        // Not available name will be the slot name
        else
            slotItemName = "N/A";
        

        slotItemStock = copy.getSlotItemStock();

        // At minimum, it should have at least 10 capacity of items
        if(copy.getMAX() >= 10)
            MAX = copy.getMAX();
        else
            MAX = 10;
       

    }

	
	/**
     * Instructs slot to hold a different item, or even another of the same item
     * 
     * @param givenItem the new item replacing the slot's original item
     * @param qty the initial stock count of the new item
     */
    public void replaceStock(VM_Item givenItem, 
                             int qty)
    {	
        if(givenItem != null) 
        {
			item = givenItem;
            if(qty <= MAX)
			    slotItemStock = qty;
            else
                slotItemStock = MAX;
			slotItemName = new String(givenItem.getItemName());
		}
        
        
    }
	
	
	/**
     * Checks whether this slot contains the desired number of its item or more
     * 
     * @param qty the desired number of pieces of the item
	 * @return true if slot contain the desired quantity of items, false otherwise
     */
	public boolean hasEnoughStock(int qty) {
		if(slotItemStock >= 0 && qty >= 0 && qty <= slotItemStock)
			return true;
        
		return false;
	}
	
	
	/**
     * Computes for that fraction of the total cost
     * contributed by the desired number of items from this slot
     * 
     * @param qty the desired quantity of items
	 * @return the total cost of desired number of items from this slot
     */
	public double computePartialCost(int qty) 
    {
        double sum = 0;
        sum = item.getItemPrice() * qty;
        return sum;

	}

	
	 /**
     * Tells slot to "release" a certain quantity of its item,
     * which in the current design of this program
     * simply means increasing or decreasing the stock counter
     * 
     * @param qty the number of items to be released from this slot
     */
    public void releaseStock(int qty)
    {

		// Only release sto
        if(qty > 0 && hasEnoughStock(qty) && item != null)
        {
            slotItemStock -= qty;
            storedProfit += item.getItemPrice()*qty;
            slotItemSold += qty;
        }
    }
	
	
    /**
     * Gets the name of this slot
     * 
     * @return the String representing the name of the slot
     */
    public String getSlotItemName() 
    {

        return slotItemName;
    }


    /**
     * Gets the VM_Item object of this slot
     * 
     * @return the item that is in this slot
     */
    public VM_Item getItem() {
        return item;
    }


    /**
     * Gets the stock count of items in the slot
     * 
     * @return the number of items currently in this slot
     */
    public int getSlotItemStock() {
        return slotItemStock;
    }

    
	/**
     * Gets the maximum number of items that this slot can hold
     * 
     * @return the maximum capacity of this slot
     */
    public int getMAX() {
        return MAX;
    }

    
	/**
     * Displays the information of the item currently held by this slot,
     * as well as the stock count.
     *
     */
    public void displayAllItems(){
		
		
        if (slotItemStock > 0)
        {
            System.out.println("Qty: " + slotItemStock);
            System.out.println(item + "\n");   
        }
        else
            System.out.println(slotItemName + " slot is empty.\n");

    }


    
	/**
     * Adds to the stock count of this slot if givenItem
     * has the the same name as the currently held item,
     * but sets the stock count and replaces the currently held item
     * with givenItem if the two items have different names.
     * 
	 * @param givenItem the given type of item to be added
     * @param qty the quantity of objects
     */
    public void addItemStock(VM_Item givenItem, 
                             int qty)
    {


        Scanner sc = new Scanner(System.in);
        
        // Error that there was no stock added
        if(givenItem == null && qty <= 0)
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
        
        // State and excess and return it
        else if(qty + slotItemStock > MAX && givenItem.getItemName().equalsIgnoreCase(slotItemName)) {
            System.out.println("You have an excess of " + (qty+slotItemStock- MAX) + " " +givenItem.getItemName() + " while we were stocking. Returning...");
            System.out.println("\033[1;33m" + "Press and Enter Any Key To Continue..." + "\033[0m");
            sc.nextLine();
            
        }

        // If slot was initialized empty, proceed to put in stock
		if(item == null || slotItemStock == 0)
            replaceStock(givenItem, qty);
		// Skips conditional construct if restocker is empty
		else if(givenItem == null && qty <= 0 );
        // If the slot is not empty, then proceed to add the stock
        else if(givenItem.getItemName().equalsIgnoreCase(slotItemName) && (slotItemStock+qty) <= MAX)
            slotItemStock += qty;
        // If qty is full, just set MAX as current stock
        else if(givenItem.getItemName().equalsIgnoreCase(slotItemName) && (slotItemStock+qty) > MAX)
            slotItemStock = MAX;
        // if this slot already has an item, but has a different name
        else
            warnReplace(givenItem, qty);
        
        sc = null;

    }
		

    /**
     * Adds to the stock count of this slot, if not yet at maximum capacity
     * 
     * @param qty the number to add to stock counter attribute
     * @return true if qty is 1 or greater, false otherwise
     */
	public boolean addItemStock(int qty)
    {

        Scanner sc = new Scanner(System.in);

        // Add item only if this slot contains no item
        if(item == null)
		{
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
            sc = null;
			return false;
		}

        // Set stock to max if there was an excess
        else if(qty + slotItemStock > MAX)
		{
            System.out.println("You have an excess of " + (qty+slotItemStock- MAX) + " " + slotItemName + " while we were stocking. Returning...");
            System.out.println("\033[1;33m" + "Press and Enter Any Key To Continue..." + "\033[0m");
            sc.nextLine();
            slotItemStock = MAX;
        }

        // Add item
		else
			slotItemStock += qty;

        sc = null;
		return true;

        
    }

   

	
	/**
     * This method Sets the price of the item object held by this slot
     * 
     * @param amt the new price of the item

     */
	public void repriceItem(double amt) {
		item.setPrice(amt);
	}
	
	 
	/**
     * Gets how many items have been sold
	 *	by this slot since last restocking
     * 
     * @return the number of items sold by this slot since last restocking
     */
    public int getSlotItemSold() {
        return slotItemSold;
    }

	
	/**
     * Sets the number of items sold
	 * by this slot since the last restocking
     * 
     * @param slotItemSold the new number of items sold by this slot since last restocking
     */
	public void setSlotItemSold(int slotItemSold) 
    {
			this.slotItemSold = slotItemSold;
	}
	
	
	/**
	 * Gets the amount of profit generated
	 * by this slot since last restocking
	 *
	 * @return the profit made by the slot thus far
	 */
    public double getStoredProfit()
    {
        return storedProfit;
    }
	
	
	/**
	 * This method resets the stored profit back to 0.0
	 *
	 *
	 */
    public void clearStoredProfit()
    {
        storedProfit = 0.0;
    }
	
	
    /**
     * Informs user of restocking conflict,
	 * asks user to choose whether or not to replace currently stocked item(s)
	 * with the new givenItem
     * 
     * @param givenItem the new item to be held by this slot
     * @param stock the initial stock count of the new item
     */
    private void warnReplace(VM_Item givenItem, 
                             int stock)
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("\033[1;33mConflict with another type of item\033[0m, will you be replacing this stock of " + slotItemName + 
                            " with " + givenItem.getItemName() + ". (Y/N)");
        
        // Only proceed to replace when user agrees
        if(sc.nextLine().equalsIgnoreCase("Y"))
        {
            System.out.println("Replaced " + slotItemName + " with " + givenItem.getItemName());
            replaceStock(givenItem, stock);
        }

        sc = null;
    }
	

	
    /** the item held by this slot */
    private VM_Item item;
    /** the number of items sold by this slot since last restocking or repricing */
	private int slotItemSold;
	/** the slot's item name */
    private String slotItemName;
    /** the current stock count of the held item */
    private int slotItemStock;
    /** profit that this slot has collected */
    private double storedProfit;
    /** max capacity of this slot */
    private final int MAX;
    
}