package ca.ulaval.glo4002.game.domain.contact.factory;

import ca.ulaval.glo4002.game.domain.contact.RattedInContact;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

public class RattedInContactFactory {
    public RattedInContact create(RattedInAccount requester, RattedInAccount receiver) {
        return new RattedInContact(requester, receiver);
    }
}
