package ca.ulaval.glo4002.game.domain.character;

public class WorkAvailability {

    private static final int REALITY_SHOW_UNAVAILABLE_TURNS = 2;
    private static final int SCANDAL_UNAVAILABLE_TURNS = 2;
    private static final int HARASSMENT_ACCUSATION_UNAVAILABLE_TURNS = 3;

    private int turnsBeforeWorkIsAllowed;
    private boolean currentlyWorking;

    public WorkAvailability() {
        turnsBeforeWorkIsAllowed = 0;
        currentlyWorking = false;
    }

    public boolean isOpenToWork() {
        return turnsBeforeWorkIsAllowed == 0 && !currentlyWorking;
    }

    public void nextTurn() {
        turnsBeforeWorkIsAllowed = Math.max(turnsBeforeWorkIsAllowed - 1, 0);
    }

    public void setCurrentlyWorking(boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public void participateToRealityShow() {
        cannotWorkForNumberOfTurn(REALITY_SHOW_UNAVAILABLE_TURNS);
    }

    public void receiveScandal() {
        cannotWorkForNumberOfTurn(SCANDAL_UNAVAILABLE_TURNS);
    }

    public void receiveHarassmentAccusation() {
        cannotWorkForNumberOfTurn(HARASSMENT_ACCUSATION_UNAVAILABLE_TURNS);
    }

    private void cannotWorkForNumberOfTurn(int turn) {
        turnsBeforeWorkIsAllowed = turn;
    }
}
