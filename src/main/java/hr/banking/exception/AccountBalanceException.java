package hr.banking.exception;

/**
 * Exception related to the balance of the account.
 */
public class AccountBalanceException extends Exception {

    public AccountBalanceException(String message){
        super(message);
    }
}
