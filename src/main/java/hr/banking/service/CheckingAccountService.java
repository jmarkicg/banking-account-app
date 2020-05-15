package hr.banking.service;

import hr.banking.domain.CheckingAccount;
import hr.banking.domain.Owner;
import hr.banking.exception.AccountBalanceException;
import hr.banking.exception.InsufficentFundsAccountBalanceException;

import java.util.List;

/**
 * Account service for {@link CheckingAccount} object.
 * Contains {@link CheckingAccount} specific methods.
 */
public class CheckingAccountService extends AccountService<CheckingAccount> {

    public CheckingAccount addAccount(CheckingAccount account) {
        return super.storeAccount(account);
    }

    public CheckingAccount createNewAccount(Owner owner, Float balance, Float limit){
        CheckingAccount account = new CheckingAccount(owner, balance, limit);
        return addAccount(account);
    }

    @Override
    public CheckingAccount withDrawMoneyFromAccount(CheckingAccount account, Float amount) throws AccountBalanceException {
        if (!checkIfAmountToBeWithdrawnIsValid(account, amount)){
            throw new InsufficentFundsAccountBalanceException("Invalid amount to withdraw" + amount
                    + ", insufficient funds.");
        } else {
            return super.withDrawMoneyFromAccount(account, amount);
        }
    }

    protected Boolean checkIfAmountToBeWithdrawnIsValid(CheckingAccount account, Float amount){
        if (super.checkIfAmountToBeWithdrawnIsValid(account, amount)){
            float valueResult = account.getBalance() - amount;
            return valueResult >= account.getLimit();
        } else {
            return false;
        }
    }

    public void transferMoney(CheckingAccount fromAccount, CheckingAccount toAccount, Float amount) throws AccountBalanceException {
        withDrawMoneyFromAccount(fromAccount, amount);
        addMoneyToAccount(toAccount, amount);
    }

    public List<CheckingAccount> getAllCheckingAccounts(){
        return getAllAccountsByType(CheckingAccount.class);
    }
}

