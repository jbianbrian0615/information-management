package scis.model;
public class Products {

    private String prodid;
    private String description;
    private double price;

    public Products() {
    }

    public Products(String prodid, String description, double price) {
        this.prodid = prodid;
        this.description = description;
        this.price = price;
    }


    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return prodid;
    }
}