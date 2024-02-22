package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
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
public class AgentTest {

    private static final int HIGH_REPUTATION_POINTS = 100, LOW_REPUTATION_POINTS = 90;
    private static final int AMOUNT_OF_FOLLOWERS = 1000;
    private static final int REALITY_SHOW_FOLLOWERS_TO_WIN = 8000;
    private static final String AGENT_NAME = "Bob";
    private static final CharacterID AGENT_ID = new CharacterID(AGENT_NAME);
    private static final CharacterID AN_ACTOR_ID = new CharacterID("Charlie");
    private static final CharacterID ANOTHER_ACTOR_ID = new CharacterID("Alice");
    private static final Money BOX_OFFICE_GAINS = new Money(762.0f);
    private static final Money SALARY = new Money(280.0f);

    @Mock
    private HamstagramAccount hamstagramAccount;
    @Mock
    private BankAccount bankAccount;
    @Mock
    private Reputation reputation;
    @Mock
    private Actor actor, anotherActor;
    @Mock
    private Lawyer bestLawyer, worseLawyer;
    @Mock
    private Character anotherCharacter;
    @Mock
    private RattedInAccount rattedInAccount;
    @Mock
    private WorkAvailability workAvailability;
    @Mock
    private LawyerSelector lawyerSelector;
    @Mock
    private Lawsuit aLawsuit;

    private Agent agent;

    @BeforeEach
    public void setUpAgent() {
        agent = new Agent(AGENT_ID, AGENT_NAME, bankAccount, reputation, SALARY, workAvailability, hamstagramAccount, rattedInAccount);
    }

    @Test
    public void givenUnavailableAgent_whenSendPropositionToChosenActors_thenActorDoNotReceiveAProposition() {
        givenUnavailableAgent();

        agent.sendPropositionToChosenActors(Set.of(actor));

        Mockito.verify(actor, Mockito.never()).receiveRepresentationPropositionFromAgent(agent);
    }

    @Test
    public void givenAnAvailableAgentAndAnActorNotRepresentedByAnAgent_whenSendPropositionToChosenActors_thenActorReceivesAProposition() {
        givenAvailableAgent();
        Mockito.when(actor.isRepresentedByAnAgent()).thenReturn(false);
        Mockito.when(actor.getReputationPoints()).thenReturn(HIGH_REPUTATION_POINTS);
        Mockito.when(agent.getReputationPoints()).thenReturn(LOW_REPUTATION_POINTS);

        agent.sendPropositionToChosenActors(Set.of(actor));

        Mockito.verify(actor).receiveRepresentationPropositionFromAgent(agent);
    }

    @Test
    public void givenAnAvailableAgentAndAnActorRepresentedByAnAgent_whenSendPropositionToChosenActors_thenActorDoesntReceiveAProposition() {
        givenAvailableAgent();
        Mockito.when(actor.isRepresentedByAnAgent()).thenReturn(true);

        agent.sendPropositionToChosenActors(Set.of(actor));

        Mockito.verify(actor, Mockito.never()).receiveRepresentationPropositionFromAgent(agent);
    }

    @Test
    public void givenAnAvailableAgentRepresentingAnActor_whenSigningWithAnotherActor_thenAgentRepresentsBothActors() {
        givenAnAvailableAgentRepresentingAnActor();

        agent.signWithActor(anotherActor);

        assertTrue(agent.getAllActors().contains(actor));
        assertTrue(agent.getAllActors().contains(anotherActor));
    }

    @Test
    public void givenAnAvailableAgentRepresentingAnActor_whenSigningWithTheSameActor_thenAgentIgnoresNewProposition() {
        givenAnAvailableAgentRepresentingAnActor();

        agent.signWithActor(actor);

        assertEquals(1, agent.getAllActors().size());
        assertTrue(agent.getAllActors().contains(actor));
    }

    @Test
    public void givenAnAgent_whenAnotherCharacterWithSameOrMoreReputationRequestContact_thenAgentAcceptRequest() {
        Mockito.when(anotherCharacter.getReputationPoints()).thenReturn(HIGH_REPUTATION_POINTS);
        Mockito.when(agent.getReputationPoints()).thenReturn(HIGH_REPUTATION_POINTS);

        assertTrue(agent.isContactAcceptable(anotherCharacter));
    }

    @Test
    public void givenAnAgent_whenAnotherCharacterWithLessReputationRequestContact_thenAgentRefuseRequest() {
        Mockito.when(anotherCharacter.getReputationPoints()).thenReturn(LOW_REPUTATION_POINTS);
        Mockito.when(agent.getReputationPoints()).thenReturn(HIGH_REPUTATION_POINTS);

        assertFalse(agent.isContactAcceptable(anotherCharacter));
    }

