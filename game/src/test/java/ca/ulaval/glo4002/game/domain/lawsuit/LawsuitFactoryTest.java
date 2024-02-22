package ca.ulaval.glo4002.game.domain.lawsuit;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LawsuitFactoryTest {

    private static final int TURN_NUMBER = 2;
    private static final String CHARACTER_NAME = "Bob";
    private static final CharacterInteractionActionType ACTION_TYPE = CharacterInteractionActionType.GOSSIP;

    private LawsuitFactory lawsuitFactory;

    @BeforeEach
    public void setUp() {
        lawsuitFactory = new LawsuitFactory();
    }

    @Test
    public void whenCreatingALawsuit_thenLawsuitIsCreated() {

        Lawsuit lawsuit = lawsuitFactory.createLawsuit(TURN_NUMBER, CHARACTER_NAME, ACTION_TYPE);

        assertEquals(Lawsuit.class, lawsuit.getClass());
        assertEquals(ACTION_TYPE, lawsuit.getActionType());
        assertEquals(CHARACTER_NAME, lawsuit.getCharacterName());
        assertEquals(TURN_NUMBER, lawsuit.getTurnNumber());
        assertNull(lawsuit.getLawyerName());
    }
}
