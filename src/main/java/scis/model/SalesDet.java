package scis.model;

public class SalesDet {
    private String prodid;
    private int qtysold;
    private double uprice;

    public SalesDet(String prodid, int qtysold, double uprice) {
        this.prodid = prodid;
        this.qtysold = qtysold;
        this.uprice = uprice;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public int getQtysold() {
        return qtysold;
    }

    public void setQtysold(int qtysold) {
        this.qtysold = qtysold;
    }

    public double getUprice() {
        return uprice;
    }

    public void setUprice(double uprice) {
        this.uprice = uprice;
    }
}