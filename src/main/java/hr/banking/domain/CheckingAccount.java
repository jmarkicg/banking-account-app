package hr.banking.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckingAccount extends Account{

    /* The account limit stored as negative value e.g. -1000. */
    private Float limit;

    public CheckingAccount(Owner owner, Float balance, Float limit) {
        super(owner, balance);
        this.limit = limit;
    }
}
