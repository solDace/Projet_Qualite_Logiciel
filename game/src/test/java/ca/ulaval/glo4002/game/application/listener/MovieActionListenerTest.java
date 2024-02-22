package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.MovieService;
import ca.ulaval.glo4002.game.domain.action.AddMovieGameAction;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.AllMoviesChangeStepGameAction;
import ca.ulaval.glo4002.game.domain.movie.MovieType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieActionListenerTest {

    public static final String A_MOVIE_TITLE = "Le film";
    public static final MovieType A_MOVIE_TYPE = MovieType.A;
    @Mock
    private MovieService movieService;

    private MovieActionListener movieActionListener;

    @BeforeEach
    public void setUp() {
        movieActionListener = new MovieActionListener(movieService);
    }

    @Test
    public void givenAddMovieAction_whenOnGameAction_thenMovieServiceAddsTheMovieIsCalled() {
        AddMovieGameAction gameAction = new AddMovieGameAction(A_MOVIE_TITLE, A_MOVIE_TYPE);

        movieActionListener.onGameAction(gameAction);

        Mockito.verify(movieService).addMovie(gameAction.title(), A_MOVIE_TYPE);
    }

    @Test
    public void givenAllMoviesChangeStepAction_whenOnGameAction_thenAllMoviesChangeStep() {
        AllMoviesChangeStepGameAction gameAction = new AllMoviesChangeStepGameAction();

        movieActionListener.onGameAction(gameAction);

        Mockito.verify(movieService).allMoviesChangeStep();
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        movieActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(movieService);
    }
}
