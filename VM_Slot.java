import java.util.Scanner;
/** The class VM_Slot represents a slot
  * that is built within a Vending machine
  * containing items
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */

public class VM_Slot {


    /**
     * This is a constructor that initializes a slot's item and capacity.
     * A slot would then contain at least this one item. 
     * 
     * @param item the item that this slot contains
     * @param capacity the capacity that this slot can hold
     */
    public VM_Slot(int capacity){

        slotItemName = null;
        item = null;
		
        slotItemStock = 0;
		slotItemSold = 0;

        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
    }
	
	
	public VM_Slot(VM_Slot copy)
    {
        slotItemSold = copy.getSlotItemSold();
        item = new VM_Item(copy.getSlotItemName(), copy.getItem().getItemPrice(), copy.getItem().getItemCalories());

        if(item != null)
            slotItemName = item.getItemName();
        else
            slotItemName = "N/A";
	
        slotItemStock = copy.getSlotItemStock();

        if(copy.getMAX() >= 10)
            MAX = copy.getMAX();
        else
            MAX = 10;
       
    }


    public void replaceStock(VM_Item givenItem, int qty)
    {

		
		if(givenItem != null) 
        {
			item = givenItem;
            if(slotItemStock+qty <= MAX)
			    slotItemStock += qty;
            else
                slotItemStock = MAX;
			slotItemName = new String(givenItem.getItemName());
		}
    }

	/**
	 * No input validation
	 *
	 *
	 */
    public void releaseStock(int qty)
    {

		
        if(qty > 0 && hasEnoughStock(qty))
        {
            slotItemStock -= qty;
            slotItemSold += qty;
        }
    }
	
	public boolean hasEnoughStock(int qty) {
		if(slotItemStock >= 0 && qty >= 0 && qty <= slotItemStock)
			return true;
        
		return false;
	}
	
	public double computePartialCost(int qty) {
        double sum = 0;
        sum = item.getItemPrice() * qty;
        return sum;

	}

    /**
     * This method returns the name of the slot
     * @return string representing the name of the slot
     */
    public String getSlotItemName() 
    {

        return slotItemName;
    }



    /**
     * This method gets the item
     * @return the item that is in this slot
     */
    public VM_Item getItem() {
        return item;
    }

    /**
     * This method gets the stock count of items in the slot
     * @return stock count of item in this slot
     */
    public int getSlotItemStock() {
        return slotItemStock;
    }

    /**
     * This method gets the max capacity of this slot
     * @return the max capacity of this slot
     */
    public int getMAX() {
        return MAX;
    }

    /**
     * Displays all item currently in slot
     */
    public void displayAllItems(){

        if (slotItemStock > 0)
        {
            System.out.println("\nQty: " + slotItemStock);
            System.out.println(item + "\n");   
        }
        else
            System.out.println(slotItemName + " slot is empty.");

    }



    /**
     * This method adds stock count of this slot
     * 
	 * @param givenItems the given type of item to be added
     * @param qty the quantity of objects
     */
    public void addItemStock(VM_Item givenItem, 
                             int qty)
    {


        Scanner sc = new Scanner(System.in);
        
        if(givenItem == null && qty <= 0)
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
        else if(qty + slotItemStock > MAX) {
            System.out.println("You have an excess of " + (qty+slotItemStock- MAX) + " " +givenItem.getItemName() + " while we were stocking. Returning...");
            System.out.println("\033[1;33m" + "Press and Enter Any Key To Continue..." + "\033[0m");
            sc.nextLine();
            
        }

        // If slot was initialized empty, proceed to put in stock
		if(item == null)
            replaceStock(givenItem, qty);
		// Skips conditional construct if restocker is empty
		else if(givenItem == null && qty <= 0 );
        // Check if the slot was empty
        else if(slotItemStock <= 0)
            replaceStock(givenItem, qty);
        // If the slot is not empty, then proceed to add the stock
        else if(givenItem.getItemName().equalsIgnoreCase(slotItemName) && (slotItemStock+qty) <= MAX)
            slotItemStock += qty;
        
        else if(givenItem.getItemName().equalsIgnoreCase(slotItemName) && (slotItemStock+qty) >= MAX)
            slotItemStock = MAX;
        // if this slot already has an item, but has a different name
        else
            warnReplace(givenItem, qty);
        
        

    }
	
	public boolean addItemStock(int qty)
    {

        Scanner sc = new Scanner(System.in);

        if(qty <= 0)
		{
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
			return false;
		}
        else if(qty + slotItemStock > MAX)
		{
            System.out.println("You have an excess of " + (qty+slotItemStock- MAX) + " " + slotItemName + " while we were stocking. Returning...");
            System.out.println("\033[1;33m" + "Press and Enter Any Key To Continue..." + "\033[0m");
            sc.nextLine();
            slotItemStock = MAX;
        }
		else
			slotItemStock += qty;
		return true;
    }

   

    private void warnReplace(VM_Item givenItem, 
                             int qty)
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("\033[1;33mConflict with another type of item\033[0m, will you be replacing this stock of " + slotItemName + 
                            " with " + givenItem.getItemName() + ". (Y/N)");
        if(sc.nextLine().equalsIgnoreCase("Y"))
        {
            System.out.println("Replaced " + slotItemName + " with " + givenItem.getItemName());
            replaceStock(givenItem, qty);
        }

        sc.close();
    }
	
	public boolean repriceItem(double amt) {
		return item.setPrice(amt);
	}
	
	 /**
     * This method gets the amount of items sold in this slot
     * @return
     */
    public int getSlotItemSold() {
        return slotItemSold;
    }
	
	public void setSlotItemSold(int slotItemSold) {
			this.slotItemSold = slotItemSold;
	}
	
	
	
    
    private VM_Item item;
    /**The slot's item name*/
	private int slotItemSold;
    private String slotItemName;
    /**The current item stock of this item */
    private int slotItemStock;
    // Max capacity of item in this slot
    private final int MAX;
    
}
