package ca.ulaval.glo4002.game.domain.movie.casting;

import ca.ulaval.glo4002.game.domain.movie.MovieType;

public class CastingStrategyFactory {
    public CastingStrategy create(MovieType type) {
        return switch (type) {
            case A -> new CastingStrategyForMovieTypeA();
            case B -> new CastingStrategyForMovieTypeB();
        };
    }
}
