package ca.ulaval.glo4002.game.interfaces.rest.dto.converter;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CharacterActionConverterTest {

    private static final String REALITY_SHOW_ACTION_CODE = "RS";
    private static final String PROMOTE_MOVIE_ACTION_CODE = "PO";
    private static final String GOSSIP_ACTION_CODE = "FR";
    private static final String REVEAL_SCANDAL_ACTION_CODE = "SC";
    private static final String HARASSMENT_ACTION_CODE = "PL";

    private CharacterActionConverter characterActionConverter;

    @BeforeEach
    public void setUp() {
        characterActionConverter = new CharacterActionConverter();
    }

    @Test
    public void givenRealityShowActionCode_whenFromActionCode_thenReturnParticipateToRealityShowActionType() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode(REALITY_SHOW_ACTION_CODE);

        assertEquals(CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW, typeReturned);
    }

    @Test
    public void givenPromoteMovieActionCode_whenFromActionCode_thenReturnPromoteMovieActionType() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode(PROMOTE_MOVIE_ACTION_CODE);

        assertEquals(CharacterInteractionActionType.PROMOTE_MOVIE, typeReturned);
    }

    @Test
    public void givenGossipActionCode_whenFromActionCode_thenReturnStartGossipActionType() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode(GOSSIP_ACTION_CODE);

        assertEquals(CharacterInteractionActionType.GOSSIP, typeReturned);
    }

    @Test
    public void givenRevealScandalActionCode_whenFromActionCode_thenReturnRevealScandalActionType() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode(REVEAL_SCANDAL_ACTION_CODE);

        assertEquals(CharacterInteractionActionType.REVEAL_SCANDAL, typeReturned);
    }

    @Test
    public void givenHarassmentActionCode_whenFromActionCode_thenReturnHarassmentActionType() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode(HARASSMENT_ACTION_CODE);

        assertEquals(CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT, typeReturned);
    }

    @Test
    public void givenInvalidActionCode_whenFromActionCode_thenReturnNull() {

        CharacterInteractionActionType typeReturned = characterActionConverter.fromActionCode("NOT_A_VALID_ACTION_CODE");

        assertNull(typeReturned);
    }

    @Test
    public void givenRealityShowActionType_whenToActionCode_thenReturnParticipateToRealityShowActionCode() {

        String returnedCode = characterActionConverter.toActionCode(CharacterInteractionActionType.PARTICIPATE_TO_REALITY_SHOW);

        assertEquals(REALITY_SHOW_ACTION_CODE, returnedCode);
    }

    @Test
    public void givenPromoteMovieActionType_whenToActionCode_thenReturnPromoteMovieActionCode() {

        String returnedCode = characterActionConverter.toActionCode(CharacterInteractionActionType.PROMOTE_MOVIE);

        assertEquals(PROMOTE_MOVIE_ACTION_CODE, returnedCode);
    }

    @Test
    public void givenGossipActionType_whenToActionCode_thenReturnGossipActionCode() {

        String returnedCode = characterActionConverter.toActionCode(CharacterInteractionActionType.GOSSIP);

        assertEquals(GOSSIP_ACTION_CODE, returnedCode);
    }

    @Test
    public void givenRevealScandalActionType_whenToActionCode_thenReturnRevealScandalActionCode() {

        String returnedCode = characterActionConverter.toActionCode(CharacterInteractionActionType.REVEAL_SCANDAL);

        assertEquals(REVEAL_SCANDAL_ACTION_CODE, returnedCode);
    }

    @Test
    public void givenHarassmentActionType_whenToActionCode_thenReturnHarassmentActionCode() {

        String returnedCode = characterActionConverter.toActionCode(CharacterInteractionActionType.COMPLAINT_FOR_HARASSMENT);

        assertEquals(HARASSMENT_ACTION_CODE, returnedCode);
    }
}
