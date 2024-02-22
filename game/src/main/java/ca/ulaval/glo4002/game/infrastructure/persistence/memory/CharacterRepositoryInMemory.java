package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterOnRattedIn;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.Lawyer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterRepositoryInMemory implements CharacterRepository {
    private static final Map<CharacterID, Character> CHARACTERS = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Optional<Character> getCharacter(CharacterID name) {
        return Optional.ofNullable(CHARACTERS.get(name));
    }

    @Override
    public Optional<Actor> getActor(CharacterID name) {
        return getAllActors().stream()
                             .filter(actor -> actor.getCharacterID().equals(name))
                             .findFirst();
    }

    @Override
    public Optional<Agent> getAgent(CharacterID name) {
        return getAllAgents().stream()
                             .filter(agent -> agent.getCharacterID().equals(name))
                             .findFirst();
    }

    @Override
    public Optional<CharacterOnRattedIn> getCharacterOnRattedIn(CharacterID uniqueIdentifier) {
        return CHARACTERS.values().stream()
                         .filter(CharacterOnRattedIn.class::isInstance)
                         .filter(character -> character.getCharacterID().equals(uniqueIdentifier))
                         .findFirst()
                         .map(CharacterOnRattedIn.class::cast);
    }

    @Override
    public Set<Character> getAllCharacters() {
        return new HashSet<>(CHARACTERS.values());
    }

    @Override
    public Set<Character> getAllCharactersWithLawsuits() {
        return CHARACTERS.values().stream()
                         .filter(character -> character.getNbLawsuits() > 0)
                         .collect(Collectors.toSet());
    }

    @Override
    public Set<Agent> getAllAgents() {
        return CHARACTERS.values().stream()
                         .filter(character -> character instanceof Agent)
                         .map(character -> (Agent) character)
                         .collect(Collectors.toSet());
    }

    @Override
    public Set<Lawyer> getAllLawyers() {
        return CHARACTERS.values().stream()
                         .filter(character -> character instanceof Lawyer)
                         .map(character -> (Lawyer) character)
                         .collect(Collectors.toSet());
    }

    @Override
    public Set<Actor> getAllActors() {
        return CHARACTERS.values().stream()
                         .filter(character -> character instanceof Actor)
                         .map(character -> (Actor) character)
                         .collect(Collectors.toSet());
    }

    @Override
    public void saveCharacter(Character character) {
        CHARACTERS.put(character.getCharacterID(), character);
    }

    @Override
    public void deleteAllCharacters() {
        CHARACTERS.clear();
    }

    @Override
    public void deleteCharacter(CharacterID characterID) {
        CHARACTERS.remove(characterID);
    }

    @Override
    public Set<Actor> getAvailableActors() {
        return getAllActors().stream()
                             .filter(Actor::isAvailable)
                             .collect(Collectors.toSet());
    }

    @Override
    public Set<Actor> getAllActorsRepresentedByAgent(CharacterID agentId) {
        return getAllActors().stream()
                             .filter(actor -> actor.getAgent().isPresent() && actor.getAgent().get().getCharacterID().equals(agentId))
                             .collect(Collectors.toSet());
    }

    @Override
    public Optional<Agent> getAgentRepresentingActor(CharacterID actorId) {
        Optional<Actor> actor = getActor(actorId);

        if (actor.isPresent()) {
            return actor.get().getAgent();
        }

        return Optional.empty();
    }
}
