package hr.banking;

import hr.banking.domain.Owner;
import hr.banking.domain.SavingsAccount;
import hr.banking.exception.AccountBalanceException;
import hr.banking.exception.InsufficentFundsAccountBalanceException;
import hr.banking.service.OwnerService;
import hr.banking.service.SavingsAccountService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SavingAccountBankingTest {

    private OwnerService ownerService;

    private SavingsAccountService savingsAccountService;

    private SavingsAccount savingsAccount1;

    private SavingsAccount savingsAccount2;

    /**
     * If Spring was used, the services would be registered as Service beans and would be autowired here.
     */
    @Before
    public void initServices(){
        this.ownerService = new OwnerService();
        this.savingsAccountService = new SavingsAccountService();
        createSavingAccount1();
    }

    private void createSavingAccount1(){
        Owner owner = ownerService.createNewOwner("Ivan", "Ivic", 123l);
        savingsAccount1 = savingsAccountService.createNewAccount(owner, 100f, 3.5f);
    }

    private void createSavingAccount2(){
        Owner owner = ownerService.createNewOwner("Marko", "Markic", 456l);
        savingsAccount2 = savingsAccountService.createNewAccount(owner, 50f, 3.5f);
    }

    /**
     * Testing {@link SavingsAccount} creation and that account fields have bees set properly
     * for newly created account (the ID, balance has been set properly).
     */
    @Test
    public void testSavingAccountCreationMoneyAdd() {
        assertTrue(savingsAccount1 != null && savingsAccount1.getId() != null);
        assertTrue(savingsAccount1.getBalance().equals(100f));

        savingsAccountService.addMoneyToAccount(savingsAccount1, 150f);
        savingsAccount1 = savingsAccountService.getAccountById(savingsAccount1.getId());

        assertEquals(savingsAccount1.getBalance().longValue(), 250l);
    }

    /**
     * Testing {@link hr.banking.domain.SavingsAccount} withdrawal of the money
     * from created account when there's sufficient funds on it.
     */
    @Test
    public void testSavingAccountMoneyWithdrawalSufficientFunds() throws AccountBalanceException {
        assertEquals(savingsAccount1.getBalance().longValue(), 100);

        savingsAccountService.withDrawMoneyFromAccount(savingsAccount1, 60f);
        savingsAccount1 = savingsAccountService.getAccountById(savingsAccount1.getId());

        assertEquals(savingsAccount1.getBalance().longValue(), 40);
    }

    /**
     * Testing {@link hr.banking.domain.SavingsAccount} withdrawal of the money
     * from created account when there's no sufficient funds on it.
     */
    @Test (expected = InsufficentFundsAccountBalanceException.class)
    public void testSavingAccountMoneyWithdrawalInsufficientFunds() throws AccountBalanceException {
        assertEquals(savingsAccount1.getBalance().longValue(), 100);

        savingsAccountService.withDrawMoneyFromAccount(savingsAccount1, 120f);
    }

    /**
     * Testing {@link hr.banking.domain.SavingsAccount} providing interest to all users.
     * from created account when there's sufficient funds on it.
     */
    @Test
    public void testSavingAccountsCreationAndProvideInterestToAllUsers(){
        createSavingAccount2();
        assertEquals(savingsAccount1.getBalance().longValue(), 100);
        assertEquals(savingsAccount1.getInterestRate().floatValue(), 3.5, 0);
        assertEquals(savingsAccount2.getBalance().longValue(), 50);
        assertEquals(savingsAccount2.getInterestRate().floatValue(), 3.5, 0);

        savingsAccountService.provideInterestToAllUsers();

        savingsAccount1 = savingsAccountService.getAccountById(savingsAccount1.getId());
        savingsAccount2 = savingsAccountService.getAccountById(savingsAccount2.getId());

        assertEquals(savingsAccount1.getBalance().floatValue(), 103.5f, 0);
        assertEquals(savingsAccount2.getBalance().floatValue(), 51.75f, 0);
    }

    /**
     * Testing interest rate change for {@link hr.banking.domain.SavingsAccount}.
     */
    @Test
    public void testSavingAccountInterestRateChange(){
        assertEquals(savingsAccount1.getInterestRate().floatValue(), 3.5, 0);

        savingsAccountService.changeInterestRate(savingsAccount1, 4.5f);
        savingsAccount1 = savingsAccountService.getAccountById(savingsAccount1.getId());

        assertEquals(savingsAccount1.getInterestRate(), 4.5f, 0);

    }
}
