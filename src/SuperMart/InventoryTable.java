package SuperMart;

public class InventoryTable {

    String Quantity;
    String Price;
    String ProductID;
    String CostPrice;
    String Category;

    public InventoryTable(String Quantity, String Price, String ProductID, String CostPrice, String Category){

        this.Quantity = Quantity;
        this.Price = Price;
        this.ProductID = ProductID;
        this.CostPrice = CostPrice;
        this.Category=Category;
    }

    public String getQuantity(){
        return Quantity;
    }

    public void setQuantity(String Quantity){
        this.Quantity = Quantity;
    }

    public String getPrice(){
        return Price;
    }

    public void setPrice(String Price){
        this.Price = Price;
    }

    public String getProductID(){
        return ProductID;
    }

    public void setProductID(String ProductID){
        this.ProductID = ProductID;
    }

    public String getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(String costPrice) {
        CostPrice = costPrice;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
