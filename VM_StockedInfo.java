import java.util.LinkedHashMap;
import java.util.Map;

public class VM_StockedInfo {

    
    public VM_StockedInfo(VM_Regular vmMachine)
    {
        money = vmMachine.getMoney();
        itemNamesAndStock = vmMachine.getItemNamesAndStock();

    }

    public Money getMoney() {
        return money;
    }

    public LinkedHashMap<String, Integer> getItemNamesAndStock() {
        return itemNamesAndStock;
    }

    // Move this to VM_Regular
    private void displayAllInfo()
    {
        for(Map.Entry<String,Integer> tempNamesAndStock: itemNamesAndStock.entrySet())
        {
            System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", " Item Name ", "Item Init Stock ", "Items Sold", " Items in Stock", "Profit Collected");
            System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", tempNamesAndStock.getKey(), tempNamesAndStock.getKey().toString());
            System.out.printf("\t| %20s | %20s | %11s | %20s | %20s \n", tempNamesAndStock.getKey(), tempNamesAndStock.getKey().toString());
        }
    }

    private LinkedHashMap<String, Integer> itemNamesAndStock;
    private Money money;
}
