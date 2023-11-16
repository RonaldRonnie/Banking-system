import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Interface for calculating interest
interface InterestCalculable {
    double calculateInterest();
}

// Interface for handling overdraft
interface Overdrafteable {
    void applyOverdraft();
}

// BankAccount class with encapsulated attributes
class BankAccount {
    private String accountNumber;
    private double balance;
    private String customerName;

    // Constructor
    public BankAccount(String accountNumber, double balance, String customerName) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerName = customerName;
    }

    // Deposit method
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    // Withdrawal method
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }

    // Balance inquiry method
    public double getBalance() {
        return balance;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for customer name
    public String getCustomerName() {
        return customerName;
    }
}

// SavingsAccount class inheriting from BankAccount and implementing InterestCalculable interface
class SavingsAccount extends BankAccount implements InterestCalculable {
    private double interestRate;

    // Constructor
    public SavingsAccount(String accountNumber, double balance, String customerName, double interestRate) {
        super(accountNumber, balance, customerName);
        this.interestRate = interestRate;
    }

    // Implementation of calculateInterest method from InterestCalculable interface
    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }
}

// CheckingAccount class inheriting from BankAccount and implementing Overdrafteable interface
class CheckingAccount extends BankAccount implements Overdrafteable {
    private double overdraftLimit;

    // Constructor
    public CheckingAccount(String accountNumber, double balance, String customerName, double overdraftLimit) {
        super(accountNumber, balance, customerName);
        this.overdraftLimit = overdraftLimit;
    }

    // Implementation of applyOverdraft method from Overdrafteable interface
    @Override
    public void applyOverdraft() {
        System.out.println("Overdraft applied. Current balance: " + (getBalance() - overdraftLimit));
    }

    public double getOverdraftLimit() {
        return 0;
    }
}

// Main class for console interface
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, BankAccount> accounts = new HashMap<>();

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Balance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createAccount(scanner, accounts);
                    break;
                case 2:
                    performTransaction(scanner, accounts, "deposit");
                    break;
                case 3:
                    performTransaction(scanner, accounts, "withdraw");
                    break;
                case 4:
                    viewBalance(scanner, accounts);
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner, Map<String, BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        System.out.print("Choose account type (1. Savings / 2. Checking): ");
        int accountTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (accountTypeChoice == 1) {
            System.out.print("Enter interest rate for savings account: ");
            double interestRate = scanner.nextDouble();
            accounts.put(accountNumber, new SavingsAccount(accountNumber, initialBalance, customerName, interestRate));
        } else if (accountTypeChoice == 2) {
            System.out.print("Enter overdraft limit for checking account: ");
            double overdraftLimit = scanner.nextDouble();
            accounts.put(accountNumber, new CheckingAccount(accountNumber, initialBalance, customerName, overdraftLimit));
        } else {
            System.out.println("Invalid account type choice. Account creation failed.");
        }

        System.out.println("Account created successfully.");
    }

    private static void performTransaction(Scanner scanner, Map<String, BankAccount> accounts, String transactionType) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);

            System.out.print("Enter amount to " + transactionType + ": ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            if (transactionType.equals("deposit")) {
                account.deposit(amount);
            } else if (transactionType.equals("withdraw")) {
                if (account instanceof CheckingAccount) {
                    // For checking accounts, check for overdraft
                    CheckingAccount checkingAccount = (CheckingAccount) account;
                    if (amount > account.getBalance() + checkingAccount.getOverdraftLimit()) {
                        System.out.println("Withdrawal failed. Exceeds overdraft limit.");
                    } else {
                        account.withdraw(amount);
                    }
                } else {
                    // For other account types, check for sufficient balance
                    account.withdraw(amount);
                }
            }
        } else {
            System.out.println("Account not found. " + transactionType + " failed.");
        }
    }

    private static void viewBalance(Scanner scanner, Map<String, BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            System.out.println("Account Holder: " + account.getCustomerName());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Current Balance: " + account.getBalance());

            if (account instanceof InterestCalculable) {
                InterestCalculable interestAccount = (InterestCalculable) account;
                System.out.println("Interest Earned: " + interestAccount.calculateInterest());
            }

            if (account instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                System.out.println("Overdraft Limit: " + checkingAccount.getOverdraftLimit());
            }
        } else {
            System.out.println("Account not found. Balance inquiry failed.");
        }
    }
}
