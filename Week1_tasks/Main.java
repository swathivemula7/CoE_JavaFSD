package week1;
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds. Current balance: " + balance);
        } else {
            balance -= amount;
            System.out.println("Withdrew " + amount + ". New balance: " + balance);
        }
    }

    public double getBalance() {
        return balance;
    }
}

class BankTransaction implements Runnable {
    private BankAccount account;
    private boolean isDeposit;
    private double amount;

    public BankTransaction(BankAccount account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (isDeposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance of 1000

        Thread user1 = new Thread(new BankTransaction(account, true, 500));  // User 1 deposits 500
        Thread user2 = new Thread(new BankTransaction(account, false, 200)); // User 2 withdraws 200
        Thread user3 = new Thread(new BankTransaction(account, true, 300));  // User 3 deposits 300
        Thread user4 = new Thread(new BankTransaction(account, false, 1500)); // User 4 tries to withdraw 1500 (insufficient funds)

        user1.start();
        user2.start();
        user3.start();
        user4.start();
    }
}
