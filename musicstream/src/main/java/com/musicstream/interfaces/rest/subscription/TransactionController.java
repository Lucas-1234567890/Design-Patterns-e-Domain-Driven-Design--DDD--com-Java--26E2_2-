package com.musicstream.interfaces.rest.subscription;

import com.musicstream.application.subscription.TransactionApplicationService;
import com.musicstream.domain.antifraud.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller REST para autorização de transações financeiras.
 * Parte 1 - Operação 2: Autorização de Transação.
 * Aplica automaticamente as regras de antifraude.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionApplicationService transactionApplicationService;

    /**
     * POST /api/transactions
     * Autoriza uma transação financeira aplicando as regras de antifraude.
     *
     * Regras aplicadas:
     * - Cartão deve estar ativo
     * - Máximo 3 transações em 2 minutos
     * - Máximo 2 transações similares (mesmo valor + comerciante) em 2 minutos
     */
    @PostMapping
    public ResponseEntity<Transaction> authorizeTransaction(@RequestBody AuthorizeTransactionRequest request) {
        Transaction transaction = transactionApplicationService.authorizeTransaction(
                request.accountId(),
                request.amount(),
                request.merchant()
        );
        return ResponseEntity.ok(transaction);
    }

    public record AuthorizeTransactionRequest(Long accountId, BigDecimal amount, String merchant) {}
}
