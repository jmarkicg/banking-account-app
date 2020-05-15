package hr.banking.service;

import hr.banking.domain.CheckingAccount;
import hr.banking.domain.Owner;

public class CheckingAccountService extends AccountService<CheckingAccount> {

    public CheckingAccount addAccount(CheckingAccount account) {
        return super.storeAccount(account);
    }

    public CheckingAccount createNewAccount(Owner owner, Long balance, Long limit){
        CheckingAccount account = new CheckingAccount(owner, balance, limit);
        return addAccount(account);
    }


}
