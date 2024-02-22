package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategyFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieFactoryTest {

    private static final String A_MOVIE_TITLE = "Motion picture";
    private static final MovieID A_MOVIE_ID = new MovieID(A_MOVIE_TITLE);

    @Mock
    private CastingStrategyFactory castingStrategyFactory;
    @Mock
    private BoxOffice boxOffice;

    private MovieFactory movieFactory;

    @BeforeEach
    public void setUp() {
        movieFactory = new MovieFactory(castingStrategyFactory);
    }

    @Test
    public void whenCreateWithMovieTypeA_thenReturnMovieCastingStrategyForMovieTypeA() {
        Movie movie = movieFactory.create(A_MOVIE_ID, A_MOVIE_TITLE, MovieType.A, boxOffice);

        Mockito.verify(castingStrategyFactory).create(MovieType.A);
        Assertions.assertEquals(MovieType.A.toString(), movie.getDisplayType());
    }

    @Test
    public void whenCreateWithMovieTypeB_thenReturnMovieWithCastingStrategyForMovieTypeB() {
        Movie movie = movieFactory.create(A_MOVIE_ID, A_MOVIE_TITLE, MovieType.B, boxOffice);

        Mockito.verify(castingStrategyFactory).create(MovieType.B);
        Assertions.assertEquals(MovieType.B.toString(), movie.getDisplayType());
    }
}
