package br.com.suporte.chamados.model;

public record Chamado(
        Long id,
        String titulo,
        String solicitante,
        String setor,
        Prioridade prioridade,
        Status status
) {
}