    @Test
    public void givenOnlyHamstagramAccountUnderEliminationThreshold_whenIsEliminated_thenItIsEliminated() {
        givenOnlyHamstagramAccountUnderEliminationThreshold();

        boolean isUnderEliminationThreshold = agent.isUnderEliminationThreshold();

        assertTrue(isUnderEliminationThreshold);
    }

    @Test
    public void whenAgentIsEliminated_thenRemoveItselfFromItsRattedInContacts() {

        agent.eliminate();

        Mockito.verify(rattedInAccount).removeItselfFromItsContactsNetwork();
    }

    @Test
    public void givenAnAgentWithAClient_whenAgentIsEliminated_thenAgentIsFreeFromHisClient() {
        givenAnAvailableAgentRepresentingAnActor();

        agent.eliminate();

        Mockito.verify(actor).removeAgent();
    }

    @Test
    public void whenReceiveScandal_thenHamstagramReceiveScandalToo() {

        agent.receiveScandal();

        Mockito.verify(hamstagramAccount).receiveScandal();
    }

    @Test
    public void givenAnAgentWithClients_whenReceivingAScandal_thenHeLosesAllItsClients() {
        givenAvailableAgentWithTwoClients();

        agent.receiveScandal();

        Mockito.verify(actor).removeAgent();
        Mockito.verify(anotherActor).removeAgent();
        assertTrue(agent.getAllActors().isEmpty());
    }

    @Test
    public void givenAnAgentWithClients_whenParticipatingToRealityShow_thenHeLosesAllItsClients() {
        givenAvailableAgentWithTwoClients();

        agent.participateToRealityShow();

        Mockito.verify(actor).removeAgent();
        Mockito.verify(anotherActor).removeAgent();
        assertTrue(agent.getAllActors().isEmpty());
    }

    @Test
    public void whenParticipatingToRealityShow_thenWinRealityShowFollowers() {

        agent.participateToRealityShow();

        Mockito.verify(hamstagramAccount).addFollowers(REALITY_SHOW_FOLLOWERS_TO_WIN);
    }

    @Test
    public void givenAnAgentWithClients_whenReceiveHarassmentAccusation_thenHeLosesAllClients() {
        givenAvailableAgentWithTwoClients();

        agent.receiveHarassmentAccusation();

        Mockito.verify(actor).removeAgent();
        Mockito.verify(anotherActor).removeAgent();
        assertTrue(agent.getAllActors().isEmpty());
    }

    @Test
    public void givenAnAgentWithoutALawyer_whenHireBestLawyerWithTwoLawyers_thenUseLawyerReturnedByLawyerFinder() {

        agent.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        Mockito.verify(lawyerSelector).findBestLawyer(Set.of(bestLawyer, worseLawyer), agent.getRattedInAccount().orElseThrow());
    }

