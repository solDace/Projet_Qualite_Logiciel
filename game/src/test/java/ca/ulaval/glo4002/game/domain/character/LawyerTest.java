package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LawyerTest {

    private static final String LAWYER_NAME = "Charlie", CLIENT_NAME = "Jean";
    private static final CharacterID LAWYER_ID = new CharacterID(LAWYER_NAME);
    private static final String ANOTHER_LAWYER_NAME = "Zoe";
    private static final CharacterID CLIENT_ID = new CharacterID(CLIENT_NAME);
    private static final CharacterID ANOTHER_LAWYER_ID = new CharacterID(LAWYER_NAME);
    private static final Money SALARY = new Money(168.0f);

    @Mock
    private Lawsuit aLawsuit, anotherLawsuit;
    @Mock
    private BankAccount bankAccount;
    @Mock
    private Reputation reputation;
    @Mock
    private Lawyer bestLawyer, worseLawyer;
    @Mock
    private Character client;
    @Mock
    private RattedInAccount rattedInAccount, contactRattedInAccount;
    @Mock
    private WorkAvailability workAvailability;
    @Mock
    private LawyerSelector lawyerSelector;
    @Mock
    private Lawyer anotherLawyer;

    private Character anotherCharacter, lawyerClient;
    private Lawyer lawyer;

    @BeforeEach
    public void setUp() {
        lawyer = new Lawyer(LAWYER_ID, LAWYER_NAME, bankAccount, reputation, SALARY, workAvailability, rattedInAccount);
        lawyerClient = new Lawyer(CLIENT_ID, CLIENT_NAME, bankAccount, reputation, SALARY, workAvailability, contactRattedInAccount);
        anotherCharacter = new Lawyer(ANOTHER_LAWYER_ID, ANOTHER_LAWYER_NAME, bankAccount, reputation, SALARY, workAvailability, contactRattedInAccount);
    }

    @Test
    public void whenAnotherCharacterRequestContact_thenAlwaysAccept() {
        assertTrue(lawyer.isContactAcceptable(anotherCharacter));
    }

    @Test
    public void whenLawyerIsEliminated_thenRemoveItselfFromItsRattedInContacts() {

        lawyer.eliminate();

        Mockito.verify(rattedInAccount).removeItselfFromItsContactsNetwork();
    }

    @Test
    public void givenLawyerWithAClient_whenLawyerIsEliminated_thenLawyerIsFreeFromHisClient() {
        lawyer.startWorkingForClient(client);

        lawyer.eliminate();

        Mockito.verify(client).freeLawyer();
    }

    @Test
    public void givenALawyerWithoutAClient_whenSignClient_thenTheLawyerSetsHitRattedInAccountStatusToBUSY() {

        lawyer.startWorkingForClient(anotherCharacter);

        Mockito.verify(rattedInAccount).setToBusy();
    }

    @Test
    public void whenFinishWorkingForClient_thenTheLawyerSetsHitRattedInAccountStatusToOPEN_TO_WORK() {

        lawyer.finishWorkingForClient();

        Mockito.verify(rattedInAccount).setToOpenToWork();
    }

    @Test
    public void givenALawyerWithoutALawyer_whenHireBestLawyerWithTwoLawyer_thenUseLawyerReturnedByLawyerFinder() {

        lawyer.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        Mockito.verify(lawyerSelector).findBestLawyer(Set.of(bestLawyer, worseLawyer), rattedInAccount);
    }

    @Test
    public void givenALawyerWithoutALawyer_whenHireBestLawyerWithTwoAvailableLawyer_thenTheLawyerHasTheLawyerChosenByTheLawyerFinder() {
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(bestLawyer, worseLawyer), rattedInAccount)).thenReturn(Optional.of(bestLawyer));

        lawyer.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        assertEquals(Optional.of(bestLawyer), lawyer.getLawyer());
    }

    @Test
    public void givenALawyerWithoutALawyer_whenHireBestLawyerWithNoLawyerAvailable_thenTheLawyerDoesNotSignTheLawyer() {
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(bestLawyer, worseLawyer), rattedInAccount)).thenReturn(Optional.empty());

        lawyer.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        assertTrue(lawyer.getLawyer().isEmpty());
    }

    @Test
    public void givenALawyerWithALawyer_whenHireBestLawyer_thenTheLawyerDoesNotChangeLawyer() {
        lawyer.hireLawyer(worseLawyer);

        lawyer.hireBestLawyer(Set.of(bestLawyer), lawyerSelector);

        assertEquals(Optional.of(worseLawyer), lawyer.getLawyer());
    }

    @Test
    public void givenALawyerWithAClient_whenClaimFees_thenHisClientPaysTheFees() {
        lawyer.startWorkingForClient(client);

        lawyer.claimFees();

        Mockito.verify(client).payFees(bankAccount, lawyer.getSalary());
    }

    @Test
    public void givenALawyerNotHired_whenClaimFees_thenNoTransactionIsDone() {

        lawyer.claimFees();

        Mockito.verifyNoInteractions(bankAccount);
    }

    @Test
    public void givenALawyerWithAContact_whenAskingIfAccountIsInContacts_thenItIsInContacts() {
        Mockito.when(rattedInAccount.isInContacts(contactRattedInAccount)).thenReturn(true);

        boolean isLawyerInContacts = lawyer.isInContacts(contactRattedInAccount);

        assertTrue(isLawyerInContacts);
    }

    @Test
    public void givenALawyerWithoutContacts_whenAskingIfAccountIsInContacts_thenItIsNotInContacts() {
        Mockito.when(rattedInAccount.isInContacts(contactRattedInAccount)).thenReturn(false);

        boolean isLawyerInContacts = lawyer.isInContacts(contactRattedInAccount);

        assertFalse(isLawyerInContacts);
    }

    @Test
    public void givenALawyerWithNoClient_whenSettleLawsuitForClient_thenSettleNoLawsuit() {

        Optional<Lawsuit> resolvedLawsuit = lawyer.settleLawsuitForClient();

        assertTrue(resolvedLawsuit.isEmpty());
    }

    @Test
    public void givenALawyerRepresentingAClientWithLawsuits_whenSettleLawsuitForClient_thenSettleALawsuitForHim() {
        lawyerClient.hireLawyer(lawyer);
        lawyerClient.receiveLawsuit(aLawsuit);

        Optional<Lawsuit> resolvedLawsuit = lawyer.settleLawsuitForClient();

        assertEquals(aLawsuit, resolvedLawsuit.orElseThrow());
    }

    @Test
    public void whenReceivingALawsuit_thenHisLawsuitNumberIncrements() {

        lawyer.receiveLawsuit(aLawsuit);

        assertEquals(1, lawyer.getNbLawsuits());
    }

    @Test
    public void whenReceiveLawsuit_thenIsNotAvailableToWork() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(false);

        lawyer.receiveLawsuit(aLawsuit);

        assertFalse(lawyer.isAvailable());
        Mockito.verify(workAvailability).setCurrentlyWorking(false);
    }

    @Test
    public void givenALawyerWithOneRemainingLawsuit_whenResolvingThatLawsuit_thenTheLawyerIsAvailable() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(true);
        Mockito.when(rattedInAccount.isOpenToWork()).thenReturn(true);
        lawyer.receiveLawsuit(aLawsuit);
        lawyer.hireLawyer(anotherLawyer);

        lawyer.resolveLawsuit();

        assertTrue(lawyer.isAvailable());
    }

    @Test
    public void givenALawyerWithManyLawsuits_whenDeleteOlderLawsuit_thenTheLawyerHasOneLessLawsuit() {
        givenALawyerWithManyLawsuits();

        lawyer.resolveLawsuit();

        assertEquals(1, lawyer.getNbLawsuits());
        Mockito.verify(workAvailability, Mockito.never()).setCurrentlyWorking(true);
    }

    @Test
    public void givenALawyerWithAClient_whenReceivingALawsuit_thenTheLawyerLoosesHisClient() {
        lawyer.startWorkingForClient(client);

        lawyer.receiveLawsuit(aLawsuit);

        assertTrue(lawyer.getClient().isEmpty());
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenCharacterLosesAllContacts() {

        lawyer.receiveHarassmentAccusation();

        Mockito.verify(rattedInAccount).removeItselfFromItsContactsNetwork();
    }

    private void givenALawyerWithManyLawsuits() {
        lawyer.receiveLawsuit(aLawsuit);
        lawyer.receiveLawsuit(anotherLawsuit);
        lawyer.hireLawyer(anotherLawyer);
    }
}
