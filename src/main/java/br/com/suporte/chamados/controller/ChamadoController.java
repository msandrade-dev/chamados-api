package br.com.suporte.chamados.controller;

import br.com.suporte.chamados.model.Chamado;
import br.com.suporte.chamados.model.Prioridade;
import br.com.suporte.chamados.model.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final AtomicLong proximoId = new AtomicLong(11);
    private final List<Chamado> chamados = new CopyOnWriteArrayList<>(List.of(
            new Chamado(1L, "Computador não liga", "Ana Souza", "Financeiro", Prioridade.ALTA, Status.ABERTO),
            new Chamado(2L, "Impressora sem papel", "Bruno Lima", "Recursos Humanos", Prioridade.BAIXA, Status.RESOLVIDO),
            new Chamado(3L, "Queda de conexão com a internet", "Carla Mendes", "Vendas", Prioridade.ALTA, Status.EM_ANDAMENTO),
            new Chamado(4L, "Criação de conta de e-mail", "Diego Alves", "Marketing", Prioridade.MEDIA, Status.ABERTO),
            new Chamado(5L, "Troca de senha de acesso", "Elisa Rocha", "Financeiro", Prioridade.MEDIA, Status.RESOLVIDO),
            new Chamado(6L, "Monitor com tela piscando", "Fábio Costa", "Engenharia", Prioridade.BAIXA, Status.ABERTO),
            new Chamado(7L, "Acesso bloqueado ao sistema", "Gabriela Reis", "Operações", Prioridade.ALTA, Status.EM_ANDAMENTO),
            new Chamado(8L, "Instalação de impressora", "Henrique Dias", "Diretoria", Prioridade.MEDIA, Status.RESOLVIDO),
            new Chamado(9L, "Lentidão no computador", "Isabela Martins", "Logística", Prioridade.BAIXA, Status.EM_ANDAMENTO),
            new Chamado(10L, "VPN indisponível", "João Oliveira", "Tecnologia", Prioridade.ALTA, Status.ABERTO)
    ));

    @GetMapping
    public List<Chamado> listar(
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) Status status) {
        return chamados.stream()
                .filter(chamado -> prioridade == null || chamado.prioridade() == prioridade)
                .filter(chamado -> status == null || chamado.status() == status)
                .toList();
    }

    @GetMapping("/prioritarios")
    public List<Chamado> listarPrioritarios() {
        return chamados.stream()
                .filter(chamado -> chamado.prioridade() == Prioridade.ALTA)
                .filter(chamado -> chamado.status() != Status.RESOLVIDO)
                .toList();
    }

    @GetMapping("/{id}")
    public Chamado buscarPorId(@PathVariable Long id) {
        return chamados.stream()
                .filter(chamado -> chamado.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Chamado> criar(@Valid @RequestBody NovoChamado novoChamado) {
        Chamado chamado = new Chamado(
                proximoId.getAndIncrement(),
                novoChamado.titulo(),
                novoChamado.solicitante(),
                novoChamado.setor(),
                novoChamado.prioridade(),
                novoChamado.status()
        );
        chamados.add(chamado);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamado);
    }

    public record NovoChamado(
            @NotBlank String titulo,
            @NotBlank String solicitante,
            @NotBlank String setor,
            @NotNull Prioridade prioridade,
            @NotNull Status status
    ) {
    }
}