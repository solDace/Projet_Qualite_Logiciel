package ca.ulaval.glo4002.game.domain.hamstagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HamstagramIDTest {
    private static final String USERNAME = "Star wars", ANOTHER_USERNAME = "Inception";

    private HamstagramID hamstagramID;

    @BeforeEach
    public void setup() {
        hamstagramID = new HamstagramID(USERNAME);
    }

    @Test
    public void givenTwoHamstagramIDCreatedWithSameString_whenComparing_thenReturnTrue() {
        HamstagramID anIdenticalHamstagramId = new HamstagramID(USERNAME);

        boolean comparison = hamstagramID.equals(anIdenticalHamstagramId);

        assertTrue(comparison);
    }

    @Test
    public void givenTwoHamstagramIDsCreatedWithDifferentString_whenEquals_thenReturnFalse() {
        HamstagramID aDifferentHamstagramId = new HamstagramID(ANOTHER_USERNAME);

        boolean comparison = hamstagramID.equals(aDifferentHamstagramId);

        assertFalse(comparison);
    }
}
