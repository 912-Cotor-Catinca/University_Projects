package exceptions;

public class TypeMismatchException extends MyException {
    public TypeMismatchException() {
        super();
    }

    public TypeMismatchException(String message) {
        super(message);
    }
}
