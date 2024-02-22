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
class BoxOfficeMovieStateTest {

    @Mock
    private Movie movie;

    private BoxOfficeMovieState boxOfficeMovieState;

    @BeforeEach
    public void setUp() {
        boxOfficeMovieState = new BoxOfficeMovieState(movie);
    }

    @Test
    public void whenChangeStep_thenMovieGivesBonus() {

        boxOfficeMovieState.changeStep();

        Mockito.verify(movie).giveBonus();
    }

    @Test
    public void whenChangeStep_thenMovieChangeToArchiveState() {

        boxOfficeMovieState.changeStep();

        Mockito.verify(movie).changeState(Mockito.any(ArchivedMovieState.class));
    }

    @Test
    public void whenGetStatus_thenReturnBOX_OFFICE() {
        assertEquals(MovieStatus.BOX_OFFICE, boxOfficeMovieState.getStatus());
    }
}
