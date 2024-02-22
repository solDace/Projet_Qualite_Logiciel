package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.Movie;
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
public class ActorTest {
    private static final String CHARACTER_NAME = "Bob";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);
    private static final int REALITY_SHOW_FOLLOWERS_TO_WIN = 20000;
    private static final int NB_OF_FOLLOWERS_ON_HAMSTAGRAM = 10000;
    private static final Money AGENT_BONUS = new Money(16845.0f);
    private static final Money BOX_OFFICE_VALUE = new Money(10.0f);
    private static final Money SALARY = new Money(100.0f);
    private static final double ACTOR_FOLLOWERS_BY_DOLLAR_RATIO = 100.0;
    private static final int AMOUNT_OF_FOLLOWERS = 578;
    private static final Money BOX_OFFICE_GAINS = new Money(451.0f);

    @Mock
    private BankAccount bankAccount;
    @Mock
    private Hamstrology hamstrology;
    @Mock
    private Reputation reputation;
    @Mock
    private HamstagramAccount hamstagramAccount;
    @Mock
    private Movie aMovie;
    @Mock
    private Agent agent, anotherAgent;
    @Mock
    private RattedInAccount agentRattedInAccount;
    @Mock
    private Lawyer bestLawyer, worseLawyer, unavailableLawyer;
    @Mock
    private WorkAvailability workAvailability;
    @Mock
    private LawyerSelector lawyerSelector;
    @Mock
    private Lawsuit aLawsuit;

    private Actor actor;

    @BeforeEach
    public void setup() {
        actor = new Actor(CHARACTER_ID, CHARACTER_NAME, bankAccount, reputation, SALARY, workAvailability, hamstagramAccount);
    }

    @Test
    public void whenCreatingAnActor_thenActorDoesNotHaveAnAgent() {
        assertFalse(actor.isRepresentedByAnAgent());
    }

    @Test
    public void givenAnAvailableActor_whenTurningAMovie_thenActorIsNotAvailable() {

        actor.turnAMovie(aMovie);

        assertFalse(actor.isAvailable());
    }

    @Test
    public void givenAnAvailableActorWithNoProposition_whenChooseFromPotentialAgents_thenHamstrologyDoesntChooseAnAgent() {

        actor.chooseFromPotentialAgents(hamstrology);

        Mockito.verifyNoInteractions(hamstrology);
    }

    @Test
    public void givenAnAvailableActorWithARepresentationProposition_whenChooseFromPotentialAgents_thenHamstrologyChoosesAnAgent() {
        actor.receiveRepresentationPropositionFromAgent(agent);

        actor.chooseFromPotentialAgents(hamstrology);

        Mockito.verify(hamstrology).chooseAnAgent(actor.getName(), Set.of(agent));
    }

    @Test
    public void givenAnActorNotRepresentedByAnAgent_whenSigningWithAnAgent_thenActorIsRepresentedByThisAgent() {

        actor.signWithAgent(agent);

        assertEquals(Optional.of(agent), actor.getAgent());
    }

    @Test
    public void givenAnActorNotRepresentedByAnAgent_whenSigningWithAnAgent_thenAgentSignsWithTheActor() {

        actor.signWithAgent(agent);

        Mockito.verify(agent).signWithActor(actor);
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenSigningWithAnotherAgent_thenActorIsStillRepresentedByTheSameAgent() {
        actor.signWithAgent(agent);

        actor.signWithAgent(anotherAgent);

        assertEquals(Optional.of(agent), actor.getAgent());
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenSigningWithTheSameAgent_thenActorIsStillRepresentedByThisAgent() {
        actor.signWithAgent(agent);

        actor.signWithAgent(agent);

        assertEquals(Optional.of(agent), actor.getAgent());
    }

    @Test
    public void givenAnActorWithAnAgent_whenSendBonusToAgent_thenAgentReceivesBonus() {
        actor.signWithAgent(agent);

        actor.sendBonusToAgent(AGENT_BONUS);

        Mockito.verify(agent).gainMoney(AGENT_BONUS);
    }

    @Test
    public void whenReceiveScandal_thenHamstagramAccountReceivesScandal() {

        actor.receiveScandal();

        Mockito.verify(hamstagramAccount).receiveScandal();
    }

    @Test
    public void givenAnActorInAMovie_whenReceiveScandal_thenIsRemovedFromTheMovie() {
        givenAnActorInAMovie();

        actor.receiveScandal();

        Mockito.verify(aMovie).removeFromCasting(actor.getCharacterID());
    }

    @Test
    public void givenAnActorInAMovie_whenReceiveScandal_thenActorIsAvailable() {
        givenAnActorInAMovie();

        actor.receiveScandal();

        assertTrue(actor.isAvailable());
    }

    @Test
    public void givenAnActorInAMovie_whenParticipatingInRealityShow_thenIsRemovedFromTheMovie() {
        givenAnActorInAMovie();

        actor.participateToRealityShow();

        Mockito.verify(aMovie).removeFromCasting(actor.getCharacterID());
    }

    @Test
    public void whenParticipatingInRealityShow_thenWinRealityShowFollowers() {

        actor.participateToRealityShow();

        Mockito.verify(hamstagramAccount).addFollowers(REALITY_SHOW_FOLLOWERS_TO_WIN);
    }

    @Test
    public void givenAnActorInAMovie_whenReceiveHarassmentAccusation_thenActorIsRemovedFromMovie() {
        givenAnActorInAMovie();

        actor.receiveHarassmentAccusation();

        Mockito.verify(aMovie).removeFromCasting(actor.getCharacterID());
    }

    @Test
    public void whenAddFollowers_thenHamstagramAccountIsAddingFollowers() {

        actor.addFollowers(AMOUNT_OF_FOLLOWERS);

        Mockito.verify(hamstagramAccount).addFollowers(AMOUNT_OF_FOLLOWERS);
    }

    @Test
    public void givenOnlyHamstagramAccountUnderEliminationThreshold_whenIsEliminated_thenItIsEliminated() {
        givenOnlyHamstagramAccountUnderEliminationThreshold();

        boolean isUnderEliminationThreshold = actor.isUnderEliminationThreshold();

        assertTrue(isUnderEliminationThreshold);
    }

    @Test
    public void givenAnActorNotPromotingAMovie_whenBoostBoxOffice_thenSameBoxOfficeIsReturned() {

        Money newBoxOfficeGains = actor.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(BOX_OFFICE_GAINS, newBoxOfficeGains);
    }

    @Test
    public void givenAnActorUnableToPromoteAMovieBecauseOfHisHamstagramAccount_whenBoostBoxOffice_thenSameBoxOfficeIsReturned() {
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(false);
        actor.promoteMovie();

        Money newBoxOfficeGains = actor.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(BOX_OFFICE_GAINS, newBoxOfficeGains);
    }

    @Test
    public void givenAnActorUnableToPromoteAMovieBecauseOfHisReputation_whenBoostBoxOffice_thenSameBoxOfficeIsReturned() {
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(true);
        Mockito.when(reputation.canPromoteMovie()).thenReturn(false);
        actor.promoteMovie();

        Money newBoxOfficeGains = actor.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(BOX_OFFICE_GAINS, newBoxOfficeGains);
    }

    @Test
    public void givenAnActorSuccessfullyPromotingAMovie_whenBoostBoxOffice_thenBoxOfficeGainsIsMultipliedByTwo() {
        givenAnActorSuccessfullyPromotingAMovie();

        Money moneyGains = actor.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(BOX_OFFICE_GAINS.multiply(2), moneyGains);
    }

    @Test
    public void givenAnActorSuccessfullyPromotingAMovieAndBoostingBoxOffice_whenBoostBoxOfficeAgain_thenBoxOfficeIsNotBoosted() {
        givenAnActorSuccessfullyPromotingAMovie();
        actor.promoteMovie();
        Money boostedBoxOfficeGains = actor.boostBoxOffice(BOX_OFFICE_GAINS);

        Money newBoostedBoxOfficeGains = actor.boostBoxOffice(boostedBoxOfficeGains);

        assertEquals(boostedBoxOfficeGains, newBoostedBoxOfficeGains);
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenBoostBoxOffice_thenAgentBoostsBoxOffice() {
        actor.signWithAgent(agent);

        actor.boostBoxOffice(BOX_OFFICE_VALUE);

        Mockito.verify(agent).boostBoxOffice(BOX_OFFICE_VALUE);
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenBoostBoxOffice_thenReturnAgentBoostedBoxOffice() {
        actor.signWithAgent(agent);
        Mockito.when(agent.boostBoxOffice(Mockito.any(Money.class))).thenReturn(BOX_OFFICE_VALUE);

        Money returnedBoxOfficeValue = actor.boostBoxOffice(BOX_OFFICE_VALUE);

        assertEquals(BOX_OFFICE_VALUE, returnedBoxOfficeValue);
    }

    @Test
    public void givenAnActorNotRepresentedByAnAgentAndNotPromotingAMovie_whenBoostBoxOffice_thenReturnBoxOfficeValue() {

        Money returnedValue = actor.boostBoxOffice(BOX_OFFICE_VALUE);

        assertEquals(returnedValue, BOX_OFFICE_VALUE);
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenRemoveAgent_thenActorIsNotRepresentedByAnAgent() {
        actor.signWithAgent(agent);

        actor.removeAgent();

        assertTrue(actor.getAgent().isEmpty());
    }

    @Test
    public void givenAnActorWithoutALawyerAndWithoutAnAgent_whenHireBestLawyerWithTwoLawyers_thenLawyerFinderIsNotCalled() {

        actor.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        Mockito.verify(lawyerSelector, Mockito.never()).findBestLawyer(Mockito.anySet(), Mockito.any(RattedInAccount.class));
    }

    @Test
    public void givenAnActorWithoutALawyerAndWithAnAgent_whenHireBestLawyerWithTwoLawyers_thenLawyerFinderFindsBestLawyer() {
        actor.signWithAgent(agent);
        Mockito.when(agent.getRattedInAccount()).thenReturn(Optional.of(agentRattedInAccount));

        actor.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        Mockito.verify(lawyerSelector).findBestLawyer(Set.of(bestLawyer, worseLawyer), agentRattedInAccount);
    }

    @Test
    public void givenAnActorWithoutALawyerAndWithAnAgent_whenHireBestLawyerWithTwoAvailableLawyers_thenTheChosenLawyerIsTheMostFittingOne() {
        actor.signWithAgent(agent);
        Mockito.when(agent.getRattedInAccount()).thenReturn(Optional.of(agentRattedInAccount));
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(bestLawyer, worseLawyer), agentRattedInAccount)).thenReturn(Optional.of(bestLawyer));

        actor.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        assertEquals(Optional.of(bestLawyer), actor.getLawyer());
    }

    @Test
    public void givenOnlyUnavailableLawyer_whenHireBestLawyer_thenTheActorDoesNotSignALawyer() {
        actor.signWithAgent(agent);
        Mockito.when(agent.getRattedInAccount()).thenReturn(Optional.of(agentRattedInAccount));
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(unavailableLawyer), agentRattedInAccount)).thenReturn(Optional.empty());

        actor.hireBestLawyer(Set.of(unavailableLawyer), lawyerSelector);

        assertTrue(actor.getLawyer().isEmpty());
    }

    @Test
    public void givenAnActorWithALawyer_whenHireBestLawyer_thenTheAgentDoesNotChangeLawyer() {
        actor.hireLawyer(worseLawyer);

        actor.hireBestLawyer(Set.of(bestLawyer), lawyerSelector);

        assertEquals(Optional.of(worseLawyer), actor.getLawyer());
    }

    @Test
    public void whenFreeFromCurrentMovie_thenWorkAvailabilityIsChangedToFalse() {

        actor.freeFromCurrentMovie();

        Mockito.verify(workAvailability).setCurrentlyWorking(false);
    }

    @Test
    public void givenActorWithFollowers_whenGetFollowersByDollarRatio_thenNumberOfFollowerDividedByItsSalary() {
        Mockito.when(hamstagramAccount.getNbFollowers()).thenReturn(NB_OF_FOLLOWERS_ON_HAMSTAGRAM);

        double ratio = actor.getFollowersByDollarRatio();

        assertEquals(ACTOR_FOLLOWERS_BY_DOLLAR_RATIO, ratio);
    }

    @Test
    public void givenAnActorWithoutALawsuit_whenReceivingALawsuit_thenHisLawsuitNumberIncrements() {

        actor.receiveLawsuit(aLawsuit);

        assertEquals(1, actor.getNbLawsuits());
    }

    @Test
    public void givenAnActorInAMovie_whenReceivingALawsuit_thenHeHisRemovedFromTheMovieCasting() {
        givenAnActorInAMovie();

        actor.receiveLawsuit(aLawsuit);

        Mockito.verify(aMovie).removeFromCasting(actor.getCharacterID());
    }

    @Test
    public void givenAnActorInAMovie_whenEliminate_thenActorIsRemovedFromMovie() {
        givenAnActorInAMovie();

        actor.eliminate();

        Mockito.verify(aMovie).removeFromCasting(actor.getCharacterID());
    }

    @Test
    public void givenAnActorWithAnAgent_whenIsEliminated_thenAgentIsNotWorkingForTheActorAnymore() {
        actor.signWithAgent(agent);

        actor.eliminate();

        Mockito.verify(agent).removeActor(actor.getCharacterID());
        assertTrue(actor.getAgent().isEmpty());
    }

    private void givenAnActorInAMovie() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(true);
        actor.turnAMovie(aMovie);
    }

    private void givenAnActorSuccessfullyPromotingAMovie() {
        Mockito.when(reputation.canPromoteMovie()).thenReturn(true);
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(true);
        actor.promoteMovie();
    }

    private void givenOnlyHamstagramAccountUnderEliminationThreshold() {
        Mockito.when(bankAccount.bankBalanceLowerThanOrEqualToBalanceThreshold()).thenReturn(false);
        Mockito.when(reputation.isUnderEliminationThreshold()).thenReturn(false);
        Mockito.when(hamstagramAccount.isUnderEliminationThreshold()).thenReturn(true);
    }
}
