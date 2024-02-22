package ca.ulaval.glo4002.game.domain.character;

import java.util.Optional;
import java.util.Set;

public interface CharacterRepository {
    Optional<Character> getCharacter(CharacterID name);

    Optional<Actor> getActor(CharacterID name);

    Optional<Agent> getAgent(CharacterID name);

    Optional<CharacterOnRattedIn> getCharacterOnRattedIn(CharacterID uniqueIdentifier);

    void saveCharacter(Character character);

    Set<Character> getAllCharacters();

    Set<Character> getAllCharactersWithLawsuits();

    Set<Agent> getAllAgents();

    Set<Lawyer> getAllLawyers();

    Set<Actor> getAllActors();

    void deleteAllCharacters();

    void deleteCharacter(CharacterID characterID);

    Set<Actor> getAvailableActors();

    Set<Actor> getAllActorsRepresentedByAgent(CharacterID agentID);

    Optional<Agent> getAgentRepresentingActor(CharacterID id);
}
