package hr.banking.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavingsAccount extends Account {

    private Float interestRate;

    public SavingsAccount(Owner owner, Long balance, Float interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }
}
