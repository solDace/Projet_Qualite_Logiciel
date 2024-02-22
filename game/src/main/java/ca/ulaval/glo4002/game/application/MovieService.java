package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieID;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import ca.ulaval.glo4002.game.domain.movie.factory.BoxOfficeFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieIDFactory;

import java.util.Collection;
import java.util.Set;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieFactory movieFactory;
    private final BoxOfficeFactory boxOfficeFactory;
    private final CharacterRepository characterRepository;
    private final MovieIDFactory movieIDFactory;

    @Inject
    public MovieService(MovieRepository movieRepository, MovieFactory movieFactory, BoxOfficeFactory boxOfficeFactory,
                        CharacterRepository characterRepository, MovieIDFactory movieIDFactory) {
        this.movieRepository = movieRepository;
        this.movieFactory = movieFactory;
        this.boxOfficeFactory = boxOfficeFactory;
        this.characterRepository = characterRepository;
        this.movieIDFactory = movieIDFactory;
    }

    public Collection<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public void addMovie(String title, MovieType type) {
        MovieID movieID = movieIDFactory.create(title);
        if (movieRepository.getAllMoviesByStatus(MovieStatus.NEWLY_ADDED).isEmpty() && movieRepository.getMovie(movieID).isEmpty()) {
            Movie movie = movieFactory.create(movieID, title, type, boxOfficeFactory.createBoxOffice());
            movieRepository.saveMovie(movie);
        }
    }

    public void allMoviesChangeStep() {
        Set<Movie> allMovies = movieRepository.getAllMovies();

        allMovies.forEach(movie -> {
            Set<Actor> availableActors = characterRepository.getAvailableActors();

            movie.changeStep();

            if (movie.getStatus() == MovieStatus.CASTING) {
                movie.addActorsToPotentialCast(availableActors);
            }

            movieRepository.saveMovie(movie);
        });
    }

}
