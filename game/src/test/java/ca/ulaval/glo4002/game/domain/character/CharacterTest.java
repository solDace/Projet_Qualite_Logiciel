package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.Optional;

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
public class CharacterTest {

    private static final Money BANK_BALANCE = new Money(1000.0f), EMPTY_BALANCE = new Money(0.0f);
    private static final Money NON_EMPTY_BALANCE = new Money(10.0f);
    private static final Money AMOUNT_OF_MONEY = new Money(100.0f);
    private static final Money SALARY = new Money(100.0f);
    private static final Money REALITY_SHOW_MONEY_TO_WIN = new Money(50000);
    private static final String CHARACTER_NAME = "Bob";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);

    @Mock
    private Lawsuit aLawsuit, anotherLawsuit;
    @Mock
    private Reputation reputation, targetReputation;
    @Mock
    private WorkAvailability workAvailability;
    @Mock
    private Lawyer lawyer;
    @Mock
    private BankAccount bankAccount;

    private Character character, targetCharacter;

    @BeforeEach
    public void setUp() {
        character = new ConcreteCharacterTest(CHARACTER_ID, CHARACTER_NAME, bankAccount, reputation, SALARY, workAvailability);
    }

    @Test
    public void givenACharacterOpenToWork_whenCheckingAvailability_thenIsAvailable() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(true);

        boolean availability = character.isAvailable();

        assertTrue(availability);
    }

    @Test
    public void givenACharacterNotOpenToWork_whenCheckingAvailability_thenIsNotAvailable() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(false);

        boolean availability = character.isAvailable();

        assertFalse(availability);
    }

    @Test
    public void whenLoseReputation_thenReputationIsSubtractedThatAmount() {

        int reputationLost = 245;
        character.loseReputation(reputationLost);

        Mockito.verify(reputation).losePoints(reputationLost);
    }

    @Test
    public void givenOnlyReputationUnderEliminationThreshold_whenIsUnderEliminationThreshold_thenItIsTrue() {
        givenReputationUnderEliminationThreshold();
        givenANotEmptyBankBalance();

        boolean isUnderEliminationThreshold = character.isUnderEliminationThreshold();

        assertTrue(isUnderEliminationThreshold);
    }

    @Test
    public void givenOnlyEmptyBankBalance_whenIsUnderEliminationThreshold_thenItIsTrue() {
        givenEmptyBankBalance();
        givenReputationOverEliminationThreshold();

        boolean isUnderEliminationThreshold = character.isUnderEliminationThreshold();

        assertTrue(isUnderEliminationThreshold);
    }

    @Test
    public void givenBankAccountAndReputationOverEliminationThreshold_whenIsUnderEliminationThreshold_thenItIsFalse() {
        givenANotEmptyBankBalance();
        givenReputationOverEliminationThreshold();

        assertFalse(character.isUnderEliminationThreshold());
    }

    @Test
    public void whenLoseMoney_thenBankAccountWithdrawThisAmountOfMoney() {

        character.loseMoney(AMOUNT_OF_MONEY);

        Mockito.verify(bankAccount).withdraw(AMOUNT_OF_MONEY);
    }

    @Test
    public void whenGainSalary_thenSalaryAmountIsAddedToBankBalanceAmount() {

        character.gainSalary();

        Mockito.verify(bankAccount).deposit(SALARY);
    }

    @Test
    public void whenGainMoney_thenBankAccountDepositThatAmount() {

        character.gainMoney(AMOUNT_OF_MONEY);

        Mockito.verify(bankAccount).deposit(AMOUNT_OF_MONEY);
    }

    @Test
    public void givenATargetCharacter_whenPayFees_thenBankAccountTransfersTheMoneyToTargetCharacterBankAccount() {
        BankAccount targetBankAccount = new BankAccount(BANK_BALANCE);

        character.payFees(targetBankAccount, AMOUNT_OF_MONEY);

        Mockito.verify(bankAccount).transferMoney(targetBankAccount, AMOUNT_OF_MONEY);
    }

    @Test
    public void givenCharacterWithoutEnoughReputationPoints_whenRevealScandal_thenTargetedCharacterDoesNotReceiveAScandal() {
        Character targetedCharacter = Mockito.mock(Character.class);
        Mockito.when(reputation.canRevealScandal()).thenReturn(false);

        character.revealScandal(targetedCharacter);

        Mockito.verifyNoInteractions(targetedCharacter);
    }

    @Test
    public void givenCharacterWithEnoughReputationPoints_whenRevealScandal_thenTargetedCharacterLosesReputation() {
        givenTargetCharacter();
        Mockito.when(reputation.canRevealScandal()).thenReturn(true);

        character.revealScandal(targetCharacter);

        Mockito.verify(targetReputation).receiveScandal();
    }

    @Test
    public void givenCharacterWithEnoughReputationTargetingAnotherCharacter_whenRevealScandal_thenTargetedWorkAvailabilityIsAffectedByScandal() {
        givenTargetCharacter();
        Mockito.when(reputation.canRevealScandal()).thenReturn(true);

        character.revealScandal(targetCharacter);

        Mockito.verify(workAvailability).receiveScandal();
    }

    @Test
    public void whenGossipAboutTargetCharacter_thenHasSentRumorsIsSetTrue() {
        givenTargetCharacter();

        character.gossipAbout(targetCharacter);

        assertTrue(character.hasAlreadySentARumor());
    }

    @Test
    public void givenATargetCharacter_whenGossipAboutTargetCharacter_thenTargetCharacterReceiveRumor() {
        givenTargetCharacter();

        character.gossipAbout(targetCharacter);

        Mockito.verify(targetReputation).receiveRumor();
    }

    @Test
    public void whenParticipateToRealityShow_thenCharacterGainsMoneyFromRealityShow() {

        character.participateToRealityShow();

        Mockito.verify(bankAccount).deposit(REALITY_SHOW_MONEY_TO_WIN);
    }

    @Test
    public void whenParticipateToRealityShow_thenCharacterLosesReputationFromRealityShow() {

        character.participateToRealityShow();

        Mockito.verify(reputation).participateToRealityShow();
    }

    @Test
    public void whenParticipateToRealityShow_thenWorkAvailabilityIsAffectedByTheParticipationToTheShow() {

        character.participateToRealityShow();

        Mockito.verify(workAvailability).participateToRealityShow();
    }

    @Test
    public void givenCharacterThatHasAlreadySentAFalseRumor_whenComplaintForHarassment_thenTargetedCharacterDoesNotReceiveHarassmentAccusation() {
        Character targetedCharacter = Mockito.mock(Character.class);
        character.gossipAbout(character);

        character.complaintForHarassment(targetedCharacter);

        Mockito.verifyNoInteractions(targetedCharacter);
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenCharacterLosesReputation() {

        character.receiveHarassmentAccusation();

        Mockito.verify(reputation).receiveHarassmentAccusation();
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenCharacterWorkAvailabilityIsAffectedByTheAccusation() {

        character.receiveHarassmentAccusation();

        Mockito.verify(workAvailability).receiveHarassmentAccusation();
    }

    @Test
    public void givenCharacterThatHasNeverSentAFalseRumor_whenComplaintForHarassment_thenTargetedCharacterReceivesHarassmentAccusation() {
        Character targetedCharacter = Mockito.mock(Character.class);

        character.complaintForHarassment(targetedCharacter);

        Mockito.verify(targetedCharacter).receiveHarassmentAccusation();
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenCharacterAccountIsAffectedByTheAccusation() {

        character.receiveHarassmentAccusation();

        Mockito.verify(reputation).receiveHarassmentAccusation();
        Mockito.verify(workAvailability).receiveHarassmentAccusation();
    }

    @Test
    public void whenHireLawyer_thenTheCharacterHasTheLawyer() {

        character.hireLawyer(lawyer);

        assertEquals(Optional.of(lawyer), character.getLawyer());
    }

    @Test
    public void whenHireLawyer_thenTheLawyerStartWorkingForTheCharacter() {

        character.hireLawyer(lawyer);

        Mockito.verify(lawyer).startWorkingForClient(character);
    }

    @Test
    public void givenACharacterWithRemainingLawsuits_whenDeleteOlderLawsuit_thenReturnOldestLawsuit() {
        givenACharacterWithRemainingLawsuits();

        Lawsuit lawsuit = character.resolveLawsuit();

        assertEquals(aLawsuit, lawsuit);
    }

    @Test
    public void givenACharacterWithRemainingLawsuits_whenDeleteOlderLawsuit_thenCharacterDoNotFreeHisLawyer() {
        givenACharacterWithRemainingLawsuits();

        character.resolveLawsuit();

        Mockito.verify(lawyer, Mockito.never()).finishWorkingForClient();
    }

    @Test
    public void whenNextTurn_thenWorkAvailabilityExecuteNextTurnItself() {

        character.nextTurn();

        Mockito.verify(workAvailability).nextTurn();
    }

    @Test
    public void givenACharacterWithoutALawsuit_whenReceivingALawsuit_thenTheCharacterHasOneLawsuit() {

        character.receiveLawsuit(aLawsuit);

        assertEquals(1, character.getNbLawsuits());
    }

    @Test
    public void givenACharacterWithALawsuit_whenResolvingThatLawsuit_thenTheCharacterHasNoLawsuit() {
        character.receiveLawsuit(aLawsuit);

        character.resolveLawsuit();

        assertEquals(0, character.getNbLawsuits());
    }

    @Test
    public void givenACharacterRepresentedByALawyer_whenFreeLawyer_thenLawyerFinishesWorkingForClient() {
        character.hireLawyer(lawyer);

        character.freeLawyer();

        Mockito.verify(lawyer).finishWorkingForClient();
    }

    @Test
    public void givenACharacterRepresentedByALawyer_whenFreeLawyer_thenCharacterIsNotRepresentedByALawyerAnymore() {
        character.hireLawyer(lawyer);

        character.freeLawyer();

        assertTrue(character.getLawyer().isEmpty());
    }

    @Test
    public void givenACharacterRepresentedByALawyer_whenEliminated_thenLawyerIsNotWorkingForCharacterAnymore() {
        character.hireLawyer(lawyer);

        character.eliminate();

        Mockito.verify(lawyer).finishWorkingForClient();
        assertTrue(character.getLawyer().isEmpty());
    }

    private void givenReputationUnderEliminationThreshold() {
        Mockito.when(reputation.isUnderEliminationThreshold()).thenReturn(true);
    }

    private void givenReputationOverEliminationThreshold() {
        Mockito.when(reputation.isUnderEliminationThreshold()).thenReturn(false);
    }

    private void givenANotEmptyBankBalance() {
        BankAccount nonEmptyBankAccount = new BankAccount(NON_EMPTY_BALANCE);
        character = new ConcreteCharacterTest(CHARACTER_ID, CHARACTER_NAME, nonEmptyBankAccount, reputation, SALARY, workAvailability);
    }

    private void givenEmptyBankBalance() {
        BankAccount emptyBankAccount = new BankAccount(EMPTY_BALANCE);
        character = new ConcreteCharacterTest(CHARACTER_ID, CHARACTER_NAME, emptyBankAccount, reputation, SALARY, workAvailability);
    }

    private void givenTargetCharacter() {
        BankAccount targetBankAccount = new BankAccount(BANK_BALANCE);
        targetCharacter = new ConcreteCharacterTest(CHARACTER_ID, CHARACTER_NAME, targetBankAccount, targetReputation, SALARY, workAvailability);
    }

    private void givenACharacterWithRemainingLawsuits() {
        character.receiveLawsuit(aLawsuit);
        character.receiveLawsuit(anotherLawsuit);
        character.hireLawyer(lawyer);
    }
}
