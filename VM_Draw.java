import java.util.ArrayList;
/** The class VM_Draw represents a simple console based output
  * of what it looks to be on screen of the availability of items
  * inside the vending machine
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */

public class VM_Draw {

    /**
     * This constructor builds the data ASCII art of a vending machine
     * based on the given type of Vending Machine in the parameters. It 
     * also stores the name
     * @param vmMachine a built vending machine
     */
    public VM_Draw(VM_Regular vmMachine){

        int i;
        String temp;

        double price;
     
        

        VM_Slot[] slots = vmMachine.getSlots(); // gets all the slots along with the status
        stringLabels = new ArrayList<String>(); 
        priceLabels = new ArrayList<String>();
        vmName = vmMachine.getName();


        // The margin for adjustment in the drawing once exceeding the minimum height and width
        maxLenMarginPrice = 0;


        
        for(i = 0; i < slots.length; i++)
        {

            // Only sets up string if the slot selected are already initialized with stocks
            if(slots[i] != null && slots[i].getSlotItemStock() > 0)
            {

                // Chooses a 3 letter substring from the name of the item, in some cases a 2 letter substring can be considered
                if(slots[i].getSlotItemName().length() >= 3)
                    stringLabels.add(i, slots[i].getSlotItemName().substring(0, 3));
                else
                    stringLabels.add(i, slots[i].getSlotItemName().substring(0, 2) + " ");

                
                // Get the price and turn it to string
                price = slots[i].getItem().getItemPrice();
                priceLabels.add(i, price + "");
                setupPriceLabels(i);
                

            }

            else
            {   

                // Sets a default brand "XXX" for slots that are empty
                stringLabels.add(i, "\033[1;31m" + "XXX" + "\033[0m");

                temp = DEFAULT_PRICE;
                priceLabels.add(i, " N/A ");

                
                
            }
            
   
        }
        // Reassess all the pricelabels and configure their size and string
        formatPriceLabels();
        i = 0;
        
        while(i < priceLabels.size())
        {
           
            temp = stringLabels.get(i);
            stringLabels.set(i, "\033[1;32m" + temp + "\033[0m");   // Colors green

            // Increment for the next price label to check
            i++;  
        }
         



    }

    
    /**
     * This method updates the initial values of the labels based on the given
     * Vending machine model. It swaps new item names, or turns an item into red 
     * if it is out of stock
     * 
     * @param vmMachine the vending machine model built
     */
    public void updateVM(VM_Regular vmMachine)
    {
        VM_Slot[] slots = vmMachine.getSlots();
        String subName;
        int i;


        // Checks downwards of all items
        for (i = 0; i < slots.length; i++)
        {
            if(slots[i] != null && slots[i].getItem() != null)
            {
                // Checks for the substring of three letters of the slot's item name, two letters if not possible
                if(slots[i].getSlotItemName().length() >= 3)
                    subName = slots[i].getSlotItemName().substring(0, 3);
                else
                    subName = slots[i].getSlotItemName().substring(0, 2);
                

                // If slot is empty or no stock, indicate red text
                if((slots[i].getSlotItemStock() == 0 || slots[i].getItem() == null) && 
                    stringLabels.get(i).equalsIgnoreCase(subName))
                {

                    // Highlights item as red
                    stringLabels.set(i, stringLabels.get(i).replace("\033[1;32m", "\033[1;31m"));
    

                    
                }
  
                    
                // If slot is replaced and has stock, indicate new green text
                else if(!stringLabels.get(i).equalsIgnoreCase(subName) && slots[i].getSlotItemStock() > 0 && slots[i].getItem() != null)
                {
                    // Changes the item
                    stringLabels.set(i, "\033[1;32m" + subName + "\033[0m");
                    priceLabels.set(i, slots[i].getItem().getItemPrice() + "");
                    setupPriceLabels(i);
                    


                }
            }
            if(slots[i] == null || slots[i].getItem() == null || slots[i].getSlotItemStock() == 0)
            {
                if(stringLabels.get(i) != null)
                    stringLabels.set(i, stringLabels.get(i).replace("\033[1;32m", "\033[1;31m"));
            }

        
 
            
        }

        // Formats the price labels to fit in within the Vending machine drawing
        formatPriceLabels();
        
    

    }

