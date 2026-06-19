package com.musicstream.domain.account.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * Value Object que representa o cartão de crédito do usuário.
 * Garante invariantes de negócio: um cartão deve sempre ter número e status definidos.
 * Linguagem ubíqua: CartaoDeCredito (CreditCard)
 */
@Embeddable
@Getter
public class CreditCard {

    private String cardNumber;
    private boolean active;

    protected CreditCard() {
        // Necessário para JPA
    }

    private CreditCard(String cardNumber, boolean active) {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Número do cartão não pode ser nulo ou vazio.");
        }
        this.cardNumber = cardNumber;
        this.active = active;
    }

    /**
     * Cria um cartão de crédito ativo.
     */
    public static CreditCard of(String cardNumber) {
        return new CreditCard(cardNumber, true);
    }

    /**
     * Cria um cartão de crédito com status explícito.
     */
    public static CreditCard of(String cardNumber, boolean active) {
        return new CreditCard(cardNumber, active);
    }

    /**
     * Verifica se o cartão está ativo para uso em transações.
     */
    public boolean isEligibleForTransaction() {
        return this.active;
    }

    /**
     * Retorna novo Value Object com cartão desativado (imutabilidade).
     */
    public CreditCard deactivate() {
        return new CreditCard(this.cardNumber, false);
    }

    /**
     * Retorna novo Value Object com cartão ativado (imutabilidade).
     */
    public CreditCard activate() {
        return new CreditCard(this.cardNumber, true);
    }
}
