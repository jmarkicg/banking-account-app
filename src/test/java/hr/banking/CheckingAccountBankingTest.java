package hr.banking;

import hr.banking.domain.CheckingAccount;
import hr.banking.domain.Owner;
import hr.banking.service.CheckingAccountService;
import hr.banking.service.OwnerService;
import hr.banking.service.SavingsAccountService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckingAccountBankingTest {


    private OwnerService ownerService;
    
    private CheckingAccountService checkingAccountService;

    private SavingsAccountService savingsAccountService;

    /**
     * If Spring was used, the services would be registered as Service beans and would be autowired here.
     */
    @Before
    public void initServices(){
        this.ownerService = new OwnerService();
        this.checkingAccountService = new CheckingAccountService();
        this.savingsAccountService = new SavingsAccountService();

    }

    /**
     * Testing {@link hr.banking.domain.CheckingAccount} creation and that account fields have bees set properly
     * for newly created account (the ID, balance has been set properly).
     */
    @Test
    public void testCheckingAccountCreationMoneyAdd() {
        initServices();

        Owner owner = ownerService.createNewOwner("Ivan", "Ivic", 123l);
        CheckingAccount account = checkingAccountService.createNewAccount(owner, 100l, -1000l);

        assertTrue(account != null && account.getId() != null);
        assertTrue(account.getBalance().equals(100l));

        checkingAccountService.addMoneyToAccount(account, 150l);

        account = checkingAccountService.getAccountById(account.getId());

        assertEquals(account.getBalance().longValue(), 250);
    }

}
