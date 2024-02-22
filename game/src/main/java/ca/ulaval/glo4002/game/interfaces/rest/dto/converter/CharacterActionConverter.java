package ca.ulaval.glo4002.game.interfaces.rest.dto.converter;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;

import java.util.HashMap;
import java.util.Map;

public class CharacterActionConverter {

    private final Map<String, CharacterInteractionActionType> fromActionCode = new HashMap<>();
    private final Map<CharacterInteractionActionType, String> fromActionType = new HashMap<>();

    public CharacterActionConverter() {
        this.fromActionCode.put("RS", CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW);
        this.fromActionCode.put("PO", CharacterInteractionActionType.PROMOTE_MOVIE);
        this.fromActionCode.put("FR", CharacterInteractionActionType.GOSSIP);
        this.fromActionCode.put("SC", CharacterInteractionActionType.REVEAL_SCANDAL);
        this.fromActionCode.put("PL", CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT);
        this.fromActionType.put(CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW, "RS");
        this.fromActionType.put(CharacterInteractionActionType.PROMOTE_MOVIE, "PO");
        this.fromActionType.put(CharacterInteractionActionType.GOSSIP, "FR");
        this.fromActionType.put(CharacterInteractionActionType.REVEAL_SCANDAL, "SC");
        this.fromActionType.put(CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT, "PL");
    }

    public CharacterInteractionActionType fromActionCode(String actionCode) {
        return fromActionCode.get(actionCode);
    }

    public String toActionCode(CharacterInteractionActionType actionType) {
        return fromActionType.get(actionType);
    }
}
