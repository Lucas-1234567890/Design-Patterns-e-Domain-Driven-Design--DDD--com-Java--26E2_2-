package com.musicstream.domain.antifraud.model;

/**
 * Value Object que representa uma violação detectada pelo sistema de antifraude.
 * Utiliza os nomes exatos da linguagem ubíqua definida no enunciado.
 */
public enum FraudViolation {

    /**
     * Cartão de crédito não está ativo.
     */
    CARD_NOT_ACTIVE("cartao-nao-ativo"),

    /**
     * Mais de 3 transações em intervalo de 2 minutos.
     */
    HIGH_FREQUENCY_SMALL_INTERVAL("alta-frequencia-pequeno-intervalo"),

    /**
     * Mais de 2 transações similares (mesmo valor e comerciante) em 2 minutos.
     */
    DOUBLED_TRANSACTION("transacao-duplicada"),

    /**
     * Usuário não possui cartão de crédito cadastrado.
     */
    INVALID_CREDIT_CARD("cartao-invalido");

    private final String code;

    FraudViolation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
