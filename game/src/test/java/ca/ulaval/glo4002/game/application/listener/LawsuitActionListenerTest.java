package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.LawsuitService;
import ca.ulaval.glo4002.game.domain.action.AllHireLawyer;
import ca.ulaval.glo4002.game.domain.action.AllSettleLawsuits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LawsuitActionListenerTest {

    @Mock
    private LawsuitService lawsuitService;
    private LawsuitActionListener lawsuitActionListener;

    @BeforeEach
    public void setUp() {
        lawsuitActionListener = new LawsuitActionListener(lawsuitService);
    }

    @Test
    public void givenAllHireLawyerAction_whenOnGameAction_thenLawsuitServiceServiceHireLawyers() {
        AllHireLawyer allHireLawyer = new AllHireLawyer();

        lawsuitActionListener.onGameAction(allHireLawyer);

        Mockito.verify(lawsuitService).hireLawyers();
    }

    @Test
    public void givenAllSettleLawsuitsAction_whenOnGameAction_thenLawsuitServiceServiceSettleLawsuits() {
        AllSettleLawsuits allSettleLawsuits = new AllSettleLawsuits();

        lawsuitActionListener.onGameAction(allSettleLawsuits);

        Mockito.verify(lawsuitService).settleLawsuits();
    }
}
