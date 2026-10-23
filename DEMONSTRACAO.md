# Testes e demonstracao da API

Base usada nos testes: `http://localhost:8080`.

## 1. Listar todos os chamados

**Requisicao:**

```http
GET /chamados
```

**Resposta:** `HTTP 200`

```json
[
  {"id":1,"titulo":"Computador não liga","prioridade":"ALTA","status":"ABERTO"},
  {"id":2,"titulo":"Impressora sem papel","prioridade":"BAIXA","status":"RESOLVIDO"},
  {"id":3,"titulo":"Queda de conexão com a internet","prioridade":"ALTA","status":"EM_ANDAMENTO"},
  {"id":4,"titulo":"Criação de conta de e-mail","prioridade":"MEDIA","status":"ABERTO"},
  {"id":5,"titulo":"Troca de senha de acesso","prioridade":"MEDIA","status":"RESOLVIDO"},
  {"id":6,"titulo":"Monitor com tela piscando","prioridade":"BAIXA","status":"ABERTO"},
  {"id":7,"titulo":"Acesso bloqueado ao sistema","prioridade":"ALTA","status":"EM_ANDAMENTO"},
  {"id":8,"titulo":"Instalação de impressora","prioridade":"MEDIA","status":"RESOLVIDO"},
  {"id":9,"titulo":"Lentidão no computador","prioridade":"BAIXA","status":"EM_ANDAMENTO"},
  {"id":10,"titulo":"VPN indisponível","prioridade":"ALTA","status":"ABERTO"}
]
```

**Resultado:** A API retornou os 10 chamados fictícios carregados em memória.

## 2. Consultar por ID

**Requisicao:**

```http
GET /chamados/3
```

**Resposta:** `HTTP 200`

```json
{"id":3,"titulo":"Queda de conexão com a internet","solicitante":"Carla Mendes","setor":"Vendas","prioridade":"ALTA","status":"EM_ANDAMENTO"}
```

**Resultado:** O chamado de ID 3 foi localizado corretamente.

## 3. Filtrar por setor

**Requisicao:**

```http
GET /chamados/setor?nome=financeiro
```

**Resposta:** `HTTP 200`

```json
[
  {"id":1,"titulo":"Computador não liga","setor":"Financeiro"},
  {"id":5,"titulo":"Troca de senha de acesso","setor":"Financeiro"}
]
```

**Resultado:** O filtro encontrou dois chamados e aceitou o setor em letras minúsculas.

## 4. Filtrar por prioridade

**Requisicao:**

```http
GET /chamados/prioridade?nivel=alta
```

**Resposta:** `HTTP 200`

```json
[
  {"id":1,"prioridade":"ALTA"},
  {"id":3,"prioridade":"ALTA"},
  {"id":7,"prioridade":"ALTA"},
  {"id":10,"prioridade":"ALTA"}
]
```

**Resultado:** Foram retornados os quatro chamados de alta prioridade.

## 5. Buscar pelo título

**Requisicao:**

```http
GET /chamados/busca?texto=IMPRESSORA
```

**Resposta:** `HTTP 200`

```json
[
  {"id":2,"titulo":"Impressora sem papel"},
  {"id":8,"titulo":"Instalação de impressora"}
]
```

**Resultado:** A busca encontrou os títulos mesmo com o texto em letras maiúsculas.

## 6. Busca sem correspondência

**Requisicao:**

```http
GET /chamados/busca?texto=nao-existe
```

**Resposta:** `HTTP 200`

```json
[]
```

**Resultado:** A API retorna uma lista vazia quando nenhum título corresponde à busca.

## 7. Listar chamados críticos

**Requisicao:**

```http
GET /chamados/criticos
```

**Resposta:** `HTTP 200`

```json
[
  {"id":1,"prioridade":"ALTA","status":"ABERTO"},
  {"id":3,"prioridade":"ALTA","status":"EM_ANDAMENTO"},
  {"id":7,"prioridade":"ALTA","status":"EM_ANDAMENTO"},
  {"id":10,"prioridade":"ALTA","status":"ABERTO"}
]
```

**Resultado:** Foram combinadas as regras de prioridade alta e status diferente de resolvido.

## 8. Resumo por status

**Requisicao:**

```http
GET /chamados/resumo
```

**Resposta:** `HTTP 200`

```json
{"abertos":4,"emAndamento":3,"resolvidos":3,"total":10}
```

**Resultado:** O resumo informa as quantidades por status e o total geral.

## 9. Cadastrar chamado

**Requisicao:**

```http
POST /chamados
Content-Type: application/json
```

```json
{
  "titulo": "Notebook não inicia",
  "solicitante": "Marina Silva",
  "setor": "Administrativo",
  "prioridade": "MEDIA",
  "status": "ABERTO"
}
```

**Resposta:** `HTTP 201 Created`

```json
{"id":11,"titulo":"Notebook não inicia","solicitante":"Marina Silva","setor":"Administrativo","prioridade":"MEDIA","status":"ABERTO"}
```

**Resultado:** O chamado foi cadastrado com identificador único 11.

## 10. Verificar chamado cadastrado

**Requisicao:**

```http
GET /chamados/11
```

**Resposta:** `HTTP 200`

```json
{"id":11,"titulo":"Notebook não inicia","solicitante":"Marina Silva","setor":"Administrativo","prioridade":"MEDIA","status":"ABERTO"}
```

**Resultado:** A consulta confirmou que os dados enviados no cadastro foram armazenados em memória.
