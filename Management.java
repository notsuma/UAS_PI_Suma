package microservices;
import java.util.*;

import javax.sound.midi.Soundbank;

public class Management {
    public static void main(String[] args) {
        UserService userService = new UserService();
        TradingService tradingService = new TradingService(userService);
        PortfolioService portfolioService = new PortfolioService(userService, tradingService);
        AuditService auditService = new AuditService();
        Scanner scanner = new Scanner(System.in);

        Map<String, String> stocks = new LinkedHashMap<>();
        stocks.put("PT Bank Central Asia Tbk (BBCA)", "Rp9250/share");
        stocks.put("PT Bank Rakyat Indonesia (Persero) Tbk (BBRI)", "Rp5550/share");
        stocks.put("PT Bank Mandiri (Persero) Tbk (BMRI)", "Rp5975/share");
        stocks.put("PT Bank Negara Indonesia (Persero) Tbk (BBNI)", "Rp5200/share");
        stocks.put("PT Astra International Tbk (ASII)", "Rp5650/share");

        // Bonds
        List<String> bonds = new ArrayList<>();
        bonds.add("Sucorinvest Equity Fund");
        bonds.add("Sucorinvest Maxi Fund");
        bonds.add("Sucorinvest Sharia Equity Fund");
        bonds.add("Trim Syariah Saham");
        bonds.add("Manulife Saham Andalan");

        System.out.println("==STOCKS AND BONDS TRANSACTION PROGRAM==");

        while (true) {
            System.out.println("\n Command List: ");
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
                System.out.println("\n== ADD USER ==");
                System.out.print("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter user name: ");
                String userName = scanner.nextLine();
                userService.addUser(userId, userName);
                System.out.println("= User Added =");
            } else if (command == 2) {
                System.out.println("\n== REMOVE USER ==");
                System.out.print("Enter user ID: ");
                String userId = scanner.nextLine();
                userService.removeUser(userId);
                System.out.println("= User removed =");
            } else if (command == 3) {
                System.out.println("\n== GET USER ==");
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    System.out.println("Username: " + user.userName);
                } else {
                    System.out.println("User not found");
                }
            } else if (command == 4) {
                System.out.println("\n== BUY STOCKS ==");
                System.out.println("Stocks List:");
                    int index = 0;
                    for (Map.Entry<String, String> entry : stocks.entrySet()) {
                    System.out.println(index + " : " + entry.getKey() + " : " + entry.getValue());
                    index++;
                }

                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                System.out.println("Enter stock index: ");
                int stockIndex = scanner.nextInt();
                System.out.println("Enter lot quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                tradingService.buyStock(userId, stockIndex, quantity);
                auditService.logTransaction(userId, "buyStock", tradingService.stockNames.get(stockIndex), quantity);
                System.out.println(userId + " bought stocks with the amount " + quantity + "lot of " + tradingService.stockNames.get(stockIndex));
                System.out.println("== Transaction Success ==");
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 5) {
                System.out.println("\n== SELL STOCKS ==");
                System.out.println("\nStocks:");
                    int index = 0;
                    for (Map.Entry<String, String> entry : stocks.entrySet()) {
                    System.out.println(index + " : " + entry.getKey() + " : " + entry.getValue());
                    index++;
                }
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) { 
                    System.out.println("== Sell Input ==");
                    System.out.println("Enter stock index: ");
                    int stockIndex = scanner.nextInt();
                    System.out.println("Enter lot quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline left-over
                    tradingService.sellStock(userId, stockIndex, quantity);
                    auditService.logTransaction(userId, "sellStock", tradingService.stockNames.get(stockIndex), quantity);
                    System.out.println("\n== Stock Sold ==");
                    System.out.println("User ID: " + userId);
                    System.out.println("Quantity: " + quantity + " lot");
                    System.out.println("Stock Name: " + tradingService.stockNames.get(stockIndex));
                    System.out.println("== Transaction Success ==");
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 6) {
                System.out.println("\n== BUY BONDS ==");
                System.out.println("\nBonds:");
                    int index = 0;
                    for (String bond : bonds) {
                    System.out.println(index + " : " + bond);
                    index++;
                    }
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    System.out.println("Enter bond index: ");
                    int bondIndex = scanner.nextInt();
                    System.out.println("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline left-over
                    tradingService.buyBond(userId, bondIndex, quantity);
                    auditService.logTransaction(userId, "buyBond", tradingService.bondNames.get(bondIndex), quantity);
                    System.out.println(userId + " BOUGHT bonds with the amount " + quantity + " of " + tradingService.bondNames.get(bondIndex));
                    System.out.println("== Transaction Success ==");
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 7) {
                System.out.println("\n== SELL BONDS ==");
                System.out.println("Bonds:");
                    int index = 0;
                    for (String bond : bonds) {
                    System.out.println(index + " : " + bond);
                    index++;
                    }
                System.out.println("Enter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    System.out.println("Enter bond index: ");
                    int bondIndex = scanner.nextInt();
                    System.out.println("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline left-over
                    tradingService.sellBond(userId, bondIndex, quantity);
                    auditService.logTransaction(userId, "sellBond", tradingService.bondNames.get(bondIndex), quantity);
                    System.out.println(userId + " SOLD bonds with the amount " + quantity + " of " + tradingService.bondNames.get(bondIndex));
                    System.out.println("== Transaction Success ==");
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 8) {
                System.out.println("\nEnter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    
                    int totalValue = portfolioService.getTotalStockValue(userId);
                    System.out.println("Total stock value: " + totalValue);
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 9) {
                System.out.println("\nEnter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    int totalValue = portfolioService.getTotalBondValue(userId);
                    System.out.println("Total bond value: " + totalValue);
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 10) {
                System.out.println("\nEnter user ID: ");
                String userId = scanner.nextLine();
                User user = userService.getUser(userId);
                if (user != null) {
                    List<Transaction> transactions = auditService.getTransactions(userId);
                    for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                    }
                } else {
                    System.out.println("User not found");
                }

            } else if (command == 11) {
                System.out.println("Goodbye, Have a great day!");
                break;
            } else {
                System.out.println("\nUnknown command");
            }
        }

        scanner.close();
    }
}