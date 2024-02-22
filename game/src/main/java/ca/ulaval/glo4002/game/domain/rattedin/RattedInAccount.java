package ca.ulaval.glo4002.game.domain.rattedin;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.contact.RattedInContact;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class RattedInAccount {

    private final RattedInID rattedInID;
    private final CharacterID characterID;
    private final String username;
    private final HashMap<RattedInID, RattedInContact> contacts = new HashMap<>();

    private RattedInAccountStatus rattedInAccountStatus;

    public RattedInAccount(RattedInID rattedInID, CharacterID characterID, String username, RattedInAccountStatus status) {
        this.rattedInID = rattedInID;
        this.characterID = characterID;
        this.username = username;
        this.rattedInAccountStatus = status;
    }

    public String getUsername() {
        return username;
    }

    public RattedInID getRattedInID() {
        return rattedInID;
    }

    public void addContact(RattedInID contactName, RattedInContact contact) {
        contacts.put(contactName, contact);
    }

    public Set<String> getContactsUsernames() {
        return contacts.keySet().stream().map(RattedInID::asString).collect(Collectors.toSet());
    }

    public RattedInAccountStatus getAccountStatus() {
        return rattedInAccountStatus;
    }

    public boolean isOpenToWork() {
        return rattedInAccountStatus.equals(RattedInAccountStatus.OPEN_TO_WORK);
    }

    public void setToOpenToWork() {
        rattedInAccountStatus = RattedInAccountStatus.OPEN_TO_WORK;
    }

    public void setToBusy() {
        rattedInAccountStatus = RattedInAccountStatus.BUSY;
    }

    public void removeItselfFromItsContactsNetwork() {
        contacts.forEach((contactName, contact) -> contact.removeUserFromItsContact(rattedInID));
        contacts.clear();
    }

    public void removeContact(RattedInID contactName) {
        contacts.remove(contactName);
    }

    public CharacterID getCharacterID() {
        return characterID;
    }

    public boolean isInContacts(RattedInAccount rattedInAccount) {
        return contacts.get(rattedInAccount.getRattedInID()) != null;
    }
}
