package ca.ulaval.glo4002.game.interfaces.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ConstraintValidationResponse {
    public final String message;
    public final List<String> validations;

    @JsonCreator
    public ConstraintValidationResponse(String message, List<String> validations) {
        this.message = message;
        this.validations = validations;
    }
}
