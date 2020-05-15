package hr.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Owner {

    private Long id;

    private String firstName;

    private String lastName;

    private Long oib;
}
