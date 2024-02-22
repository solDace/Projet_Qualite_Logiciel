package ca.ulaval.glo4002.game.domain.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReputationTest {
    private static final int FIRST_RUMOR_REPUTATION_LOST = 5;
    private static final int SECOND_RUMOR_REPUTATION_LOST = 10;
    private static final int THIRD_OR_MORE_RUMOR_REPUTATION_LOST = 15;
    private static final int REPUTATION_THRESHOLD_TO_REVEAL_SCANDAL = 60;
    private static final int REPUTATION_UNDER_THRESHOLD_TO_REVEAL_SCANDAL = REPUTATION_THRESHOLD_TO_REVEAL_SCANDAL - 1;
    private static final int REPUTATION_OVER_THRESHOLD_TO_REVEAL_SCANDAL = REPUTATION_THRESHOLD_TO_REVEAL_SCANDAL + 1;
    private static final int MOVIE_PROMOTION_REPUTATION_THRESHOLD = 60;
    private static final int REPUTATION_UNDER_MOVIE_PROMOTION_THRESHOLD = MOVIE_PROMOTION_REPUTATION_THRESHOLD - 1;
    private static final int REPUTATION_OVER_MOVIE_PROMOTION_THRESHOLD = MOVIE_PROMOTION_REPUTATION_THRESHOLD + 1;
    private static final int ELIMINATION_REPUTATION_THRESHOLD = 15;
    private static final int REPUTATION_UNDER_ELIMINATION_THRESHOLD = ELIMINATION_REPUTATION_THRESHOLD - 1;
    private static final int REPUTATION_OVER_ELIMINATION_THRESHOLD = ELIMINATION_REPUTATION_THRESHOLD + 1;
    private static final int REALITY_SHOW_REPUTATION_LOST = 10;
    private static final int SCANDAL_REPUTATION_LOST = 10;
    private static final int HARASSMENT_ACCUSATION_REPUTATION_LOST = 10;

    Reputation reputation;

    @BeforeEach
    public void setUp() {
        int initialReputationPoints = 5000;
        reputation = new Reputation(initialReputationPoints);
    }

    @Test
    public void whenLoosePoints_thenLostPointsAreSubtractedFromReputationPoints() {
        int initialReputationPoints = reputation.getPoints();

        int reputationLost = 300;
        reputation.losePoints(reputationLost);

        assertEquals(initialReputationPoints - reputationLost, reputation.getPoints());
    }

    @Test
    public void whenParticipateToRealityShow_thenLooseREALITY_SHOW_REPUTATION() {
        int initialReputationPoints = reputation.getPoints();

        reputation.participateToRealityShow();

        assertEquals(initialReputationPoints - REALITY_SHOW_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void whenReceiveScandal_thenLooseSCANDAL_REPUTATION() {
        int initialReputationPoints = reputation.getPoints();

        reputation.receiveScandal();

        assertEquals(initialReputationPoints - SCANDAL_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void whenReceiveHarassmentAccusation_thenLooseHARASSMENT_ACCUSATION_REPUTATION_LOST() {
        int initialReputationPoints = reputation.getPoints();

        reputation.receiveHarassmentAccusation();

        assertEquals(initialReputationPoints - HARASSMENT_ACCUSATION_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void givenNoRumorReceived_whenReceiveRumor_thenLooseReputationPointsCorrespondingToFirstRumor() {
        int initialReputationPoints = reputation.getPoints();

        reputation.receiveRumor();

        assertEquals(initialReputationPoints - FIRST_RUMOR_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void givenOneRumorReceived_whenReceiveRumor_thenLooseReputationPointsCorrespondingToSecondRumor() {
        reputation.receiveRumor();
        int initialReputationPoints = reputation.getPoints();

        reputation.receiveRumor();

        assertEquals(initialReputationPoints - SECOND_RUMOR_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void givenTwoRumorReceived_whenReceiveRumor_thenLooseReputationPointsCorrespondingToThirdOrMoreRumor() {
        reputation.receiveRumor();
        reputation.receiveRumor();
        int initialReputationPoints = reputation.getPoints();

        reputation.receiveRumor();

        assertEquals(initialReputationPoints - THIRD_OR_MORE_RUMOR_REPUTATION_LOST, reputation.getPoints());
    }

    @Test
    public void givenAReputationUnderTHRESHOLD_TO_REVEAL_SCANDAL_whenCanRevealScandal_thenItIsFalse() {
        reputation = new Reputation(REPUTATION_UNDER_THRESHOLD_TO_REVEAL_SCANDAL);

        assertFalse(reputation.canRevealScandal());
    }

    @Test
    public void givenAReputationOverTHRESHOLD_TO_REVEAL_SCANDAL_whenCanRevealScandal_thenItIsTrue() {
        reputation = new Reputation(REPUTATION_OVER_THRESHOLD_TO_REVEAL_SCANDAL);

        assertTrue(reputation.canRevealScandal());
    }

    @Test
    public void givenAReputationUnderMOVIE_PROMOTION_THRESHOLD_whenCanPromoteMovie_thenItIsFalse() {
        reputation = new Reputation(REPUTATION_UNDER_MOVIE_PROMOTION_THRESHOLD);

        assertFalse(reputation.canPromoteMovie());
    }

    @Test
    public void givenAReputationOverMOVIE_PROMOTION_THRESHOLD_whenCanPromoteMovie_thenItIsTrue() {
        reputation = new Reputation(REPUTATION_OVER_MOVIE_PROMOTION_THRESHOLD);

        assertTrue(reputation.canPromoteMovie());
    }

    @Test
    public void givenAReputationUnderELIMINATION_REPUTATION_THRESHOLD_whenIsUnderEliminationThreshold_thenItIsTrue() {
        reputation = new Reputation(REPUTATION_UNDER_ELIMINATION_THRESHOLD);

        assertTrue(reputation.isUnderEliminationThreshold());
    }

    @Test
    public void givenAReputationOverELIMINATION_REPUTATION_THRESHOLD_whenIsUnderEliminationThreshold_thenItIsFalse() {
        reputation = new Reputation(REPUTATION_OVER_ELIMINATION_THRESHOLD);

        assertFalse(reputation.isUnderEliminationThreshold());
    }
}
