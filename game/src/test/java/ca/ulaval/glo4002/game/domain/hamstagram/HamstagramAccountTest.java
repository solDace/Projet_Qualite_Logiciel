package ca.ulaval.glo4002.game.domain.hamstagram;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HamstagramAccountTest {
    private static final int INITIAL_NUMBER_OF_FOLLOWERS = 10000, FOLLOWERS_ELIMINATION_THRESHOLD = 1000;
    private static final String A_USER_NAME = "BOB123";
    private static final CharacterID A_USER_ID = new CharacterID(A_USER_NAME);
    private static final HamstagramID A_HAMSTAGRAM_ID = new HamstagramID(A_USER_NAME);
    private static final double FOLLOWER_PERCENTAGE_LOST_ON_SCANDAL = 0.4;
    private static final double FOLLOWER_PERCENTAGE_LOST_ON_HARASSMENT_ACCUSATION = 0.7;

    private HamstagramAccount hamstagramAccount;

    @BeforeEach
    public void setUp() {
        hamstagramAccount = new HamstagramAccount(A_HAMSTAGRAM_ID, A_USER_ID, A_USER_NAME);
    }

    @Test
    public void givenHamstagramAccountWithFollowers_whenAccountLosesFollowers_thenThatManyFollowersAreSubtractedFromTheAccount() {
        int followersLost = 397;

        hamstagramAccount.loseFollowers(followersLost);

        int expectedFollowerCount = INITIAL_NUMBER_OF_FOLLOWERS - followersLost;
        assertEquals(expectedFollowerCount, hamstagramAccount.getNbFollowers());
    }

    @Test
    public void givenHamstagramAccountWithFollowers_whenAccountLosesMoreFollowersThanCurrentAmount_thenFollowerCountIsZero() {
        int followersLost = INITIAL_NUMBER_OF_FOLLOWERS + 1;

        hamstagramAccount.loseFollowers(followersLost);

        assertEquals(0, hamstagramAccount.getNbFollowers());
    }

    @Test
    public void givenHamstagramAccountUnderEliminationThreshold_whenCheckUnderEliminationThreshold_thenItIsTrue() {
        int followersLostToBringItUnderEliminationThreshold = INITIAL_NUMBER_OF_FOLLOWERS - FOLLOWERS_ELIMINATION_THRESHOLD + 1;
        hamstagramAccount.loseFollowers(followersLostToBringItUnderEliminationThreshold);

        assertTrue(hamstagramAccount.isUnderEliminationThreshold());
    }

    @Test
    public void givenHamstagramAccountOnEliminationThreshold_whenCheckUnderEliminationThreshold_thenItIsFalse() {
        int followerLostToBringItOnEliminationThreshold = INITIAL_NUMBER_OF_FOLLOWERS - FOLLOWERS_ELIMINATION_THRESHOLD;
        hamstagramAccount.loseFollowers(followerLostToBringItOnEliminationThreshold);

        assertFalse(hamstagramAccount.isUnderEliminationThreshold());
    }

    @Test
    public void givenHamstagramAccountWithFollowers_whenAccountAddFollowers_thenThatManyFollowersAreAddedToTheAccount() {
        int followerAdded = 397;

        hamstagramAccount.addFollowers(followerAdded);

        int expectedFollowerCount = INITIAL_NUMBER_OF_FOLLOWERS + followerAdded;
        assertEquals(expectedFollowerCount, hamstagramAccount.getNbFollowers());
    }

    @Test
    public void givenAnHamstagramAccountWithFollowers_whenReceiveScandal_thenLose40PercentsOfHisFollowers() {
        hamstagramAccount.receiveScandal();

        int expectedFollowerCount = (int) (INITIAL_NUMBER_OF_FOLLOWERS * (1.0 - FOLLOWER_PERCENTAGE_LOST_ON_SCANDAL));
        assertEquals(expectedFollowerCount, hamstagramAccount.getNbFollowers());
    }

    @Test
    public void givenAnHamstagramAccountWithFollowers_whenReceiveHarassmentAccusation_thenLose70PercentsOfHisFollowers() {
        hamstagramAccount.receiveHarassmentAccusation();

        int expectedFollowerCount = (int) (INITIAL_NUMBER_OF_FOLLOWERS * (1.0 - FOLLOWER_PERCENTAGE_LOST_ON_HARASSMENT_ACCUSATION));
        assertEquals(expectedFollowerCount, hamstagramAccount.getNbFollowers());
    }
}
