package Exceptions;

import java.util.concurrent.ExecutionException;

public class ExecutionExceptions extends ExecutionException {
    public ExecutionExceptions(String message){
        super(message);
    }
}
