package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Collection;
import java.util.stream.Collectors;

public class MovieDtoAssembler {

    public MovieDto assemble(Movie movie) {
        Collection<String> casting = movie.getCasting().stream().map(Actor::getName).collect(Collectors.toSet());
        Collection<String> potentialCasting = movie.getPotentialCasting().stream().map(Actor::getName).collect(Collectors.toSet());

        return new MovieDto(movie.getTitle(), movie.getDisplayType(), potentialCasting, casting, movie.getBoxOfficeValue().intValue());
    }
}
