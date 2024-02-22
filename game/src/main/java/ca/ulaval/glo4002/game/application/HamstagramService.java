package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountNotFoundException;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramID;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramIDFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;

import java.util.Set;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class HamstagramService {
    private final HamstagramRepository hamstagramRepository;
    private final HamstagramIDFactory hamstagramIDFactory;

    @Inject
    public HamstagramService(HamstagramRepository hamstagramRepository, HamstagramIDFactory hamstagramIDFactory) {
        this.hamstagramRepository = hamstagramRepository;
        this.hamstagramIDFactory = hamstagramIDFactory;
    }

    public HamstagramAccount getHamstagramAccount(String username) {
        HamstagramID hamstagramID = hamstagramIDFactory.create(username);
        return hamstagramRepository.getHamstagramAccount(hamstagramID).orElseThrow(HamstagramAccountNotFoundException::new);
    }

    public void allAccountsLoseFollowers(int nbFollowers) {
        Set<HamstagramAccount> allAccounts = hamstagramRepository.getAllHamstagramAccounts();

        allAccounts.forEach(hamstagramAccount -> hamstagramAccount.loseFollowers(nbFollowers));

        allAccounts.forEach(hamstagramRepository::saveHamstagramAccount);
    }
}
