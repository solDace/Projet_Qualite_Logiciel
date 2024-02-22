package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.CharacterService;
import ca.ulaval.glo4002.game.domain.action.AddCharacterGameAction;
import ca.ulaval.glo4002.game.domain.action.AllCharactersNextTurn;
import ca.ulaval.glo4002.game.domain.action.AllCheckAndEliminateGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseMoneyGameAction;
import ca.ulaval.glo4002.game.domain.action.AllLoseReputationPointsGameAction;
import ca.ulaval.glo4002.game.domain.character.CharacterType;
import ca.ulaval.glo4002.game.domain.money.Money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CharacterActionListenerTest {

    private static final Money ANY_MONEY_AMOUNT = new Money(5566.0f);
    private static final String A_CHARACTER_NAME = "Bob";
    private static final Money A_SALARY = new Money(1000.0f);
    private static final int REPUTATION_POINTS = 1000;

    @Mock
    private CharacterService characterService;
    private CharacterActionListener characterActionListener;

    @BeforeEach
    public void setUp() {
        characterActionListener = new CharacterActionListener(characterService);
    }

    @Test
    public void givenAddCharacterAction_whenOnGameAction_thenCharacterServiceAddsTheCharacter() {
        AddCharacterGameAction addCharacterGameAction = new AddCharacterGameAction(A_CHARACTER_NAME, A_SALARY, CharacterType.AGENT);

        characterActionListener.onGameAction(addCharacterGameAction);

        Mockito.verify(characterService).addCharacter(A_CHARACTER_NAME, A_SALARY, CharacterType.AGENT);
    }

    @Test
    public void givenAllLoseReputationPointsAction_whenOnGameAction_thenCharacterServiceRemovesAllReputationPoints() {
        AllLoseReputationPointsGameAction allLoseReputationPointsGameAction = new AllLoseReputationPointsGameAction(REPUTATION_POINTS);

        characterActionListener.onGameAction(allLoseReputationPointsGameAction);

        Mockito.verify(characterService).allLoseReputationPoints(REPUTATION_POINTS);
    }

    @Test
    public void givenAllLoseMoneyAction_whenOnGameActionAction_thenCharacterServiceRemovesAllMoney() {
        AllLoseMoneyGameAction allLoseMoneyGameAction = new AllLoseMoneyGameAction(ANY_MONEY_AMOUNT);

        characterActionListener.onGameAction(allLoseMoneyGameAction);

        Mockito.verify(characterService).allLoseMoney(ANY_MONEY_AMOUNT);
    }

    @Test
    public void givenAnyOtherGameAction_whenOnGameAction_thenDoNothing() {
        AllCheckAndEliminateGameAction notSupportedGameAction = new AllCheckAndEliminateGameAction();

        characterActionListener.onGameAction(notSupportedGameAction);

        Mockito.verifyNoInteractions(characterService);
    }

    @Test
    public void givenAllCharactersNextTurnAction_whenOnGameAction_thenCharacterServiceAllNextTurn() {
        AllCharactersNextTurn allIncrementTurnForUnavailabilityGameAction = new AllCharactersNextTurn();

        characterActionListener.onGameAction(allIncrementTurnForUnavailabilityGameAction);

        Mockito.verify(characterService).allNextTurn();
    }
}
