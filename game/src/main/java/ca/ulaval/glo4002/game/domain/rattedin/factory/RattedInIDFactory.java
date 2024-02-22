package ca.ulaval.glo4002.game.domain.rattedin.factory;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInID;

public class RattedInIDFactory {
    public RattedInID create(String name) {
        return new RattedInID(name);
    }
}
