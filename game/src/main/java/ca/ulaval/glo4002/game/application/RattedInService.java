package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterNotFoundException;
import ca.ulaval.glo4002.game.domain.character.CharacterOnRattedIn;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;
import ca.ulaval.glo4002.game.domain.contact.RattedInContact;
import ca.ulaval.glo4002.game.domain.contact.factory.RattedInContactFactory;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountNotFoundException;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInIDFactory;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class RattedInService {
    private final RattedInRepository rattedInRepository;
    private final CharacterRepository characterRepository;
    private final RattedInContactFactory contactFactory;
    private final RattedInIDFactory rattedInIDFactory;
    private final CharacterIDFactory characterIDFactory;

    @Inject
    public RattedInService(RattedInRepository rattedInRepository, CharacterRepository characterRepository,
                           RattedInContactFactory contactFactory, RattedInIDFactory rattedInIDFactory,
                           CharacterIDFactory characterIDFactory) {
        this.rattedInRepository = rattedInRepository;
        this.characterRepository = characterRepository;
        this.contactFactory = contactFactory;
        this.rattedInIDFactory = rattedInIDFactory;
        this.characterIDFactory = characterIDFactory;
    }

    public RattedInAccount getRattedInAccount(String username) {
        RattedInID rattedInID = rattedInIDFactory.create(username);
        return rattedInRepository.getRattedInAccount(rattedInID).orElseThrow(RattedInAccountNotFoundException::new);
    }

    public void makeContactRequest(String receiverName, String requesterName) {
        RattedInID receiverRattedInID = rattedInIDFactory.create(receiverName);
        RattedInID requesterRattedInID = rattedInIDFactory.create(requesterName);
        CharacterID receiverCharacterID = characterIDFactory.create(receiverName);
        CharacterID requesterCharacterID = characterIDFactory.create(requesterName);

        Character requester = characterRepository.getCharacter(requesterCharacterID).orElseThrow(CharacterNotFoundException::new);
        CharacterOnRattedIn receiver = characterRepository.getCharacterOnRattedIn(receiverCharacterID).orElseThrow(CharacterNotFoundException::new);

        if (receiver.isContactAcceptable(requester)) {
            RattedInAccount receiverAccount = getRattedInAccount(receiver.getName());
            RattedInAccount requesterAccount = getRattedInAccount(requester.getName());

            RattedInContact contact = contactFactory.create(requesterAccount, receiverAccount);

            receiverAccount.addContact(requesterRattedInID, contact);
            requesterAccount.addContact(receiverRattedInID, contact);
        }
    }
}
