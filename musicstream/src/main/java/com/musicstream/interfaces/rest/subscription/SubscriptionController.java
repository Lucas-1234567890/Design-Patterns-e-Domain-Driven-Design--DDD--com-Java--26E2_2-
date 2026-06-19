package com.musicstream.interfaces.rest.subscription;

import com.musicstream.application.subscription.SubscriptionApplicationService;
import com.musicstream.domain.subscription.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para operações de Assinatura.
 * Parte 1 - Operação 5: Assinatura.
 * Garante que o usuário só possua um plano ativo por vez.
 */
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionApplicationService subscriptionApplicationService;

    /**
     * POST /api/subscriptions
     * Assina um plano para o usuário.
     * Retorna erro 422 se o usuário já possuir plano ativo.
     */
    @PostMapping
    public ResponseEntity<Subscription> subscribe(@RequestBody SubscribeRequest request) {
        Subscription subscription = subscriptionApplicationService.subscribe(
                request.accountId(),
                request.planType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    /**
     * DELETE /api/subscriptions/{accountId}
     * Cancela a assinatura ativa do usuário.
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable Long accountId) {
        Subscription subscription = subscriptionApplicationService.cancelSubscription(accountId);
        return ResponseEntity.ok(subscription);
    }

    /**
     * PATCH /api/subscriptions/{accountId}/plan
     * Altera o plano ativo do usuário.
     */
    @PatchMapping("/{accountId}/plan")
    public ResponseEntity<Subscription> changePlan(
            @PathVariable Long accountId,
            @RequestBody ChangePlanRequest request) {
        Subscription subscription = subscriptionApplicationService.changePlan(accountId, request.planType());
        return ResponseEntity.ok(subscription);
    }

    public record SubscribeRequest(Long accountId, String planType) {}

    public record ChangePlanRequest(String planType) {}
}
