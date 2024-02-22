package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.Hamstrology;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RepresentationServiceTest {

    private static final String ACTOR_NAME = "Bob";
    private static final CharacterID ACTOR_ID = new CharacterID(ACTOR_NAME);
    private static final String SECOND_ACTOR_NAME = "Billy";
    private static final String AGENT_NAME = "Robert";
    private static final CharacterID AGENT_ID = new CharacterID(AGENT_NAME);

    private RepresentationService representationService;

    @Mock
    private Actor actor, anotherActor;
    @Mock
    private Agent agent, anotherAgent;
    @Mock
    private Lawyer lawyer, anotherLawyer;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private Hamstrology hamstrology;
    @Mock
    private CharacterIDFactory characterIDFactory;

    @BeforeEach
    public void setUp() {
        representationService = new RepresentationService(characterRepository, hamstrology, characterIDFactory);
    }

    @Test
    public void givenAnAgentButNoActors_whenMakeAllRepresentationArrangements_thenAgentSendsRepresentationPropositionToNoOne() {
        Mockito.when(characterRepository.getAllAgents()).thenReturn(Set.of(agent));
        Mockito.when(characterRepository.getAllActors()).thenReturn(Collections.emptySet());

        representationService.makeAllRepresentationArrangements();

        Mockito.verify(agent).sendPropositionToChosenActors(Collections.emptySet());
    }

    @Test
    public void givenTwoAgentsAndTwoActors_whenMakeAllRepresentationArrangements_thenEachAgentSendsRepresentationPropositions() {
        givenTwoAgentsAndTwoActors();

        representationService.makeAllRepresentationArrangements();

        Mockito.verify(agent).sendPropositionToChosenActors(Set.of(actor, anotherActor));
        Mockito.verify(anotherAgent).sendPropositionToChosenActors(Set.of(actor, anotherActor));
    }

    @Test
    public void givenTwoAgentsAndTwoActors_whenMakeAllRepresentationArrangements_thenEachActorChoosesPropositionWithHamstrology() {
        givenTwoAgentsAndTwoActors();

        representationService.makeAllRepresentationArrangements();

        Mockito.verify(actor).chooseFromPotentialAgents(hamstrology);
        Mockito.verify(anotherActor).chooseFromPotentialAgents(hamstrology);
    }

    @Test
    public void givenAgentsRepresentingActors_whenPayAllRepresentingAgents_thenEachAgentClaimsHisFees() {
        Mockito.when(characterRepository.getAllAgents()).thenReturn(Set.of(agent, anotherAgent));

        representationService.payAllRepresentingAgents();

        Mockito.verify(agent).claimFees();
        Mockito.verify(anotherAgent).claimFees();
    }

    @Test
    public void givenLawyersRepresentingCharacters_whenPayAllRepresentingLawyers_thenEachLawyerClaimsHisFees() {
        Mockito.when(characterRepository.getAllLawyers()).thenReturn(Set.of(lawyer, anotherLawyer));

        representationService.payAllRepresentingLawyers();

        Mockito.verify(lawyer).claimFees();
        Mockito.verify(anotherLawyer).claimFees();
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenGettingAgentName_thenAgentNameIsReturned() {
        Mockito.when(characterRepository.getActor(ACTOR_ID)).thenReturn(Optional.of(actor));
        Mockito.when(actor.getAgent()).thenReturn(Optional.of(agent));
        Mockito.when(agent.getName()).thenReturn(AGENT_NAME);
        Mockito.when(characterIDFactory.create(ACTOR_NAME)).thenReturn(ACTOR_ID);

        String agentName = representationService.getAgentNameRepresentingActor(ACTOR_NAME);

        assertEquals(AGENT_NAME, agentName);
    }

    @Test
    public void givenAnActorNotRepresented_whenGetAgentNameRepresentingActor_thenAgentNameIsEmpty() {
        Mockito.when(characterRepository.getActor(ACTOR_ID)).thenReturn(Optional.of(actor));
        Mockito.when(actor.getAgent()).thenReturn(Optional.empty());
        Mockito.when(characterIDFactory.create(ACTOR_NAME)).thenReturn(ACTOR_ID);

        String agentName = representationService.getAgentNameRepresentingActor(ACTOR_NAME);

        assertTrue(agentName.isEmpty());
    }

    @Test
    public void givenAnAgentRepresentingMultipleActors_whenGetActorsRepresentedByAgent_thenAllActorsNameAreReturnedInAnyOrder() {
        Mockito.when(characterRepository.getAgent(AGENT_ID)).thenReturn(Optional.of(agent));
        Mockito.when(agent.getAllActors()).thenReturn(Set.of(actor, anotherActor));
        Mockito.when(actor.getName()).thenReturn(ACTOR_NAME);
        Mockito.when(anotherActor.getName()).thenReturn(SECOND_ACTOR_NAME);
        Mockito.when(characterIDFactory.create(AGENT_NAME)).thenReturn(AGENT_ID);

        Set<String> actorsNames = representationService.getActorNamesRepresentedByAgent(AGENT_NAME);

        assertThat(actorsNames, Matchers.containsInAnyOrder(ACTOR_NAME, SECOND_ACTOR_NAME));
    }

    @Test
    public void givenAnAgentRepresentingNoActors_whenGetActorsRepresentedByAgent_thenThereIsNoActorsName() {
        Mockito.when(characterRepository.getAgent(AGENT_ID)).thenReturn(Optional.of(agent));
        Mockito.when(agent.getAllActors()).thenReturn(Collections.emptySet());
        Mockito.when(characterIDFactory.create(AGENT_NAME)).thenReturn(AGENT_ID);

        Set<String> actorsNames = representationService.getActorNamesRepresentedByAgent(AGENT_NAME);

        assertTrue(actorsNames.isEmpty());
    }

    private void givenTwoAgentsAndTwoActors() {
        Mockito.when(characterRepository.getAllAgents()).thenReturn(Set.of(agent, anotherAgent));
        Mockito.when(characterRepository.getAllActors()).thenReturn(Set.of(actor, anotherActor));
    }
}
