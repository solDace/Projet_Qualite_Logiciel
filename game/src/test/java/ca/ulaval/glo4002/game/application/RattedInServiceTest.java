package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
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

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RattedInServiceTest {

    private static final String USERNAME = "Zorro";
    private static final String RECEIVER_USERNAME = "Jina";
    private static final String REQUESTER_USERNAME = "Sally";
    private static final RattedInID RATTED_IN_ID = new RattedInID(USERNAME);
    private static final RattedInID RECEIVER_RATTED_IN_ID = new RattedInID(RECEIVER_USERNAME);
    private static final RattedInID REQUESTER_RATTED_IN_ID = new RattedInID(REQUESTER_USERNAME);
    private static final CharacterID RECEIVER_ID = new CharacterID(RECEIVER_USERNAME);
    private static final CharacterID REQUESTER_ID = new CharacterID(REQUESTER_USERNAME);

    @Mock
    private CharacterOnRattedIn contactReceiver;
    @Mock
    private Character character;
    @Mock
    private RattedInAccount aRattedInAccount, anotherRattedInAccount;
    @Mock
    private RattedInRepository rattedInRepository;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private RattedInContactFactory contactFactory;
    @Mock
    private RattedInContact rattedInContact;
    @Mock
    private RattedInIDFactory rattedInIDFactory;
    @Mock
    private CharacterIDFactory characterIDFactory;

    private RattedInService rattedInService;

    @BeforeEach
    public void setUp() {
        rattedInService =
            new RattedInService(rattedInRepository, characterRepository, contactFactory, rattedInIDFactory, characterIDFactory);
    }

    @Test
    public void givenAUsername_whenGettingRattedInAccount_thenReturnAssociatedRattedInAccount() {
        Mockito.when(rattedInRepository.getRattedInAccount(RATTED_IN_ID)).thenReturn(Optional.of(aRattedInAccount));
        Mockito.when(rattedInIDFactory.create(USERNAME)).thenReturn(RATTED_IN_ID);

        RattedInAccount returnedAccount = rattedInService.getRattedInAccount(USERNAME);

        assertEquals(aRattedInAccount, returnedAccount);
    }

    @Test
    public void givenNoAccountForAUsername_whenGettingRattedInAccount_thenThrowAccountNotFoundException() {
        assertThrows(RattedInAccountNotFoundException.class, () -> rattedInService.getRattedInAccount(USERNAME));
    }

    @Test
    public void givenTwoRattedInAccountsAndRequesterRespectsRequirement_whenMakeContactRequest_thenContactsAddEachOther() {
        givenAReceivingCharacterAndARequestingCharacter();
        givenTwoRattedInAccounts();
        Mockito.when(contactReceiver.isContactAcceptable(character)).thenReturn(true);

        rattedInService.makeContactRequest(RECEIVER_USERNAME, REQUESTER_USERNAME);

        Mockito.verify(aRattedInAccount).addContact(REQUESTER_RATTED_IN_ID, rattedInContact);
        Mockito.verify(anotherRattedInAccount).addContact(RECEIVER_RATTED_IN_ID, rattedInContact);
    }

    @Test
    public void givenTwoRattedInAccountsAndRequesterDoesNotRespectsRequirement_whenMakeContactRequest_thenNeitherContactAreAdded() {
        givenAReceivingCharacterAndARequestingCharacter();
        Mockito.when(contactReceiver.isContactAcceptable(character)).thenReturn(false);

        rattedInService.makeContactRequest(RECEIVER_USERNAME, REQUESTER_USERNAME);

        Mockito.verify(aRattedInAccount, Mockito.never()).addContact(REQUESTER_RATTED_IN_ID, rattedInContact);
        Mockito.verify(anotherRattedInAccount, Mockito.never()).addContact(RECEIVER_RATTED_IN_ID, rattedInContact);
    }

    private void givenTwoRattedInAccounts() {
        Mockito.when(rattedInRepository.getRattedInAccount(RECEIVER_RATTED_IN_ID)).thenReturn(Optional.of(aRattedInAccount));
        Mockito.when(rattedInRepository.getRattedInAccount(REQUESTER_RATTED_IN_ID)).thenReturn(Optional.of(anotherRattedInAccount));
        Mockito.when(character.getName()).thenReturn(REQUESTER_USERNAME);
        Mockito.when(contactReceiver.getName()).thenReturn(RECEIVER_USERNAME);
        Mockito.when(contactFactory.create(anotherRattedInAccount, aRattedInAccount)).thenReturn(rattedInContact);
    }

    private void givenAReceivingCharacterAndARequestingCharacter() {
        Mockito.when(characterRepository.getCharacter(REQUESTER_ID)).thenReturn(Optional.of(character));
        Mockito.when(characterRepository.getCharacterOnRattedIn(RECEIVER_ID)).thenReturn(Optional.of(contactReceiver));
        Mockito.when(characterIDFactory.create(RECEIVER_USERNAME)).thenReturn(RECEIVER_ID);
        Mockito.when(characterIDFactory.create(REQUESTER_USERNAME)).thenReturn(REQUESTER_ID);
        Mockito.when(rattedInIDFactory.create(RECEIVER_USERNAME)).thenReturn(RECEIVER_RATTED_IN_ID);
        Mockito.when(rattedInIDFactory.create(REQUESTER_USERNAME)).thenReturn(REQUESTER_RATTED_IN_ID);
    }
}
