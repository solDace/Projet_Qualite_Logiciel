package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MovieDtoAssemblerTest {

    private static final String AN_ACTOR_NAME = "Bob", ANOTHER_ACTOR_NAME = "Laurence";
    private static final Money BOX_OFFICE_VALUE = new Money(9999);

    @Mock
    private Actor anActor, anotherActor;
    @Mock
    private Movie aMovie;

    private MovieDtoAssembler movieDtoAssembler;

    @BeforeEach
    public void setUp() {
        movieDtoAssembler = new MovieDtoAssembler();
    }

    @Test
    public void givenAMovie_whenAssemblingMovieDto_thenMovieDtoIsReturnedWithMovieValues() {
        givenAMovieWithActors();

        MovieDto movieDto = movieDtoAssembler.assemble(aMovie);

        assertEquals(aMovie.getTitle(), movieDto.title());
        assertEquals(aMovie.getDisplayType(), movieDto.type());
        assertEquals(aMovie.getBoxOfficeValue().intValue(), movieDto.boxOffice());
        assertThat(movieDto.casting(), Matchers.containsInAnyOrder(AN_ACTOR_NAME, ANOTHER_ACTOR_NAME));
        assertThat(movieDto.potentialCasting(), Matchers.containsInAnyOrder(AN_ACTOR_NAME, ANOTHER_ACTOR_NAME));
    }

    private void givenAMovieWithActors() {
        Mockito.when(aMovie.getTitle()).thenReturn("Le film A");
        Mockito.when(aMovie.getDisplayType()).thenReturn("A");
        Mockito.when(aMovie.getBoxOfficeValue()).thenReturn(BOX_OFFICE_VALUE);
        Mockito.when(aMovie.getCasting()).thenReturn(Set.of(anActor, anotherActor));
        Mockito.when(aMovie.getPotentialCasting()).thenReturn(Set.of(anActor, anotherActor));
        Mockito.when(anActor.getName()).thenReturn(AN_ACTOR_NAME);
        Mockito.when(anotherActor.getName()).thenReturn(ANOTHER_ACTOR_NAME);
    }
}
