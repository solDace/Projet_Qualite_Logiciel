package ca.ulaval.glo4002.game.domain.hamstagram;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class HamstagramAccountFactory {

    private final HamstagramIDFactory hamstagramIDFactory;

    @Inject
    public HamstagramAccountFactory(HamstagramIDFactory hamstagramIDFactory) {
        this.hamstagramIDFactory = hamstagramIDFactory;
    }

    public HamstagramAccount createAccount(String username, CharacterID characterID) {
        HamstagramID hamstagramID = hamstagramIDFactory.create(username);

        return new HamstagramAccount(hamstagramID, characterID, username);
    }
}
