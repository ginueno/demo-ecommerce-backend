package ecommerce.finalproject.exception;

public class CartItemException extends Exception{
    public CartItemException() {
        super();
    }

    public CartItemException(String message) {
        super(message);
    }

    public CartItemException(Throwable cause) {
        super(cause);
    }

    public CartItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
