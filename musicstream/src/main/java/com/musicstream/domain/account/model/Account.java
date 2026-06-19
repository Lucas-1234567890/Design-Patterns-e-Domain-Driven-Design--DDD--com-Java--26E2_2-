package com.musicstream.domain.account.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Aggregate Root do contexto de Conta.
 * Encapsula todas as invariantes de negócio relacionadas à conta do usuário.
 * Linguagem ubíqua: Conta (Account)
 */
@Entity
@Table(name = "accounts")
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "cardNumber", column = @Column(name = "card_number")),
        @AttributeOverride(name = "active", column = @Column(name = "card_active"))
    })
    private CreditCard creditCard;

    protected Account() {
        // Necessário para JPA
    }

    /**
     * Cria uma nova conta. Garante que email e nome não sejam vazios.
     */
    public static Account create(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome da conta não pode ser vazio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email da conta não pode ser vazio.");
        }
        Account account = new Account();
        account.name = name;
        account.email = email;
        return account;
    }

    /**
     * Associa um cartão de crédito à conta.
     */
    public void assignCreditCard(CreditCard creditCard) {
        if (creditCard == null) {
            throw new IllegalArgumentException("Cartão de crédito não pode ser nulo.");
        }
        this.creditCard = creditCard;
    }

    /**
     * Verifica se a conta possui cartão de crédito válido e ativo.
     */
    public boolean hasValidCreditCard() {
        return this.creditCard != null && this.creditCard.isEligibleForTransaction();
    }

    /**
     * Desativa o cartão de crédito da conta.
     */
    public void deactivateCreditCard() {
        if (this.creditCard == null) {
            throw new IllegalStateException("Conta não possui cartão de crédito associado.");
        }
        this.creditCard = this.creditCard.deactivate();
    }

    /**
     * Ativa o cartão de crédito da conta.
     */
    public void activateCreditCard() {
        if (this.creditCard == null) {
            throw new IllegalStateException("Conta não possui cartão de crédito associado.");
        }
        this.creditCard = this.creditCard.activate();
    }
}
