package ca.ulaval.glo4002.game.domain.character;

public interface CharacterOnRattedIn {
    boolean isContactAcceptable(Character contact);

    void removeItselfFromItsContacts();

    String getName();
}
