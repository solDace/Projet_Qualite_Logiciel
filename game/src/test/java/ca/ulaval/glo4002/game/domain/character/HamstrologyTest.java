package ca.ulaval.glo4002.game.domain.character;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HamstrologyTest {

    private static final int LOW_REPUTATION_POINTS = 1000, MEDIUM_REPUTATION_POINTS = 2000, HIGH_REPUTATION_POINTS = 3000;
    private static final String
        A_NAME = "Jack", BOB_NAME = "Bob", ROB_NAME = "Rob", JOHN_NAME = "John", SUZANNE_NAME = "Suzanne", BOBBY_NAME = "Bobby", BILL_NAME = "Bill";

    @Mock
    private Agent firstAgent, secondAgent, thirdAgent;
    private Hamstrology hamstrology;

    @BeforeEach
    public void setup() {
        hamstrology = new Hamstrology();
    }

    @Test
    public void givenNoPropositions_whenChooseAnAgent_thenNoAgentIsChosen() {

        Optional<Agent> chosenAgent = hamstrology.chooseAnAgent(A_NAME, Collections.emptySet());

        assertTrue(chosenAgent.isEmpty());
    }

    @Test
    public void givenOnlyOneProposition_whenChooseAnAgent_thenTheAgentIsReturned() {

        Optional<Agent> chosenAgent = hamstrology.chooseAnAgent(A_NAME, Set.of(firstAgent));

        assertEquals(chosenAgent, Optional.of(firstAgent));
    }

    @Test
    public void givenMultiplePropositionsWithAgentsWithDifferentFirstLetterNames_whenChooseAnAgent_thenTheAgentWithTheCloserFirstLetterToTheActorIsChosen() {
        Mockito.when(firstAgent.getName()).thenReturn(JOHN_NAME);
        Mockito.when(secondAgent.getName()).thenReturn(ROB_NAME);
        Mockito.when(thirdAgent.getName()).thenReturn(BOBBY_NAME);

        Optional<Agent> chosenAgent = hamstrology.chooseAnAgent(SUZANNE_NAME, Set.of(firstAgent, secondAgent, thirdAgent));

        assertEquals(chosenAgent, Optional.of(secondAgent));
    }

    @Test
    public void givenMultiplePropositionWithAgentWithSameFirstLettersInTheirName_whenChooseAnAgent_thenTheAgentWithHigherReputationIsChosen() {
        Mockito.when(firstAgent.getName()).thenReturn(BOB_NAME);
        Mockito.when(secondAgent.getName()).thenReturn(BOBBY_NAME);
        Mockito.when(thirdAgent.getName()).thenReturn(BILL_NAME);
        Mockito.when(firstAgent.getReputationPoints()).thenReturn(LOW_REPUTATION_POINTS);
        Mockito.when(secondAgent.getReputationPoints()).thenReturn(MEDIUM_REPUTATION_POINTS);
        Mockito.when(thirdAgent.getReputationPoints()).thenReturn(HIGH_REPUTATION_POINTS);

        Optional<Agent> chosenAgent = hamstrology.chooseAnAgent(SUZANNE_NAME, Set.of(firstAgent, secondAgent, thirdAgent));

        assertEquals(chosenAgent, Optional.of(thirdAgent));
    }
}
