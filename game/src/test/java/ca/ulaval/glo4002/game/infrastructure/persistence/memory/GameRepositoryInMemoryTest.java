package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.game.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameRepositoryInMemoryTest {

    private GameRepositoryInMemory gameRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        gameRepositoryInMemory = new GameRepositoryInMemory();
    }

    @Test
    public void givenAGame_whenSaved_thenWeCanGetItBack() {
        Game game = Mockito.mock(Game.class);

        gameRepositoryInMemory.saveCurrentGame(game);

        Game savedGame = gameRepositoryInMemory.getCurrentGame();
        assertEquals(game, savedGame);
    }
}
