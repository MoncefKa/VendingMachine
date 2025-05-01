package entities;

import enums.Coins;
import exceptions.NotSufficientCoins;
import exceptions.ProductNotFound;
import exceptions.ProductUnmatchedPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class VendingMachine {

    private HashMap<Product, Integer> productStock;
    private HashMap<Coins, Integer> coinsStock;
    private List<Coins> insertedCoins = new ArrayList<>();
    private Product productDetails;
    private static final int LOW_STOCK_THRESHOLD = 2;
    private static final int LOW_COIN_THRESHOLD = 2;

    public VendingMachine() {
        this.productStock = new HashMap<>();
        this.coinsStock = new HashMap<>();
    }

    public void addProductStock(Product product, Integer numberOfProduct) {
        // Define logic
        if (productStock.containsKey(product)) {
            int currentStock = productStock.get(product);
            productStock.put(product, currentStock + numberOfProduct);
        } else {
            productStock.put(product, numberOfProduct);
        }
    }

    public void addCoinToStock(Coins coin, Integer quantity) {
        // Coin Current Stock
        if(coinsStock.containsKey(coin)) {
            int currentCoinsStock = coinsStock.get(coin);
            coinsStock.put(coin, currentCoinsStock + quantity);
        }
            else{
                coinsStock.put(coin,quantity);
            }
        }

    public int getCoinStock(Coins coin) {
        return coinsStock.getOrDefault(coin, 0);
    }

    // Insert coin for user not for manager
    public void insertedCoin(Coins coin) {
        // Check Inserted coins for the user
        try {
            insertedCoins.add(coin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //calculate the inserted coins of each type
    public Integer calculateInsertedCoinsValue() {
        double sumOfCoins = 0d;
        for (Coins coin : insertedCoins) {
            sumOfCoins += coin.getValue();
        }
        return (int) sumOfCoins;
    }

    //Calculating the change
    public double returnChange() {

        int sumInsertedValues = calculateInsertedCoinsValue();
        double getProductPrice = productDetails.getPrice();

        return getProductPrice - sumInsertedValues;
    }

    //flush the coins type list from values
    public void resetCoins(){
        if(!insertedCoins.isEmpty()){
            insertedCoins.clear();
        }
    }

    //Removes A Product from the stock
    public void removeProductFromStock(Product productName,Integer quantity){
        if(productStock.containsKey(productName)){
            int currentQuantity = productStock.get(productName);
            if(quantity<currentQuantity){
                productStock.put(productName, currentQuantity - quantity);
            }
            else{
                productStock.remove(productName);
            }
        }
    }

    //removes coin from  stock
    public void removeCoinFromStock(Coins coin, Integer quantity) {
        if (coinsStock.containsKey(coin)) {
            int currentQuantity = coinsStock.get(coin);
            if (currentQuantity > quantity) {
                coinsStock.put(coin, currentQuantity - quantity);
            } else {
                // Remove the coin entry if quantity is zero or less
                coinsStock.remove(coin);
            }
        }
    }


    private boolean isEffectivelyZero(double value) {
        return Math.round(value * 100.0) == 0;
    }

    //check if there is enough change to give back
    private boolean canReturnChange(double change) {
        double remaining = Math.round(change * 100.0) / 100.0;
        Coins[] vCoins = Coins.values();

        for (Coins coin : vCoins) {
            int available = coinsStock.getOrDefault(coin, 0);
            double value = coin.getValue();
            int count = (int) Math.min(available, Math.floor(remaining / value));
            remaining -= count * value;
            remaining = Math.round(remaining * 100.0) / 100.0;
        }

        return isEffectivelyZero(remaining);
    }

    //return the change if the return change method is valid
    private void giveChange(double change) {
        double remaining = Math.round(change * 100.0) / 100.0;
        Coins[] sortedCoins = Coins.values();

        for (Coins coin : sortedCoins) {
            int available = coinsStock.getOrDefault(coin, 0);
            double value = coin.getValue();
            int count = (int) Math.min(available, Math.floor(remaining / value));

            if (count > 0) {
                removeCoinFromStock(coin, count);
                remaining -= count * value;
                remaining = Math.round(remaining * 100.0) / 100.0;
            }

            if (isEffectivelyZero(remaining)) break;
        }
    }

    //get the products that are low on stock
    public List<Product> getLowStockProducts() {
        List<Product> lowStock = new ArrayList<>();
        productStock.forEach((product, quantity) -> {
            if (quantity <= LOW_STOCK_THRESHOLD) {
                lowStock.add(product);
            }
        });
        return lowStock;
    }

    //get coins that are low on stock
    public List<Coins> getLowCoinStock() {
        List<Coins> lowStock = new ArrayList<>();
        coinsStock.forEach((coin, quantity) -> {
            if (quantity <= LOW_COIN_THRESHOLD) {
                lowStock.add(coin);
            }
        });
        return lowStock;
    }

    //check if product added to stock
    public int getProductStock(Product product) {
        return productStock.getOrDefault(product, 0);
    }

    public void purchase(Integer ref,Coins insertCoin){
        /*
         * On Product Bought decrease the quantity of product
         * Check if coins are exactly the price of product
         * else calculate the change
         * update coins stock after calculations
         */
        // Case 1 : No Product In Stock
        // Case 2 : Not Sufficient Coins In Stock
        // Case 3 : Last Product In Stock
        // Case 4 : No Change
        // Find product by ref
        // Make the call to the inserted coin method
        insertedCoin(insertCoin);
        Product selectedProduct = null;

        //go through the products to find the reference of the product
        for (Product product : productStock.keySet()) {
            if (product.getRef().equals(ref)) {
                selectedProduct = product;
                break;
            }
        }

        //if selected product is not in the vending machine call custom exception
        if (selectedProduct == null) {
            throw new RuntimeException(new ProductNotFound("No product with ref"));
        }

        //Intialise Produt price and total inserted coins
        double productPrice=selectedProduct.getPrice();
        double totalInsertedCoins=calculateInsertedCoinsValue();
        //Check if inserted coins matches the coins inserted
        if(totalInsertedCoins<productPrice){
            throw new ProductUnmatchedPrice("Insert More Coins Please");
        }

        productDetails = selectedProduct;

        //calculcate the change for the user
        double change = returnChange();
        if (change > 0) {
            if (!canReturnChange(change)) {
                throw new NotSufficientCoins("Cannot provide change: ");
            }
            giveChange(change);
        }

        //add coin to stock which is inserted from the user
        for (Coins c : insertedCoins) addCoinToStock(c, 1);
        //remove the product from the product stock when selected
        removeProductFromStock(selectedProduct, 1);
    }

    public void onAdminInteraction(HashMap<Product, Integer> productRefills, HashMap<Coins, Integer> coinRefills) {

        List<Product> lowProducts = getLowStockProducts();
        //go through the products that are low on stock to refill them
        for (Product p : lowProducts) {
            int refillQty = productRefills.getOrDefault(p, LOW_STOCK_THRESHOLD * 2);
            addProductStock(p, refillQty);
        }

        List<Coins> lowCoins = getLowCoinStock();
        //go through coins stock that are low to refill them
        for (Coins c : lowCoins) {
            int refillQty = coinRefills.getOrDefault(c, LOW_COIN_THRESHOLD * 5);
            addCoinToStock(c, refillQty);
        }

        //reset coins in stock
        resetCoins();
    }
    }







