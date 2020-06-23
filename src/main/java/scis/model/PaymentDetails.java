package scis.model;

public class PaymentDetails {
    private String invid;
    private Double amtapplied;

    public PaymentDetails() {
    }

    public PaymentDetails(String invid, Double amtapplied) {
        this.invid = invid;
        this.amtapplied = amtapplied;
    }

    public String getInvid() {
        return invid;
    }

    public void setInvid(String invid) {
        this.invid = invid;
    }

    public Double getAmtapplied() {
        return amtapplied;
    }

    public void setAmtapplied(Double amtapplied) {
        this.amtapplied = amtapplied;
    }
}
