package ca.ulaval.glo4002.game.interfaces.rest.resource;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.application.MovieService;
import ca.ulaval.glo4002.game.domain.action.AddMovieGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import ca.ulaval.glo4002.game.interfaces.rest.dto.movie.MovieCreationDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.movie.MovieDto;
import ca.ulaval.glo4002.game.interfaces.rest.dto.movie.MoviesDtoAssembler;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MovieResourceTest {

    private static final String A_MOVIE_TITLE = "RUNTOWN BATTLE";
    private static final String A_MOVIE_TYPE = "B";

    @Mock
    private GameService gameService;
    @Mock
    private MovieService movieService;
    @Mock
    private MovieCreationDto movieCreationDto;
    @Mock
    private Collection<Movie> movies;
    @Mock
    private Set<MovieDto> moviesDto;
    @Mock
    private MoviesDtoAssembler moviesDtoAssembler;

    private MovieResource movieResource;

    @BeforeEach
    public void setUp() {
        movieResource = new MovieResource(movieService, gameService, moviesDtoAssembler);
    }

    @Test
    public void whenAddMovie_thenAddMovieGameActionIsAddedToTheGame() {
        Mockito.when(movieCreationDto.title()).thenReturn(A_MOVIE_TITLE);
        Mockito.when(movieCreationDto.type()).thenReturn(A_MOVIE_TYPE);

        movieResource.addMovie(movieCreationDto);

        GameAction gameAction = new AddMovieGameAction(A_MOVIE_TITLE, MovieType.valueOf(A_MOVIE_TYPE));
        Mockito.verify(gameService).addAction(gameAction);
    }

    @Test
    public void whenAddMovie_thenReturnResponseWithStatusOK() {
        Mockito.when(movieCreationDto.type()).thenReturn(A_MOVIE_TYPE);

        Response response = movieResource.addMovie(movieCreationDto);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void givenMovies_whenGetAllMovies_thenReturnStatusOkResponseWithSetOfMovieDtoInItsBody() {
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);
        Mockito.when(moviesDtoAssembler.assemble(movies)).thenReturn(moviesDto);

        Response response = movieResource.getAllMovies();

        assertEquals(moviesDto, response.getEntity());
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
}
