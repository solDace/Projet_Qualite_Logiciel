package ca.ulaval.glo4002.game.domain.money;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MoneyTest {

    private static final float AMOUNT = 5.15f;
    private static final float OTHER_AMOUNT = 10.53f;
    private static final float AMOUNT_PLUS_OTHER_AMOUNT = roundToTwoDecimals(AMOUNT + OTHER_AMOUNT);
    private static final float AMOUNT_MINUS_OTHER_AMOUNT = roundToTwoDecimals(AMOUNT - OTHER_AMOUNT);
    private static final float LOW_AMOUNT = 1.11f;
    private static final float HIGH_AMOUNT = 10.25f;
    private static final double MULTIPLIER = 2.15;
    private static final float AMOUNT_MULTIPLIED_BY_MULTIPLIER = roundToTwoDecimals(AMOUNT * MULTIPLIER);
    private Money money;
    private Money sameMoney;
    private Money otherMoney;
    private Money lowMoney;
    private Money highMoney;

    private static float roundToTwoDecimals(double value) {
        return (float) Math.round(value * 100.0f) / 100.0f;
    }

    @Test
    public void givenAValueWithMoreThanTwoDecimals_whenCreatingMoneyWithThisValue_thenMoneyIsRoundedWithTwoDecimals() {
        float valueWithThreeDigits = 2.136f;
        money = new Money(valueWithThreeDigits);

        float valueRounded = roundToTwoDecimals(valueWithThreeDigits);
        assertEquals(valueRounded, money.floatValue());
    }

    @Test
    public void givenAMoneyAndOtherMoney_whenAddOtherMoneyToTheMoney_thenOtherMoneyIsAddedToTheMoney() {
        money = new Money(AMOUNT);
        otherMoney = new Money(OTHER_AMOUNT);

        money = money.add(otherMoney);

        assertEquals(AMOUNT_PLUS_OTHER_AMOUNT, money.floatValue());
    }

    @Test
    public void givenAMoneyAndOtherMoney_whenSubtractOtherMoneyToTheMoney_thenOtherMoneyIsSubtractedFromTheMoney() {
        money = new Money(AMOUNT);
        otherMoney = new Money(OTHER_AMOUNT);

        money = money.subtract(otherMoney);

        assertEquals(AMOUNT_MINUS_OTHER_AMOUNT, money.floatValue());
    }

    @Test
    public void whenMultiplyAValueToTheMoney_thenMoneyIsMultipliedByTheValue() {
        money = new Money(AMOUNT);

        money = money.multiply(MULTIPLIER);

        assertEquals(AMOUNT_MULTIPLIED_BY_MULTIPLIER, money.floatValue());
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenLowerThanOrEqual_thenLowMoneyIsLowerThanOrEqualThanHighMoney() {
        givenALowMoneyAndAHighMoney();

        boolean isLowerThanOrEqual = lowMoney.lowerThanOrEqual(highMoney);

        assertTrue(isLowerThanOrEqual);
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenLowerThanOrEqual_thenHighMoneyIsNotLowerThanOrEqualThanLowMoney() {
        givenALowMoneyAndAHighMoney();

        boolean isLowerThanOrEqual = highMoney.lowerThanOrEqual(lowMoney);

        assertFalse(isLowerThanOrEqual);
    }

    @Test
    public void whenLowerThanOrEqualOfTheSameMoney_thenAmountIsLowerThanOrEqualThanSameMoney() {
        givenTwoMoneyWithSameAmount();

        boolean isLowerThanOrEqual = money.lowerThanOrEqual(sameMoney);

        assertTrue(isLowerThanOrEqual);
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenLowerThan_thenLowMoneyIsLowerThanHighMoney() {
        givenALowMoneyAndAHighMoney();

        boolean isLower = lowMoney.lowerThan(highMoney);

        assertTrue(isLower);
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenLowerThan_thenHighMoneyIsNotLowerThanLowMoney() {
        givenALowMoneyAndAHighMoney();

        boolean isLower = highMoney.lowerThan(lowMoney);

        assertFalse(isLower);
    }

    @Test
    public void givenTwoDifferentMoney_whenEquals_thenTheMoneyAreNotEquals() {
        givenTwoMoneyWithDifferentAmount();

        boolean isEqual = money.equals(otherMoney);

        assertFalse(isEqual);
    }

    @Test
    public void givenTwoMoneyWithSameAmount_whenEquals_thenTheMoneyAreEquals() {
        givenTwoMoneyWithSameAmount();

        boolean isEqual = money.equals(sameMoney);

        assertTrue(isEqual);
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenComparingLowMoneyToHighMoney_thenTheResultIsNegative() {
        givenALowMoneyAndAHighMoney();

        int comparison = lowMoney.compareTo(highMoney);

        assertTrue(comparison < 0);
    }

    @Test
    public void givenALowMoneyAndAHighMoney_whenComparingHighMoneyToLowMoney_thenTheResultIsPositive() {
        givenALowMoneyAndAHighMoney();

        int comparison = highMoney.compareTo(lowMoney);

        assertTrue(comparison > 0);
    }

    @Test
    public void givenTwoMoneyWithSameAmount_whenCompareTo_thenTheResultIsZero() {
        givenTwoMoneyWithSameAmount();

        int comparison = money.compareTo(sameMoney);

        assertEquals(0, comparison);
    }

    private void givenTwoMoneyWithDifferentAmount() {
        money = new Money(AMOUNT);
        otherMoney = new Money(OTHER_AMOUNT);
    }

    private void givenTwoMoneyWithSameAmount() {
        money = new Money(AMOUNT);
        sameMoney = new Money(AMOUNT);
    }

    private void givenALowMoneyAndAHighMoney() {
        lowMoney = new Money(LOW_AMOUNT);
        highMoney = new Money(HIGH_AMOUNT);
    }
}
