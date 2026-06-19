package com.musicstream.domain.subscription.service;

import com.musicstream.domain.subscription.model.PlanType;
import com.musicstream.domain.subscription.model.Subscription;
import com.musicstream.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Domain Service do contexto de Assinatura.
 * Garante a invariante: usuário pode ter somente um plano ativo.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    /**
     * Cria uma nova assinatura para a conta.
     * Garante que somente um plano ativo por conta seja permitido.
     *
     * @throws IllegalStateException se a conta já possui assinatura ativa
     */
    public Subscription subscribe(Long accountId, PlanType planType) {
        if (subscriptionRepository.hasActiveSubscription(accountId)) {
            throw new IllegalStateException("O usuário já possui um plano ativo. Cancele o plano atual antes de assinar um novo.");
        }
        Subscription subscription = Subscription.create(accountId, planType);
        return subscriptionRepository.save(subscription);
    }

    /**
     * Cancela a assinatura ativa da conta.
     *
     * @throws IllegalStateException se a conta não possui assinatura ativa
     */
    public Subscription cancelSubscription(Long accountId) {
        Subscription subscription = subscriptionRepository.findActiveSubscriptionByAccountId(accountId)
                .orElseThrow(() -> new IllegalStateException("Nenhuma assinatura ativa encontrada para a conta: " + accountId));
        subscription.cancel();
        return subscriptionRepository.save(subscription);
    }

    /**
     * Altera o plano da assinatura ativa.
     */
    public Subscription changePlan(Long accountId, PlanType newPlanType) {
        Subscription subscription = subscriptionRepository.findActiveSubscriptionByAccountId(accountId)
                .orElseThrow(() -> new IllegalStateException("Nenhuma assinatura ativa encontrada para a conta: " + accountId));
        subscription.changePlan(newPlanType);
        return subscriptionRepository.save(subscription);
    }
}
