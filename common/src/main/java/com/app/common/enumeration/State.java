package com.app.common.enumeration;

import com.app.common.exception.EnumNotFoundException;

public enum State {
    OPEN("Open"), PENDING("Pending"), WAITING_FOR_PACKAGING("Waiting for packaging"),
    ON_THE_WAY("On the way"), READY("Ready"), CANCELLED("Cancelled"), REJECTED("Rejected");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public static State fromString(String text) {
        for (State state : State.values()) {
            if (state.value.equalsIgnoreCase(text)) {
                return state;
            }
        }
        throw new EnumNotFoundException(State.class, text);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "State{" +
            "value='" + value + '\'' +
            '}';
    }
}
