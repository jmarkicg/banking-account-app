package hr.banking.service;

import hr.banking.domain.Account;
import hr.banking.domain.Owner;
import hr.banking.domain.SavingsAccount;

public class SavingsAccountService extends AccountService<SavingsAccount> {

    public SavingsAccount addAccount(SavingsAccount account) {
        return super.storeAccount(account);
    }

    public SavingsAccount createNewAccount(Owner owner, Long balance, Float interestRate){
        SavingsAccount account = new SavingsAccount(owner, balance, interestRate);
        return addAccount(account);
    }


}
