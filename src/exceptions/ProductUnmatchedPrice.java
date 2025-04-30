package exceptions;

public class ProductUnmatchedPrice extends RuntimeException {
    public ProductUnmatchedPrice(String message) {
        super(message);
    }
}
