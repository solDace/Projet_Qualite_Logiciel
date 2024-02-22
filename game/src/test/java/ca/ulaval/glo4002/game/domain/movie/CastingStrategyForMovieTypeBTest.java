package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategyForMovieTypeB;

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
public class CastingStrategyForMovieTypeBTest {

    private static final int CAST_SIZE = 2;
    private static final Money A_POOR_SALARY = new Money(0.0f);
    private static final Money A_GREAT_SALARY = new Money(10.0f);

    @Mock
    Movie aMovie;
    @Mock
    private Actor aPoorActor, anotherPoorActor, aRichActor;

    private CastingStrategyForMovieTypeB castingStrategy;

    @BeforeEach
    public void setUp() {
        castingStrategy = new CastingStrategyForMovieTypeB();
    }

    @Test
    public void whenSelectMovieCastFromSetOfCandidatesWhichSizeIsSmallerToCastSize_thenReturnEmptySet() {
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(aPoorActor));

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertTrue(casting.isEmpty());
    }

    @Test
    public void givenAMovieWithPotentialCastSizeIsGreaterOrEqualToCastSize_whenSelectMovieCast_thenReturnSetOfActorWhichSizeEqualCastSize() {
        givenSetOfCandidatesWhichSizeIsGreaterOrEqualToCastSize();
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(aPoorActor, aRichActor, anotherPoorActor));

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertEquals(CAST_SIZE, casting.size());
    }

    @Test
    public void givenAMovieWithPotentialCastSizeIsGreaterOrEqualToCastSize_whenSelectMovieCast_thenReturnSetOfActorsWithLowestSalary() {
        givenSetOfCandidatesWhichSizeIsGreaterOrEqualToCastSize();
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(aPoorActor, aRichActor, anotherPoorActor));

        Set<Actor> casting = castingStrategy.selectMovieCast(aMovie, CAST_SIZE);

        assertEquals(CAST_SIZE, casting.size());
        assertEquals(Set.of(aPoorActor, anotherPoorActor), casting);
    }

    private void givenSetOfCandidatesWhichSizeIsGreaterOrEqualToCastSize() {
        Mockito.when(aPoorActor.getSalary()).thenReturn(A_POOR_SALARY);
        Mockito.when(anotherPoorActor.getSalary()).thenReturn(A_POOR_SALARY);
        Mockito.when(aRichActor.getSalary()).thenReturn(A_GREAT_SALARY);
    }
}
