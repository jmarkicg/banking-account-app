package hr.banking.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckingAccount extends Account{

    private Long limit;

    public CheckingAccount(Owner owner, Long balance, Long limit) {
        super(owner, balance);
        this.limit = limit;
    }
}
