package ca.ulaval.glo4002.game.domain.hamstagram;

public class HamstagramID {
    private final String username;

    public HamstagramID(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HamstagramID otherHamstagramID)) {
            return false;
        }

        return otherHamstagramID.username.equals(username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
