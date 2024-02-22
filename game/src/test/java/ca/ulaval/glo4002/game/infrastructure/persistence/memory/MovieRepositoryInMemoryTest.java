package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieID;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

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
class MovieRepositoryInMemoryTest {

    private static final String MOVIE_TITLE = "Les détestables";
    private static final String ANOTHER_MOVIE_TITLE = "Les intouchables";
    private static final MovieID MOVIE_ID = new MovieID(MOVIE_TITLE);
    private static final MovieID ANOTHER_MOVIE_ID = new MovieID(ANOTHER_MOVIE_TITLE);

    @Mock
    private Movie movie, anotherMovie, relesedMovie, newlyAddedMovie;

    private MovieRepositoryInMemory repository;

    @BeforeEach
    public void setUp() {
        repository = new MovieRepositoryInMemory();
        repository.deleteAllMovies();
    }

    @Test
    public void givenNoMovieInRepository_whenAddingAMovie_thenMovieIsInRepository() {
        Mockito.when(movie.getMovieID()).thenReturn(MOVIE_ID);

        repository.saveMovie(movie);
        Movie movieReturned = repository.getMovie(MOVIE_ID).orElseThrow();

        assertEquals(movie.getTitle(), movieReturned.getTitle());
    }

    @Test
    public void whenGettingAMovieNotInRepository_thenReturnEmptyOptional() {
        MovieID movieNotInRepositoryID = new MovieID("MovieNotInRepo");

        assertTrue(repository.getMovie(movieNotInRepositoryID).isEmpty());
    }

    @Test
    public void givenTwoMoviesInRepository_whenDeleteAllMovies_thenThoseAreRemovedFromRepository() {
        givenTwoMoviesInRepository();

        repository.deleteAllMovies();

        assertTrue(repository.getMovie(MOVIE_ID).isEmpty());
        assertTrue(repository.getMovie(ANOTHER_MOVIE_ID).isEmpty());
    }

    @Test
    public void givenTwoMovies_whenGetAllMovies_thenReturnTheTwoMovies() {
        givenTwoMoviesInRepository();

        Set<Movie> allMovies = repository.getAllMovies();

        assertEquals(Set.of(movie, anotherMovie), allMovies);
    }

    @Test
    public void givenAReleasedMovieAndANewLyAddedMovie_whenGetAllMoviesByStatusReleased_thenOnlyReturnTheMovieWithAssociatedType() {
        givenAReleasedMovieAndANewLyAddedMovie();

        Set<Movie> allMovies = repository.getAllMoviesByStatus(MovieStatus.RELEASE);

        assertEquals(Set.of(relesedMovie), allMovies);
    }

    private void givenTwoMoviesInRepository() {
        Mockito.when(movie.getMovieID()).thenReturn(MOVIE_ID);
        Mockito.when(anotherMovie.getMovieID()).thenReturn(ANOTHER_MOVIE_ID);
        repository.saveMovie(movie);
        repository.saveMovie(anotherMovie);
    }

    private void givenAReleasedMovieAndANewLyAddedMovie() {
        Mockito.when(relesedMovie.getMovieID()).thenReturn(MOVIE_ID);
        Mockito.when(newlyAddedMovie.getMovieID()).thenReturn(ANOTHER_MOVIE_ID);
        Mockito.when(relesedMovie.getStatus()).thenReturn(MovieStatus.RELEASE);
        Mockito.when(newlyAddedMovie.getStatus()).thenReturn(MovieStatus.NEWLY_ADDED);
        repository.saveMovie(relesedMovie);
        repository.saveMovie(newlyAddedMovie);
    }
}
