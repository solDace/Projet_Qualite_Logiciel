package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class ArchivedMovieState extends MovieState {
    public ArchivedMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.ARCHIVED;
    }
}
