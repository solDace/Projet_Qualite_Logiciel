package ca.ulaval.glo4002.game.domain.rattedin;

public class RattedInID {
    private final String username;

    public RattedInID(String username) {
        this.username = username;
    }

    public String asString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RattedInID otherRattedInID)) {
            return false;
        }

        return otherRattedInID.username.equals(username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
