package ca.ulaval.glo4002.game.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UnknownErrorResponse {
    public final String message;

    @JsonCreator
    public UnknownErrorResponse(String message) {
        this.message = message;
    }
}
