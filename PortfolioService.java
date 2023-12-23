package microservices;

import java.util.*;

class PortfolioService {
    UserService userService;
    TradingService tradingService;

    PortfolioService(UserService userService, TradingService tradingService) {
        this.userService = userService;
        this.tradingService = tradingService;
    }

    int getTotalStockValue(String userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            int totalValue = 0;
           
            for (Map.Entry<String, Integer> entry : user.stocks.entrySet()) {
                String stockName = entry.getKey();
                int quantity = entry.getValue();
                int stockPrice = tradingService.stockPrices.get(stockName);

                totalValue += quantity * stockPrice;
            }
            return totalValue;
        } else {
            return 0;
        }
    }

    int getTotalBondValue(String userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            int totalValue = 0;
            for (Map.Entry<String, Integer> entry : user.bonds.entrySet()) {
                String bondName = entry.getKey();
                int quantity = entry.getValue();
                int bondPrice = tradingService.bondPrices.get(bondName);
                totalValue += quantity * bondPrice;
            }
            return totalValue;
        } else {
            return 0;
        }
    }
}