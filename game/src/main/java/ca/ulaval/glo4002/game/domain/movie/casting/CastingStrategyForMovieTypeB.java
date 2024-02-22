package ca.ulaval.glo4002.game.domain.movie.casting;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CastingStrategyForMovieTypeB implements CastingStrategy {

    @Override
    public Set<Actor> selectMovieCast(Movie movie, int finalCastSize) {
        Set<Actor> casting = new HashSet<>();

        if (movie.getPotentialCasting().size() >= finalCastSize) {
            Set<Actor> selectedActors = movie.getPotentialCasting()
                                             .stream()
                                             .sorted(Comparator.comparing(Character::getSalary))
                                             .limit(finalCastSize)
                                             .collect(Collectors.toSet());

            selectedActors.forEach(actor -> {
                actor.turnAMovie(movie);
                casting.add(actor);
            });
        }

        return casting;
    }
}
