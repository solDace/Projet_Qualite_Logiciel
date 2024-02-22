package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class CastingMovieState extends MovieState {
    public CastingMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
        movie.auditionPotentialCast();

        if (movie.isCastingComplete()) {
            movie.changeState(new FilmingMovieState(movie));
        }
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.CASTING;
    }
}
