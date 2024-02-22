package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.Hamstrology;
import ca.ulaval.glo4002.game.domain.character.Lawyer;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class RepresentationService {
    private final CharacterRepository characterRepository;
    private final Hamstrology hamstrology;
    private final CharacterIDFactory characterIDFactory;

    @Inject
    public RepresentationService(CharacterRepository characterRepository, Hamstrology hamstrology, CharacterIDFactory characterIDFactory) {
        this.characterRepository = characterRepository;
        this.hamstrology = hamstrology;
        this.characterIDFactory = characterIDFactory;
    }

    public void makeAllRepresentationArrangements() {
        Set<Agent> agents = characterRepository.getAllAgents();
        Set<Actor> actors = characterRepository.getAllActors();

        agents.forEach(agent -> agent.sendPropositionToChosenActors(actors));
        actors.forEach(actor -> actor.chooseFromPotentialAgents(hamstrology));
    }

    public void payAllRepresentingAgents() {
        characterRepository.getAllAgents().forEach(Agent::claimFees);
    }

    public void payAllRepresentingLawyers() {
        characterRepository.getAllLawyers().forEach(Lawyer::claimFees);
    }

    public String getAgentNameRepresentingActor(String actorUsername) {
        CharacterID actorID = characterIDFactory.create(actorUsername);
        Optional<Actor> actor = characterRepository.getActor(actorID);

        String agentName = "";
        if (actor.isPresent()) {
            Optional<Agent> agent = actor.get().getAgent();
            if (agent.isPresent()) {
                agentName = agent.get().getName();
            }
        }
        return agentName;
    }

    public Set<String> getActorNamesRepresentedByAgent(String agentUsername) {
        CharacterID agentID = characterIDFactory.create(agentUsername);
        Optional<Agent> agent = characterRepository.getAgent(agentID);
        Set<String> actorNamesRepresentedByAgent = new HashSet<>();

        if (agent.isPresent()) {
            actorNamesRepresentedByAgent = agent.get().getAllActors().stream().map(Actor::getName).collect(Collectors.toSet());
        }
        return actorNamesRepresentedByAgent;
    }
}
