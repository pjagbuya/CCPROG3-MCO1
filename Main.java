import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java && javac MainDisplay.java && javac Order.java
 */

/**
 * Citations:
 * https://ascii-generator.site/t/
 */

public class Main{
    public static void main(String[] args) {
		MainDisplay mDisplay = new MainDisplay();
		mDisplay.displayWelcome();
		/*
        Main md = new Main();
        Scanner sc = new Scanner(System.in);
        int userInt;
        
		
		
        // VM_Draw draw = new VM_Draw(null, 0);
        userInt = 0;

        // draw.drawAndSetVM();

        VM_Slot slotMilk = new VM_Slot(new VM_Item("Milk", 27.00, 42), 6);
        slotMilk.addStock(8);
        slotMilk.dislpayAllItems();
	

        while(userInt != 5){
            md.displayChoices();
            System.out.print("Enter a choice(1-5): ");
            userInt = sc.nextInt();
    
            switch(userInt){
                case 1:
                    System.out.println("\tLorem Ipsum make Regular Vending Machine");
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
                    System.out.println("\tNow exiting");
                    break;
            }
        }
		*/
		
		// all the hashmaps :>
		LinkedHashMap<String, Integer> duplicate = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> payment = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> change = new LinkedHashMap<String, Integer>();
		//LinkedHashMap<String, Integer> order = new LinkedHashMap<String, Integer>();
		Order order = new Order();
		
		// initialization of VM and how many slots it can hold
		VM_Regular vm = new VM_Regular(2, 10);

		// slot initialization
		VM_Item milk = new VM_Item("Milk", 27.00, 42);
		VM_Item c2 = new VM_Item("C2", 20.00, 42);

		vm.setSlot(milk, 3, 0);
		vm.setSlot(c2, 3, 1);

	
	
		VM_Draw vmArt = new VM_Draw(vm);								//added

	
		vmArt.drawAndSetVM();											//added
		
		if(vm.isThisValid())		// Check if all slots are filled	// added
			vm.addToStockedInfo();	// save stock info					// added


		
		// display VM's initial stock
		vm.displayAllItems();
		
		// testing the other method for adding VM's cash reserves
		System.out.println("Adding one more One Thousand Bill...\n");
		vm.addBillsOrCoins(1000, 1);
		
		// run VM's in selling mode (simulates user buying from the VM)
		vm.sellingOperation(duplicate, payment, change, order);

		
		vm.displayAllStockInfo();										//added

		vmArt.updateVM(vm);												//added - updates to see if things are stocked or out of stock
		vmArt.drawAndSetVM();											//added
		
    }

	/*
    public void displayChoices(){
        Paint.turnOnYellow();
        System.out.println("Choices:");
        System.out.println("\t[1] Make Regular Vending Machine");
        System.out.println("\t[2] Make Special Vending Machine");
        System.out.println("\t[3] Test Vending Features");
        System.out.println("\t[4] Test Maintenance Features");
        System.out.println("\t[5] Exit");
        System.out.println();

        Paint.turnOffColor();
    }
	*/
}

