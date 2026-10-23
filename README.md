# Chamados API

API REST para gerenciamento de chamados de suporte técnico, com armazenamento em memória.

## Executar

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

- `GET /chamados`: lista chamados; aceita `prioridade` e `status` como filtros.
- `GET /chamados/prioritarios`: lista chamados de prioridade alta ainda não resolvidos.
- `GET /chamados/{id}`: consulta um chamado pelo ID.
- `POST /chamados`: cria um chamado.

Exemplo de corpo para criação:

```json
{
  "titulo": "Teclado com defeito",
  "solicitante": "Marina Silva",
  "setor": "Administrativo",
  "prioridade": "MEDIA",
  "status": "ABERTO"
}
```