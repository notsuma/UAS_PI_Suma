package microservices;
import java.util.*;

public class TradingService {
    UserService userService;
    List<String> stockNames;
    List<String> bondNames;
    Map<String, Integer> stockPrices;
    Map<String, Integer> bondPrices;

    TradingService(UserService userService) {
        this.userService = userService;
        this.stockNames = Arrays.asList("BBCA", "BBRI", "BMRI", "BBNI", "ASII");
        this.bondNames = Arrays.asList("Sucorinvest Equity Fund", "Sucorinvest Maxi Fund", "Sucorinvest Sharia Equity Fund", "Trim Syariah Saham", "Manulife Saham Andalan");
        this.stockPrices = new HashMap<>();
        this.bondPrices = new HashMap<>();

        // Initialize stock prices (in Rupiah per 100 shares)
        stockPrices.put("BBCA", 925000);
        stockPrices.put("BBRI", 555000);
        stockPrices.put("BMRI", 597500);
        stockPrices.put("BBNI", 520000);
        stockPrices.put("ASII", 565000);

        // Initialize bond prices (in Rupiah per bond)
        bondPrices.put("Sucorinvest Equity Fund", 1000000);
        bondPrices.put("Sucorinvest Maxi Fund", 1000000);
        bondPrices.put("Sucorinvest Sharia Equity Fund", 1000000);
        bondPrices.put("Trim Syariah Saham", 1000000);
        bondPrices.put("Manulife Saham Andalan", 1000000);
    }

    void buyStock(String userId, int stockIndex, int quantity) {
        if (stockIndex >= 0 && stockIndex < stockNames.size()) {
            String stockName = stockNames.get(stockIndex);
            User user = userService.getUser(userId);
            if (user != null) {
                int currentQuantity = user.stocks.getOrDefault(stockName, 0);
                user.stocks.put(stockName, currentQuantity + quantity);
            }
        }
    }

    void sellStock(String userId, int stockIndex, int quantity) {
        if (stockIndex >= 0 && stockIndex < stockNames.size()) {
            String stockName = stockNames.get(stockIndex);
            User user = userService.getUser(userId);
            if (user != null && user.stocks.containsKey(stockName)) {
                int currentQuantity = user.stocks.get(stockName);
                if (currentQuantity >= quantity) {
                    user.stocks.put(stockName, currentQuantity - quantity);
                }
            }
        }
    }

    void buyBond(String userId, int bondIndex, int quantity) {
        if (bondIndex >= 0 && bondIndex < bondNames.size()) {
            String bondName = bondNames.get(bondIndex);
            User user = userService.getUser(userId);
            if (user != null) {
                int currentQuantity = user.bonds.getOrDefault(bondName, 0);
                user.bonds.put(bondName, currentQuantity + quantity);
            }
        }
    }

    void sellBond(String userId, int bondIndex, int quantity) {
        if (bondIndex >= 0 && bondIndex < bondNames.size()) {
            String bondName = bondNames.get(bondIndex);
            User user = userService.getUser(userId);
            if (user != null && user.bonds.containsKey(bondName)) {
                int currentQuantity = user.bonds.get(bondName);
                if (currentQuantity >= quantity) {
                    user.bonds.put(bondName, currentQuantity - quantity);
                }
            }
        }
    }
}
