package microservices;

import java.util.*;

class Transaction {
    String userId;
    String type;
    String assetName;
    int quantity;

    Transaction(String userId, String type, String assetName, int quantity) {
        this.userId = userId;
        this.type = type;
        this.assetName = assetName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", assetName='" + assetName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

class AuditService {
    List<Transaction> transactions;

    AuditService() {
        this.transactions = new ArrayList<>();
    }

    void logTransaction(String userId, String type, String assetName, int quantity) {
        Transaction transaction = new Transaction(userId, type, assetName, quantity);
        transactions.add(transaction);
    }

    List<Transaction> getTransactions(String userId) {
        List<Transaction> userTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.userId.equals(userId)) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        TradingService tradingService = new TradingService(userService);
        PortfolioService portfolioService = new PortfolioService(userService, tradingService);
        AuditService auditService = new AuditService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add user");
            System.out.println("2. Remove user");
            System.out.println("3. Get user");
            System.out.println("4. Buy stock");
            System.out.println("5. Sell stock");
            System.out.println("6. Buy bond");
            System.out.println("7. Sell bond");
            System.out.println("8. Get total stock value");
            System.out.println("9. Get total bond value");
            System.out.println("10. Get transactions");
            System.out.println("11. Exit");
            System.out.println("Enter command number: ");
            int command = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            if (command == 1) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.println("Enter user name: ");
                String userName = scanner.nextLine();
                userService.addUser(userId, userName);
            } else if (command == 2) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                userService.removeUser(userId);
            } else if (command == 3) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    System.out.println("User name: " + user.userName);
                } else {
                    System.out.println("User not found");
                }
            } else if (command == 4) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.println("Enter stock index: ");
                int stockIndex = scanner.nextInt();
                System.out.println("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                tradingService.buyStock(userId, stockIndex, quantity);
                auditService.logTransaction(userId, "buyStock", tradingService.stockNames.get(stockIndex), quantity);
            } else if (command == 5) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.println("Enter stock index: ");
                int stockIndex = scanner.nextInt();
                System.out.println("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                tradingService.sellStock(userId, stockIndex, quantity);
                auditService.logTransaction(userId, "sellStock", tradingService.stockNames.get(stockIndex), quantity);
            } else if (command == 6) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.println("Enter bond index: ");
                int bondIndex = scanner.nextInt();
                System.out.println("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                tradingService.buyBond(userId, bondIndex, quantity);
                auditService.logTransaction(userId, "buyBond", tradingService.bondNames.get(bondIndex), quantity);
            } else if (command == 7) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.println("Enter bond index: ");
                int bondIndex = scanner.nextInt();
                System.out.println("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                tradingService.sellBond(userId, bondIndex, quantity);
                auditService.logTransaction(userId, "sellBond", tradingService.bondNames.get(bondIndex), quantity);
            } else if (command == 8) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                int totalValue = portfolioService.getTotalStockValue(userId);
                System.out.println("Total stock value: " + totalValue);
            } else if (command == 9) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                int totalValue = portfolioService.getTotalBondValue(userId);
                System.out.println("Total bond value: " + totalValue);
            } else if (command == 10) {
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                List<Transaction> transactions = auditService.getTransactions(userId);
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            } else if (command == 11) {
                break;
            } else {
                System.out.println("Unknown command");
            }
        }

        scanner.close();
    }
}
