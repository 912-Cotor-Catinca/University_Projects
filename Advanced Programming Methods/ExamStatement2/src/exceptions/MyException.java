package exceptions;

public abstract class MyException extends RuntimeException {
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}
