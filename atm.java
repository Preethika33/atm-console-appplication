import java.util.HashMap;
import java.util.Scanner;

class Bank {
    private String name;
    private HashMap<String, User> users;
    private HashMap<String, Admin> admins;

    public Bank(String name) {
        this.name = name;
        this.users = new HashMap<>();
        this.admins = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addAdmin(Admin admin) {
        admins.put(admin.getUsername(), admin);
    }

    public boolean authenticateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).authenticate(password);
    }

    public boolean authenticateAdmin(String username, String password) {
        return admins.containsKey(username) && admins.get(username).authenticate(password);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}

class User {
    private String username;
    private String password;
    private float balance;

    public User(String username, String password, float balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void deposit(float amount) {
        balance += amount;
        System.out.println("Deposit successful. Current balance: " + balance);
    }

    public void withdraw(float amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: " + balance);
    }
}

class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("MyBank");

        // Create users and admins
        User user1 = new User("user1", "password1", 1000);  // Starting balance of 1000
        User user2 = new User("user2", "password2", 500);   // Starting balance of 500
        Admin admin1 = new Admin("admin1", "adminpassword");

        // Add users and admins to the bank
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAdmin(admin1);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to MyBank!");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (bank.authenticateUser(username, password)) {
                    System.out.println("User authentication successful!");
                    User user = bank.getUsers().get(username);

                    // User operations
                    while (true) {
                        System.out.println("\n1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Check Balance");
                        System.out.println("4. Logout");
                        System.out.print("Enter your choice: ");
                        String userChoice = scanner.nextLine();

                        if (userChoice.equals("1")) {
                            System.out.print("Enter the amount to deposit: ");
                            float amount = scanner.nextFloat();
                            scanner.nextLine(); // Consume newline character
                            user.deposit(amount);
                        } else if (userChoice.equals("2")) {
                            System.out.print("Enter the amount to withdraw: ");
                            float amount = scanner.nextFloat();
                            scanner.nextLine(); // Consume newline character
                            user.withdraw(amount);
                        } else if (userChoice.equals("3")) {
                            user.checkBalance();
                        } else if (userChoice.equals("4")) {
                            System.out.println("Logging out...");
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }

                } else {
                    System.out.println("User authentication failed!");
                }

            } else if (choice.equals("2")) {
                System.out.print("Enter admin username: ");
                String adminUsername = scanner.nextLine();
                System.out.print("Enter admin password: ");
                String adminPassword = scanner.nextLine();
                if (bank.authenticateAdmin(adminUsername, adminPassword)) {
                    System.out.println("Admin authentication successful!");
                    // Admin operations can be added here
                } else {
                    System.out.println("Admin authentication failed!");
                }

            } else if (choice.equals("3")) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
