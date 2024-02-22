package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountNotFoundException;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramID;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramIDFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HamstagramServiceTest {

    private static final int FOLLOWER_LOST = 200;
    private static final String USER_NAME = "user123";
    private static final HamstagramID HAMSTAGRAM_ID = new HamstagramID(USER_NAME);

    @Mock
    private HamstagramAccount anHamstagramAccount;
    @Mock
    private HamstagramAccount anotherHamstagramAccount;
    @Mock
    private HamstagramRepository hamstagramRepository;
    @Mock
    private HamstagramIDFactory hamstagramIDFactory;

    private HamstagramService hamstagramService;

    @BeforeEach
    public void setUp() {
        hamstagramService = new HamstagramService(hamstagramRepository, hamstagramIDFactory);
    }

    @Test
    public void givenAUserID_whenGetHamstagramAccount_thenReturnAssociatedAccount() {
        Mockito.when(hamstagramRepository.getHamstagramAccount(HAMSTAGRAM_ID)).thenReturn(Optional.of(anHamstagramAccount));
        Mockito.when(hamstagramIDFactory.create(USER_NAME)).thenReturn(HAMSTAGRAM_ID);

        HamstagramAccount returnedAccount = hamstagramService.getHamstagramAccount(USER_NAME);

        assertEquals(anHamstagramAccount, returnedAccount);
    }

    @Test
    public void givenAccountDoesNotExistForUserID_whenGetHamstagramAccount_thenThrowAccountNotFoundException() {
        assertThrows(HamstagramAccountNotFoundException.class, () -> hamstagramService.getHamstagramAccount(USER_NAME));
    }

    @Test
    public void givenTwoSavedAccounts_whenAllLoseFollowers_thenThoseAccountsLoseThatManyFollowers() {
        Mockito.when(hamstagramRepository.getAllHamstagramAccounts()).thenReturn(Set.of(anHamstagramAccount, anotherHamstagramAccount));

        hamstagramService.allAccountsLoseFollowers(FOLLOWER_LOST);

        Mockito.verify(anHamstagramAccount).loseFollowers(FOLLOWER_LOST);
        Mockito.verify(anotherHamstagramAccount).loseFollowers(FOLLOWER_LOST);
    }

    @Test
    public void givenTwoSavedAccounts_whenAllLoseFollowers_thenThoseAccountsAreSavedAgain() {
        Mockito.when(hamstagramRepository.getAllHamstagramAccounts()).thenReturn(Set.of(anHamstagramAccount, anotherHamstagramAccount));

        hamstagramService.allAccountsLoseFollowers(FOLLOWER_LOST);

        Mockito.verify(hamstagramRepository).saveHamstagramAccount(anHamstagramAccount);
        Mockito.verify(hamstagramRepository).saveHamstagramAccount(anotherHamstagramAccount);
    }
}
