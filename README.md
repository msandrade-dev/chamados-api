# Chamados API

API REST para gerenciamento de chamados de suporte técnico, com armazenamento em memória.

## Executar

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

- `GET /chamados`: lista todos os chamados.
- `GET /chamados/{id}`: consulta um chamado pelo ID.
- `GET /chamados/setor?nome=financeiro`: filtra chamados por setor.
- `GET /chamados/prioridade?nivel=alta`: filtra chamados por prioridade.
- `GET /chamados/busca?texto=impressora`: busca chamados pelo título.
- `GET /chamados/criticos`: lista chamados de prioridade alta ainda não resolvidos.
- `GET /chamados/resumo`: retorna as quantidades por status e o total geral.
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