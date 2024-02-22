package ca.ulaval.glo4002.game.domain.rattedin.factory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class RattedInAccountFactory {
    private final RattedInIDFactory rattedInIDFactory;

    @Inject
    public RattedInAccountFactory(RattedInIDFactory rattedInIDFactory) {
        this.rattedInIDFactory = rattedInIDFactory;
    }

    public RattedInAccount createAccount(String username, CharacterID characterID, RattedInAccountStatus status) {
        return new RattedInAccount(rattedInIDFactory.create(username), characterID, username, status);
    }
}
