package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoviesDtoAssemblerTest {

    @Mock
    private MovieDtoAssembler movieDtoAssembler;
    @Mock
    private Movie a_movie, another_movie;
    private MoviesDtoAssembler moviesDtoAssembler;

    @BeforeEach
    public void setUp() {
        moviesDtoAssembler = new MoviesDtoAssembler(movieDtoAssembler);
    }

    @Test
    public void whenAssembleASetOfMovies_thenMovieDtoAssemblerIsCalledForEachMovie() {

        moviesDtoAssembler.assemble(Set.of(a_movie, another_movie));

        Mockito.verify(movieDtoAssembler, Mockito.atLeastOnce()).assemble(a_movie);
        Mockito.verify(movieDtoAssembler, Mockito.atLeastOnce()).assemble(another_movie);
    }
}
