package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Agent;
import ca.ulaval.glo4002.game.domain.money.Money;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BoxOfficeTest {

    private static final int ACTOR_FOLLOWERS = 20000, ANOTHER_ACTOR_FOLLOWERS = 22000;
    private static final int AGENT_FOLLOWERS = 12000, ANOTHER_AGENT_FOLLOWERS = 17000;
    private static final Money BOX_OFFICE_TWO_ACTORS_SAME_AGENT = new Money(1040000.0f);
    private static final Money BOX_OFFICE_TWO_ACTORS_DIFFERENT_AGENTS = new Money(1210000.0f);
    private static final Money BOX_OFFICE_TWO_ACTORS_WITHOUT_AN_AGENT = new Money(920000.0f);
    private static final Money ACTOR_BOOSTED_BOX_OFFICE = new Money(100000.0f);

    @Mock
    private Actor actor, anotherActor;
    @Mock
    private Agent agent, anotherAgent;

    private BoxOffice boxOffice;

    @BeforeEach
    public void setUp() {
        boxOffice = new BoxOffice();
    }

    @Test
    public void givenTwoActorsWithoutAnAgentPlayingInAMovie_whenCalculatingBoxOffice_thenBoxOfficeIsCalculated() {
        givenTwoActorsWithoutAnAgentPlayingInAMovie();

        boxOffice.calculateBoxOffice(Set.of(actor, anotherActor));

        assertEquals(BOX_OFFICE_TWO_ACTORS_WITHOUT_AN_AGENT, boxOffice.getBoxOfficeGains());
    }

    @Test
    public void givenTwoActorsWithADifferentAgentPlayingInAMovie_whenCalculatingBoxOffice_thenBoxOfficeIsCalculated() {
        givenTwoActorsWithADifferentAgentPlayingInAMovie();

        boxOffice.calculateBoxOffice(Set.of(actor, anotherActor));

        assertEquals(BOX_OFFICE_TWO_ACTORS_DIFFERENT_AGENTS, boxOffice.getBoxOfficeGains());
    }

    @Test
    public void givenTwoActorsWithSameAgentPlayingInAMovie_whenCalculatingBoxOffice_thenBoxOfficeIsCalculatedWithoutCountingTwiceTheAgentFollowers() {
        givenTwoActorsWithSameAgentPlayingInAMovie();

        boxOffice.calculateBoxOffice(Set.of(actor, anotherActor));

        assertEquals(BOX_OFFICE_TWO_ACTORS_SAME_AGENT, boxOffice.getBoxOfficeGains());
    }

    @Test
    public void givenTwoActors_whenBoostBoxOffice_thenBoxOfficeGainsAreBoostedForEachActor() {
        Mockito.when(actor.boostBoxOffice(Mockito.any(Money.class))).thenReturn(ACTOR_BOOSTED_BOX_OFFICE);
        Mockito.when(anotherActor.boostBoxOffice(Mockito.any(Money.class))).thenReturn(ACTOR_BOOSTED_BOX_OFFICE);

        boxOffice.boostBoxOffice(Set.of(actor, anotherActor));

        Mockito.verify(actor).boostBoxOffice(Mockito.any(Money.class));
        Mockito.verify(anotherActor).boostBoxOffice(Mockito.any(Money.class));
    }

    @Test
    public void givenTwoActors_whenBoostBoxOffice_thenCalculatedBoxOfficeGainsAreTheSameAsTheGainsBoostedByActors() {
        Mockito.when(actor.boostBoxOffice(Mockito.any(Money.class))).thenReturn(ACTOR_BOOSTED_BOX_OFFICE);
        Mockito.when(anotherActor.boostBoxOffice(Mockito.any(Money.class))).thenReturn(ACTOR_BOOSTED_BOX_OFFICE);

        boxOffice.boostBoxOffice(Set.of(actor, anotherActor));

        assertEquals(ACTOR_BOOSTED_BOX_OFFICE, boxOffice.getBoxOfficeGains());
    }

    private void givenTwoActorsWithSameAgentPlayingInAMovie() {
        Mockito.when(actor.getAgent()).thenReturn(Optional.of(agent));
        Mockito.when(anotherActor.getAgent()).thenReturn(Optional.of(agent));

        Mockito.when(actor.getNbFollowers()).thenReturn(ACTOR_FOLLOWERS);
        Mockito.when(anotherActor.getNbFollowers()).thenReturn(ANOTHER_ACTOR_FOLLOWERS);
        Mockito.when(agent.getNbFollowers()).thenReturn(AGENT_FOLLOWERS);
    }

    private void givenTwoActorsWithADifferentAgentPlayingInAMovie() {
        Mockito.when(actor.getAgent()).thenReturn(Optional.of(agent));
        Mockito.when(anotherActor.getAgent()).thenReturn(Optional.of(anotherAgent));

        Mockito.when(actor.getNbFollowers()).thenReturn(ACTOR_FOLLOWERS);
        Mockito.when(anotherActor.getNbFollowers()).thenReturn(ANOTHER_ACTOR_FOLLOWERS);
        Mockito.when(agent.getNbFollowers()).thenReturn(AGENT_FOLLOWERS);
        Mockito.when(anotherAgent.getNbFollowers()).thenReturn(ANOTHER_AGENT_FOLLOWERS);
    }

    private void givenTwoActorsWithoutAnAgentPlayingInAMovie() {
        Mockito.when(actor.getAgent()).thenReturn(Optional.empty());
        Mockito.when(anotherActor.getAgent()).thenReturn(Optional.empty());

        Mockito.when(actor.getNbFollowers()).thenReturn(ACTOR_FOLLOWERS);
        Mockito.when(anotherActor.getNbFollowers()).thenReturn(ANOTHER_ACTOR_FOLLOWERS);
    }
}
