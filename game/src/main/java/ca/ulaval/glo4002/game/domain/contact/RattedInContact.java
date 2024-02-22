package ca.ulaval.glo4002.game.domain.contact;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

public class RattedInContact {

    private final RattedInAccount requester;
    private final RattedInAccount receiver;

    public RattedInContact(RattedInAccount requester, RattedInAccount receiver) {
        this.requester = requester;
        this.receiver = receiver;
    }

    public void removeUserFromItsContact(RattedInID rattedInID) {
        if (receiver.getRattedInID().equals(rattedInID)) {
            requester.removeContact(rattedInID);
        } else if (requester.getRattedInID().equals(rattedInID)) {
            receiver.removeContact(rattedInID);
        }
    }
}
