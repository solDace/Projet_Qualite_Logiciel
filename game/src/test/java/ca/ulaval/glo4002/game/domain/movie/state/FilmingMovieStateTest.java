package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FilmingMovieStateTest {

    @Mock
    private Movie movie;

    private FilmingMovieState filmingMovieState;

    @BeforeEach
    public void setUp() {
        filmingMovieState = new FilmingMovieState(movie);
    }

    @Test
    public void whenChangeStep_thenFirstlyMoviePayActorsThenMovieFreeActors() {

        filmingMovieState.changeStep();

        InOrder movieInOrder = Mockito.inOrder(movie);
        movieInOrder.verify(movie).payActors();
        movieInOrder.verify(movie).freeActors();
    }

    @Test
    public void whenChangeStep_thenMovieChangeToReleaseState() {

        filmingMovieState.changeStep();

        Mockito.verify(movie).changeState(Mockito.any(ReleaseMovieState.class));
    }

    @Test
    public void whenGetStatus_thenReturnFILMING() {
        assertEquals(MovieStatus.FILMING, filmingMovieState.getStatus());
    }
}
