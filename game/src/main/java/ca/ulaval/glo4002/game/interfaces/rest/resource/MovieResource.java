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

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final MovieService movieService;
    private final GameService gameService;
    private final MoviesDtoAssembler moviesDtoAssembler;

    @Inject
    public MovieResource(MovieService movieService, GameService gameService, MoviesDtoAssembler moviesDtoAssembler) {
        this.movieService = movieService;
        this.gameService = gameService;
        this.moviesDtoAssembler = moviesDtoAssembler;
    }

    @POST
    public Response addMovie(MovieCreationDto movieCreationDto) {
        MovieType type = MovieType.valueOf(movieCreationDto.type());
        GameAction gameAction = new AddMovieGameAction(movieCreationDto.title(), type);
        gameService.addAction(gameAction);

        return Response.ok().build();
    }

    @GET
    public Response getAllMovies() {
        Collection<Movie> movies = movieService.getAllMovies();
        Set<MovieDto> moviesDto = moviesDtoAssembler.assemble(movies);

        return Response.ok().entity(moviesDto).build();
    }
}
