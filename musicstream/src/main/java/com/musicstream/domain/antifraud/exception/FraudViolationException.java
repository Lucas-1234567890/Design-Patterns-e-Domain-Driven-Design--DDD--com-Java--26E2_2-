package com.musicstream.domain.antifraud.exception;

import com.musicstream.domain.antifraud.model.FraudViolation;

import java.util.List;

/**
 * Exceção lançada quando violações de antifraude são detectadas em uma transação.
 */
public class FraudViolationException extends RuntimeException {

    private final List<FraudViolation> violations;

    public FraudViolationException(List<FraudViolation> violations) {
        super("Transação rejeitada por violações de antifraude: " + violations);
        this.violations = violations;
    }

    public List<FraudViolation> getViolations() {
        return violations;
    }
}
