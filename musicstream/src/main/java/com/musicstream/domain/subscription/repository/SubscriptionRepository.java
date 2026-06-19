package com.musicstream.domain.subscription.repository;

import com.musicstream.domain.subscription.model.Subscription;

import java.util.Optional;

/**
 * Contrato do repositório de Assinaturas.
 * Intention Revealing Interface.
 */
public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    /**
     * Busca a assinatura ativa de uma conta, se existir.
     */
    Optional<Subscription> findActiveSubscriptionByAccountId(Long accountId);

    /**
     * Verifica se a conta já possui alguma assinatura ativa.
     */
    boolean hasActiveSubscription(Long accountId);
}
