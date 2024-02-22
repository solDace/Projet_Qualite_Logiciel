package ca.ulaval.glo4002.game.domain.lawsuit;

import java.util.List;

public interface LawsuitRepository {

    List<Lawsuit> getAllLawsuits();

    void saveLawsuit(Lawsuit lawsuit);

    void deleteAllLawsuits();

    void deleteLawsuit(Lawsuit lawsuit);

    void deleteAllCharacterLawsuits(String characterName);
}
