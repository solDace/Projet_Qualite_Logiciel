package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class ResetGameService {

    private final CharacterRepository characterRepository;
    private final HamstagramRepository hamstagramRepository;
    private final RattedInRepository rattedInRepository;
    private final MovieRepository movieRepository;
    private final LawsuitRepository lawsuitRepository;

    @Inject
    public ResetGameService(CharacterRepository characterRepository, HamstagramRepository hamstagramRepository,
                            RattedInRepository rattedInRepository, MovieRepository movieRepository, LawsuitRepository lawsuitRepository) {
        this.characterRepository = characterRepository;
        this.hamstagramRepository = hamstagramRepository;
        this.rattedInRepository = rattedInRepository;
        this.movieRepository = movieRepository;
        this.lawsuitRepository = lawsuitRepository;
    }

    public void resetAllRepositories() {
        characterRepository.deleteAllCharacters();
        hamstagramRepository.deleteAllHamstagramAccounts();
        rattedInRepository.deleteAllRattedInAccounts();
        movieRepository.deleteAllMovies();
        lawsuitRepository.deleteAllLawsuits();
    }
}
