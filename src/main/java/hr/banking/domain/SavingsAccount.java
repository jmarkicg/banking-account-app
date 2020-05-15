package hr.banking.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavingsAccount extends Account {

    /* The interest rate stored as percentage value e.g. 3.5 */
    private Float interestRate;

    public SavingsAccount(Owner owner, Float balance, Float interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }
}
