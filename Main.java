import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;

/*
	javac Main.java && javac Money.java && javac VM_Slot.java && javac VM_Regular.java && javac VM_Item.java
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
		
		
		
		int[] quantities = new int[2];
		quantities[0] = 1;
		quantities[1] = 3;

		Order orderPaul = new Order();
		Money moneyPaul = new Money(true);	// boolean is for testing
		moneyPaul.addBillsOrCoins(200, 1);
		moneyPaul.addBillsOrCoins(50, 1);
		
		double paymentTotal = 0;
		LinkedHashMap<String, Integer> duplicate = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> currentMoneyDenom = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> payment = new LinkedHashMap<String, Integer>();
		
		// initialization of VM and how many slots it can hold
		VM_Regular vm = new VM_Regular(2, 10);

		VM_Item milk = new VM_Item("Milk", 27.00, 42);
		VM_Item c2 = new VM_Item("C2", 20.00, 42);
		


		// initialization of slots and their contents
		// VM_Slot milkSlot = new VM_Slot(milk, 6);
		// VM_Slot c2Slot = new VM_Slot(c2, 10);


		// milkSlot.addItemStock(milkSlot, 3);
		// c2Slot.addItemStock(c2Slot, 2)
		
		// STOCKING
		vm.setSlot(milk, 3, 0);
		vm.setSlot(c2, 3, 1);
		

		// PAUL ORDERS
		orderPaul.addOrder(vm.getSlot("Milk"), 1);
		orderPaul.addOrder(vm.getSlot("C2"), 3);
		

		// Display all available items
		vm.displayAllItems();


		
		



		// duplicating denomination hashmap of VM, while setting payment denominations to zero
		// currentMoneyDenom = vm.getDenominations();

		
		// // DENOMINATIONS FOR PAUL
		// for(String i : currentMoneyDenom.keySet()) {
		// 	duplicate.put(i, currentMoneyDenom.get(i));
		// 	payment.put(i, 0);
		// }
		
		// // setting payment to 1 pc. of Fifty Bill
		// payment.put("One Hundred Bill", 1);
		
		

		// //display duplicate of denomination hashmap of VM
		System.out.println("Paul's money");
		for( Map.Entry m : moneyPaul.getDenominations().entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());

		}


		
		//calculating payment total
		// for(String i : payment.keySet()){
		// 	paymentTotal += payment.get(i)*Money.strToVal.get(i);
		// }

		System.out.println("VM money");
		for( Map.Entry m : vm.getCurrentMoney().getDenominations().entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());

		}
		
		
		// calculating and displaying total cost of order
		// System.out.println("Total Cost: " + vm.computeTotalCost(quantities) + "\n");
		System.out.println("Total Cost: " + orderPaul.getTotalCost() + "\n");

		
		System.out.println("Payment Total: " + moneyPaul.getTotalMoney());
		// checks if transaction is valid
		vm.addDenominations(moneyPaul.getDenominations());
		System.out.println("Total Reserves after this payment: " + (vm.getTotalOfMoneyReserves() - orderPaul.getTotalCost()));

		
		paymentTotal = moneyPaul.getTotalMoney();

		LinkedHashMap<String, Integer> tempHashMap = moneyPaul.getDenominations();

		if(	vm.hasEnoughStock(orderPaul) &&
			( (vm.getTotalOfMoneyReserves() - orderPaul.getTotalCost()) >= 0)
			 && vm.canGiveChange(orderPaul, moneyPaul) ) // user is assumed to insert a 250 peso total // a;so messed up
		{
			System.out.println("Paul's money is" +  paymentTotal);
			System.out.println("\nTRANSACTION PROCEEDS-------");
			// Vending machine 
			System.out.println("Paul's money is" +  moneyPaul.getTotalMoney());
			vm.acceptOrder(orderPaul, moneyPaul);
			// vm.updateMoneyWithChange(orderPaul, moneyPaul);

			
			
			

		}
		else
		{
			System.out.println("\nCANNOT TRANSACT--------------");
			vm.subtractBillsOrCoins(moneyPaul);
		}

			
		
		vm.displayAllItems();
		
		//display duplicate of denomination hashmap
		for( Map.Entry m : vm.getDenominations().entrySet() ) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
		System.out.println("Total Reserves: " + vm.getTotalOfMoneyReserves());
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

