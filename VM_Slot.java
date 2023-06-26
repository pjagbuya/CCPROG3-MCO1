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

		/*
        items[0] = givenItem;

        if(givenItem != null)
            slotItemName = givenItem.getItemName();
        else
            slotItemName = "N/A";
		*/
		
        slotItemStock = 0;

        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
		
		items = new VM_Item[MAX];
    }
	
	
    public VM_Slot(int capacity){
        this(null, capacity);
    }


    public void replaceStock(VM_Item[] givenItems)
    {
		int i;
        if(givenItems != null && givenItems.length > 0) {
            slotItemName = givenItems[0].getItemName();
			for(i = 0; i < MAX; i++)
				items[i] = null;
			for(i = 0; i < givenItems.length; i++)
				items[i] = givenItems[i];
		
			slotItemStock = givenItems.length;
		}
    }

	/**
	 * No input validation
	 *
	 *
	 */
    public VM_Item[] releaseStock(int qty)
    {
		int i;
		int j = 0;
		VM_Item[] releasableItems = new VM_Item[qty];
		
        slotItemStock -= qty;
		for(i = 0; i < qty; i++) {
			releasableItems[i] = items[i];
			items[i] = null;
		}
		while(i < MAX) {
			items[j] = items[i];
			items[i] = null;
			i++;
			j++;
		}
		slotItemStock -= qty;
			
		return releasableItems;
    }
	
	public boolean hasEnoughStock(int qty) {
		if(slotItemStock >= 0 && qty >= 0 && qty <= slotItemStock)
			return true;
        
		return false;
	}
	
	public double computePartialCost(int qty) {
        double sum = 0;
        sum = items[0].getItemPrice() * qty;
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
     * This method gets the item array
     * @return the array of items that are in this slot
     */
    public VM_Item[] getItems() {
        return items;
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
            System.out.println("Qty. In Stock: " + slotItemStock);
            System.out.println(items[0]);   
        }
        else
            System.out.println("No items in this slot");

    }



    /**
     * This method adds stock count of this slot
     * 
	 * @param givenItems the array of objects to be added
     * @param qty number of items to be added
     */
    public void addItemStock(VM_Item[] givenItems, int qty)
    {
		int i;
		int j = slotItemStock;
        
        if(givenItems == null && givenItems.length <= 0)
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
        else if(givenItems.length + slotItemStock > MAX) {
            System.out.println("You have an excess of " + (MAX-qty) + givenItems[0].getItemName() + " while we were stocking.");
            slotItemStock = MAX;
        }
		
		// Skips conditional construct if restocker array is empty
		if(givenItems == null || givenItems.length == 0);
        // Check if the slot was empty
        else if(items == null)
            replaceStock(givenItems);
        // If the slot is not empty, then proceed to add the stock
        else if(items != null && && givenItems[0].getItemName().equalsIgnoreCase(slotItemName))
		{
            slotItemStock += givenItems.length;
			for(i = 0; i < givenItems.length; i++)
			{
				items[j] = givenItems[i];
				j++;
			}
		}
        // if this slot already has an item, but has a different
        else
            warnReplace(givenItems);
        
        

    }

   

    private void warnReplace(VM_Item[] givenItems)
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("\033[1;33mConflict with another type of item\033[0m, will you be replacing this stock of " + slotItemName + 
                            " with " + givenItems[0].getItemName() + ". (Y/N)");
        if(sc.nextLine().equalsIgnoreCase("Y"))
        {
            System.out.println("Replaced " + slotItemName + " with " + givenItems[0].getItemName());
            replaceStock(givenItems);
        }

        sc.close();
    }
    
    private VM_Item[] items;
    /**The slot's item name*/
    private String slotItemName;
    /**The current item stock of this item */
    private int slotItemStock;
    // Max capacity of item in this slot
    private final int MAX;
    
}
