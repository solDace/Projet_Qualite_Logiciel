package ca.ulaval.glo4002.game.domain.movie.factory;

import ca.ulaval.glo4002.game.domain.movie.MovieID;

public class MovieIDFactory {
    public MovieID create(String movieName) {
        return new MovieID(movieName);
    }
}
