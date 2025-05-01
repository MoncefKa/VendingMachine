package exceptions;

import entities.Product;

import java.util.List;

public class LowProductStock extends RuntimeException {
    private List<Product> lowStockProducts;

    public LowProductStock(String message,List<Product>lowStockProduct) {
        super(message);
        this.lowStockProducts=lowStockProduct;
    }

    public List<Product> getLowStockProducts() {
        return lowStockProducts;
    }
}
