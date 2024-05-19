import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/*
 * Item class is an abstract class which contains the common attributes of Book, Toy and Stationery
 * @param name - name of the item
 * @param barcode - barcode of the item
 * @param price - price of the item
 */
abstract class Item{
    private String name;
    private String barcode;
    private double price;

    /*
     * Constructor to initialize the Item object
     * @param name - name of the item
     * @param barcode - barcode of the item
     * @param price - price of the item
     */
    public Item(String name, String barcode, double price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    /*
     * Overridden toString method to display the barcode and price of the item
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0#");
        return "Its barcode is " + barcode + " and its price is " + df.format(price);
    }
}

/*
 * Book class extending Item
 * @param author - author of the book
 * toString method is overridden to display the author of the book
 */
class Book extends Item {
    private String author;

    /*
     * Constructor to initialize the Book object
     * @param name - name of the book
     * @param author - author of the book
     * @param barcode - barcode of the book
     * @param price - price of the book
     */
    public Book(String name, String author, String barcode, double price) {
        super(name, barcode, price);
        this.author = author;
    }

    /*
     * Overridden toString method to display the author of the book
     */
    @Override
    public String toString() {
        return "Author of the " + getName() + " is " + author + ". " + super.toString();
    }
}

/*
 * Toy class extending Item
 */
class Toy extends Item {
    private String color;

    /*
     * Constructor to initialize the Toy object
     * @param name - name of the toy
     * @param color - color of the toy
     * @param barcode - barcode of the toy
     * @param price - price of the toy
     */
    public Toy(String name, String color, String barcode, double price) {
        super(name, barcode, price);
        this.color = color;
    }

    /*
     * Overridden toString method to display the color of the toy
     */
    @Override
    public String toString() {
        return "Color of the " + getName() + " is " + color + ". " + super.toString();
    }
}

/*
 * Stationery class extending Item
 * @param kind - kind of the stationery
 * toString method is overridden to display the kind of the stationery
 */
class Stationery extends Item {
    private String kind;

    public Stationery(String name, String kind, String barcode, double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    /*
     * Overridden toString method to display the kind of the stationery
     */
    @Override
    public String toString() {
        return "Kind of the " + getName() + " is " + kind + ". " + super.toString();
    }
}

// Generic Inventory class
class Inventory<T extends Item> {
    private List<T> items = new ArrayList<>();

    /*
     * Method to add item to the inventory
     * @param item - item to be added
     */
    public void addItem(T item) {
        items.add(item);
    }

    /*
     * Method to remove item from the inventory
     * @param barcode - barcode of the item to be removed
     * @return true if item is removed, false otherwise
     */
    public boolean removeItem(String barcode) {
        return items.removeIf(item -> item.getBarcode().equals(barcode));
    }

    /*
     * Method to search item by barcode
     * @param barcode - barcode of the item to be searched
     * @return item if found, null otherwise
     */
    public T searchByBarcode(String barcode) {
        return items.stream()
                    .filter(item -> item.getBarcode().equals(barcode))
                    .findFirst()
                    .orElse(null);
    }

    /*
     * Method to search item by name
     * @param name - name of the item to be searched
     * @return item if found, null otherwise
     */
    public T searchByName(String name) {
        return items.stream()
                    .filter(item -> item.getName().equals(name))
                    .findFirst()
                    .orElse(null);
    }

    /*
     * Method to display all the items in the inventory
     */
    public void displayAllItems() {
        for (T item : items) {
            System.out.println(item);
        }
    }
}

public class Main {
    static Inventory<Book> books = new Inventory<>();
    static Inventory<Toy> toys = new Inventory<>();
    static Inventory<Stationery> stationeries = new Inventory<>();
    /*
     * Methods to process add new item command and add the item to the respective inventory
     * based on the type of item Book, Toy or Stationery
     * @param parts - array of strings containing the command parts
     * parts[1] - type of item
     * parts[2] - name of item
     * parts[3] - author/color/kind of item
     * parts[4] - barcode of item
     * parts[5] - price of item
     */
    public static void addCommand(String[] parts){
        String type = parts[1];
        switch (type) {
            case "Book":
                books.addItem(new Book(parts[2], parts[3], parts[4], Double.parseDouble(parts[5])));
                break;
            case "Toy":
                toys.addItem(new Toy(parts[2], parts[3], parts[4], Double.parseDouble(parts[5])));
                break;
            case "Stationery":
                stationeries.addItem(new Stationery(parts[2], parts[3], parts[4], Double.parseDouble(parts[5])));
                break;
        }
    }

    /*
     * Methods to process remove item command and remove the item from the respective inventory
     * based on the barcode of the item
     * @param barcode - barcode of the item to be removed
     */
    public static void removeCommand(String barcode){
        System.out.println("REMOVE RESULTS:");
        if (!books.removeItem(barcode) && !toys.removeItem(barcode) && !stationeries.removeItem(barcode)){
            System.out.println("Item is not found.");
        }else{
            System.out.println("Item is removed.");
        }
        System.out.println("------------------------------");
    }

    /*
     * Methods to process search by barcode command and search the item in the respective inventory
     * based on the barcode of the item
     * @param barcode - barcode of the item to be searched
     */
    public static void searchByBarcodeCommand(String barcode){
        System.out.println("SEARCH RESULTS:");
        Item item = books.searchByBarcode(barcode);
        if(item == null) item = toys.searchByBarcode(barcode);
        if (item == null) item = stationeries.searchByBarcode(barcode);
        if(item == null){
            System.out.println("Item is not found.");
        }else{
            System.out.println(item);
        }
        System.out.println("------------------------------");
    }

    /*
     * Methods to process search by name command and search the item in the respective inventory
     * based on the name of the item
     * @param name - name of the item to be searched
     */
    public static void searchByNameCommand(String name){
        System.out.println("SEARCH RESULTS:");
        Item item = books.searchByName(name);
        if(item == null) item = toys.searchByName(name);
        if (item == null) item = stationeries.searchByName(name);
        if(item == null){
            System.out.println("Item is not found.");
        }else{
            System.out.println(item);
        }
        System.out.println("------------------------------");
    }

    /*
     * Method to display all the items in the inventories
     */
    public static void displayCommand(){
        System.out.println("INVENTORY:");
        books.displayAllItems();
        toys.displayAllItems();
        stationeries.displayAllItems();
        System.out.println("------------------------------");
    }

    /*
     * Method to read input file and process the commands
     * @param inputFileName - name of the input file
     * @throws IOException
     */
    public static void processInputs(String inputFileName) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            String command = parts[0];

            switch (command) {
                case "ADD":
                    addCommand(parts);
                    break;
                case "REMOVE":
                    removeCommand(parts[1]);
                    break;
                case "SEARCHBYBARCODE":
                    searchByBarcodeCommand(parts[1]);
                    break;
                case "SEARCHBYNAME":
                    searchByNameCommand(parts[1]);
                    break;
                case "DISPLAY":
                    displayCommand();
                    break;
            }
        }
        reader.close();
    }

    /*
     * Main method reads input file and processes the commands
     */
    public static void main(String[] args){
        if (args.length != 2) {
            System.err.println("Usage: java Main <input file> <output file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];
        try {
            System.setOut(new PrintStream(new FileOutputStream(outputFile))); // Redirecting output to file
            processInputs(inputFile); // Processing inputs
            System.out.close(); // Closing output stream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