    /**
     * This method prints and displays the ASCII art of the vending machine to be displayed on the screen
     * with respected to the positions of the items in the slots[] of the Vending Machine Object
     * 
     */
    public void drawAndSetVM()
    {
        int stringInd;
        int priceInd;
        int spaceCnt;
        int slotInd;
        int i;
        int j;

        stringInd = 0;
        priceInd = 0;
        slotInd = 0;
        System.out.println("The slots are structured as: ");
        System.out.println("+---------+");
        System.out.println("|  \033[1;32mName\033[0m   |");
        System.out.println("| Slot No.|");
        System.out.println("|  \033[1;33mPrice\033[0m  |");
        System.out.println("+---------+");

        System.out.println("Prices in \033[1;33mPhp\033[0m");
        System.out.println("Out of stock means \033[1;31mRED\033[0m");
        System.out.println("In stock means \033[1;32mGREEN\033[0m");


        System.out.println();
        System.out.println("Vending Machine: \033[1;33m" + vmName + "\033[0m");

        // Below would consist a five layer patterned ASCII art
        // Draws based on the amount of labels, 3 labels gives one row, 6 labels gives an additional row, and so on
        for ( i = 0; i < (int)(Math.ceil(stringLabels.size()/3.0))*BOX_HEIGHT; i++) 
        {


            // Each additional margin to be adjusted is deemed to be added to the box width
            for (j = 0; j < 3*(BOX_WIDTH+maxLenMarginPrice); j++) 
            {

                // Slects first and last rows
                if( i % 5 == 0 || i % 5 == 4)
                {

                    // Select box corners
                    if(j % (BOX_WIDTH+maxLenMarginPrice) == 0 || j % (BOX_WIDTH+maxLenMarginPrice) == (BOX_WIDTH+maxLenMarginPrice)-1)
                    {
                        System.out.print("+");
                    } 
                    else 
                    {
                        System.out.print("-");
                    }
                    
                }
                // The between layers, index 1,2,3
                else
                {
                    // First and last section of a box
                    if(j % (BOX_WIDTH+maxLenMarginPrice) == 0 || j % (BOX_WIDTH+maxLenMarginPrice) == (BOX_WIDTH+maxLenMarginPrice)-1)
                        System.out.print("|");

                    // Top most of middle layer
                    else if(i % 5 == 1 && j % (BOX_WIDTH+maxLenMarginPrice) == (BOX_WIDTH+maxLenMarginPrice)/2-1)
                    {
                        // Prints string
                        if(stringInd < stringLabels.size())
                            System.out.print(stringLabels.get(stringInd));
                        
                        // No labels means this is blocked off
                        else
                        {
                        
                            System.out.print("   ");
                            
                        }
                        
                        j += 2;     // allots the next two characters for the two characters of this word
                        stringInd++;
                    }
                    // Selects thos of which that are not the middle section of top most middle layer and just put spaces
                    else if(i % 5 == 1 && (j % (BOX_WIDTH+maxLenMarginPrice) < (BOX_WIDTH+maxLenMarginPrice)/2-1 || 
                            j % (BOX_WIDTH+maxLenMarginPrice) > (BOX_WIDTH+maxLenMarginPrice)/2+1))
                    {
                        System.out.print(" ");
                    }
                    // The center layer is selected
                    else if(i % 5 == 2 && j % (BOX_WIDTH+maxLenMarginPrice) == (BOX_WIDTH+maxLenMarginPrice)/2)
                    {
                        
                        // Print the slot number
                        if(slotInd < stringLabels.size())
                        {
                            // Check if the label is greater than or less than
                            if ((slotInd+1 + "").length() >= 2)     // When slot label length count increases
                            {
                                System.out.print((slotInd+1 + ""));
                                j += 1;                             // Move on to the next space

                                
                            }
                            else
                            {
                                System.out.print(slotInd+1);
               
                               
                            }
                            
                            slotInd++;

                        }   
                           
                        else
                        {
                            
                            System.out.print(" ");
   

                            
                        }


                        
                    }
  
                    // Starting at after the first space
                    else if(i % 5 == 3 && j % (BOX_WIDTH+maxLenMarginPrice) == 2)
                    {   
                        
   
                        if(priceInd < priceLabels.size())
                        {
                    

                            System.out.print(priceLabels.get(priceInd));

                            // This is the size of the price label added, adds the space it consumes
                            j += (BOX_WIDTH + maxLenMarginPrice)/2+1;

                            // Cases where Java starts using in E when representing big integers
                            if((BOX_WIDTH+maxLenMarginPrice) >= 13)
                            {
                                if((BOX_WIDTH+maxLenMarginPrice) - 13 >= 2)
                                    j += 2;
                                else
                                    j += (BOX_WIDTH+maxLenMarginPrice) - 13;
                            }
                            
                            priceInd++;
                        }
                        else
                        {

                            // Spaces indicating an empty/blocked slot
                            spaceCnt = 0;
                            System.out.print(" ");
                            while(spaceCnt < (BOX_WIDTH/2) + 1 + maxLenMarginPrice)
                            {
                                System.out.print(" ");

                                // Increment to the amount of spaces occuring
                                j += 1;
                                spaceCnt++;
                            }


                                
                        }


    

                    }
                    else
                    {
  
                        System.out.print(" ");
                        // Increment for next slot number
                        
                    }



                }


            

                
                
            }
  
            System.out.println();
        }

      
    }


