package ca.ulaval.glo4002.game.domain.rattedin;

public enum RattedInAccountStatus {
    OPEN_TO_WORK("openToWork"),
    BUSY("busy"),
    N_A("N/A");

    private final String typeName;

    RattedInAccountStatus(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
