package hr.banking.exception;

/**
 * Exception related to the balance of the account.
 */
public class InsufficentFundsAccountBalanceException extends AccountBalanceException {

    public InsufficentFundsAccountBalanceException(String message){
        super(message);
    }
}
