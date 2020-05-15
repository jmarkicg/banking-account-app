package hr.banking.service;

import hr.banking.domain.Account;
import hr.banking.domain.Owner;

import java.util.HashMap;
import java.util.Map;

public class OwnerService {

    /**
     * These fields represent in memory storage of Owners, as well as the identification sequence.
     * idSequence - analogous to DB sequence.
     * ownersRepository - analogous to e.g. JPA repository
     */
    private Long idSequence = 1l;
    private Map<Long, Owner> ownersRepository =  new HashMap<Long, Owner>();

    public Owner createNewOwner(String firstName, String lastName, Long oib){
        Owner owner = new Owner(idSequence, firstName, lastName, oib);
        ownersRepository.put(idSequence, owner);
        incrementIdSequence();
        return owner;
    }

    private void incrementIdSequence(){
        idSequence += 1;
    }
}
