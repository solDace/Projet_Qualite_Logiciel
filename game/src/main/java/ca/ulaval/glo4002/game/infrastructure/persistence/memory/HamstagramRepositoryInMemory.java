package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramID;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.jvnet.hk2.annotations.Service;

@Service
public class HamstagramRepositoryInMemory implements HamstagramRepository {

    private static final Map<HamstagramID, HamstagramAccount> ACCOUNTS = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Optional<HamstagramAccount> getHamstagramAccount(HamstagramID hamstagramID) {
        return Optional.ofNullable(ACCOUNTS.get(hamstagramID));
    }

    @Override
    public Set<HamstagramAccount> getAllHamstagramAccounts() {
        return new HashSet<>(ACCOUNTS.values());
    }

    @Override
    public void saveHamstagramAccount(HamstagramAccount hamstagramAccount) {
        ACCOUNTS.put(hamstagramAccount.getHamstagramID(), hamstagramAccount);
    }

    @Override
    public void deleteAllHamstagramAccounts() {
        ACCOUNTS.clear();
    }

    @Override
    public void deleteHamstagramAccountsByCharacterID(CharacterID characterID) {
        Set<HamstagramID> accountsToDelete = new HashSet<>();

        for (HamstagramAccount account : ACCOUNTS.values()) {
            if (characterID.equals(account.getCharacterID())) {
                accountsToDelete.add(account.getHamstagramID());
            }
        }

        accountsToDelete.forEach(ACCOUNTS::remove);
    }
}
