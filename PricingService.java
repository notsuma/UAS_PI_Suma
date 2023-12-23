package microservices;

import java.util.*;

class PricingService {
    TradingService tradingService;

    PricingService(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    int getStockPrice(int stockIndex) {
        if (stockIndex >= 0 && stockIndex < tradingService.stockNames.size()) {
            String stockName = tradingService.stockNames.get(stockIndex);
            return tradingService.stockPrices.get(stockName);
        } else {
            return 0;
        }
    }

    int getBondPrice(int bondIndex) {
        if (bondIndex >= 0 && bondIndex < tradingService.bondNames.size()) {
            String bondName = tradingService.bondNames.get(bondIndex);
            return tradingService.bondPrices.get(bondName);
        } else {
            return 0;
        }
    }
}