    /**
     * This helper method helps setup the price labels to be appropriately display on the protoype Vending Machine Drawing
     * 
     * @param ind index of price label to be assessed
     */
    private void setupPriceLabels(int ind)
    {
        int i;
        String temp;

        i = ind;
        
        
        

                
        // Check the string size of the price
        temp = priceLabels.get(i);

        // Check if the substring only have two characters, Ex: 20.0, substring ".0" is selected, thus if block executes
        if(temp.substring(temp.indexOf('.')).length() < 3 )
            priceLabels.set(i, temp + "0");
        
        // update the tempholder of string
        temp = priceLabels.get(i);


        // If size of the string is greater than 5 (which is (BOX_WIDTH/2)), updates for the minimum size is activated
        // Sets the margin to take into account for other items and allocate enough space adjustment
        // Only updates maxLenMarginPrice if it finds a bigger number that has a longer string
        if(temp.length() > (BOX_WIDTH/2) + 1 && 
            temp.length()-((BOX_WIDTH/2) + 1) > maxLenMarginPrice)
        {

            maxLenMarginPrice = temp.length()-((BOX_WIDTH/2) + 1);

        }
    }

    /**
     * This helper method helps format price labels to be as long as the longest price label available
     * 
     */
    private void formatPriceLabels()
    {

        int i;
        int alternatingNum;
        String temp;

        i = 0;
        // Reassess all the pricelabels and configure their size and string
        while(i < priceLabels.size())
        {
            
            temp = priceLabels.get(i);

            // Revert changes to N/A label to reapply pattern of spaces needed
            if (temp.contains("N/A"))
                priceLabels.set(i, " N/A ");
            else
                priceLabels.set(i, priceLabels.get(i).replaceAll(" ", ""));

            temp = priceLabels.get(i);
            alternatingNum = 0;
            /*  For this case, our expression (BOX_WIDTH/2) + 1 gives us 5, adding maxLenMarginPrice gives us back
                The .length() of the price label that has the longest string.
                
                While loop keeps looping until the length of the selected priceLabel string of temp is at least equal 
                to the priceLabel with the longest string
            */
            while(temp.length() < (BOX_WIDTH/2) + 1 + maxLenMarginPrice)
            {
                // An alternating pattern of putting space left and right of the text
                if(alternatingNum%2 == 0)
                {
                    priceLabels.set(i," " + temp);
                    temp = priceLabels.get(i);
                }
                else
                {
                    priceLabels.set(i, temp + " ");
                    temp = priceLabels.get(i); 
                }
                alternatingNum++;

            }

            // For cases when maxLenMargin is just 1 or 0, just add a space
            if(maxLenMarginPrice <= 1 )
                priceLabels.set(i, temp + " ");
            



            // priceLabels update colors
            temp = priceLabels.get(i) ;
            priceLabels.set(i, "\033[1;33m" + temp + "\033[0m");    // Colors yellow 


            // Increment for the next price label to check
            i++;  
        }
    }

    /**This text will be shown as the name of the vending machien */
    private String vmName;
    /**Labels of identified items in the vending machine */
    private ArrayList<String> stringLabels;
    /**Price labels of identified items in the vending machine*/
    private ArrayList<String> priceLabels;
    /**Minimum box height of the vending machine slots*/
    private static final int BOX_HEIGHT = 5;
    /**Minimum box width of the vending machine slots */
    private static final int BOX_WIDTH = 9;
    /**The default price label when no item is present in the slot */
    private static final String DEFAULT_PRICE = "N/A";
    /**The margin of adjustment accounted for the price labels*/
    private int maxLenMarginPrice;

}
