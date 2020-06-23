package scis;

import scis.model.Products;

import java.util.ArrayList;

public class SampleSaveAndGet {
    public static void main(String[] args) {
        Products product = new Products("Soap", "Body Soap", 50.0);
        saveNewProduct(product);
        getListOfProducts();
    }

    private static void getListOfProducts() {
        Data.setConnection();
        ArrayList<Products> products = Data.getProducts();
        try {
            for (Products pr : products) {
                printProduct(pr);
            }
            Data.DbDone();
        } catch (Exception e) {
            printMessage("An error occurred!\n" + e.getMessage());
        }
    }

    private static void saveNewProduct(Products product) {
        Data.setConnection();
        String output = Data.saveProduct(product);
        try {
            if (output.equals("")) {
                printMessage("Successfully Saved!");
            } else {
                printMessage("Failed to Save!");
            }
            Data.DbDone();
        } catch (Exception e) {
            printMessage("An error occurred!\n" + e.getMessage());
        }
    }

    private static void printProduct(Products pr) {
        String description = pr.getDescription();
        String prId = pr.getProdid();
        double price = pr.getPrice();
        String prInfo = String.format("(Product ID: %s)\t(Product Price: %.2f)\t(Product Description: %s)", prId, price, description);
        printMessage(prInfo);
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
