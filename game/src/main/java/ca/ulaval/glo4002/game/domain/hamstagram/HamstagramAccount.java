package ca.ulaval.glo4002.game.domain.hamstagram;

import ca.ulaval.glo4002.game.domain.character.CharacterID;

public class HamstagramAccount {

    private static final int INITIAL_NUMBER_OF_FOLLOWERS = 10000;
    private static final int FOLLOWERS_ELIMINATION_THRESHOLD = 1000;
    private static final int MOVIE_PROMOTION_FOLLOWER_THRESHOLD = 9000;
    private static final double FOLLOWER_PERCENTAGE_LOST_ON_SCANDAL = 0.4;
    private static final double FOLLOWER_PERCENTAGE_LOST_ON_HARASSMENT_ACCUSATION = 0.7;

    private final String username;
    private final HamstagramID hamstagramID;
    private final CharacterID characterID;
    private int nbFollowers;

    public HamstagramAccount(HamstagramID hamstagramID, CharacterID characterID, String username) {
        this.hamstagramID = hamstagramID;
        this.characterID = characterID;
        this.username = username;
        this.nbFollowers = INITIAL_NUMBER_OF_FOLLOWERS;
    }

    public HamstagramID getHamstagramID() {
        return hamstagramID;
    }

    public String getUsername() {
        return username;
    }

    public int getNbFollowers() {
        return nbFollowers;
    }

    public void loseFollowers(int qty) {
        nbFollowers = Math.max(nbFollowers - qty, 0);
    }

    public boolean isUnderEliminationThreshold() {
        return nbFollowers < FOLLOWERS_ELIMINATION_THRESHOLD;
    }

    public void addFollowers(int qty) {
        nbFollowers += qty;
    }

    public CharacterID getCharacterID() {
        return characterID;
    }

    public boolean canPromoteMovie() {
        return nbFollowers >= MOVIE_PROMOTION_FOLLOWER_THRESHOLD;
    }

    public void receiveScandal() {
        losePercentageOfFollowers(FOLLOWER_PERCENTAGE_LOST_ON_SCANDAL);
    }

    public void receiveHarassmentAccusation() {
        losePercentageOfFollowers(FOLLOWER_PERCENTAGE_LOST_ON_HARASSMENT_ACCUSATION);
    }

    private void losePercentageOfFollowers(double percentage) {
        nbFollowers = (int) (nbFollowers * (1.0 - percentage));
    }
}
