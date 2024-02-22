package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CastingMovieStateTest {

    @Mock
    private Movie movie;

    private CastingMovieState castingMovieState;

    @BeforeEach
    public void setUp() {
        castingMovieState = new CastingMovieState(movie);
    }

    @Test
    public void whenChangeStep_thenMovieAuditionPotentialCast() {

        castingMovieState.changeStep();

        Mockito.verify(movie).auditionPotentialCast();
    }

    @Test
    public void givenAMovieWithACompleteCasting_whenChangeStep_thenMovieChangeToFilmingState() {
        Mockito.when(movie.isCastingComplete()).thenReturn(true);

        castingMovieState.changeStep();

        Mockito.verify(movie).changeState(Mockito.any(FilmingMovieState.class));
    }

    @Test
    public void whenGetStatus_thenReturnCASTING() {
        assertEquals(MovieStatus.CASTING, castingMovieState.getStatus());
    }
}
