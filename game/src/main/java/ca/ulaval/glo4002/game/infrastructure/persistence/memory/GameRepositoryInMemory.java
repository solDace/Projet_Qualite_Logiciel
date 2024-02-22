package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.game.Game;
import ca.ulaval.glo4002.game.domain.game.GameRepository;

import org.jvnet.hk2.annotations.Service;

@Service
public class GameRepositoryInMemory implements GameRepository {
    private static Game currentGame = new Game();

    @Override
    public Game getCurrentGame() {
        return currentGame;
    }

    @Override
    public void saveCurrentGame(Game game) {
        currentGame = game;
    }
}
