package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramID;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class HamstagramRepositoryInMemoryTest {

    private static final String USER_NAME_WITH_NO_ACCOUNT = "JEAN_123";
    private static final HamstagramID HAMSTAGRAM_ID_WITH_NO_ACCOUNT = new HamstagramID(USER_NAME_WITH_NO_ACCOUNT);
    private static final String A_USER_NAME = "BOB_123";
    private static final HamstagramID A_HAMSTAGRAM_ID = new HamstagramID(A_USER_NAME);
    private static final CharacterID A_CHARACTER_ID = new CharacterID(A_USER_NAME);
    private static final String ANOTHER_USER_NAME = "ALICE_123";
    private static final HamstagramID ANOTHER_HAMSTAGRAM_ID = new HamstagramID(ANOTHER_USER_NAME);
    private static final CharacterID ANOTHER_CHARACTER_ID = new CharacterID(ANOTHER_USER_NAME);

    @Mock
    private HamstagramAccount anHamstagramAccount;
    @Mock
    private HamstagramAccount anotherHamstagramAccount;

    private HamstagramRepositoryInMemory hamstagramRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        hamstagramRepositoryInMemory = new HamstagramRepositoryInMemory();
        hamstagramRepositoryInMemory.deleteAllHamstagramAccounts();
    }

    @Test
    public void givenAccountDoesNotExist_whenGetHamstagramAccount_thenReturnEmptyOptional() {
        assertTrue(hamstagramRepositoryInMemory.getHamstagramAccount(HAMSTAGRAM_ID_WITH_NO_ACCOUNT).isEmpty());
    }

    @Test
    public void whenWeSaveAnHamstagramAccount_thenWeCanGetItFromTheRepository() {
        Mockito.when(anHamstagramAccount.getHamstagramID()).thenReturn(A_HAMSTAGRAM_ID);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anHamstagramAccount);

        HamstagramAccount returnedAccount = hamstagramRepositoryInMemory.getHamstagramAccount(A_HAMSTAGRAM_ID).orElseThrow();

        assertEquals(anHamstagramAccount, returnedAccount);
    }

    @Test
    public void givenTwoHamstagramAccounts_whenGetHamstagramForUserID_thenReturnAssociatedHamstagramAccount() {
        givenTwoHamstagramAccountsInRepository();

        HamstagramAccount returnedAccount = hamstagramRepositoryInMemory.getHamstagramAccount(ANOTHER_HAMSTAGRAM_ID).orElseThrow();

        assertEquals(anotherHamstagramAccount, returnedAccount);
    }

    @Test
    public void givenTwoHamstagramAccounts_whenGetAllHamstagramAccounts_thenReturnTheTwoHamstagramAccounts() {
        givenTwoHamstagramAccountsInRepository();

        Set<HamstagramAccount> allAccounts = hamstagramRepositoryInMemory.getAllHamstagramAccounts();

        assertEquals(Set.of(anHamstagramAccount, anotherHamstagramAccount), allAccounts);
    }

    @Test
    public void givenTwoHamstagramAccountsInRepository_whenDeleteAllHamstagramAccounts_thenThoseAreNotInTheRepositoryAnymore() {
        givenTwoHamstagramAccountsInRepository();

        hamstagramRepositoryInMemory.deleteAllHamstagramAccounts();

        assertTrue(hamstagramRepositoryInMemory.getHamstagramAccount(A_HAMSTAGRAM_ID).isEmpty());
        assertTrue(hamstagramRepositoryInMemory.getHamstagramAccount(ANOTHER_HAMSTAGRAM_ID).isEmpty());
    }

    @Test
    public void givenAnHamstagramAccountWithACharacterID_whenDeleteHamstagramAccountByThisCharacterID_thenItIsRemovedFromRepository() {
        Mockito.when(anHamstagramAccount.getCharacterID()).thenReturn(A_CHARACTER_ID);
        Mockito.when(anHamstagramAccount.getHamstagramID()).thenReturn(A_HAMSTAGRAM_ID);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anHamstagramAccount);

        hamstagramRepositoryInMemory.deleteHamstagramAccountsByCharacterID(A_CHARACTER_ID);

        assertTrue(hamstagramRepositoryInMemory.getHamstagramAccount(A_HAMSTAGRAM_ID).isEmpty());
    }

    @Test
    public void givenAnHamstagramAccountWithACharacterID_whenDeleteHamstagramAccountByAnotherCharacterID_thenNothingIsDeleted() {
        Mockito.when(anHamstagramAccount.getCharacterID()).thenReturn(A_CHARACTER_ID);
        Mockito.when(anHamstagramAccount.getHamstagramID()).thenReturn(A_HAMSTAGRAM_ID);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anHamstagramAccount);

        hamstagramRepositoryInMemory.deleteHamstagramAccountsByCharacterID(ANOTHER_CHARACTER_ID);

        assertEquals(1, hamstagramRepositoryInMemory.getAllHamstagramAccounts().size());
    }

    @Test
    public void givenAHamstagramAccountInRepository() {
        Mockito.when(anHamstagramAccount.getCharacterID()).thenReturn(A_CHARACTER_ID);
        Mockito.when(anHamstagramAccount.getHamstagramID()).thenReturn(A_HAMSTAGRAM_ID);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anHamstagramAccount);

        hamstagramRepositoryInMemory.deleteHamstagramAccountsByCharacterID(A_CHARACTER_ID);

        assertTrue(hamstagramRepositoryInMemory.getHamstagramAccount(A_HAMSTAGRAM_ID).isEmpty());
    }

    private void givenTwoHamstagramAccountsInRepository() {
        Mockito.when(anHamstagramAccount.getHamstagramID()).thenReturn(A_HAMSTAGRAM_ID);
        Mockito.when(anotherHamstagramAccount.getHamstagramID()).thenReturn(ANOTHER_HAMSTAGRAM_ID);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anHamstagramAccount);
        hamstagramRepositoryInMemory.saveHamstagramAccount(anotherHamstagramAccount);
    }
}
