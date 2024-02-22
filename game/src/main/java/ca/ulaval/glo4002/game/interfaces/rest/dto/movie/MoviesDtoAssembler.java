package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

public class MoviesDtoAssembler {

    private final MovieDtoAssembler movieDtoAssembler;

    @Inject
    public MoviesDtoAssembler(MovieDtoAssembler movieDtoAssembler) {
        this.movieDtoAssembler = movieDtoAssembler;
    }

    public Set<MovieDto> assemble(Collection<Movie> movies) {
        return movies.stream().map(movieDtoAssembler::assemble).collect(Collectors.toSet());
    }
}
