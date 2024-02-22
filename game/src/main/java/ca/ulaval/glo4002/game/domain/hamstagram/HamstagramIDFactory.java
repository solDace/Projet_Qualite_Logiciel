package ca.ulaval.glo4002.game.domain.hamstagram;

public class HamstagramIDFactory {
    public HamstagramID create(String name) {
        return new HamstagramID(name);
    }
}
