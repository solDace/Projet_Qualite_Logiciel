package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.rattedin.RattedInAccount;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LawyerSelector {
    public Optional<Lawyer> findBestLawyer(Set<Lawyer> allLawyers, RattedInAccount rattedInAccount) {
        if (rattedInAccount == null) {
            return Optional.empty();
        }

        List<Lawyer> candidatesLawyer = removeLawyerNotInRattedInAccountContacts(allLawyers, rattedInAccount);
        candidatesLawyer = removeLawyerNotOpenToWork(candidatesLawyer);

        if (candidatesLawyer.isEmpty()) {
            return Optional.empty();
        }

        candidatesLawyer.sort(Comparator.comparing(Lawyer::getReputationPoints, Collections.reverseOrder())
                                        .thenComparing(Lawyer::getName));

        return Optional.of(candidatesLawyer.get(0));
    }

    private List<Lawyer> removeLawyerNotInRattedInAccountContacts(Set<Lawyer> allLawyers, RattedInAccount rattedInAccount) {
        return allLawyers.stream().filter(lawyer -> lawyer.isInContacts(rattedInAccount)).collect(Collectors.toList());
    }

    private List<Lawyer> removeLawyerNotOpenToWork(List<Lawyer> allLawyers) {
        return allLawyers.stream().filter(Lawyer::isAvailable).collect(Collectors.toList());
    }
}
