package ca.ulaval.glo4002.game.infrastructure.persistence.memory;

import ca.ulaval.glo4002.game.domain.lawsuit.Lawsuit;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;

import java.util.ArrayList;
import java.util.List;

public class LawsuitRepositoryInMemory implements LawsuitRepository {

    private static final List<Lawsuit> LAWSUITS = new ArrayList<>();

    @Override
    public List<Lawsuit> getAllLawsuits() {
        return LAWSUITS;
    }

    @Override
    public void saveLawsuit(Lawsuit lawsuit) {
        LAWSUITS.add(lawsuit);
    }

    @Override
    public void deleteAllLawsuits() {
        LAWSUITS.clear();
    }

    @Override
    public void deleteLawsuit(Lawsuit lawsuit) {
        LAWSUITS.remove(lawsuit);
    }

    @Override
    public void deleteAllCharacterLawsuits(String characterName) {
        LAWSUITS.removeIf(lawsuit -> lawsuit.getCharacterName().equals(characterName));
    }
}