    @Test
    public void givenAnAgentWithoutALawyer_whenHireBestLawyerWithTwoAvailableLawyers_thenHeHiresTheLawyerChosenByTheLawyerFinder() {
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(bestLawyer, worseLawyer), agent.getRattedInAccount().orElseThrow()))
               .thenReturn(Optional.of(bestLawyer));

        agent.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        assertEquals(Optional.of(bestLawyer), agent.getLawyer());
    }

    @Test
    public void givenAnAgentWithoutALawyer_whenHireBestLawyerWithNoAvailableLawyer_thenHeDoesNotSignTheLawyer() {
        Mockito.when(lawyerSelector.findBestLawyer(Set.of(bestLawyer, worseLawyer), agent.getRattedInAccount().orElseThrow())).thenReturn(Optional.empty());

        agent.hireBestLawyer(Set.of(bestLawyer, worseLawyer), lawyerSelector);

        assertTrue(agent.getLawyer().isEmpty());
    }

    @Test
    public void givenAnAgentWithALawyer_whenHireBestLawyer_thenHeDoesNotChangeLawyer() {
        agent.hireLawyer(worseLawyer);

        agent.hireBestLawyer(Set.of(bestLawyer), lawyerSelector);

        assertEquals(Optional.of(worseLawyer), agent.getLawyer());
    }

    @Test
    public void givenAgentWithManyClients_whenRemoveActorUsingItsActorId_thenItsRemovedFromClients() {
        givenAgentWithManyClients();
        Mockito.when(actor.getCharacterID()).thenReturn(AN_ACTOR_ID);
        Mockito.when(anotherActor.getCharacterID()).thenReturn(ANOTHER_ACTOR_ID);

        agent.removeActor(AN_ACTOR_ID);

        assertFalse(agent.getAllActors().contains(actor));
        assertTrue(agent.getAllActors().contains(anotherActor));
    }

    @Test
    public void givenAnAgentWithoutALawsuit_whenReceivingALawsuit_thenHisLawsuitNumberIncrements() {

        agent.receiveLawsuit(aLawsuit);

        assertEquals(1, agent.getNbLawsuits());
    }

    @Test
    public void givenAnAgentWithClients_whenReceivingALawsuit_thenHeLoseAllHisClients() {
        agent.signWithActor(actor);
        agent.signWithActor(anotherActor);

        agent.receiveLawsuit(aLawsuit);

        assertTrue(agent.getAllActors().isEmpty());
    }

    @Test
    public void givenAgentWithManyClients_whenClaimFees_thenHisClientsPayFees() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(true);
        agent.signWithActor(actor);
        agent.signWithActor(anotherActor);

        agent.claimFees();

        Mockito.verify(actor).payFees(bankAccount, agent.getSalary());
        Mockito.verify(anotherActor).payFees(bankAccount, agent.getSalary());
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenCharacterLosesAllContacts() {

        agent.receiveHarassmentAccusation();

        Mockito.verify(rattedInAccount).removeItselfFromItsContactsNetwork();
    }

    @Test
    public void whenAddFollowers_thenHamstagramAccountAddFollowers() {

        agent.addFollowers(AMOUNT_OF_FOLLOWERS);

        Mockito.verify(hamstagramAccount).addFollowers(AMOUNT_OF_FOLLOWERS);
    }

    @Test
    public void givenAnAgentPromotingAMovie_whenAgentBoostBoxOffice_thenBoxOfficeGainsAreDouble() {
        givenAgentPromotingAMovie();

        Money returnedValue = agent.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(returnedValue, BOX_OFFICE_GAINS.multiply(2));
    }

    @Test
    public void givenAnAgentWhoHasNotPromotedAMovie_whenAgentBoostBoxOffice_thenBoxOfficeGainsAreDouble() {

        Money returnedValue = agent.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(returnedValue, BOX_OFFICE_GAINS);
    }

    @Test
    public void givenAnAgentWithTooLittleFollowerToPromoteAMovie_whenAgentBoostBoxOffice_thenBoxOfficeGainsAreNotChanged() {
        givenAnAgentWithHamstagramToCannotPromoteAMovie();

        Money returnedValue = agent.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(returnedValue, BOX_OFFICE_GAINS);
    }

    @Test
    public void givenAnAgentWithReputationTooLowToPromoteAMovie_whenAgentBoostBoxOffice_thenBoxOfficeGainsAreNotChanged() {
        givenAnAgentWithReputationThatCannotToPromoteAMovie();

        Money returnedValue = agent.boostBoxOffice(BOX_OFFICE_GAINS);

        assertEquals(returnedValue, BOX_OFFICE_GAINS);
    }

    private void givenAvailableAgentWithTwoClients() {
        givenAnAvailableAgentRepresentingAnActor();
        agent.signWithActor(anotherActor);
    }

    private void givenAgentWithManyClients() {
        givenAvailableAgentWithTwoClients();
        Mockito.when(actor.getCharacterID()).thenReturn(AN_ACTOR_ID);
        Mockito.when(anotherActor.getCharacterID()).thenReturn(ANOTHER_ACTOR_ID);
    }

    private void givenAnAvailableAgentRepresentingAnActor() {
        givenAvailableAgent();
        agent.signWithActor(actor);
    }

    private void givenAvailableAgent() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(true);
    }

    private void givenUnavailableAgent() {
        Mockito.when(workAvailability.isOpenToWork()).thenReturn(false);
    }

    private void givenAgentPromotingAMovie() {
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(true);
        Mockito.when(reputation.canPromoteMovie()).thenReturn(true);
        agent.promoteMovie();
    }

    private void givenAnAgentWithReputationThatCannotToPromoteAMovie() {
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(true);
        Mockito.when(reputation.canPromoteMovie()).thenReturn(false);
        agent.promoteMovie();
    }

    private void givenAnAgentWithHamstagramToCannotPromoteAMovie() {
        Mockito.when(hamstagramAccount.canPromoteMovie()).thenReturn(false);
        agent.promoteMovie();
    }

    private void givenOnlyHamstagramAccountUnderEliminationThreshold() {
        Mockito.when(bankAccount.bankBalanceLowerThanOrEqualToBalanceThreshold()).thenReturn(false);
        Mockito.when(reputation.isUnderEliminationThreshold()).thenReturn(false);
        Mockito.when(hamstagramAccount.isUnderEliminationThreshold()).thenReturn(true);
    }
}
