package ca.ulaval.glo4002.game.domain.rattedin;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import java.util.Optional;

public interface RattedInRepository {
    Optional<RattedInAccount> getRattedInAccount(RattedInID userID);

    void saveRattedInAccount(RattedInAccount rattedInAccount);

    void deleteAllRattedInAccounts();

    void deleteRattedInAccountByCharacterID(CharacterID characterID);
}
