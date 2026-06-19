package com.musicstream.interfaces.rest.account;

import com.musicstream.application.account.AccountApplicationService;
import com.musicstream.domain.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST para operações de Conta.
 * Parte 1 - Operação 1: Criação de Conta.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountApplicationService accountApplicationService;

    /**
     * POST /api/accounts
     * Cria uma nova conta de usuário.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountApplicationService.createAccount(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /**
     * GET /api/accounts/{id}
     * Busca uma conta pelo id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountApplicationService.findAccount(id);
        return ResponseEntity.ok(account);
    }

    /**
     * POST /api/accounts/{id}/credit-card
     * Associa um cartão de crédito à conta.
     */
    @PostMapping("/{id}/credit-card")
    public ResponseEntity<Account> assignCreditCard(
            @PathVariable Long id,
            @RequestBody AssignCreditCardRequest request) {
        Account account = accountApplicationService.assignCreditCard(id, request.cardNumber(), request.active());
        return ResponseEntity.ok(account);
    }

    public record CreateAccountRequest(String name, String email) {}

    public record AssignCreditCardRequest(String cardNumber, boolean active) {}
}
