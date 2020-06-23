package scis.model;

import java.util.Date;
import java.util.List;

public class Payments {
    private int orno;
    private Date paydate;
    private double amount;
    private Integer custid;
    private List<PaymentDetails> paymentDetailsList;

    public Payments() {
    }

    public Payments(int orno, Date paydate, double amount, Integer custid, List<PaymentDetails> paymentDetailsList) {
        this.orno = orno;
        this.paydate = paydate;
        this.amount = amount;
        this.custid = custid;
        this.paymentDetailsList = paymentDetailsList;
    }

    public int getOrno() {
        return orno;
    }

    public void setOrno(int orno) {
        this.orno = orno;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public List<PaymentDetails> getPaymentDetailsList() {
        return paymentDetailsList;
    }

    public void setPaymentDetailsList(List<PaymentDetails> paymentDetailsList) {
        this.paymentDetailsList = paymentDetailsList;
    }
}
