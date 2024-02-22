package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.Optional;
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
public class LawyerSelectorTest {

    private static final int HIGH_REPUTATION = 1000;
    private static final int LOW_REPUTATION = 50;

    @Mock
    private Lawyer aLawyer;
    @Mock
    private Lawyer lawyerOpenToWork, lawyerBusy;
    @Mock
    private Lawyer lawyerWithHighReputation, lawyerWithLowReputation;
    @Mock
    private Lawyer lawyerAlice, lawyerZane;
    @Mock
    private RattedInAccount rattedInAccount;

    private LawyerSelector lawyerSelector;

    @BeforeEach
    public void setUp() {
        lawyerSelector = new LawyerSelector();
    }

    @Test
    public void givenNoLawyersInContactAndALawyerOpenToWork_whenFindBestLawyer_thenNoLawyerIsReturned() {
        Mockito.when(lawyerOpenToWork.isInContacts(rattedInAccount)).thenReturn(false);

        Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(Set.of(lawyerOpenToWork), rattedInAccount);

        assertTrue(lawyerFound.isEmpty());
    }

    @Test
    public void givenABusyLawyerInContact_whenFindBestLawyer_thenNoLawyerIsReturned() {
        Mockito.when(lawyerBusy.isAvailable()).thenReturn(false);
        Mockito.when(lawyerBusy.isInContacts(rattedInAccount)).thenReturn(true);

        Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(Set.of(lawyerBusy), rattedInAccount);

        assertTrue(lawyerFound.isEmpty());
    }

    @Test
    public void givenTwoLawyersInContactAndOpenToWork_whenFindBestLawyer_thenLawyerWithHighestReputationPointsIsReturned() {
        givenTwoLawyersInContactAndOpenToWork();
        Mockito.when(lawyerWithHighReputation.getReputationPoints()).thenReturn(HIGH_REPUTATION);
        Mockito.when(lawyerWithLowReputation.getReputationPoints()).thenReturn(LOW_REPUTATION);

        Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(Set.of(lawyerWithLowReputation, lawyerWithHighReputation), rattedInAccount);

        assertEquals(Optional.of(lawyerWithHighReputation), lawyerFound);
    }

    @Test
    public void givenTwoLawyersInContactAndOpenToWorkAndWithSameReputation_whenFindBestLawyer_thenLawyerIsChosenByAlphabeticalOrder() {
        givenTwoLawyersInContactOpenToWorkAndWithSameReputation();

        Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(Set.of(lawyerZane, lawyerAlice), rattedInAccount);

        assertEquals(Optional.of(lawyerAlice), lawyerFound);
    }

    @Test
    public void whenFindBestLawyerWithNoRattedInAccount_thenNoLawyerIsReturned() {

        Optional<Lawyer> lawyerFound = lawyerSelector.findBestLawyer(Set.of(aLawyer), null);

        assertTrue(lawyerFound.isEmpty());
    }

    private void givenTwoLawyersInContactAndOpenToWork() {
        Mockito.when(lawyerWithHighReputation.isAvailable()).thenReturn(true);
        Mockito.when(lawyerWithLowReputation.isAvailable()).thenReturn(true);
        Mockito.when(lawyerWithHighReputation.isInContacts(rattedInAccount)).thenReturn(true);

        Mockito.when(lawyerWithLowReputation.isInContacts(rattedInAccount)).thenReturn(true);
    }

    private void givenTwoLawyersInContactOpenToWorkAndWithSameReputation() {
        Mockito.when(lawyerZane.isAvailable()).thenReturn(true);
        Mockito.when(lawyerAlice.isAvailable()).thenReturn(true);

        Mockito.when(lawyerAlice.isInContacts(rattedInAccount)).thenReturn(true);
        Mockito.when(lawyerAlice.getName()).thenReturn("Alice");
        Mockito.when(lawyerAlice.getReputationPoints()).thenReturn(HIGH_REPUTATION);

        Mockito.when(lawyerZane.isInContacts(rattedInAccount)).thenReturn(true);
        Mockito.when(lawyerZane.getName()).thenReturn("Zane");
        Mockito.when(lawyerZane.getReputationPoints()).thenReturn(HIGH_REPUTATION);
    }
}
