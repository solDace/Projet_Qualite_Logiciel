package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResetGameServiceTest {

    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private HamstagramRepository hamstagramRepository;
    @Mock
    private RattedInRepository rattedInRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private LawsuitRepository lawsuitRepository;

    private ResetGameService resetGameService;

    @BeforeEach
    public void setUp() {
        resetGameService = new ResetGameService(characterRepository, hamstagramRepository, rattedInRepository, movieRepository, lawsuitRepository);
    }

    @Test
    public void whenResetAllRepositories_thenCharacterRepositoryDeletesAllCharacters() {

        resetGameService.resetAllRepositories();

        Mockito.verify(characterRepository).deleteAllCharacters();
    }

    @Test
    public void whenResetAllRepositories_thenHamstagramRepositoryDeletesAllHamstagramAccounts() {

        resetGameService.resetAllRepositories();

        Mockito.verify(hamstagramRepository).deleteAllHamstagramAccounts();
    }

    @Test
    public void whenResetAllRepositories_thenRattedInRepositoryDeletesAllRattedInAccounts() {

        resetGameService.resetAllRepositories();

        Mockito.verify(rattedInRepository).deleteAllRattedInAccounts();
    }

    @Test
    public void whenResetAllRepositories_thenMovieRepositoryDeletesAllMovies() {

        resetGameService.resetAllRepositories();

        Mockito.verify(movieRepository).deleteAllMovies();
    }

    @Test
    public void whenResetAllRepositories_thenLawsuitRepositoryDeletesAllMovies() {

        resetGameService.resetAllRepositories();

        Mockito.verify(lawsuitRepository).deleteAllLawsuits();
    }
}
