package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieID;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieRepositoryInMemory implements MovieRepository {

    private static final Map<MovieID, Movie> MOVIES = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public Optional<Movie> getMovie(MovieID movieID) {
        return Optional.ofNullable(MOVIES.get(movieID));
    }

    @Override
    public void saveMovie(Movie movie) {
        MOVIES.put(movie.getMovieID(), movie);
    }

    @Override
    public Set<Movie> getAllMovies() {
        return new LinkedHashSet<>(MOVIES.values());
    }

    @Override
    public Set<Movie> getAllMoviesByStatus(MovieStatus movieStatus) {
        return getAllMovies().stream().filter(movie -> movie.getStatus().equals(movieStatus)).collect(Collectors.toSet());
    }

    @Override
    public void deleteAllMovies() {
        MOVIES.clear();
    }
}
