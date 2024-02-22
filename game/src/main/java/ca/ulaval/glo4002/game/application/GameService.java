package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameAction;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameActionFactory;
import ca.ulaval.glo4002.game.domain.action.GameAction;
import ca.ulaval.glo4002.game.domain.action.ResetGameGameAction;
import ca.ulaval.glo4002.game.domain.game.Game;
import ca.ulaval.glo4002.game.domain.game.GameRepository;

import java.util.List;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameActionEmitter gameActionEmitter;
    private final CharacterInteractionGameActionFactory characterInteractionGameActionFactory;

    @Inject
    public GameService(GameRepository gameRepository, GameActionEmitter gameActionEmitter,
                       CharacterInteractionGameActionFactory characterInteractionGameActionFactory) {
        this.gameRepository = gameRepository;
        this.gameActionEmitter = gameActionEmitter;
        this.characterInteractionGameActionFactory = characterInteractionGameActionFactory;
    }

    public int executeTurn() {
        Game currentGame = gameRepository.getCurrentGame();

        List<GameAction> gameActions = currentGame.nextTurn();
        gameActionEmitter.executeAllActions(gameActions);

        gameRepository.saveCurrentGame(currentGame);

        return currentGame.getTurnNumber();
    }

    public void resetGame() {
        Game currentGame = gameRepository.getCurrentGame();

        currentGame.reset();

        gameActionEmitter.emitAction(new ResetGameGameAction());

        gameRepository.saveCurrentGame(currentGame);
    }

    public void addAction(GameAction gameAction) {
        Game currentGame = gameRepository.getCurrentGame();
        currentGame.addAction(gameAction);
        gameRepository.saveCurrentGame(currentGame);
    }

    public void addCharacterInteraction(String sendingCharacterID, String receivingCharacterID, CharacterInteractionActionType actionType) {
        Game currentGame = gameRepository.getCurrentGame();
        CharacterInteractionGameAction action = characterInteractionGameActionFactory.createCharacterInteractionGameAction(sendingCharacterID,
                                                                                                                           receivingCharacterID,
                                                                                                                           actionType,
                                                                                                                           currentGame.getTurnNumber() + 1);
        currentGame.addCharacterInteraction(action);
        gameRepository.saveCurrentGame(currentGame);
    }
}
