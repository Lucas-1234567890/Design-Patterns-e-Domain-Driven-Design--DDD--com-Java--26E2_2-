package com.musicstream.domain.subscription.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Aggregate Root do contexto de Assinatura.
 * Garante invariante de negócio: usuário pode ter somente um plano ativo.
 * Linguagem ubíqua: Assinatura (Subscription)
 */
@Entity
@Table(name = "subscriptions")
@Getter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active;

    protected Subscription() {
        // Necessário para JPA
    }

    /**
     * Cria uma nova assinatura ativa para uma conta.
     */
    public static Subscription create(Long accountId, PlanType planType) {
        if (accountId == null) throw new IllegalArgumentException("accountId não pode ser nulo.");
        if (planType == null) throw new IllegalArgumentException("Tipo de plano não pode ser nulo.");

        Subscription subscription = new Subscription();
        subscription.accountId = accountId;
        subscription.planType = planType;
        subscription.startDate = LocalDate.now();
        subscription.active = true;
        return subscription;
    }

    /**
     * Cancela a assinatura ativa, registrando a data de encerramento.
     */
    public void cancel() {
        if (!this.active) {
            throw new IllegalStateException("A assinatura já está cancelada.");
        }
        this.active = false;
        this.endDate = LocalDate.now();
    }

    /**
     * Altera o tipo de plano da assinatura ativa.
     */
    public void changePlan(PlanType newPlanType) {
        if (!this.active) {
            throw new IllegalStateException("Não é possível alterar o plano de uma assinatura cancelada.");
        }
        if (newPlanType == null) {
            throw new IllegalArgumentException("Novo tipo de plano não pode ser nulo.");
        }
        this.planType = newPlanType;
    }

    /**
     * Verifica se a assinatura está ativa.
     */
    public boolean isActive() {
        return active;
    }
}
