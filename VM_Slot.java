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
    public VM_Slot(VM_Item givenItem, int capacity){


        item = givenItem;

        if(givenItem != null)
            slotItemName = givenItem.getItemName();
        else
            slotItemName = "N/A";

        slotItemStock = 0;

        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
    }
	
	
    public VM_Slot(int capacity){
        this(null, capacity);
    }


    public void replaceStock(VM_Item givenItem, int stock)
    {
        if(givenItem != null && stock > 0)
            slotItemName = givenItem.getItemName();
            item = givenItem;
            slotItemStock = stock;
        
    }

    public void releaseStock(int quantity)
    {
        if(hasEnoughStock(quantity))
        {
            slotItemStock -= quantity;
        }
        
        
    }
	
	public boolean hasEnoughStock(int quantity) {
		if(slotItemStock != 0 && quantity >= 0 && quantity <= slotItemStock)
			return true;
        
		return false;
	}
	
	public double computePartialCost(int quantity) {
        double sum;
        sum = 0;
		
        sum = item.getItemPrice() * quantity;

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
     * This method gets the item that is sold
     * @return the item that is being sold under ths slot
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

    // DELETE DIS BELOW
    /**
     * Displays all item currently in slot
     */
    public void displayAllItems(){

        if (item != null)
        {
            System.out.println("Stock: " + slotItemStock);
            System.out.println(item);
            
        }
            
        else
            System.out.println("No items in this slot");

    }



    /**
     * This method adds stock count of this slot
     * 
     * @param stock number of items to be added
     */
    public void addItemStock(VM_Item givenItem, int stock)
    {
       
        
        if(givenItem == null && stock <= 0)
        {
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
        }
        else if(stock + slotItemStock > MAX)
        {
            System.out.println("You have an excess of " + (MAX-stock) + givenItem.getItemName() + " while we were stocking.");
            slotItemStock = MAX;
            return;
        }

        // Check if the slot was empty
        if(item == null)
            replaceStock(givenItem, stock);
        

        // If the slot is not empty, then proceed to add the stock
        else if(item != null && 
                givenItem.getItemName().equalsIgnoreCase(slotItemName))
        
            slotItemStock += stock;
        

        // if this slot already has an item, but has a different
        else
            warnReplace(givenItem, stock);
        
        

    }

   

    private void warnReplace(VM_Item givenItem, int stock)
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("\033[1;33mConflict with another type of item\033[0m, will you be replacing this stock of " + slotItemName + 
                            " with " + givenItem.getItemName() + ". (Y/N)");
        if(sc.nextLine().equalsIgnoreCase("Y"))
        {
            System.out.println("Replaced " + slotItemName + " with " + givenItem.getItemName());
            replaceStock(givenItem, stock);

        }

        sc.close();
    }
    
    private VM_Item item;
    /**The slot's item name*/
    private String slotItemName;
    /**The current item stock of this item */
    private int slotItemStock;
    // Max capacity of item in this slot
    private final int MAX;
    
}
