package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class ReleaseMovieState extends MovieState {

    public ReleaseMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
        movie.calculateGainInBoxOffice();
        movie.changeState(new BoxOfficeMovieState(movie));
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.RELEASE;
    }
}
