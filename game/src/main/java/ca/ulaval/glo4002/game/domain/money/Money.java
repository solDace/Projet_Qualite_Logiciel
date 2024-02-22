package ca.ulaval.glo4002.game.domain.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money implements Comparable<Money> {

    private final BigDecimal amount;

    public Money(float amount) {
        this.amount = round(BigDecimal.valueOf(amount));
    }

    public Money add(Money amountToAdd) {
        BigDecimal money = amount.add(amountToAdd.amount);
        BigDecimal roundedMoney = round(money);
        return new Money(roundedMoney.floatValue());
    }

    public Money subtract(Money amountToSubtract) {
        BigDecimal money = amount.subtract(amountToSubtract.amount);
        return new Money(money.floatValue());
    }

    public Money multiply(double value) {
        BigDecimal multiplier = BigDecimal.valueOf(value);
        BigDecimal money = amount.multiply(multiplier);
        BigDecimal roundedMoney = round(money);
        return new Money(roundedMoney.floatValue());
    }

    private BigDecimal round(BigDecimal newAmount) {
        int decimals = 2;
        return newAmount.setScale(decimals, RoundingMode.HALF_UP);
    }

    public boolean lowerThanOrEqual(Money money) {
        return this.amount.compareTo(money.amount) <= 0;
    }

    public boolean lowerThan(Money money) {
        return this.amount.compareTo(money.amount) < 0;
    }

    public float floatValue() {
        return amount.floatValue();
    }

    public double doubleValue() {
        return amount.doubleValue();
    }

    public int intValue() {
        return amount.intValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other.getClass() == Money.class && amount.equals(((Money) other).amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public int compareTo(Money other) {
        return Double.compare(amount.doubleValue(), other.doubleValue());
    }
}
