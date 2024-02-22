package ca.ulaval.glo4002.game.domain.hamstagram;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import java.util.Optional;
import java.util.Set;

public interface HamstagramRepository {
    Optional<HamstagramAccount> getHamstagramAccount(HamstagramID hamstagramID);

    Set<HamstagramAccount> getAllHamstagramAccounts();

    void saveHamstagramAccount(HamstagramAccount hamstagramAccount);

    void deleteAllHamstagramAccounts();

    void deleteHamstagramAccountsByCharacterID(CharacterID characterID);
}
