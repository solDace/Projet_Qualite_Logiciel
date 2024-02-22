package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BoxOffice {

    private static final Money BASE_GAIN = new Money(500000.0f);
    private static final Money ADDITIONAL_BONUS_PER_FOLLOWER = new Money(10.0f);
    private Money boxOfficeGains;

    public BoxOffice() {
        this.boxOfficeGains = new Money(0.0f);
    }

    public Money getBoxOfficeGains() {
        return new Money(boxOfficeGains.floatValue());
    }

    public void calculateBoxOffice(Collection<Actor> casting) {
        boxOfficeGains = new Money(BASE_GAIN.floatValue());

        Money additionalGain = ADDITIONAL_BONUS_PER_FOLLOWER.multiply(totalNumberOfFollowers(casting));

        boxOfficeGains = boxOfficeGains.add(additionalGain);
    }

    public void boostBoxOffice(Collection<Actor> casting) {
        casting.forEach(actor -> boxOfficeGains = actor.boostBoxOffice(boxOfficeGains));
    }

    private int totalNumberOfFollowers(Collection<Actor> actors) {
        Set<Agent> agents = agentsOfActors(actors);
        int numberOfActorsFollowers = 0;
        int numberOfAgentsFollowers = 0;

        for (Actor actor : actors) {
            numberOfActorsFollowers += actor.getNbFollowers();
        }
        for (Agent agent : agents) {
            numberOfAgentsFollowers += agent.getNbFollowers();
        }

        return numberOfActorsFollowers + numberOfAgentsFollowers;
    }

    private Set<Agent> agentsOfActors(Collection<Actor> actors) {
        Set<Agent> agents = new HashSet<>();
        for (Actor actor : actors) {
            if (actor.getAgent().isPresent()) {
                agents.add(actor.getAgent().get());
            }
        }

        return agents;
    }
}
