package scis.model;

public class Customers {
    private Integer custid;
    private String custname;
    private String address;
    private String telno;
    private double balance;

    public Customers() {
    }

    public Customers(Integer custid, String custname, String address, String telno, double balance) {
        this.custid = custid;
        this.custname = custname;
        this.address = address;
        this.telno = telno;
        this.balance = balance;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return custname;
    }
}
