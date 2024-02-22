package ca.ulaval.glo4002.game.domain.rattedIn;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RattedInIDTest {

    private static final String USERNAME = "Bob", ANOTHER_USERNAME = "Alice";

    private RattedInID rattedInID;

    @BeforeEach
    public void setUp() {
        rattedInID = new RattedInID(USERNAME);
    }

    @Test
    public void givenARattedInIdCreatedWithAString_whenAsString_thenTheStringIsReturned() {

        String name = rattedInID.asString();

        assertEquals(USERNAME, name);
    }

    @Test
    public void givenTwoRattedInIDsCreatedWithSameString_whenComparing_thenReturnTrue() {
        RattedInID identicalRattedInID = new RattedInID(USERNAME);

        boolean comparison = rattedInID.equals(identicalRattedInID);

        assertTrue(comparison);
    }

    @Test
    public void givenTwoRattedInIDsCreatedWithDifferentString_whenComparing_thenReturnFalse() {
        RattedInID differentRattedInID = new RattedInID(ANOTHER_USERNAME);

        boolean comparison = rattedInID.equals(differentRattedInID);

        assertFalse(comparison);
    }
}
