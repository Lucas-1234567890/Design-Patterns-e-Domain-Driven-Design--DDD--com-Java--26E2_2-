package com.musicstream.domain.antifraud.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma transação financeira no contexto de antifraude.
 * Linguagem ubíqua: Transacao (Transaction)
 * Armazena o histórico necessário para avaliar as regras de fraude.
 */
@Entity
@Table(name = "transactions")
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String merchant;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    protected Transaction() {
        // Necessário para JPA
    }

    /**
     * Cria uma transação aprovada.
     */
    public static Transaction approved(Long accountId, BigDecimal amount, String merchant) {
        return create(accountId, amount, merchant, TransactionStatus.APPROVED);
    }

    /**
     * Cria uma transação rejeitada.
     */
    public static Transaction rejected(Long accountId, BigDecimal amount, String merchant) {
        return create(accountId, amount, merchant, TransactionStatus.REJECTED);
    }

    private static Transaction create(Long accountId, BigDecimal amount, String merchant, TransactionStatus status) {
        validateFields(accountId, amount, merchant);
        Transaction t = new Transaction();
        t.accountId = accountId;
        t.amount = amount;
        t.merchant = merchant;
        t.occurredAt = LocalDateTime.now();
        t.status = status;
        return t;
    }

    private static void validateFields(Long accountId, BigDecimal amount, String merchant) {
        if (accountId == null) throw new IllegalArgumentException("accountId não pode ser nulo.");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Valor da transação deve ser positivo.");
        if (merchant == null || merchant.isBlank()) throw new IllegalArgumentException("Comerciante não pode ser vazio.");
    }

    /**
     * Verifica se a transação é similar a outra (mesmo valor e comerciante).
     */
    public boolean isSimilarTo(Transaction other) {
        return this.amount.compareTo(other.amount) == 0
                && this.merchant.equalsIgnoreCase(other.merchant);
    }

    /**
     * Verifica se a transação ocorreu dentro de um intervalo de minutos a partir de um ponto de referência.
     */
    public boolean occurredWithinMinutesOf(LocalDateTime reference, int minutes) {
        return !this.occurredAt.isBefore(reference.minusMinutes(minutes));
    }
}
