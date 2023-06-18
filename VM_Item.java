/** The class VM_Item represents an item
  * that is built within a slot that is
  * inside the vending machine 
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class VM_Item {

    /**
     * This is a constructor that initializes an item's name,
     * price, and calories based on the given parameters. It also
     * sets the number of items sold to 0 and the pointer to the next
     * item as null.
     * 
     * @param name - string representation of the name of this item
     * @param price - price or cost of this item
     * @param calories - amount of calories of this item
     */
    public VM_Item(String name, double price, int calories)
    {
        itemName = name;
        itemPrice = price;
        itemCalories = calories;
        nextItem = null;
        
        
    }




    /**
     * This method sets a pointer to the next item for this item
     * 
     * @param item another instance of an item made by this class
     */
    public void setNextItem(VM_Item item)
    {
        if(this.equals(item) && this.nextItem == null)
            this.nextItem = item;

    }

    /**
     * This method gets the name of this item.
     * 
     * @return the string representation of this item's name
     */
    public String getItemName() 
    {
        return itemName;
    }

    /**
     * This method gets the price of this item
     * 
     * @return the price that represents the cost of this item.
     */
    public double getItemPrice() 
    {
        return itemPrice;
    }

    /**
     * This method gets the number of calories of this item.
     * 
     * @return the amount of calories from this item
     */
    public int getItemCalories() 
    {
        return itemCalories;
    }

    /**
     * This method gets the next item of this item
     * 
     * @return reference to another instance of this item
     */
    public VM_Item getNextItem() 
    {
        return nextItem;
    }


    

    /**
     * This method overrides the toString() method to return name, price,
     * and calories of this item with proper formating
     * 
     * @return String representing info of name, price, and calories, of this item
     */
    @Override
    public String toString(){
        return "Name: " + itemName + "\n" +
               "Price: Php " + itemPrice + "\n" +
               "Calories: " + itemCalories + " cal" + "\n";
               
    }

    /**
     * This method overrides the equals method to return true
     * if this item, not referring to itself, has the same name, price,
     * and calories of another item. Otherwise it returns false.
     * 
     * @return true if this item, not referring to itself, has the same name, price, 
     *         and calories of another item. Otherwise it returns false
     */
    @Override
    public boolean equals(Object item)
    {
        if(item != null)
        {
            VM_Item tempItem = (VM_Item) item;

            if(this != tempItem && this.itemName.equalsIgnoreCase(tempItem.getItemName()) 
               && this.itemPrice == tempItem.getItemPrice() && this.itemCalories == tempItem.getItemCalories())
               return true;
            else
               return false;
        }
        else
        {
            return false;
        }
        
               
    }

    /**This is the name of the item in the vending machine. */
    private String itemName;
    /**Price of the item*/
    private double itemPrice;
    /**The amount of calories in this item */
    private int itemCalories;
    /**The reference for the next item to this */
    private VM_Item nextItem;
}
