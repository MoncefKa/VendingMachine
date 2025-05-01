package entities;

import enums.Coins;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    @Test
    public void addProductsToStock(){
        var vendingMachineProduct = new VendingMachine();
        Product coca = new Product("Coca", 10.0, 10, 1);
        Product fanta = new Product("Fanta", 5.0, 10, 2);
        Product tonic = new Product("Tonic", 1.0, 10, 3);
        Product tager = new Product("Tager", 2.0, 10, 4);
        Product juice = new Product("Apple Juice", 5.0, 10, 5);
        Product orange = new Product("Orange Juice", 5.0, 10, 6);


        vendingMachineProduct.addProductStock(coca,5);
        vendingMachineProduct.addProductStock(fanta,15);
        vendingMachineProduct.addProductStock(tonic,50);
        vendingMachineProduct.addProductStock(tager,2);
        vendingMachineProduct.addProductStock(juice,6);
        vendingMachineProduct.addProductStock(orange,8);

        assertTrue(vendingMachineProduct.getProductStock(coca) > 0);
        assertTrue(vendingMachineProduct.getProductStock(fanta) > 0);
        assertTrue(vendingMachineProduct.getProductStock(tonic) > 0);
        assertTrue(vendingMachineProduct.getProductStock(tager) > 0);
        assertTrue(vendingMachineProduct.getProductStock(juice) > 0);
        assertTrue(vendingMachineProduct.getProductStock(orange) > 0);
    }

    @Test
    public void shouldAddMoreCoinsToExistingStock() {

        VendingMachine vendingMachineCoins = new VendingMachine();
        Coins coin = Coins.FIFTH;
        int initialQuantity = 10;
        int additionalQuantity = 5;
        vendingMachineCoins.addCoinToStock(coin, initialQuantity);
        vendingMachineCoins.addCoinToStock(coin, additionalQuantity);
        int updatedStock = vendingMachineCoins.getCoinStock(coin);
        assertEquals(initialQuantity + additionalQuantity, updatedStock);
    }

    @Test
    public void shouldAddDifferentCoinTypesToStock() {

        VendingMachine vendingMachineCoins = new VendingMachine();
        Coins coinOne = Coins.FIFTH;
        Coins coinTwo = Coins.TENTH;
        int quantityCoinOne = 5;
        int quantityCoinTwo = 3;

        vendingMachineCoins.addCoinToStock(coinOne, quantityCoinOne);
        vendingMachineCoins.addCoinToStock(coinTwo, quantityCoinTwo);

        int stockCoinOne = vendingMachineCoins.getCoinStock(coinOne);
        int stockCoinTwo = vendingMachineCoins.getCoinStock(coinTwo);

        assertEquals(quantityCoinOne, stockCoinOne);
        assertEquals(quantityCoinTwo, stockCoinTwo);
    }

    @Test
    public void shouldVerifyInsertedCoins_whenCoinsAreIn(){

    }

    @Test
    public void shouldReturnChange(){

    }
}