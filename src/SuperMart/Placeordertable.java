package SuperMart;

public class Placeordertable {

    String ProductDetail;
    String Quantity;

    Placeordertable(String ProductDetail, String Quantity){
        this.ProductDetail=ProductDetail;
        this.Quantity=Quantity;
    }

    public String getProductDetail() {
        return ProductDetail;
    }

    public void setProductDetail(String productDetail) {
        ProductDetail = productDetail;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
