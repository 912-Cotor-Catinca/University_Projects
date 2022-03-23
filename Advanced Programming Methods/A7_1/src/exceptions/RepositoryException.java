package exceptions;

public class RepositoryException extends MyException {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }
}
