package hr.banking.service;

import hr.banking.domain.Owner;
import hr.banking.domain.SavingsAccount;
import hr.banking.exception.AccountBalanceException;
import hr.banking.exception.InsufficentFundsAccountBalanceException;

import java.util.List;

/**
 * Account service for {@link SavingsAccount} object.
 * Contains {@link SavingsAccount} specific methods.
 */
public class SavingsAccountService extends AccountService<SavingsAccount> {

    public SavingsAccount addAccount(SavingsAccount account) {
        return super.storeAccount(account);
    }

    public SavingsAccount createNewAccount(Owner owner, Float balance, Float interestRate){
        SavingsAccount account = new SavingsAccount(owner, balance, interestRate);
        return addAccount(account);
    }

    @Override
    public SavingsAccount withDrawMoneyFromAccount(SavingsAccount account, Float amount) throws AccountBalanceException {
        float valueResult = account.getBalance() - amount;
        if (valueResult < 0){
            throw new InsufficentFundsAccountBalanceException("Invalid amount to withdraw, amount " + amount
                    + " lower then account balance value " + account.getBalance());
        } else {
            return super.withDrawMoneyFromAccount(account, amount);
        }
    }

    protected Boolean checkIfAmountToBeWithdrawnIsValid(SavingsAccount account, Float amount) {
        if (super.checkIfAmountToBeWithdrawnIsValid(account, amount)){
            float valueResult = account.getBalance() - amount;
            return valueResult >= 0;
        } else {
            return false;
        }
    }

    public SavingsAccount changeInterestRate(SavingsAccount account, Float interestRate) {
        account.setInterestRate(interestRate);
        updateAccount(account);
        return account;
    }

    public List<SavingsAccount> getAllSavingAccounts(){
        return getAllAccountsByType(SavingsAccount.class);
    }

    public void provideInterestToAllUsers(){
        List<SavingsAccount> savingsAccounts = getAllSavingAccounts();
        for (SavingsAccount savingsAccount : savingsAccounts) {
            float newValue = savingsAccount.getBalance() +
                    savingsAccount.getBalance() * savingsAccount.getInterestRate()/100;
            savingsAccount.setBalance(newValue);
            updateAccount(savingsAccount);
        }
    }


}
