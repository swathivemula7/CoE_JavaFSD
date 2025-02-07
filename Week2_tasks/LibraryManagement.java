package week2;

import java.io.*;
import java.util.*;

class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
    
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
}

class User implements Serializable {
    private String name;
    private String userID;
    private List<Book> borrowedBooks = new ArrayList<>();
    
    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }
    
    public String getName() { return name; }
    public String getUserID() { return userID; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }
}

interface ILibrary {
    void borrowBook(String ISBN, String userID) throws Exception;
    void returnBook(String ISBN, String userID) throws Exception;
    void reserveBook(String ISBN, String userID) throws Exception;
    Book searchBook(String title);
}

abstract class LibrarySystem implements ILibrary {
    protected List<Book> books = new ArrayList<>();
    protected List<User> users = new ArrayList<>();
    
    public abstract void addBook(Book book);
    public abstract void addUser(User user);
}

class LibraryManager extends LibrarySystem {
    private final int MAX_BOOKS_ALLOWED = 3;
    
    public synchronized void borrowBook(String ISBN, String userID) throws Exception {
        User user = findUser(userID);
        if (user.getBorrowedBooks().size() >= MAX_BOOKS_ALLOWED) throw new Exception("MaxBooksAllowedException");
        Book book = findBook(ISBN);
        user.getBorrowedBooks().add(book);
    }
    
    public synchronized void returnBook(String ISBN, String userID) throws Exception {
        User user = findUser(userID);
        Book book = findBook(ISBN);
        user.getBorrowedBooks().remove(book);
    }
    
    public synchronized void reserveBook(String ISBN, String userID) throws Exception {
        findBook(ISBN);
        findUser(userID);
    }
    
    public Book searchBook(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    private Book findBook(String ISBN) throws Exception {
        return books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(() -> new Exception("BookNotFoundException"));
    }
    
    private User findUser(String userID) throws Exception {
        return users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new Exception("UserNotFoundException"));
    }
    
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library.dat"))) {
            oos.writeObject(books);
            oos.writeObject(users);
        }
    }
    
    public void loadData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library.dat"))) {
            books = (List<Book>) ois.readObject();
            users = (List<User>) ois.readObject();
        }
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();
        libManager.addBook(new Book("Java Programming", "Author A", "1234"));
        libManager.addBook(new Book("Data Structures", "Author B", "5678"));
        libManager.addUser(new User("Ram", "U1"));
        libManager.addUser(new User("Sita", "U2"));
        
        Runnable task1 = () -> {
            try { libManager.borrowBook("1234", "U1"); } catch (Exception e) { System.out.println(e.getMessage()); }
        };
        Runnable task2 = () -> {
            try { libManager.returnBook("1234", "U1"); } catch (Exception e) { System.out.println(e.getMessage()); }
        };
        
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            libManager.saveData();
            libManager.loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

