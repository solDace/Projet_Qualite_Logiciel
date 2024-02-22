package ca.ulaval.glo4002.game.domain.movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MovieIDTest {
    private static final String MOVIE_NAME = "Star wars", ANOTHER_MOVIE_NAME = "Inception";

    private MovieID movieID;

    @BeforeEach
    public void setup() {
        movieID = new MovieID(MOVIE_NAME);
    }

    @Test
    public void givenTwoMovieIDCreatedWithSameString_whenComparing_thenReturnTrue() {
        MovieID anIdenticalMovieId = new MovieID(MOVIE_NAME);

        boolean comparison = movieID.equals(anIdenticalMovieId);

        assertTrue(comparison);
    }

    @Test
    public void givenTwoMovieIDsCreatedWithDifferentString_whenEquals_thenReturnFalse() {
        MovieID aDifferentMovieId = new MovieID(ANOTHER_MOVIE_NAME);

        boolean comparison = movieID.equals(aDifferentMovieId);

        assertFalse(comparison);
    }
}
