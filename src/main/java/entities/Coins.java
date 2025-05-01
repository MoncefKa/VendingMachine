package entities;

import java.util.HashMap;
import java.util.Map;

public class Coins {
    private Map<Coins, Integer> coinsStock;

    public Coins() {
        this.coinsStock = new HashMap<>();
    }

    public Map<Coins, Integer> getCoinsStock() {
        return coinsStock;
    }

    public int getCoinCount(Coins coin) {
        return coinsStock.getOrDefault(coin, 0);
    }

    public void setCoinCount(Coins coin, int quantity) {
        coinsStock.put(coin, quantity);
    }
}
