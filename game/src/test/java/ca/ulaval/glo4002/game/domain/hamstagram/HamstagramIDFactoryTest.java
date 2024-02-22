package ca.ulaval.glo4002.game.domain.hamstagram;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HamstagramIDFactoryTest {
    private static final String USERNAME = "username";
    private static final HamstagramID HAMSTAGRAM_ID = new HamstagramID(USERNAME);

    @Test
    public void whenCreateHamstagramID_thenHamstagramIDIsCreated() {
        HamstagramIDFactory hamstagramIDFactory = new HamstagramIDFactory();

        HamstagramID hamstagramID = hamstagramIDFactory.create(USERNAME);

        assertEquals(HAMSTAGRAM_ID, hamstagramID);
    }
}
