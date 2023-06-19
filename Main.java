import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java
 */

public class Main{
    public static void main(String[] args) {
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
		
		
		
		int[] quantities = new int[1];
		quantities[0] = 1;
		double paymentTotal = 0;
		LinkedHashMap<String, Integer> duplicate = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> currentMoneyDenom = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> payment = new LinkedHashMap<String, Integer>();
		
		VM_Regular vm = new VM_Regular(1);
		VM_Slot milkSlot = new VM_Slot(new VM_Item("Milk", 27.00, 42), 6);
		milkSlot.addStock(3);
		
		vm.setSlot(milkSlot, 0);
		
		vm.displayAllItems();
		
		// duplicating denomination hashmap of VM, while setting payment denominations to zero
		currentMoneyDenom = vm.getDenominations();
		for(String i : currentMoneyDenom.keySet()) {
			duplicate.put(i, currentMoneyDenom.get(i));
			payment.put(i, 0);
		}
		
		// setting payment to 1 pc. of Fifty Bill
		payment.put("Fifty Bill", 1);
		
		//display duplicate of denomination hashmap of VM
		for( Map.Entry m : duplicate.entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
		
		//calculating payment total
		for(String i : payment.keySet()){
			paymentTotal += payment.get(i)*Money.strToVal.get(i);
		}
		
		System.out.println("Payment Total: " + paymentTotal);
		// checks if transaction is valid
		if(	vm.hasEnoughStock(quantities) &&
			(vm.getTotalOfMoneyReserves() - vm.computeTotalCost(quantities) >= 0) &&
			vm.canGiveChange(paymentTotal - vm.computeTotalCost(quantities), duplicate) ) // user is assumed to insert a 50 peso bill
		{
			System.out.println("\nTRANSACTION PROCEEDS-------");
			System.out.println("Total Cost: " + vm.computeTotalCost(quantities) + "\n");
			vm.releaseStock(quantities);
			vm.setDenominations(duplicate);
			vm.addDenominations(payment);
		}
		else
			System.out.println("CANNOT TRANSACT--------------\n");
		
		vm.displayAllItems();
		
		//display duplicate of denomination hashmap
		for( Map.Entry m : vm.getDenominations().entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
		vm.getTotalOfMoneyReserves();
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

