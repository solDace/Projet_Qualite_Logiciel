package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieID;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import ca.ulaval.glo4002.game.domain.movie.factory.BoxOfficeFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieIDFactory;
import ca.ulaval.glo4002.game.domain.movie.state.MovieState;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    private static final String A_MOVIE_TITLE = "La GLOire de mon Hamster";
    private static final MovieID A_MOVIE_ID = new MovieID(A_MOVIE_TITLE);
    private static final MovieType A_MOVIE_TYPE = MovieType.A;

    @Mock
    private MovieFactory movieFactory;
    @Mock
    private BoxOfficeFactory boxOfficeFactory;
    @Mock
    private Movie aMovie, anotherMovie;
    @Mock
    private MovieState anotherMovieState;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private MovieIDFactory movieIDFactory;

    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        movieService = new MovieService(movieRepository, movieFactory, boxOfficeFactory, characterRepository, movieIDFactory);
    }

    @Test
    public void whenAllMoviesChangeStep_thenMovieRepositoryFetchAllMovies() {

        movieService.allMoviesChangeStep();

        Mockito.verify(movieRepository).getAllMovies();
    }

    @Test
    public void givenManyMoviesInRepository_whenGettingAllMoviesFromRepository_thenAllMoviesInRepositoryAreReturned() {
        Mockito.when(movieRepository.getAllMovies()).thenReturn(Set.of(aMovie, anotherMovie));

        Collection<Movie> movies = movieService.getAllMovies();

        assertTrue(movies.contains(aMovie));
        assertTrue(movies.contains(anotherMovie));
    }

    @Test
    public void givenManyMoviesInCastingInRepository_whenAllMoviesChangeStep_thenFirstMoviesChangeStepThenMoviesAddActorsToPotentialCast() {
        Mockito.when(movieRepository.getAllMovies()).thenReturn(Set.of(aMovie, anotherMovie));
        Mockito.when(aMovie.getStatus()).thenReturn(MovieStatus.CASTING);
        Mockito.when(anotherMovie.getStatus()).thenReturn(MovieStatus.CASTING);

        movieService.allMoviesChangeStep();

        InOrder aMovieStateInOrder = Mockito.inOrder(aMovie);
        aMovieStateInOrder.verify(aMovie).changeStep();
        aMovieStateInOrder.verify(aMovie).addActorsToPotentialCast(Set.of());
        InOrder anotherMovieStateInOrder = Mockito.inOrder(anotherMovieState, anotherMovie);
        anotherMovieStateInOrder.verify(anotherMovie).changeStep();
        anotherMovieStateInOrder.verify(anotherMovie).addActorsToPotentialCast(Set.of());
    }

    @Test
    public void givenAMovieNotInRepository_whenAddMovie_thenMovieIsSentToRepository() {
        Mockito.when(movieRepository.getMovie(A_MOVIE_ID)).thenReturn(Optional.empty());
        Mockito.when(movieFactory.create(A_MOVIE_ID, A_MOVIE_TITLE, A_MOVIE_TYPE, boxOfficeFactory.createBoxOffice())).thenReturn(aMovie);
        Mockito.when(movieIDFactory.create(A_MOVIE_TITLE)).thenReturn(A_MOVIE_ID);

        movieService.addMovie(A_MOVIE_TITLE, A_MOVIE_TYPE);

        Mockito.verify(movieRepository).saveMovie(aMovie);
    }

    @Test
    public void givenAMovieInRepository_whenAddingAMovieWithTheSameTitle_thenMovieIsNotSentToRepository() {
        Mockito.when(movieRepository.getMovie(A_MOVIE_ID)).thenReturn(Optional.of(aMovie));
        Mockito.when(movieIDFactory.create(A_MOVIE_TITLE)).thenReturn(A_MOVIE_ID);

        movieService.addMovie(A_MOVIE_TITLE, A_MOVIE_TYPE);

        Mockito.verify(movieRepository, Mockito.never()).saveMovie(aMovie);
    }

    @Test
    public void givenANewlyAddedMovieInRepository_whenAddingAnotherMovie_thenMovieIsNotAddedToRepository() {
        Mockito.when(movieRepository.getAllMoviesByStatus(MovieStatus.NEWLY_ADDED)).thenReturn(Set.of(anotherMovie));

        movieService.addMovie(A_MOVIE_TITLE, A_MOVIE_TYPE);

        Mockito.verify(movieRepository, Mockito.never()).saveMovie(aMovie);
    }
}
