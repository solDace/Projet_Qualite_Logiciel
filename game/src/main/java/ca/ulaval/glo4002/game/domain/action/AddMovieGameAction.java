package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.movie.MovieType;

public record AddMovieGameAction(String title, MovieType type) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ADD_MOVIE;
    }
}
