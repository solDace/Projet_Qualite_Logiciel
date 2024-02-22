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
class ReleaseMovieStateTest {

    @Mock
    private Movie movie;

    private ReleaseMovieState releaseMovieState;

    @BeforeEach
    public void setUp() {
        releaseMovieState = new ReleaseMovieState(movie);
    }

    @Test
    public void whenChangeStep_thenMovieCalculateGainInBoxOffice() {

        releaseMovieState.changeStep();

        Mockito.verify(movie).calculateGainInBoxOffice();
    }

    @Test
    public void whenChangeStep_thenMovieChangeToBoxOfficeState() {

        releaseMovieState.changeStep();

        Mockito.verify(movie).changeState(Mockito.any(BoxOfficeMovieState.class));
    }

    @Test
    public void whenGetStatus_thenReturnRELEASE() {
        assertEquals(MovieStatus.RELEASE, releaseMovieState.getStatus());
    }
}
