package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class NewlyAddedMovieState extends MovieState {

    public NewlyAddedMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
        movie.changeState(new CastingMovieState(movie));
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.NEWLY_ADDED;
    }
}
