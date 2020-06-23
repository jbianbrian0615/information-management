package scis;

import scis.model.Products;

import javax.xml.crypto.NoSuchMechanismException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AskUserInput {

    public static void main(String[] args) {

        System.out.println("===== WELCOME TO DATABASE CONSOLE APPLICATION =====\n\n");
        Scanner scanner = new Scanner(System.in);
        showTheOptions();
        String userInput = scanner.nextLine();
        String output = "\nOUTPUT LIST:";
        while (true) {
            userInput = userInput.toUpperCase();
            try {
                switch (userInput) {
                    case "Q":
                        printMessage("Thank you for using this application!");
                        System.exit(1);
                        break;
                    case "C":
                        Products product = captureUserInput(scanner);
                        if (product == null) {
                            printMessage("You need to input character for [name/description] and decimal number for [price]");
                            continue;
                        }
                        saveNewProduct(product);
                        break;
                    case "S":
                        ArrayList<Products> sortedList = sortProducts(scanner);
                        printMessage(output);
                        printProducts(sortedList);
                        break;
                    case "F":
                        ArrayList<Products> foundList = findProducts(scanner);
                        printMessage(output);
                        printProducts(foundList);
                        break;

                    case "M":
                        ArrayList<Products> fsList = findAndSort(scanner);
                        printMessage(output);
                        printProducts(fsList);
                        break;

                    default:
                        ArrayList<Products> allList = getAllProducts();
                        printMessage(output);
                        printProducts(allList);
                }
            } catch (IllegalArgumentException e) {
                printMessage("YOU HAVE ENTER A WRONG INPUT, TRY AGAIN PLEASE");
                showTheOptions();
                continue;
            }catch(NoSuchMechanismException e){

            }


            showTheOptions();
            userInput = scanner.nextLine();
        }

    }

    //**** START CREATE PRODUCT****
    private static Products captureUserInput(Scanner scanner) {
        Products product = null;
        try {
            System.out.println("Please enter the product name:");
            String prName = scanner.nextLine();
            System.out.println("Now enter the product price:");
            double prPrice;
            try {
                String prPriceStr = scanner.nextLine();
                prPrice = Double.parseDouble(prPriceStr);
            } catch (NumberFormatException e) {
                throw iae();
            }
            System.out.println("Explain what is this product for:");
            String prDesc = scanner.nextLine();
            product = new Products(prName, prDesc, prPrice);
            checkString(prName, prDesc);
        } catch (InputMismatchException e) {
            throw iae();
        }
        return product;
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
    //**** END CREATE PRODUCT****

    //**** START - SEARCHING AND SORTING PRODUCT****
    private static ArrayList<Products> findAndSort(Scanner scanner) {
        String searchText = getSearchText(scanner);
        String sortingColumn = getSortingColumn(scanner);
        String sortingDirection = getSortingDirection(scanner);
        Data.setConnection();
        ArrayList<Products> list = Data.searchProducts(searchText, sortingColumn, sortingDirection);
        try {
            Data.DbDone();
        } catch (Exception e) {
            throw nsm(e.getMessage());
        }
        return list;
    }
    //**** END - SEARCHING AND SORTING PRODUCT****

    //**** START - ALL PRODUCT****
    private static ArrayList<Products> getAllProducts() {
        Data.setConnection();
        ArrayList<Products> products = Data.getProducts();
        try {
            Data.DbDone();
        } catch (Exception e) {
            printMessage("An error occurred!\n" + e.getMessage());
        }
        return products;
    }
    //**** END - ALL PRODUCT****

    //**** START - SORTING PRODUCT****
    private static ArrayList<Products> sortProducts(Scanner scanner) {
        String sortingColumn = getSortingColumn(scanner);
        String direction = getSortingDirection(scanner);
        Data.setConnection();
        ArrayList<Products> list = Data.searchProducts(null, sortingColumn, direction);
        try {
            Data.DbDone();
        } catch (Exception e) {
            throw nsm(e.getMessage());
        }
        return list;
    }

    private static String getSortingColumn(Scanner scanner) {
        printMessage("Press 1 to sort by [name]");
        printMessage("Press 2 to sort by [price]");
        printMessage("Press 3 to sort by [description]");
        String option = scanner.nextLine();
        String dbColumn;
        switch (option) {
            case "1":
                dbColumn = "prodid";
                break;
            case "2":
                dbColumn = "price";
                break;
            case "3":
                dbColumn = "description";
                break;

            default:
                throw new IllegalArgumentException();
        }

        return dbColumn;
    }

    private static String getSortingDirection(Scanner scanner) {
        printMessage("Press 1 to sort in ascending order [ASC]");
        printMessage("Press 2 to sort in descending order [DESC]");
        String option = scanner.nextLine();
        String direction;
        switch (option) {
            case "1":
                direction = "ASC";
                break;
            case "2":
                direction = "DESC";
                break;
            default:
                throw iae();
        }

        return direction;
    }
    //**** END - SORTING PRODUCT****

    //**** START - SEARCHING PRODUCT****
    private static ArrayList<Products> findProducts(Scanner scanner) {
        String searchText = getSearchText(scanner);
        Data.setConnection();
        ArrayList<Products> products = Data.searchProducts(searchText);
        try {
            Data.DbDone();
        } catch (Exception e) {
            throw nsm(e.getMessage());
        }
        return products;
    }

    private static String getSearchText(Scanner scanner) {
        printMessage("Please enter your search text");
        String searchKeyboard = scanner.nextLine();

        if (searchKeyboard == null || searchKeyboard.trim().isEmpty())
            throw iae();

        return searchKeyboard;
    }
    //**** START - SEARCHING PRODUCT****

    private static void showTheOptions() {
        printMessage("");
        printMessage("Press Q to stop the application");
        printMessage("Enter C to create a new product");
        printMessage("Enter S for getting a sorted list");
        printMessage("Enter F to find a product");
        printMessage("Enter M for a mixture of finding and sorting");
        printMessage("Enter other keys for getting the whole list of products\n");
    }

    private static void checkString(String... strings) {
        if (strings == null)
            throw iae();

        for (String str : strings) {
            if (str == null || str.trim().isEmpty())
                throw iae();
        }
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printProducts(ArrayList<Products> prs) {
        if (prs == null)
            throw iae();

        for (Products pr : prs) {
            String description = pr.getDescription();
            String prId = pr.getProdid();
            double price = pr.getPrice();
            String prInfo = String.format("(Product ID: %s)\t(Product Price: %.2f)\t(Product Description: %s)", prId, price, description);
            printMessage(prInfo);
        }
    }

    private static IllegalArgumentException iae(String... argument) {
        if (argument == null)
            return new IllegalArgumentException();
        else{
            return new IllegalArgumentException(Arrays.asList(argument).toString());
        }
    }

    private static NoSuchMechanismException nsm(String ... argument){
        if (argument == null)
            return new NoSuchMechanismException();
        else{
            return new NoSuchMechanismException(Arrays.asList(argument).toString());
        }
    }
}
