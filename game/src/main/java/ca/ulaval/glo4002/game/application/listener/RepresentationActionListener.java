package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.RepresentationService;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class RepresentationActionListener implements GameActionListener {

    private final RepresentationService representationService;

    @Inject
    public RepresentationActionListener(RepresentationService representationService) {
        this.representationService = representationService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        switch (gameAction.getActionType()) {
            case ALL_AGENTS_REPRESENTATION_REQUEST -> {
                representationService.makeAllRepresentationArrangements();
            }
            case ALL_PAY_AGENT -> {
                representationService.payAllRepresentingAgents();
            }
            case ALL_PAY_LAWYER -> {
                representationService.payAllRepresentingLawyers();
            }
        }
    }
}
