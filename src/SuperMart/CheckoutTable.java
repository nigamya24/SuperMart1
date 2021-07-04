package SuperMart;

public class CheckoutTable {

    //int Price;
    String ProductID;
    String Quantity;
    String Price;
    String Total;


    public CheckoutTable(String ProductID, String Quantity, String Price,String Total)
    {
        this.ProductID=ProductID;
        this.Quantity=Quantity;
        this.Price=Price;
        this.Total=Total;
    }

    public String getProductID(){
        return ProductID;
    }

    public void setProductID(String ProductID){
        this.ProductID = ProductID;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotal(){
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
