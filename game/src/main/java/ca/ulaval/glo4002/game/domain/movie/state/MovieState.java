package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public abstract class MovieState {
    protected final Movie movie;

    protected MovieState(Movie movie) {
        this.movie = movie;
    }

    public abstract void changeStep();

    public abstract MovieStatus getStatus();
}
