import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

// Abstract class Item
abstract class Item{
    private String name;
    private String barcode;
    private double price;

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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0#");
        return "Its barcode is " + barcode + " and its price is " + df.format(price);
    }
}

// Book class extending Item
class Book extends Item {
    private String author;

    public Book(String name, String author, String barcode, double price) {
        super(name, barcode, price);
        this.author = author;
    }

    @Override
    public String toString() {
        return "Author of the " + getName() + " is " + author + ". " + super.toString();
    }
}

// Toy class extending Item
class Toy extends Item {
    private String color;

    public Toy(String name, String color, String barcode, double price) {
        super(name, barcode, price);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Color of the " + getName() + " is " + color + ". " + super.toString();
    }
}

// Stationery class extending Item
class Stationery extends Item {
    private String kind;

    public Stationery(String name, String kind, String barcode, double price) {
        super(name, barcode, price);
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Kind of the " + getName() + " is " + kind + ". " + super.toString();
    }
}

// Generic Inventory class
class Inventory<T extends Item> {
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public boolean removeItem(String barcode) {
        return items.removeIf(item -> item.getBarcode().equals(barcode));
    }

    public T searchByBarcode(String barcode) {
        return items.stream()
                    .filter(item -> item.getBarcode().equals(barcode))
                    .findFirst()
                    .orElse(null);
    }

    public T searchByName(String name) {
        return items.stream()
                    .filter(item -> item.getName().equals(name))
                    .findFirst()
                    .orElse(null);
    }

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

    public static void removeCommand(String barcode){
        System.out.println("REMOVE RESULTS:");
        if (!books.removeItem(barcode) && !toys.removeItem(barcode) && !stationeries.removeItem(barcode)){
            System.out.println("Item is not found.");
        }else{
            System.out.println("Item is removed.");
        }
        System.out.println("------------------------------");
    }

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

    public static void displayCommand(){
        System.out.println("INVENTORY:");
        books.displayAllItems();
        toys.displayAllItems();
        stationeries.displayAllItems();
        System.out.println("------------------------------");
    }

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

    public static void main(String[] args){
        if (args.length != 2) {
            System.err.println("Usage: java Main <input file> <output file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];
        try {
            System.setOut(new PrintStream(new FileOutputStream(outputFile)));
            processInputs(inputFile);
            System.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
