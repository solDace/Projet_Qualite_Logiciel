package ca.ulaval.glo4002.game.domain.movie.casting;

import ca.ulaval.glo4002.game.domain.movie.MovieType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CastingStrategyFactoryTest {

    private CastingStrategyFactory castingStrategyFactory;

    @BeforeEach
    public void setUp() {
        castingStrategyFactory = new CastingStrategyFactory();
    }

    @Test
    public void givenMovieTypeA_whenCreatingStrategy_thenCastingStrategyForMovieTypeAIsCreated() {
        MovieType movieTypeA = MovieType.A;

        CastingStrategy castingStrategy = castingStrategyFactory.create(movieTypeA);

        assertTrue(castingStrategy instanceof CastingStrategyForMovieTypeA);
    }

    @Test
    public void givenMovieTypeB_whenCreatingStrategy_thenCastingStrategyForMovieTypeBIsCreated() {
        MovieType movieTypeB = MovieType.B;

        CastingStrategy castingStrategy = castingStrategyFactory.create(movieTypeB);

        assertTrue(castingStrategy instanceof CastingStrategyForMovieTypeB);
    }
}
