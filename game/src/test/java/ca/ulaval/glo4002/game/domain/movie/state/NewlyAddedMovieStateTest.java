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
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NewlyAddedMovieStateTest {

    @Mock
    private Movie movie;

    private NewlyAddedMovieState newlyAddedMovieState;

    @BeforeEach
    public void setUp() {
        newlyAddedMovieState = new NewlyAddedMovieState(movie);
    }

    @Test
    public void whenChangeStep_thenMovieChangeToCastingState() {

        newlyAddedMovieState.changeStep();

        Mockito.verify(movie).changeState(any(CastingMovieState.class));
    }

    @Test
    public void whenGetStatus_thenReturnNEWLY_ADDED() {
        assertEquals(MovieStatus.NEWLY_ADDED, newlyAddedMovieState.getStatus());
    }

}
