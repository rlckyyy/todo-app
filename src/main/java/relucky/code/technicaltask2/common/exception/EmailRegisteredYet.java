package relucky.code.technicaltask2.common.exception;

public class EmailRegisteredYet extends RuntimeException{
    public EmailRegisteredYet(String message) {
        super(message);
    }
}
