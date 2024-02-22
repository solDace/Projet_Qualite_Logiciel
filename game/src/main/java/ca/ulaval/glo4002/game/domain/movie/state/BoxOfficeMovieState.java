package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class BoxOfficeMovieState extends MovieState {
    public BoxOfficeMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
        movie.giveBonus();
        movie.changeState(new ArchivedMovieState(movie));
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.BOX_OFFICE;
    }
}
