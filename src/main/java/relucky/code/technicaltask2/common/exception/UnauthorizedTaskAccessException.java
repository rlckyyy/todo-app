package relucky.code.technicaltask2.common.exception;

public class UnauthorizedTaskAccessException extends RuntimeException{
    public UnauthorizedTaskAccessException(String message) {
        super(message);
    }
}
