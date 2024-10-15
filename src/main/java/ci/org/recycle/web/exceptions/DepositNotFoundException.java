package ci.org.recycle.web.exceptions;

public class DepositNotFoundException extends Exception {
    public DepositNotFoundException(String depositNotFound) {
        super(depositNotFound);
    }
}
