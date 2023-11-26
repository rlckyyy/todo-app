package relucky.code.technicaltask2.common.exception;

public class EmailAlreadyRegistered extends RuntimeException{
    public EmailAlreadyRegistered(String message) {
        super(message);
    }
}
