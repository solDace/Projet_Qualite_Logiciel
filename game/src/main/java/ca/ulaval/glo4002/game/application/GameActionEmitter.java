package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.application.listener.CharacterActionListener;
import ca.ulaval.glo4002.game.application.listener.CharacterInteractionActionListener;
import ca.ulaval.glo4002.game.application.listener.EliminationActionListener;
import ca.ulaval.glo4002.game.application.listener.HamstagramActionListener;
import ca.ulaval.glo4002.game.application.listener.LawsuitActionListener;
import ca.ulaval.glo4002.game.application.listener.MovieActionListener;
import ca.ulaval.glo4002.game.application.listener.RattedInActionListener;
import ca.ulaval.glo4002.game.application.listener.RepresentationActionListener;
import ca.ulaval.glo4002.game.application.listener.ResetGameActionListener;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class GameActionEmitter {

    private final List<GameActionListener> listeners = new ArrayList<>();

    @Inject
    public GameActionEmitter(CharacterActionListener characterActionListener,
                             HamstagramActionListener hamstagramActionListener,
                             RattedInActionListener rattedInActionListener,
                             EliminationActionListener eliminationActionListener,
                             ResetGameActionListener resetGameActionListener,
                             RepresentationActionListener representationActionListener,
                             MovieActionListener movieActionListener,
                             CharacterInteractionActionListener characterInteractionActionListener,
                             LawsuitActionListener lawsuitActionListener) {
        this.listeners.add(characterActionListener);
        this.listeners.add(hamstagramActionListener);
        this.listeners.add(rattedInActionListener);
        this.listeners.add(eliminationActionListener);
        this.listeners.add(resetGameActionListener);
        this.listeners.add(representationActionListener);
        this.listeners.add(movieActionListener);
        this.listeners.add(characterInteractionActionListener);
        this.listeners.add(lawsuitActionListener);
    }

    public void executeAllActions(List<GameAction> gameActions) {
        for (GameAction gameAction : gameActions) {
            emitAction(gameAction);
        }
    }

    public void emitAction(GameAction gameAction) {
        for (GameActionListener listener : listeners) {
            listener.onGameAction(gameAction);
        }
    }
}
