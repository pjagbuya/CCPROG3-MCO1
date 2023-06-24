import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;
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

    /**
     * This constructor initializes the slot's capacity based
     * on the given parameters. It also set the item to null
     *
     * @param capacity the capacity of items this slot can hold
     */
    public VM_Slot(int capacity){
        this(null, capacity);
    }

    /**
     * This constructor initialize the capacity to be 10 items.
     * This sets null to be the item of this slot
     */
    public VM_Slot(){
        this(null, 10);
    }


    /**
     * This method returns true if this slot is full,
     * otherwise it returns false
     * 
     * @return true if the current slot is full,
     *         otherwise it returns false
     */
    public boolean isFull()
    {
        if(slotItemStock == MAX)
            return true;
        return false;
    }


    /**
     * This method returns true if this slot is empty, otherwise
     * it returns false
     * 
     * @return true if the slot is empty or slot holds 0 items, false otherwise
     */
    public boolean isEmpty()
    {
        if(slotItemStock == 0)
            return true;
        return false;
    }


    /**
     * This method replaces the stock given an item and stock. Stock
     * is also firstly checked to be a positive integer
     * 
     * @param givenItem the item of a stock
     * @param stock the number of items to be in stock
     */
    public void replaceStock(VM_Item givenItem, int stock)
    {
        if(givenItem != null && stock > 0 && stock <= MAX)
        {
            slotItemName = givenItem.getItemName();
            item = givenItem;
            slotItemStock = stock;

        }
        else if(givenItem != null && stock > MAX)
        {
            slotItemName = givenItem.getItemName();
            item = givenItem;
            slotItemStock = MAX;

        }
        
    }

    /**
     * This method deducts the stock from this slot. If this slot
     * has 0 slotItemStock, it will the item to null.
     * 
     * @param quantity the number of stock to be deducted
     */
    public void releaseStock(int quantity)
    {
        if(hasEnoughStock(quantity))
        {
            slotItemStock -= quantity;
            if(slotItemStock == 0)
                item = null;
        }
        
        
    }
	

    /**
     * This method checks if the quantity to being passed is within the range
     * of the slotItemStock of this slot. It also checks first if this slot was
     * empty.
     * 
     * @param quantity the number of stock to be check if it is within range
     * @return true if it is within the range of items in stock, otherwise false.
     */
	public boolean hasEnoughStock(int quantity) {
		if(!isEmpty() && quantity >= 0 && quantity <= slotItemStock)
			return true;
        
		return false;
	}


    /**
     * This method sets the item price of an item based on the parameters.
     * Also checks if the given parameters is positive.
     * 
     * @param price
     */
    public void setItemPrice(double price)
    {
        if(price > 0)
            item.setItemPrice(price);
    }
	
	public double computePartialCost(int quantity) {
        double sum;

        sum = 0;
		if(hasEnoughStock(quantity))
        {  
            
            sum = item.getItemPrice() * quantity;
            

        }

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
     * This method simply just adds the stock count to the slotItemStock based on
     * the given parameters
     * 
     * @param stock number of stock to be added in this slot
     */
    public void addItemStock(int stock)
    {
        if(item != null)
            addItemStock(item, stock);

    }
    /**
     * This method adds stock count of this slot. Checks if the item given was null
     * or has no stock amount given to add. Also indicates if the stocking overcapped
     * but proceeds to fill the stock count to MAX.
     * 
     * @param givenItem the item to be put into stock
     * @param stock number of items to be added
     */
    public void addItemStock(VM_Item givenItem, int stock)
    {
       
        
        if(givenItem == null || stock <= 0)
        {
            System.out.println("\033[1;38;5;202mERROR! no stocks/item is detected\033[0m");
            
        }
        else if(item != null &&
                stock + slotItemStock > MAX && givenItem.getItemName().equalsIgnoreCase(slotItemName))
        {
            System.out.println("You have an excess of " + (MAX-stock) + givenItem.getItemName() + " while we were stocking.");
            slotItemStock = MAX;
            
        }

        // Check if the slot was empty
        else if(item == null)
            replaceStock(givenItem, stock);
        

        // If the slot is not empty, then proceed to add the stock
        else if(item != null && 
                givenItem.getItemName().equalsIgnoreCase(slotItemName) &&
                stock + slotItemStock <= MAX)
        
            slotItemStock += stock;
        

        // if this slot already has an item, but has a different name
        else
            warnReplace(givenItem, stock);
        
            
        
        

    }

   
    /**
     * This method warns the user if he/she wants to replace the stock of items instead
     * 
     * @param givenItem item to be placed in stock
     * @param stock the number of items to be placed in stock
     */
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
