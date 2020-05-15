package hr.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
/**
 * Domain generic Account object.
 */
public class Account {

    private Long id;

    private Owner owner;

    private Long balance;

    public Account(Owner owner, Long balance){
        this.owner = owner;
        this.balance = balance;
    }


}
