package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategyForMovieTypeA;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CastingStrategyForMovieTypeATest {

    private static final int CAST_SIZE = 2;
    private static final Double A_GOOD_RATIO = 100.0D, A_MEDIUM_RATIO = 50.0D, A_BAD_RATIO = 0.0D;

    @Mock
    Movie aMovie;
    @Mock
    private Actor actorWithGoodRatio, actorWithMediumRatio, actorWithBadRatio;

    private CastingStrategyForMovieTypeA castingStrategy;

    @BeforeEach
    public void setupMovieCastingAndActors() {
        castingStrategy = new CastingStrategyForMovieTypeA();
    }

    @Test
    public void givenAMovieWithPotentialCastSizeIsSmallerThanCastSize_whenSelectMovieCast_thenReturnEmptySet() {
        Set<Actor> smallerThanCastSize = Set.of(actorWithBadRatio);
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(smallerThanCastSize);

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertTrue(casting.isEmpty());
    }

    @Test
    public void givenAMovieWithPotentialCastSizeIsHigherThanCastSize_whenSelectMovieCast_thenReturnSetOfActorsWhichSizeEqualCastSize() {
        givenSetOfCandidatesWhichSizeIsGreaterOrEqualToFinalCastSize();
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(actorWithGoodRatio, actorWithMediumRatio, actorWithBadRatio));

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertEquals(CAST_SIZE, casting.size());
    }

    @Test
    public void givenAMovieWithPotentialCastSizeIsHigherThanCastSize_whenSelectMovieCast_thenReturnSetOfActorsWithHighestRatioList() {
        givenSetOfCandidatesWhichSizeIsGreaterOrEqualToFinalCastSize();
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(actorWithGoodRatio, actorWithMediumRatio, actorWithBadRatio));

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertEquals(CAST_SIZE, casting.size());
        assertEquals(Set.of(actorWithGoodRatio, actorWithMediumRatio), casting);
    }

    private void givenSetOfCandidatesWhichSizeIsGreaterOrEqualToFinalCastSize() {
        Mockito.when(actorWithBadRatio.getFollowersByDollarRatio()).thenReturn(A_BAD_RATIO);
        Mockito.when(actorWithMediumRatio.getFollowersByDollarRatio()).thenReturn(A_MEDIUM_RATIO);
        Mockito.when(actorWithGoodRatio.getFollowersByDollarRatio()).thenReturn(A_GOOD_RATIO);
    }
}
