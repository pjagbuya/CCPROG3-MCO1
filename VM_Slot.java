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
    public VM_Slot(VM_Item item, int capacity){
        this.item = item;
        this.slotItemName = item.getItemName();
        itemStock = 1;
        MAX = capacity;
        numItemSold = 0; 
    }

    public void releaseStock(int quantity)
    {
		int i = 0;
		VM_Item currentItem = item;
		while(i < quantity) {
			currentItem = currentItem.getNextItem();
			i++;
		}
		
		item = currentItem;
		itemStock -= quantity;
        numItemSold += quantity;
    }
	
	public boolean hasEnoughStock(int quantity) {
		if(quantity >= 0 && quantity <= itemStock)
			return true;			
		return false;
	}
	
	public double computePartialCost(int quantity) {
		
		int i = 0;
		double sum = 0.0;
		VM_Item currentItem = item;
		while(i < quantity) {
			sum += currentItem.getItemPrice();
			currentItem = currentItem.getNextItem();
			i++;
		}
		System.out.println("Partial cost: " + sum);
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
     * This method gets the number of times this item was sold
     * 
     * @return current number of times this item was sold
     */
    public int getNumItemSold() 
    {
        return numItemSold;
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
    public int getItemStock() {
        return itemStock;
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
        VM_Item itemCurrent;
        itemCurrent = item;
        while(itemCurrent != null){
            System.out.println(itemCurrent.toString());
            itemCurrent = itemCurrent.getNextItem();
        }
    }

    /**
     * This method adds stock count of this slot
     * 
     * @param stock number of items to be added
     */
    public void addStock(int stock)
    {
        
        VM_Item itemCurrent;
        
        int size = stock;


        if(itemStock > 0)
        {
            // check if the stock provided is too much for capacity
            if(itemStock + stock > MAX){
                System.out.print("Stocking has an excess of " + (stock -MAX) + " " + slotItemName );

                //plurality of noun
                if((stock - MAX) > 1){
                    System.out.print("s");
                }
                System.out.println();

                // Size will then be just set to max capacity
                size = MAX;
            }
            
            // First sets up the chain to add the node, since it is at least one item to be added
            itemCurrent = new VM_Item(item.getItemName(), item.getItemPrice(), item.getItemCalories());
            item.setNextItem(itemCurrent);

            // updates the values
            size -= 1;              // value for loop
            itemStock += 1;         // stock count of this slot
            
            // First checks if the size count of iteration has been reached
            while(size > 0 && itemCurrent.getNextItem() == null){
                
                // Sets next item with a new instance of an item
                itemCurrent.setNextItem(new VM_Item(item.getItemName(), item.getItemPrice(), item.getItemCalories()));
                size--;

                // loop is linked to another item
                itemCurrent = itemCurrent.getNextItem();

            }

                
            

            

        }
        else
        {
            System.out.println("Invalid value to add stock");
            return;
        }
		

 

    }

    
    // Item of this slot
    private VM_Item item;
    /**The slot's item name*/
    private String slotItemName;
    /**The current item stock of this item */
    private int itemStock;
    /**The total number of items sold */
    private int numItemSold;
    // Max capacity of item in this slot
    private final int MAX;
    
}
