package ecommerce.finalproject.exception.Product;

public class ProductException extends Exception {
    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }

    public ProductException(Throwable cause) {
        super(cause);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
