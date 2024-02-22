package ca.ulaval.glo4002.game.domain.movie.factory;

import ca.ulaval.glo4002.game.domain.movie.BoxOffice;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieID;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategy;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategyFactory;

import jakarta.inject.Inject;

public class MovieFactory {

    private final CastingStrategyFactory castingStrategyFactory;

    @Inject
    public MovieFactory(CastingStrategyFactory castingStrategyFactory) {
        this.castingStrategyFactory = castingStrategyFactory;
    }

    public Movie create(MovieID movieID, String title, MovieType type, BoxOffice boxOffice) {
        CastingStrategy castingStrategy = castingStrategyFactory.create(type);

        return new Movie(movieID, title, type, boxOffice, castingStrategy);
    }
}
