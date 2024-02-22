package ca.ulaval.glo4002.game.domain.rattedIn;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.contact.RattedInContact;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccountStatus;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RattedInAccountTest {

    private static final String A_USERNAME = "ALICE";
    private static final RattedInID A_RATTED_IN_ID = new RattedInID(A_USERNAME);
    private static final CharacterID A_CHARACTER_ID = new CharacterID(A_USERNAME);
    private static final RattedInAccountStatus A_STATUS = RattedInAccountStatus.BUSY;
    private static final String A_CONTACT_NAME = "CHARLIE";
    private static final RattedInID A_CONTACT_RATTED_IN_ID = new RattedInID(A_CONTACT_NAME);
    private static final String ANOTHER_CONTACT_NAME = "Bob";
    private static final RattedInID ANOTHER_CONTACT_RATTED_IN_ID = new RattedInID(ANOTHER_CONTACT_NAME);
    private static final RattedInID A_RATTED_IN_ID_NOT_IN_CONTACTS = new RattedInID(A_USERNAME);

    @Mock
    private RattedInContact aContact, anotherContact;

    @Mock
    private RattedInAccount mockRattedInAccount;

    private RattedInAccount aRattedInAccount;

    @BeforeEach
    public void setUp() {
        aRattedInAccount = new RattedInAccount(A_RATTED_IN_ID, A_CHARACTER_ID, A_USERNAME, A_STATUS);
    }

    @Test
    public void whenCreatingARattedInAccount_thenThisRattedInAccountHasNoRattedInContact() {
        assertTrue(aRattedInAccount.getContactsUsernames().isEmpty());
    }

    @Test
    public void givenRattedInAccount_whenAddingAContact_thenTheContactIsAddedToTheAccount() {

        aRattedInAccount.addContact(A_CONTACT_RATTED_IN_ID, aContact);

        assertThat(aRattedInAccount.getContactsUsernames(), Matchers.contains(A_CONTACT_NAME));
    }

    @Test
    public void givenRattedInAccountWithContacts_whenRemoveItselfFromItsContactsNetwork_thenRemoveItselfFromItsContactsNetwork() {
        givenRattedInAccountWithContacts();

        aRattedInAccount.removeItselfFromItsContactsNetwork();

        Mockito.verify(aContact).removeUserFromItsContact(A_RATTED_IN_ID);
        Mockito.verify(anotherContact).removeUserFromItsContact(A_RATTED_IN_ID);
    }

    @Test
    public void givenRattedInAccountWithContacts_whenRemoveItselfFromItsContactsNetwork_thenItsContactListIsEmpty() {
        givenRattedInAccountWithContacts();

        aRattedInAccount.removeItselfFromItsContactsNetwork();

        assertTrue(aRattedInAccount.getContactsUsernames().isEmpty());
    }

    @Test
    public void givenRattedInAccountWithContacts_whenRemoveContactUsingAContactName_thenRemoveItselfFromItsContactsNetwork() {
        givenRattedInAccountWithContacts();

        aRattedInAccount.removeContact(A_CONTACT_RATTED_IN_ID);

        assertFalse(aRattedInAccount.getContactsUsernames().contains(A_CONTACT_NAME));
        assertTrue(aRattedInAccount.getContactsUsernames().contains(ANOTHER_CONTACT_NAME));
    }

    @Test
    public void givenRattedInAccountWithContacts_whenCheckingOneOfHisContact_thenItIsInItsContacts() {
        Mockito.when(mockRattedInAccount.getRattedInID()).thenReturn(A_CONTACT_RATTED_IN_ID);
        givenRattedInAccountWithContacts();

        boolean isInContacts = aRattedInAccount.isInContacts(mockRattedInAccount);

        assertTrue(isInContacts);
    }

    @Test
    public void givenRattedInAccountWithContacts_whenCheckingAnAccountNotInContacts_thenItIsNotInItsContacts() {
        Mockito.when(mockRattedInAccount.getRattedInID()).thenReturn(A_RATTED_IN_ID_NOT_IN_CONTACTS);
        givenRattedInAccountWithContacts();

        boolean isInContact = aRattedInAccount.isInContacts(mockRattedInAccount);

        assertFalse(isInContact);
    }

    @Test
    public void givenRattedInAccountSetToOpenToWork_whenSetToBusy_thenIsOpenToWorkReturnFalse() {
        aRattedInAccount.setToOpenToWork();

        aRattedInAccount.setToBusy();

        assertFalse(aRattedInAccount.isOpenToWork());
    }

    @Test
    public void givenRattedInAccountSetToBusy_whenSetToOpenToWork_thenIsOpenToWorkReturnTrue() {
        aRattedInAccount.setToBusy();

        aRattedInAccount.setToOpenToWork();

        assertTrue(aRattedInAccount.isOpenToWork());
    }

    private void givenRattedInAccountWithContacts() {
        aRattedInAccount.addContact(A_CONTACT_RATTED_IN_ID, aContact);
        aRattedInAccount.addContact(ANOTHER_CONTACT_RATTED_IN_ID, anotherContact);
    }
}
