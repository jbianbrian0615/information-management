package scis.model;

import java.util.ArrayList;
import java.util.Date;

public class Sales {
    private String invid;
    private Date invdate;
    private double amount;
    private double balance;
    private int custid;
    private Customers customer;
    private ArrayList<SalesDet> salesdet;

    public Sales() {
        salesdet = new ArrayList<SalesDet>();
    }

    public Sales(String invid, Date invdate, int custid) {
        salesdet = new ArrayList<SalesDet>();
        this.invid = invid;
        this.invdate = invdate;
        this.custid = custid;
    }

    public Sales(String invid, Date invdate, Customers customer) {
        salesdet = new ArrayList<SalesDet>();
        this.invid = invid;
        this.invdate = invdate;

        if (customer != null)
            this.custid = customer.getCustid();
        this.customer = customer;
    }

    public String getInvid() {
        return invid;
    }

    public void setInvid(String invid) {
        this.invid = invid;
    }

    public Date getInvdate() {
        return invdate;
    }

    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public Customers getCustomer() {
        return customer;
    }

    public ArrayList<SalesDet> getSalesdet() {
        return salesdet;
    }

    public void setSalesdet(ArrayList<SalesDet> salesdet) {
        this.salesdet = salesdet;
    }

    public void addSalesdet(SalesDet sd) {
        salesdet.add(sd);
    }

    public ArrayList<SalesDet> getSalesDet() {
        ArrayList<SalesDet> sdets = new ArrayList<>();
        salesdet.stream().forEach((sd) -> {
            sdets.add(sd);
        });
        return sdets;
    }
}
