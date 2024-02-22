package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.jvnet.hk2.annotations.Service;

@Service
public class RattedInRepositoryInMemory implements RattedInRepository {

    private static final Map<RattedInID, RattedInAccount> ACCOUNTS = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Optional<RattedInAccount> getRattedInAccount(RattedInID username) {
        return Optional.ofNullable(ACCOUNTS.get(username));
    }

    @Override
    public void saveRattedInAccount(RattedInAccount rattedInAccount) {
        ACCOUNTS.put(rattedInAccount.getRattedInID(), rattedInAccount);
    }

    @Override
    public void deleteAllRattedInAccounts() {
        ACCOUNTS.clear();
    }

    @Override
    public void deleteRattedInAccountByCharacterID(CharacterID characterID) {
        Set<RattedInID> accountsToDelete = new HashSet<>();

        for (RattedInAccount account : ACCOUNTS.values()) {
            if (characterID.equals(account.getCharacterID())) {
                accountsToDelete.add(account.getRattedInID());
            }
        }

        accountsToDelete.forEach(ACCOUNTS::remove);
    }
}
