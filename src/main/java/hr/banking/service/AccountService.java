package hr.banking.service;

import hr.banking.domain.Account;
import hr.banking.exception.AccountBalanceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract {@link AccountService} class used for handling several types of {@link Account} objects.
 * @param <T>
 */
public abstract class AccountService<T extends Account> {

    /**
     * These fields represent in memory storage of Accounts, as well as the identification sequence.
     * idSequence - analogous to DB sequence.
     * accountRepository - analogous to e.g. JPA repository
     */
    private Long idSequence = 1l;
    private Map<Long, T> accountRepository =  new HashMap<Long, T>();

    protected abstract T addAccount(T account);

    protected Boolean checkIfAmountToBeWithdrawnIsValid(T account, Float amount){
        return account.getBalance() != null;
    }

    protected void updateAccount(T account){
        accountRepository.put(account.getId(), account);
    }

    /**
     * Generic method which adds amount of money to account.
     * @param account
     * @param amount
     * @return
     */
    public T addMoneyToAccount(T account, Float amount){
        account.setBalance(account.getBalance() + amount);
        updateAccount(account);
        return account;
    }

    /**
     * Generic method which withdraws certain amount of money from the account.
     * @param account
     * @param amount
     * @return
     * @throws AccountBalanceException
     */
    public T withDrawMoneyFromAccount(T account, Float amount) throws AccountBalanceException {
        account.setBalance(account.getBalance() - amount);
        updateAccount(account);
        return account;
    }

    /**
     * Helper method used to increment the current id sequence value.
     */
    private void incrementIdSequence(){
        idSequence += 1;
    }

    /**
     * Adding T Account instance, generation of the id for new instance.
     * Storing newly created instance to in memory {@link Map}.
     * @param account
     * @return
     */
    protected T storeAccount(T account){
        account.setId(idSequence);
        updateAccount(account);
        incrementIdSequence();
        return account;
    }

    /**
     * Retrieval of the {@link Account} by its id.
     * @param id
     * @return
     */
    public T getAccountById(Long id){
        return accountRepository.get(id);
    }
    
    public List<T> getAllAccountsByType(Class clazz){
        List<T> checkingAccount = new ArrayList<T>();
        for (T t : accountRepository.values()){
            if(t.getClass().equals(clazz)){
                checkingAccount.add(t);
            }
        }
        return checkingAccount;
    }
}
