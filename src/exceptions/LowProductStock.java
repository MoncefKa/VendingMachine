package exceptions;
import java.util.List;
import entities.Product;

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
