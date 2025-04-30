package exceptions;

public class NotSufficientCoins extends RuntimeException {
    public NotSufficientCoins(String message) {
        super(message);
    }
}
