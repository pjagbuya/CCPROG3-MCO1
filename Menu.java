import java.util.Scanner;

public class Menu
{
    public Menu()
    {

    }

    public int gotoStartScreen()
    {

        Scanner sc = new Scanner(System.in);

        int userInputInt;




        userInputInt = -1;

        while( userInputInt == -1)
        {
            displayChoices();
            System.out.print("Input a choice(1-5): ");
             userInputInt = sc.nextInt();
             
    
            switch(userInputInt)
            {
                case 1:
                    System.out.println("\tNow going to Creating Regular Vending Machine...");
                    break;
                case 2:
                    System.out.println("\tLorem Ipsum Make Special Vending Machine");
                    break;
                case 3:
                    System.out.println("\tLorem Ipsum Test Vending Features");
                    break;
                case 4:
                    System.out.println("\tLorem Ipsum Test Maintenance Features");
                    break;
                case 5:
                    System.out.println("\tNow exiting...");
                    break;
                default:
                    System.out.println("\033[1;38;5;202mERROR! Choice misinput, please select 1-5\033[0m");
                    System.out.println();
            }
        }

        
        return userInputInt;
    }

    // Assuming that the slot itemCap is reached
    public VM_Regular gotoMakeVendingMachine()
    {
        Scanner sc = new Scanner(System.in);
        boolean isMakingVM;


        VM_Regular vmMachine;
        
        String name;
        String userInputYN;
        
        int slotCap;
        int itemCap;


        isMakingVM = true;
        itemCap = 0;
        slotCap = 0;
        vmMachine = null;
        while(isMakingVM)
        {
            System.out.print("Name of this regular vending machine: ");
            name = sc.nextLine();
            System.out.printf("How many slots at most in your vending machine \033[1;36m(>= %d)\033[0m: ", VM_Regular.getMinSLOTS()); 
            slotCap = sc.nextInt();
            sc.nextLine();          // remove newlines

            System.out.printf("How many items at most per slot \033[1;36m(>= %d)\033[0m: ", VM_Regular.getMaxITEMS());
            itemCap = sc.nextInt();
            sc.nextLine();

            vmMachine = new VM_Regular(name, slotCap, itemCap);

            isMakingVM = false;

            if(itemCap < VM_Regular.getMaxITEMS() || slotCap < 2) // swap 2 with VM_Regular.getMinSLOTS()
            {
                slotCap = VM_Regular.getMinSLOTS();
                itemCap = VM_Regular.getMaxITEMS();
                System.out.printf("Overwriting your set slots and items cap, slot capacity to \033[1;36m%d\033[0m and item capacity of slots to \033[1;36m%d\033[0m\n", 
                                    VM_Regular.getMinSLOTS(), VM_Regular.getMaxITEMS());
                System.out.print("Do you want to redo making this regular vending machine (Y/N): ");
                userInputYN = sc.nextLine();
                
                if(userInputYN.equalsIgnoreCase("y") || userInputYN.substring(0, 1).equalsIgnoreCase("y"))
                    isMakingVM = true;
                    
            }

        }

        if(vmMachine != null)
            fillItems(vmMachine, slotCap);


 



        return vmMachine;


    }

    private void fillItems(VM_Regular vm, int slotCap)
    {
        Scanner sc = new Scanner(System.in);
        VM_Item newItem;
        VM_Slot[] slots;

        String name;
        double price;
        int calories;
        int stock;

        int i;         // Counter of slots to fill in items

        slots = vm.getSlots();
        i = 0;
        System.out.println();
        System.out.println("Fill in items in the Vending Machine");
        while(i < slotCap)
        {
           

            System.out.printf("Filling for Slot %d: \n", i + 1);
            System.out.print("Name: ");
            name = sc.nextLine();
            
            System.out.print("Price: ");
            price = sc.nextDouble();
            sc.nextLine();          // remove newline char


            System.out.print("Calories: ");
            calories = sc.nextInt();
            sc.nextLine();

           newItem = new VM_Item(name, price, calories);


           System.out.printf("How many stocks of %s for slot %d: ", newItem.getItemName(), i + 1);
           stock = sc.nextInt();
           sc.nextLine();
           slots[i].addItemStock(newItem, stock);

           i++;

        }

  

    }


    private void displayChoices()
    {
        System.out.print("\033[1;33m");
        System.out.println("Choices:");
        System.out.println("\t[1] Make Regular Vending Machine");
        System.out.println("\t[2] Make Special Vending Machine");
        System.out.println("\t[3] Test Vending Features");
        System.out.println("\t[4] Test Maintenance Features");
        System.out.println("\t[5] Exit");
        System.out.println();
         System.out.print("\033[0m");
    }

}