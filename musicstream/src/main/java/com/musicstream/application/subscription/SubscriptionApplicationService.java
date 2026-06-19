package com.musicstream.application.subscription;

import com.musicstream.domain.subscription.model.PlanType;
import com.musicstream.domain.subscription.model.Subscription;
import com.musicstream.domain.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application Service para operações de Assinatura.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionApplicationService {

    private final SubscriptionService subscriptionService;

    /**
     * Assina um plano para a conta.
     */
    @Transactional
    public Subscription subscribe(Long accountId, String planTypeName) {
        PlanType planType = parsePlanType(planTypeName);
        return subscriptionService.subscribe(accountId, planType);
    }

    /**
     * Cancela a assinatura ativa da conta.
     */
    @Transactional
    public Subscription cancelSubscription(Long accountId) {
        return subscriptionService.cancelSubscription(accountId);
    }

    /**
     * Altera o plano ativo da conta.
     */
    @Transactional
    public Subscription changePlan(Long accountId, String planTypeName) {
        PlanType planType = parsePlanType(planTypeName);
        return subscriptionService.changePlan(accountId, planType);
    }

    private PlanType parsePlanType(String planTypeName) {
        try {
            return PlanType.valueOf(planTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de plano inválido: " + planTypeName + ". Valores aceitos: FREE, PREMIUM, FAMILY");
        }
    }
}
