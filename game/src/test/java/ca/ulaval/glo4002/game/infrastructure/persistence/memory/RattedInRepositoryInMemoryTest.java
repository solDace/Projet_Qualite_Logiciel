package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RattedInRepositoryInMemoryTest {

    private static final String USERNAME_WITH_NO_ACCOUNT = "JON_123";
    private static final RattedInID RATTED_IN_ID_WITH_NO_ACCOUNT = new RattedInID(USERNAME_WITH_NO_ACCOUNT);
    private static final String A_USERNAME = "CHARLY_599";
    private static final CharacterID A_CHARACTER_ID = new CharacterID(A_USERNAME);
    private static final RattedInID A_RATTED_IN_ID = new RattedInID(A_USERNAME);
    private static final String ANOTHER_USERNAME = "JENNY_771";
    private static final CharacterID ANOTHER_CHARACTER_ID = new CharacterID(ANOTHER_USERNAME);
    private static final RattedInID ANOTHER_RATTED_IN_ID = new RattedInID(ANOTHER_USERNAME);

    @Mock
    private RattedInAccount aRattedInAccount;
    @Mock
    private RattedInAccount anotherRattedInAccount;

    private RattedInRepositoryInMemory rattedInRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        rattedInRepositoryInMemory = new RattedInRepositoryInMemory();
        rattedInRepositoryInMemory.deleteAllRattedInAccounts();
    }

    @Test
    public void givenAccountDoesNotExist_whenGetRattedInAccount_thenReturnEmptyOptional() {
        assertTrue(rattedInRepositoryInMemory.getRattedInAccount(RATTED_IN_ID_WITH_NO_ACCOUNT).isEmpty());
    }

    @Test
    public void whenSavingRattedInAccount_thenRepositoryCanReturnIt() {
        Mockito.when(aRattedInAccount.getRattedInID()).thenReturn(A_RATTED_IN_ID);
        rattedInRepositoryInMemory.saveRattedInAccount(aRattedInAccount);

        RattedInAccount returnedAccount = rattedInRepositoryInMemory.getRattedInAccount(A_RATTED_IN_ID).orElseThrow();

        assertEquals(aRattedInAccount, returnedAccount);
    }

    @Test
    public void givenRepositoryWithManyRattedInAccounts_whenDeleteAllRattedInAccounts_thenThoseAreNotInTheRepositoryAnymore() {
        givenRepositoryWithManyRattedInAccounts();

        rattedInRepositoryInMemory.deleteAllRattedInAccounts();

        assertTrue(rattedInRepositoryInMemory.getRattedInAccount(A_RATTED_IN_ID).isEmpty());
        assertTrue(rattedInRepositoryInMemory.getRattedInAccount(ANOTHER_RATTED_IN_ID).isEmpty());
    }

    @Test
    public void givenARattedInAccountWithACharacterID_whenDeleteRattedInAccountByThisCharacterID_thenItIsRemovedFromRepository() {
        Mockito.when(aRattedInAccount.getCharacterID()).thenReturn(A_CHARACTER_ID);
        Mockito.when(aRattedInAccount.getRattedInID()).thenReturn(A_RATTED_IN_ID);
        rattedInRepositoryInMemory.saveRattedInAccount(aRattedInAccount);

        rattedInRepositoryInMemory.deleteRattedInAccountByCharacterID(A_CHARACTER_ID);

        assertTrue(rattedInRepositoryInMemory.getRattedInAccount(A_RATTED_IN_ID).isEmpty());
    }

    @Test
    public void givenAnRattedInAccountWithACharacterID_whenDeleteRattedInAccountByAnotherCharacterID_thenNothingIsDeleted() {
        Mockito.when(aRattedInAccount.getCharacterID()).thenReturn(A_CHARACTER_ID);
        Mockito.when(aRattedInAccount.getRattedInID()).thenReturn(A_RATTED_IN_ID);
        rattedInRepositoryInMemory.saveRattedInAccount(aRattedInAccount);

        rattedInRepositoryInMemory.deleteRattedInAccountByCharacterID(ANOTHER_CHARACTER_ID);

        assertFalse(rattedInRepositoryInMemory.getRattedInAccount(A_RATTED_IN_ID).isEmpty());
    }

    private void givenRepositoryWithManyRattedInAccounts() {
        Mockito.when(aRattedInAccount.getRattedInID()).thenReturn(A_RATTED_IN_ID);
        Mockito.when(anotherRattedInAccount.getRattedInID()).thenReturn(ANOTHER_RATTED_IN_ID);
        rattedInRepositoryInMemory.saveRattedInAccount(aRattedInAccount);
        rattedInRepositoryInMemory.saveRattedInAccount(anotherRattedInAccount);
    }
}
