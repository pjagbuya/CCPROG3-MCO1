import java.util.ArrayList;

public class VM_Draw {

    private ArrayList<String> stringLabels;
    private ArrayList<String> priceLabels;
    private static final int BOX_HEIGHT = 5;
    private static final int BOX_WIDTH = 9;

    public VM_Draw(VM_Regular vmMachine){

        int i;
        VM_Slot[] slots = vmMachine.getSlots();
        String temp;
        double price;

        stringLabels = new ArrayList<String>();
        priceLabels = new ArrayList<String>();
        System.out.println(slots.length);
        for(i = 0; i < slots.length; i++)
        {
            if(slots[i] != null && slots[i].getSlotItemStock() > 0)
            {
                if(slots[i].getSlotItemName().length() >= 3)
                    stringLabels.add(i, slots[i].getSlotItemName().substring(0, 3));
                else
                    stringLabels.add(i, slots[i].getSlotItemName().substring(0, 2) + " ");
                price = slots[i].getItem().getItemPrice();
                priceLabels.add(i, price + "0");
                if(priceLabels.get(i).length() < 5)
                {
                    
                    temp = priceLabels.get(i);
                    priceLabels.add(i, " " + temp);
                }

                temp = priceLabels.get(i) ;
                priceLabels.set(i, "\033[1;33m " + temp + "\033[0m");

                temp = stringLabels.get(i);
                stringLabels.set(i, "\033[1;32m" + temp + "\033[0m");
                

            }

            else
            {   


                stringLabels.add(i, "\033[1;31m" + "XXX" + "\033[0m");
                priceLabels.add(i, " N/A ");
            }
                
        }
        



    }
    public void updateVM(VM_Regular vmMachine)
    {
        VM_Slot[] slots = vmMachine.getSlots();
        String subName;
        int i;

        
        for (i = slots.length-1; i >= 0; i--)
        {   

        

            if(slots[i].getSlotItemName().length() >= 3)
                subName = slots[i].getSlotItemName().substring(0, 3);
            else
                subName = slots[i].getSlotItemName().substring(0, 2);
            

            // If slot is empty or no stock, indicate red text
            if((slots[i].getSlotItemStock() == 0 || slots[i].getItem() == null) && 
                stringLabels.get(i).equalsIgnoreCase(subName))
            
                
                stringLabels.set(i, stringLabels.get(i).replace("\033[1;32m", "\033[1;31m"));
                
            // If slot is replaced and has stock, indicate new green text
            else if(!stringLabels.get(i).equalsIgnoreCase(subName) && slots[i].getSlotItemStock() > 0)
            {
                stringLabels.set(i, "\033[1;33m" + subName + "\033[1;31m" + " ");
            }
            
        }
    }
    public void drawAndSetVM()
    {
        int stringInd;
        int priceInd;


        stringInd = 0;
        priceInd = 0;
        System.out.println("Prices in \033[1;33mPhp\033[0m");
        System.out.println("Out of stock means \033[1;31mRED\033[0m");
        System.out.println("In stock means \033[1;32mGREEN\033[0m");
        
        for (int i = 0; i < (int)Math.ceil(stringLabels.size()/3.0)*BOX_HEIGHT; i++) 
        {


            for (int j = 0; j < 3*BOX_WIDTH; j++) 
            {
                
                if( i % 5 == 0 || i % 5 == 4)
                {
                    if(j % 9 == 0 || j % 9 == 8)
                    {
                        System.out.print("+");
                    } else {
                        System.out.print("-");
                    }
                    
                }
                else{
                    if(j % 9 == 0 || j % 9 == 8)
                        System.out.print("|");
                    else if(i % 5 == 1 && j % 9 == 3){
                        if(stringInd < stringLabels.size())
                            System.out.print(stringLabels.get(stringInd));
                        else
                            System.out.print("   ");
                        j += 2; // alots the next two characters for the two characters of this word
                        stringInd++;
                    }
                    else if(i % 5 == 1 && (j % 9 < 3 || j % 9 > 5)){
                        System.out.print(" ");
                    } 
                    else if(i % 5 == 3 && j % 9 == 1)
                    {   
                        if(priceInd < priceLabels.size())
                        {
                            if(priceLabels.get(priceInd).length() < 6 ){
                                System.out.print(" ");
                            }
                            
                            
                            System.out.print(priceLabels.get(priceInd));
                            priceInd++;
                        }
                        else
                        {
                            System.out.print("      ");
                        }

                        j += 5;

                    }
                    else{
                        System.out.print(" ");
                    }



                }


            

                
                
            }

            System.out.println();
        }

      
    }
}
