package ca.ulaval.glo4002.game.domain.game;

public interface GameRepository {
    Game getCurrentGame();

    void saveCurrentGame(Game game);
}
