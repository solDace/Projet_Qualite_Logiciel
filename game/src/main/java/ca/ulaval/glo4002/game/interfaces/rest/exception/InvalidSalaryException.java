package ca.ulaval.glo4002.game.interfaces.rest.exception;

public class InvalidSalaryException extends InvalidParameterException {
    public InvalidSalaryException() {
        super("Invalid Salary");
    }

    @Override
    public String getError() {
        return "INVALID_SALARY";
    }

    @Override
    public String getDescription() {
        return "Salary must be > 0.";
    }
}
