package hr.banking.service;

import hr.banking.domain.Account;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountService<T extends Account> {

    /**
     * These fields represent in memory storage of Accounts, as well as the identification sequence.
     * idSequence - analogous to DB sequence.
     * accountRepository - analogous to e.g. JPA repository
     */
    private Long idSequence = 1l;
    private Map<Long, T> accountRepository =  new HashMap<Long, T>();

    public abstract T addAccount(T account);

    public void addMoneyToAccount(T account, Long amount){
        account.setBalance(account.getBalance() + amount);
        accountRepository.put(account.getId(), account);
    }

    private void incrementIdSequence(){
        idSequence += 1;
    }

    protected T storeAccount(T account){
        account.setId(idSequence);
        accountRepository.put(idSequence, account);
        incrementIdSequence();
        return account;
    }

    public T getAccountById(Long id){
        return accountRepository.get(id);
    }
}
