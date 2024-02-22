package ca.ulaval.glo4002.game.domain.movie.state;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class FilmingMovieState extends MovieState {
    public FilmingMovieState(Movie movie) {
        super(movie);
    }

    @Override
    public void changeStep() {
        movie.payActors();
        movie.freeActors();
        movie.changeState(new ReleaseMovieState(movie));
    }

    @Override
    public MovieStatus getStatus() {
        return MovieStatus.FILMING;
    }
}
