package ca.ulaval.glo4002.game.domain.movie;

import java.util.Optional;
import java.util.Set;

public interface MovieRepository {
    Optional<Movie> getMovie(MovieID movieID);

    void saveMovie(Movie movie);

    Set<Movie> getAllMovies();

    Set<Movie> getAllMoviesByStatus(MovieStatus movieStatus);

    void deleteAllMovies();
}
