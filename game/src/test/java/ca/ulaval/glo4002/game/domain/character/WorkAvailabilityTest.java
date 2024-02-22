package ca.ulaval.glo4002.game.domain.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class WorkAvailabilityTest {

    private WorkAvailability workAvailability;

    @BeforeEach
    public void setUp() {
        workAvailability = new WorkAvailability();
    }

    @Test
    public void whenSetNotCurrentlyWorking_thenIsOpenToWork() {

        workAvailability.setCurrentlyWorking(false);

        assertTrue(workAvailability.isOpenToWork());
    }

    @Test
    public void whenSetCurrentlyWorking_thenIsNotOpenToWork() {

        workAvailability.setCurrentlyWorking(true);

        assertFalse(workAvailability.isOpenToWork());
    }

    @Test
    public void whenParticipateToRealityShow_thenIsNotOpenToWork() {

        workAvailability.participateToRealityShow();

        assertFalse(workAvailability.isOpenToWork());
    }

    @Test
    public void givenParticipatingToRealityShow_whenTwoTurnsPass_thenIsOpenToWork() {
        workAvailability.participateToRealityShow();

        workAvailability.nextTurn();
        workAvailability.nextTurn();

        assertTrue(workAvailability.isOpenToWork());
    }

    @Test
    public void whenReceiveScandal_thenIsNotOpenToWork() {

        workAvailability.receiveScandal();

        assertFalse(workAvailability.isOpenToWork());
    }

    @Test
    public void givenReceivedScandal_whenTwoTurnsPass_thenIsOpenToWork() {
        workAvailability.receiveScandal();

        workAvailability.nextTurn();
        workAvailability.nextTurn();

        assertTrue(workAvailability.isOpenToWork());
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenIsNotOpenToWork() {

        workAvailability.receiveHarassmentAccusation();

        assertFalse(workAvailability.isOpenToWork());
    }

    @Test
    public void givenReceivedHarassmentAccusation_whenThreeTurnsPass_thenIsOpenToWork() {
        workAvailability.receiveHarassmentAccusation();

        workAvailability.nextTurn();
        workAvailability.nextTurn();
        workAvailability.nextTurn();

        assertTrue(workAvailability.isOpenToWork());
    }
}
