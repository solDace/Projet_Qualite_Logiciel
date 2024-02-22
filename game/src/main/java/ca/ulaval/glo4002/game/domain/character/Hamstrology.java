package ca.ulaval.glo4002.game.domain.character;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Hamstrology {
    public Optional<Agent> chooseAnAgent(String actorName, Set<Agent> potentialAgents) {
        if (potentialAgents.isEmpty()) {
            return Optional.empty();
        }

        List<Agent> representationPropositionList = potentialAgents.stream().toList();
        Agent representingAgent = representationPropositionList.get(0);
        for (int i = 1; i < representationPropositionList.size(); i++) {
            representingAgent = chooseBetweenTwoAgentProposition(actorName, representingAgent, representationPropositionList.get(i));
        }

        return Optional.of(representingAgent);
    }

    private Agent chooseBetweenTwoAgentProposition(String actorsName, Agent firstAgent, Agent secondAgent) {
        Agent choosedAgent;
        int firstAgentASCIIDistance = Math.abs(actorsName.charAt(0) - firstAgent.getName().charAt(0));
        int secondAgentASCIIDistance = Math.abs(actorsName.charAt(0) - secondAgent.getName().charAt(0));

        if (firstAgentASCIIDistance < secondAgentASCIIDistance) {
            choosedAgent = firstAgent;
        } else if (firstAgentASCIIDistance > secondAgentASCIIDistance) {
            choosedAgent = secondAgent;
        } else {
            choosedAgent = chooseAgentWithHighestReputation(firstAgent, secondAgent);
        }

        return choosedAgent;
    }

    private Agent chooseAgentWithHighestReputation(Agent firstAgent, Agent secondAgent) {
        int firstAgentAgentReputationPoints = firstAgent.getReputationPoints();
        int secondAgentAgentReputationPoints = secondAgent.getReputationPoints();

        return (firstAgentAgentReputationPoints > secondAgentAgentReputationPoints) ? firstAgent : secondAgent;
    }
}
