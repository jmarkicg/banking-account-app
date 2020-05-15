package hr.banking;

import hr.banking.domain.CheckingAccount;
import hr.banking.domain.Owner;
import hr.banking.exception.AccountBalanceException;
import hr.banking.exception.InsufficentFundsAccountBalanceException;
import hr.banking.service.CheckingAccountService;
import hr.banking.service.OwnerService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckingAccountBankingTest {

    private OwnerService ownerService;
    
    private CheckingAccountService checkingAccountService;

    private CheckingAccount checkAccount1;

    private CheckingAccount checkAccount2;

    /**
     * If Spring was used, the services would be registered as Service beans and would be autowired here.
     */
    @Before
    public void initServices(){
        this.ownerService = new OwnerService();
        this.checkingAccountService = new CheckingAccountService();
        createCheckingAccount1();
    }

    private void createCheckingAccount1(){
        Owner owner = ownerService.createNewOwner("Ivan", "Ivic", 123l);
        checkAccount1 = checkingAccountService.createNewAccount(owner, 100f, -1000f);
    }

    private void createCheckingAccount2(){
        Owner owner = ownerService.createNewOwner("Marko", "Markic", 456l);
        checkAccount2 = checkingAccountService.createNewAccount(owner, 200f, -1000f);
    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} creation and that account fields have bees set properly
     * for newly created account (the ID, balance has been set properly).
     */
    @Test
    public void testCheckingAccountCreationMoneyAdd() {
        assertTrue(checkAccount1 != null && checkAccount1.getId() != null);
        assertTrue(checkAccount1.getBalance().equals(100f));

        checkingAccountService.addMoneyToAccount(checkAccount1, 150f);
        checkAccount1 = checkingAccountService.getAccountById(checkAccount1.getId());

        assertEquals(checkAccount1.getBalance().longValue(), 250l);
    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} withdrawal of the money
     * from created account when there's sufficient funds on it.
     */
    @Test
    public void testCheckingAccountMoneyWithdrawalSufficientFunds() throws AccountBalanceException {
        assertEquals(checkAccount1.getBalance().longValue(), 100);

        checkingAccountService.withDrawMoneyFromAccount(checkAccount1, 500f);
        checkAccount1 = checkingAccountService.getAccountById(checkAccount1.getId());

        assertEquals(checkAccount1.getBalance().longValue(), -400);
    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} withdrawal of the money
     * from created account when there's no sufficient funds on it (limit is reached).
     */
    @Test (expected = InsufficentFundsAccountBalanceException.class)
    public void testCheckingAccountMoneyWithdrawalInsufficientFunds() throws AccountBalanceException {
        assertEquals(checkAccount1.getBalance().longValue(), 100);
        assertEquals(checkAccount1.getLimit().longValue(), -1000);

        checkingAccountService.withDrawMoneyFromAccount(checkAccount1, 1200f);
    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} withdrawal of the money
     * from created account when there's sufficient funds on it.
     */
    @Test
    public void testCheckingAccountTransferMoneySufficientFunds() throws AccountBalanceException {
        createCheckingAccount2();

        assertEquals(checkAccount1.getBalance().longValue(), 100);
        assertEquals(checkAccount2.getBalance().longValue(), 200);

        checkingAccountService.transferMoney(checkAccount1, checkAccount2, 50f);
        checkAccount1 = checkingAccountService.getAccountById(checkAccount1.getId());
        checkAccount2 = checkingAccountService.getAccountById(checkAccount2.getId());

        assertEquals(checkAccount1.getBalance().longValue(), 50);
        assertEquals(checkAccount2.getBalance().longValue(), 250);
    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} withdrawal of the money
     * from created account when there's NO sufficient funds on it.
     */
    @Test (expected = InsufficentFundsAccountBalanceException.class)
    public void testCheckingAccountTransferMoneyInsufficientFunds() throws AccountBalanceException {
        createCheckingAccount2();

        assertEquals(checkAccount1.getBalance().longValue(), 100);
        assertEquals(checkAccount1.getLimit().longValue(), -1000);
        assertEquals(checkAccount2.getBalance().longValue(), 200);

        checkingAccountService.transferMoney(checkAccount1, checkAccount2, 1200f);
    }

}
