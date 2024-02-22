package ca.ulaval.glo4002.game.domain.movie.casting;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Set;

public interface CastingStrategy {
    Set<Actor> selectMovieCast(Movie movie, int finalCastSize);
}
