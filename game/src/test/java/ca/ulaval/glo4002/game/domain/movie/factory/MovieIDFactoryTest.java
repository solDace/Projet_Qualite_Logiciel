package ca.ulaval.glo4002.game.domain.movie.factory;

import ca.ulaval.glo4002.game.domain.movie.MovieID;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieIDFactoryTest {
    private static final String MOVIE = "Inception";
    private static final MovieID MOVIE_ID = new MovieID(MOVIE);

    @Test
    public void whenCreateMovieID_thenMovieIDIsCreated() {
        MovieIDFactory movieIDFactory = new MovieIDFactory();

        MovieID movieID = movieIDFactory.create(MOVIE);

        assertEquals(MOVIE_ID, movieID);
    }
}
