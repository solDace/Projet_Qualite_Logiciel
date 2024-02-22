package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategy;
import ca.ulaval.glo4002.game.domain.movie.state.MovieState;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MovieTest {

    private static final int CAST_SIZE = 2;
    private static final String ACTOR_NAME = "Actor";
    private static final CharacterID ACTOR_ID = new CharacterID(ACTOR_NAME);
    private static final String ANOTHER_ACTOR_NAME = "Another_Actor";
    private static final CharacterID ANOTHER_ACTOR_ID = new CharacterID(ANOTHER_ACTOR_NAME);
    private static final String MOVIE_TITLE = "BingPot";
    private static final MovieID MOVIE_ID = new MovieID(MOVIE_TITLE);
    private static final Money BOX_OFFICE_VALUE = new Money(900000.0f);

    @Mock
    private BoxOffice boxOffice;
    @Mock
    private CastingStrategy castingStrategy;
    @Mock
    private Actor actor, anotherActor, aBusyActor;
    @Mock
    private MovieState movieState;

    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie(MOVIE_ID, MOVIE_TITLE, MovieType.A, boxOffice, castingStrategy);
    }

    @Test
    public void givenMovieWithAnyMovieState_whenChangeStep_thenMovieStateItselfThatChangeStep() {
        givenMovieWithAnyMovieState();

        movie.changeStep();

        Mockito.verify(movieState).changeStep();
    }

    @Test
    public void whenFreeActors_thenCastingIsEmpty() {

        movie.freeActors();

        assertTrue(movie.getCasting().isEmpty());
    }

    @Test
    public void whenCalculatingGainInBoxOffice_thenInOrderCalculateAndBoostBoxOffice() {
        givenAMovieInBoxOffice();

        movie.calculateGainInBoxOffice();

        InOrder inOrderBoxOffice = Mockito.inOrder(boxOffice);
        inOrderBoxOffice.verify(boxOffice).calculateBoxOffice(Set.of(actor, anotherActor));
        inOrderBoxOffice.verify(boxOffice).boostBoxOffice(Set.of(actor, anotherActor));
    }

    @Test
    public void givenAnAuditioningMovieWithABusyActor_whenAuditionPotentialCast_thenTheBusyActorQuitTheAudition() {
        Mockito.when(aBusyActor.isAvailable()).thenReturn(false);
        Mockito.when(actor.isAvailable()).thenReturn(true);
        movie.addActorsToPotentialCast(Set.of(actor, aBusyActor));

        movie.auditionPotentialCast();

        Mockito.verify(castingStrategy).selectMovieCast(movie, CAST_SIZE);
        assertTrue(movie.getCasting().isEmpty());
    }

    @Test
    public void givenAMovieWithAnIncompleteCasting_whenIsCastingComplete_thenTheCastingIsNotComplete() {
        assertFalse(movie.isCastingComplete());
    }

    @Test
    public void givenAMovieWithACompleteCasting_whenIsCastingComplete_thenTheCastingIsComplete() {
        givenAMovieWithACompleteCasting();

        assertTrue(movie.isCastingComplete());
    }

    @Test
    public void givenAMovieWithAnActorInPotentialCast_whenRemoveFromCasting_thenTheActorIsRemoveFromPotentialCasting() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        movie.addActorsToPotentialCast(Set.of(actor));

        movie.removeFromCasting(ACTOR_ID);

        assertFalse(movie.getPotentialCasting().contains(actor));
    }

    @Test
    public void givenAMovieWithAnActorInCasting_whenRemoveFromCasting_thenTheActorIsRemoveFromCasting() {
        Mockito.when(actor.getCharacterID()).thenReturn(ACTOR_ID);
        Mockito.when(anotherActor.getCharacterID()).thenReturn(ANOTHER_ACTOR_ID);
        givenAMovieWithACompleteCasting();

        movie.removeFromCasting(ACTOR_ID);

        assertFalse(movie.getCasting().contains(actor));
    }

    @Test
    public void givenAMovieOnSet_whenPayActors_thenActorsInCastingArePaid() {
        givenAMovieOnSet();

        movie.payActors();

        Mockito.verify(actor).gainSalary();
        Mockito.verify(anotherActor).gainSalary();
    }

    @Test
    public void givenAnArchivedMovie_whenGiveBonus_thenAgentsGetBonus() {
        Mockito.when(boxOffice.getBoxOfficeGains()).thenReturn(BOX_OFFICE_VALUE);
        givenAnArchivedMovie();

        movie.giveBonus();

        Mockito.verify(actor).sendBonusToAgent(movie.calculateBonusAgents());
    }

    @Test
    public void givenAnArchivedMovie_whenGiveBonus_thenActorsGetBonus() {
        Mockito.when(boxOffice.getBoxOfficeGains()).thenReturn(BOX_OFFICE_VALUE);
        givenAnArchivedMovie();

        movie.giveBonus();

        Mockito.verify(actor).gainMoney(movie.calculateBonusActors());
    }

    @Test
    public void givenAMovieWithInsufficientPotentialCast_whenAuditioning_thenNoActorIsAddedToCasting() {
        movie.addActorsToPotentialCast(Set.of(actor));

        movie.auditionPotentialCast();

        assertTrue(movie.getCasting().isEmpty());
    }

    @Test
    public void givenAMovieWithEnoughPotentialCast_whenAuditioning_thenThePotentialCastIsRemoved() {
        movie.addActorsToPotentialCast(Set.of(actor, anotherActor));

        movie.auditionPotentialCast();

        assertTrue(movie.getPotentialCasting().isEmpty());
    }

    @Test
    public void givenAMovieWithEnoughPotentialCast_whenAuditioning_thenTheCastingIsSelected() {
        Mockito.when(actor.isAvailable()).thenReturn(true);
        Mockito.when(anotherActor.isAvailable()).thenReturn(true);
        Set<Actor> potentialCast = Set.of(actor, anotherActor);
        movie.addActorsToPotentialCast(potentialCast);

        movie.auditionPotentialCast();

        Mockito.verify(castingStrategy).selectMovieCast(movie, CAST_SIZE);
    }

    @Test
    public void givenAMovieWithEnoughPotentialCast_whenAuditioning_thenTheActorsInCastingAreBusy() {
        Mockito.when(actor.isAvailable()).thenReturn(true);
        Mockito.when(anotherActor.isAvailable()).thenReturn(true);
        Set<Actor> potentialCast = Set.of(actor, anotherActor);
        Mockito.when(castingStrategy.selectMovieCast(movie, CAST_SIZE)).thenReturn(potentialCast);
        movie.addActorsToPotentialCast(potentialCast);

        movie.auditionPotentialCast();

        Mockito.verify(actor).turnAMovie(movie);
        Mockito.verify(anotherActor).turnAMovie(movie);
    }

    private void givenMovieWithAnyMovieState() {
        movie.changeState(movieState);
    }

    private void givenAMovieWithACompleteCasting() {
        Mockito.when(actor.isAvailable()).thenReturn(true);
        Mockito.when(anotherActor.isAvailable()).thenReturn(true);
        Mockito.when(castingStrategy.selectMovieCast(movie, CAST_SIZE)).thenReturn(Set.of(actor, anotherActor));
        movie.addActorsToPotentialCast(Set.of(actor, anotherActor));
        movie.auditionPotentialCast();
    }

    private void givenAMovieOnSet() {
        givenAMovieWithACompleteCasting();

        movie.changeStep();
    }

    private void givenAReleasedMovie() {
        givenAMovieOnSet();
        movie.changeStep();
    }

    private void givenAMovieInBoxOffice() {
        givenAReleasedMovie();
        movie.changeStep();
    }

    private void givenAnArchivedMovie() {
        givenAMovieInBoxOffice();
        movie.changeStep();
    }
}
