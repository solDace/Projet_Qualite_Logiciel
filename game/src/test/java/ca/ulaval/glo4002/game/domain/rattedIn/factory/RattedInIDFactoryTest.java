package ca.ulaval.glo4002.game.domain.rattedIn.factory;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInIDFactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RattedInIDFactoryTest {
    private static final String USERNAME = "Pampino";
    private static final RattedInID RATTED_IN_ID = new RattedInID(USERNAME);

    @Test
    public void whenCreateRattedInID_thenRattedInIDIsCreated() {
        RattedInIDFactory rattedInIDFactory = new RattedInIDFactory();

        RattedInID rattedInID = rattedInIDFactory.create(USERNAME);

        assertEquals(RATTED_IN_ID, rattedInID);
    }
}
