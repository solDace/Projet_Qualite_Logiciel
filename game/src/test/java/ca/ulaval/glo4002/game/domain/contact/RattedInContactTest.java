package ca.ulaval.glo4002.game.domain.contact;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RattedInContactTest {

    private static final String A_CONTACT_NAME = "Alice";
    private static final RattedInID A_CONTACT_ID = new RattedInID(A_CONTACT_NAME);
    private static final String CONTACT_NAME_TO_REMOVE = "Charlie";
    private static final RattedInID CONTACT_ID_TO_REMOVE = new RattedInID(CONTACT_NAME_TO_REMOVE);

    @Mock
    private RattedInAccount requestingAccount, receivingAccount;

    private RattedInContact rattedInContact;

    @BeforeEach
    public void setUp() {
        rattedInContact = new RattedInContact(requestingAccount, receivingAccount);
    }

    @Test
    public void whenRemoveUserFromItsContactsOnRequestingAccount_thenRequestingContactIsRemovedFromReceivingAccount() {
        Mockito.when(receivingAccount.getRattedInID()).thenReturn(A_CONTACT_ID);
        Mockito.when(requestingAccount.getRattedInID()).thenReturn(CONTACT_ID_TO_REMOVE);

        rattedInContact.removeUserFromItsContact(CONTACT_ID_TO_REMOVE);

        Mockito.verify(receivingAccount).removeContact(CONTACT_ID_TO_REMOVE);
    }

    @Test
    public void whenRemoveUserFromItsContactsOnReceivingAccount_thenReceivingContactIsRemovedFromRequestingAccount() {
        Mockito.when(receivingAccount.getRattedInID()).thenReturn(CONTACT_ID_TO_REMOVE);

        rattedInContact.removeUserFromItsContact(CONTACT_ID_TO_REMOVE);

        Mockito.verify(requestingAccount).removeContact(CONTACT_ID_TO_REMOVE);
    }
}
