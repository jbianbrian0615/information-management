
package scis;


import scis.model.Customers;
import scis.model.Products;
import scis.model.Sales;
import scis.model.SalesDet;

import java.sql.*;
import java.util.ArrayList;

public class Data {
    private static Connection con;
    private static Integer lastid;

    private Data() {
        // private constructor
    }

    public static void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String conStr = "jdbc:mysql://localhost:3306/thestore?user=root&password=";
            con = DriverManager.getConnection(conStr);
            System.out.println("connected");
            lastid = 0;
            setLastCust();
        } catch (Exception e) {
            System.out.println("bad connection");
        }
    }

    public static String saveProduct(Products newP) {
        String s = "";
        PreparedStatement ps, psc;
        String st = "INSERT INTO products(prodid, description, price)  VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(st);
            ps.setString(1, newP.getProdid());
            ps.setString(2, newP.getDescription());
            ps.setDouble(3, newP.getPrice());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException se) {
            s = se.getErrorCode() + " " + se.getMessage();
        } catch (Exception e) {
            s = e.getMessage();
        }
        return s;
    }

    public static String saveCustomer(Customers newC) {
        String s = "";
        PreparedStatement ps, psc;
        String st = "INSERT INTO customers(custid, custname, address, telno)  VALUES (?,?,?,?)";
        try {
            lastid = lastid + 1;
            ps = con.prepareStatement(st);
            ps.setInt(1, lastid);
            ps.setString(2, newC.getCustname());
            ps.setString(3, newC.getAddress());
            ps.setString(4, newC.getTelno());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException se) {
            s = se.getErrorCode() + " " + se.getMessage();
        } catch (Exception e) {
            s = e.getMessage();
        }
        return s;
    }

    public static ArrayList<Customers> getCustomers() {
        ArrayList<Customers> ca = new ArrayList<>();
        Customers cust = new Customers();
        try {
            Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = st.executeQuery("Select * from customers order by custname");
            rs.beforeFirst();
            while (rs.next()) {
                cust = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5));
                ca.add(cust);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {

        }
        return ca;
    }

    public static ArrayList<Products> getProducts() {
        ArrayList<Products> pa = new ArrayList<>();
        try {
            Statement st = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
            ResultSet rs = st.executeQuery("Select * from products order by description");
            rs.beforeFirst();
            while (rs.next()) {
                Products prod = new Products(rs.getString(1), rs.getString(2), rs.getDouble(3));
                pa.add(prod);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return pa;
    }


    public static String saveSales(Sales newS) {

        String s = "";
        PreparedStatement ps, psc;
        String stsa = "INSERT INTO sales(invid, invdate, custid)  VALUES (?,?,?)";
        String stsd = "INSERT INTO salesdetails(invid, prodid, qtysold, unitprice)  VALUES (?,?,?,?)";
        try {
            //update sales header
            ps = con.prepareStatement(stsa);
            //transaction processing
            ps.setString(1, newS.getInvid());
            ps.setDate(2, new Date(newS.getInvdate().getTime()));
            ps.setInt(3, newS.getCustid());
            ps.execute();
            //update sales details
            ps.close();
            ps = con.prepareStatement(stsd);
            ArrayList<SalesDet> salesdets = newS.getSalesDet();
            for (SalesDet sdet : salesdets) {
                ps.setString(1, newS.getInvid());
                ps.setString(2, sdet.getProdid());
                ps.setInt(3, sdet.getQtysold());
                ps.setDouble(4, sdet.getUprice());
                ps.execute();
            }
            ps.close();
        } catch (SQLException se) {
            s = se.getErrorCode() + " " + se.getMessage();
        } catch (Exception e) {
            s = e.getMessage();
        }
        return s;
    }

    private static void setLastCust() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select max(custid) from customers");
            rs.beforeFirst();
            while (rs.next()) {
                lastid = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {

        }
    }

    public static Integer getLastid() {
        return lastid;
    }

    public static void DbDone() throws Exception {
        if (con != null) {
            con.close();
            System.out.println("connection closed");
        }
    }

    public static ArrayList<Products> searchProducts(String name) {
        return searchProducts(name, "prodid", "ASC");
    }

    public static ArrayList<Products> searchProducts(String name, String orderBy, String direction) {
        ArrayList<Products> pa = new ArrayList<>();

        boolean isSearchTextEmpty = (name == null || name.trim().isEmpty());
        String query;

        if (isSearchTextEmpty) {
            query = String.format("Select * from products order by %s %s", orderBy, direction);
        } else {
            query = String.format("Select * from products where description LIKE ? OR prodid LIKE ? order by %s %s", orderBy, direction);
        }

        try {
            PreparedStatement st = con.prepareStatement(query, ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
            if (!isSearchTextEmpty) {
                st.setString(1, "%" + name + "%");
                st.setString(2, "%" + name + "%");
            }

            ResultSet rs = st.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                Products prod = new Products(rs.getString(1), rs.getString(2), rs.getDouble(3));
                pa.add(prod);
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return pa;
    }
}