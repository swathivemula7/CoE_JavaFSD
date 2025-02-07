package week2;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

class Product {
    private String productID;
    private String name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

class Location {
    private int aisle;
    private int shelf;
    private int bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }
}

class Order {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;
    
    public enum Priority {
        STANDARD, EXPEDITED
    }
    
    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }
    
    public Priority getPriority() { return priority; }
    public List<String> getProductIDs() { return productIDs; }
}

class OrderComparator implements Comparator<Order> {
    public int compare(Order o1, Order o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

class InventoryManager {
    private ConcurrentHashMap<String, Product> products;
    private PriorityBlockingQueue<Order> orderQueue;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());
    
    public InventoryManager() {
        products = new ConcurrentHashMap<>();
        orderQueue = new PriorityBlockingQueue<>(10, new OrderComparator());
    }
    
    public synchronized void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }
    
    public synchronized void addOrder(Order order) {
        orderQueue.offer(order);
    }
    
    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order != null) {
                executor.execute(() -> {
                    try {
                        fulfillOrder(order);
                    } catch (OutOfStockException e) {
                        logger.warning(e.getMessage());
                    }
                });
            }
        }
        executor.shutdown();
    }
    
    private void fulfillOrder(Order order) throws OutOfStockException {
        for (String productID : order.getProductIDs()) {
            Product product = products.get(productID);
            if (product != null) {
                synchronized (product) {
                    if (product.getQuantity() > 0) {
                        product.setQuantity(product.getQuantity() - 1);
                        logger.info("Processed product: " + productID);
                    } else {
                        throw new OutOfStockException("Product " + productID + " is out of stock.");
                    }
                }
            }
        }
    }
}

public class WarehouseInventory {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        
        Location loc1 = new Location(1, 2, 3);
        Product p1 = new Product("P001", "Laptop", 5, loc1);
        Product p2 = new Product("P002", "Phone", 3, loc1);
        inventoryManager.addProduct(p1);
        inventoryManager.addProduct(p2);
        
        List<String> order1Products = Arrays.asList("P001", "P002");
        Order order1 = new Order("O001", order1Products, Order.Priority.EXPEDITED);
        inventoryManager.addOrder(order1);
        
        inventoryManager.processOrders();
    }
}

