package ca.ulaval.glo4002.game.domain.character;

public class Reputation {

    private static final int SCANDAL_REPUTATION_LOST = 10;
    private static final int HARASSMENT_ACCUSATION_REPUTATION_LOST = 10;
    private static final int REALITY_SHOW_REPUTATION_LOST = 10;
    private static final int MOVIE_PROMOTION_REPUTATION_THRESHOLD = 60;
    private static final int ELIMINATION_REPUTATION_THRESHOLD = 15;
    private static final int REVEAL_SCANDAL_REPUTATION_THRESHOLD = 60;
    private static final int FIRST_RUMOR_REPUTATION_LOST = 5;
    private static final int SECOND_RUMOR_REPUTATION_LOST = 10;
    private static final int THIRD_OR_MORE_RUMOR_REPUTATION_LOST = 15;

    private int rumorsReceived;
    private int reputationPoints;

    public Reputation(int points) {
        this.reputationPoints = points;
        this.rumorsReceived = 0;
    }

    public void losePoints(int points) {
        reputationPoints -= points;
    }

    public int getPoints() {
        return reputationPoints;
    }

    public void receiveRumor() {
        int reputationPointsToLoose;

        switch (rumorsReceived) {
            case 0 -> reputationPointsToLoose = FIRST_RUMOR_REPUTATION_LOST;
            case 1 -> reputationPointsToLoose = SECOND_RUMOR_REPUTATION_LOST;
            default -> reputationPointsToLoose = THIRD_OR_MORE_RUMOR_REPUTATION_LOST;
        }

        losePoints(reputationPointsToLoose);

        rumorsReceived += 1;
    }

    public void participateToRealityShow() {
        losePoints(REALITY_SHOW_REPUTATION_LOST);
    }

    public void receiveScandal() {
        losePoints(SCANDAL_REPUTATION_LOST);
    }

    public void receiveHarassmentAccusation() {
        losePoints(HARASSMENT_ACCUSATION_REPUTATION_LOST);
    }

    public boolean isUnderEliminationThreshold() {
        return reputationPoints < ELIMINATION_REPUTATION_THRESHOLD;
    }

    public boolean canRevealScandal() {
        return reputationPoints >= REVEAL_SCANDAL_REPUTATION_THRESHOLD;
    }

    public boolean canPromoteMovie() {
        return reputationPoints >= MOVIE_PROMOTION_REPUTATION_THRESHOLD;
    }
}
