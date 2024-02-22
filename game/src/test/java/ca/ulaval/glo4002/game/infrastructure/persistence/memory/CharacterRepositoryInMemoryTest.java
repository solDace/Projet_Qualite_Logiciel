package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterOnRattedIn;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.character.Lawyer;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CharacterRepositoryInMemoryTest {

    private static final String ACTOR_NAME = "Actor";
    private static final CharacterID ACTOR_ID = new CharacterID(ACTOR_NAME);
    private static final String ANOTHER_ACTOR_NAME = "Hamtaro";
    private static final CharacterID ANOTHER_ACTOR_ID = new CharacterID(ANOTHER_ACTOR_NAME);
    private static final String LAWYER_NAME = "Lawyer";
    private static final CharacterID LAWYER_ID = new CharacterID(LAWYER_NAME);
    private static final String AGENT_NAME = "Agent";
    private static final CharacterID AGENT_ID = new CharacterID(AGENT_NAME);
    private static final String CHARACTER_NAME = "JEAN_123";
    private static final CharacterID CHARACTER_ID = new CharacterID(CHARACTER_NAME);
    private static final String ANOTHER_CHARACTER_NAME = "BON_123";
    private static final CharacterID ANOTHER_CHARACTER_ID = new CharacterID(ANOTHER_CHARACTER_NAME);
    private static final CharacterType CHARACTER_TYPE = CharacterType.ACTOR;

    @Mock
    private Character character, anotherCharacter;
    @Mock
    private Lawyer lawyer;
    @Mock
    private Actor actor, anotherActor;
    @Mock
    private Agent agent;

    private CharacterRepository characterRepository;

    @BeforeEach
    public void setUp() {
        characterRepository = new CharacterRepositoryInMemory();
        characterRepository.deleteAllCharacters();
    }

    @Test
    public void givenNoCharacterInRepository_whenAddingACharacter_thenCharacterIsInRepository() {
        Mockito.when(character.getCharacterID()).thenReturn(CHARACTER_ID);
        Mockito.when(character.getCharacterType()).thenReturn(CHARACTER_TYPE);

        characterRepository.saveCharacter(character);
        Character characterReturned = characterRepository.getCharacter(CHARACTER_ID).orElseThrow();

        assertEquals(character.getCharacterID(), characterReturned.getCharacterID());
        assertEquals(character.getCharacterType(), characterReturned.getCharacterType());
    }

    @Test
    public void whenGettingACharacterNotInRepository_thenReturnEmptyOptional() {
        CharacterID characterNotInRepositoryID = new CharacterID("BobNotInRepo");

        assertTrue(characterRepository.getCharacter(characterNotInRepositoryID).isEmpty());
    }

    @Test
    public void givenTwoCharactersInRepository_whenDeleteAllCharacters_thenThoseAreRemovedFromRepository() {
        givenTwoCharactersInRepository();

        characterRepository.deleteAllCharacters();

        assertTrue(characterRepository.getCharacter(CHARACTER_ID).isEmpty());
        assertTrue(characterRepository.getCharacter(ANOTHER_CHARACTER_ID).isEmpty());
    }

    @Test
    public void givenACharacterInRepository_whenDeleteThisCharacter_thenItIsRemovedFromRepository() {
        Mockito.when(character.getCharacterID()).thenReturn(CHARACTER_ID);
        characterRepository.saveCharacter(character);

        characterRepository.deleteCharacter(CHARACTER_ID);

        assertTrue(characterRepository.getCharacter(CHARACTER_ID).isEmpty());
    }

    @Test
    public void givenTwoCharacters_whenGetAllCharacters_thenReturnTheTwoCharacters() {
        givenTwoCharactersInRepository();

        Set<Character> allCharacters = characterRepository.getAllCharacters();

        assertEquals(Set.of(character, anotherCharacter), allCharacters);
    }

    @Test
    public void givenALawyerAnAgentAndAnActorInRepository_whenGetAllLawyers_thenReturnOnlyTheLawyers() {
        givenALawyerAnAgentAndAnActorInRepository();

        Set<Lawyer> allLawyers = characterRepository.getAllLawyers();

        assertEquals(Set.of(lawyer), allLawyers);
    }

    @Test
    public void givenALawyerAnAgentAndAnActorInRepository_whenGetAllActors_thenReturnOnlyTheActor() {
        givenALawyerAnAgentAndAnActorInRepository();

        Set<Actor> allActors = characterRepository.getAllActors();

        assertEquals(1, allActors.size());
        assertThat(allActors, everyItem(instanceOf(Actor.class)));
    }

    @Test
    public void givenALawyerAnAgentAndAnActorInRepository_whenGetAllAgents_thenReturnOnlyTheAgents() {
        givenALawyerAnAgentAndAnActorInRepository();

        Set<Agent> allChinchillas = characterRepository.getAllAgents();

        assertEquals(1, allChinchillas.size());
        assertThat(allChinchillas, everyItem(instanceOf(Agent.class)));
    }

    @Test
    public void givenALawyerAnAgentAndAnActorInRepository_whenGetAgent_thenReturnOnlyTheAgent() {
        givenALawyerAnAgentAndAnActorInRepository();

        Agent agentInRepository = characterRepository.getAgent(agent.getCharacterID()).orElseThrow();

        assertEquals(agent.getCharacterID(), agentInRepository.getCharacterID());
    }

    @Test
    public void givenACharacterOnRattedIn_whenGetCharacterOnRattedInUsingItsId_thenReturnSaidCharacter() {
        Mockito.when(lawyer.getName()).thenReturn(LAWYER_NAME);
        Mockito.when(lawyer.getCharacterID()).thenReturn(LAWYER_ID);
        characterRepository.saveCharacter(lawyer);

        CharacterOnRattedIn lawyerInRepository = characterRepository.getCharacterOnRattedIn(LAWYER_ID).orElseThrow();

        assertEquals(LAWYER_NAME, lawyerInRepository.getName());
    }

    @Test
    public void givenALawyerAnAgentAndAnActorInRepository_whenGetActor_thenReturnOnlyTheActor() {
        givenALawyerAnAgentAndAnActorInRepository();

        Actor actorInRepository = characterRepository.getActor(actor.getCharacterID()).orElseThrow();

        assertEquals(actor.getCharacterID(), actorInRepository.getCharacterID());
    }

    @Test
    public void givenAnAvailableActorAndABusyActorInRepository_whenGetAvailableActors_thenReturnOnlyTheAvailableActor() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(actor.isAvailable()).thenReturn(true);
        Mockito.when(anotherActor.getCharacterID()).thenReturn(ANOTHER_ACTOR_ID);
        Mockito.when(anotherActor.isAvailable()).thenReturn(false);
        characterRepository.saveCharacter(actor);
        characterRepository.saveCharacter(anotherActor);

        Set<Actor> availableActors = characterRepository.getAvailableActors();

        assertEquals(Set.of(actor), availableActors);
    }

    @Test
    public void givenAnAvailableActorAndAnUnavailableActor_whenGetAvailableActors_thenReturnOnlyTheAvailableOne() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(anotherActor.getCharacterID()).thenReturn(ANOTHER_ACTOR_ID);
        Mockito.when(actor.isAvailable()).thenReturn(true);
        Mockito.when(anotherActor.isAvailable()).thenReturn(false);
        characterRepository.saveCharacter(actor);
        characterRepository.saveCharacter(anotherActor);

        Set<Actor> returnedActors = characterRepository.getAvailableActors();

        assertEquals(Set.of(actor), returnedActors);
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenGetAllActorsRepresentedByThatAgent_thenReturnThatActor() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(actor.getAgent()).thenReturn(Optional.of(agent));
        Mockito.when(agent.getCharacterID()).thenReturn(AGENT_ID);
        characterRepository.saveCharacter(actor);

        Set<Actor> returnedActors = characterRepository.getAllActorsRepresentedByAgent(AGENT_ID);

        assertEquals(Set.of(actor), returnedActors);
    }

    @Test
    public void givenAnActorNotInRepository_whenGetAgentRepresentingActor_thenReturnNoAgent() {

        Optional<Agent> returnedAgent = characterRepository.getAgentRepresentingActor(ACTOR_ID);

        assertTrue(returnedAgent.isEmpty());
    }

    @Test
    public void givenAnActorRepresentedByAnAgent_whenGetAgentRepresentingActor_thenReturnAgentRepresentingThatActor() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(actor.getAgent()).thenReturn(Optional.of(agent));
        characterRepository.saveCharacter(actor);

        Agent returnedAgent = characterRepository.getAgentRepresentingActor(ACTOR_ID).orElseThrow();

        assertEquals(agent, returnedAgent);
    }

    @Test
    public void givenAnActorNotRepresentedByAnAgent_whenGetAgentRepresentingActor_thenReturnEmptyOptional() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(actor.getAgent()).thenReturn(Optional.empty());
        characterRepository.saveCharacter(actor);

        Optional<Agent> returnedAgent = characterRepository.getAgentRepresentingActor(ACTOR_ID);

        assertTrue(returnedAgent.isEmpty());
    }

    @Test
    public void givenACharacterWithALawsuitAndAnotherWithoutLawsuitInRepository_whenGetAgentRepresentingActor_thenReturnEmptyOptional() {
        givenACharacterWithALawsuitAndAnotherWithoutLawsuitInRepository();

        Set<Character> characters = characterRepository.getAllCharactersWithLawsuits();

        assertEquals(Set.of(character), characters);
    }

    private void givenTwoCharactersInRepository() {
        Mockito.when(character.getCharacterID()).thenReturn(CHARACTER_ID);
        Mockito.when(anotherCharacter.getCharacterID()).thenReturn(ANOTHER_CHARACTER_ID);

        characterRepository.saveCharacter(character);
        characterRepository.saveCharacter(anotherCharacter);
    }

    private void givenACharacterWithALawsuitAndAnotherWithoutLawsuitInRepository() {
        givenTwoCharactersInRepository();

        Mockito.when(character.getNbLawsuits()).thenReturn(1);
        Mockito.when(anotherCharacter.getNbLawsuits()).thenReturn(0);
    }

    private void givenALawyerAnAgentAndAnActorInRepository() {
        Mockito.when(lawyer.getCharacterID()).thenReturn(LAWYER_ID);
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(agent.getCharacterID()).thenReturn(AGENT_ID);

        characterRepository.saveCharacter(lawyer);
        characterRepository.saveCharacter(actor);
        characterRepository.saveCharacter(agent);
    }
}
