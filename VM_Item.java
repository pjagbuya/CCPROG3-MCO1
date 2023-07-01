import java.text.DecimalFormat;


/** The class VM_Item represents an item
  * to be placed inside VM
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class VM_Item {

    /**
     * This is a constructor that initializes an item's name,
     * price, and calories based on the given parameters. It also
     * sets the number of items sold to 0.
     * 
     * @param name - string representation of the name of this item
     * @param price - price or cost of this item
     * @param calories - amount of calories of this item
     */
    public VM_Item(String name, 
                   double price, 
                   int calories)
    {
        itemName = name;
        itemPrice = price;
        itemCalories = calories;
        
        
    }

    
    /**
     * Gets the name of this item
     * 
     * @return the string representation of this item's name
     */
    public String getItemName() 
    {
        return itemName;
    }

    /**
     * Gets the price of this item
     * 
     * @return the price of this item as a floating-point double
     */
    public double getItemPrice() 
    {
        return itemPrice;
    }

    /**
     * Gets the number of calories in this item
     * 
     * @return the amount of calories in this item
     */
    public int getItemCalories() 
    {
        return itemCalories;
    }

    /**
     * Sets the price of this item based on the given parameters
     * 
     * @param amt the new price to be assigned to this item

     */
	public void setPrice(double amt) {
		if(amt >= 0.5)
			itemPrice = amt;
		else
			itemPrice = 0.5; // minimum price of an item is 50 Cents

	}


    /**
     * Overrides the toString() method to return name, price,
     * and calories of this item with proper formating
     * 
     * @return the String containing name, price, and no. of calories in this item
     */
    @Override
    public String toString(){

        return "Name: " + itemName + " (\033[1;32m" + itemName.substring(0, 3) + "\033[0m)" + "\n" +
               "Price: Php " + FORMAT.format(itemPrice) + "\n" +
               "Calories: " + itemCalories + " kCal";
               
    }


	

    /** name of the item */
    private String itemName;
	/** price of the item, per piece */
    private double itemPrice;
    /** the amount of calories in this item */
    private int itemCalories;
    /** Decimal format instance that prices will follow */
    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

}
