# Chamados API

API REST para gerenciamento de chamados de suporte técnico, com armazenamento em memória.

## Executar

O projeto exige um JDK 17 ou superior. No ambiente atual, use o comando completo
para selecionar o JDK 17:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH="$JAVA_HOME/bin:$PATH"
mvn spring-boot:run
```

Se o JDK 17 já estiver configurado como padrão no seu terminal, basta executar:

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

As evidências das requisições e respostas estão em [DEMONSTRACAO.md](DEMONSTRACAO.md).

## Análise técnica

### Processamento das requisições HTTP

O Spring Boot inicia um servidor web embutido, neste projeto o Tomcat, que escuta
na porta `8080`. Quando uma requisição HTTP chega, o Spring MVC identifica o
método HTTP e o caminho solicitado, localiza o método compatível pelas
anotações de mapeamento e encaminha a execução para ele. Ao final, o resultado
do método é convertido em uma resposta HTTP.

### Função do Controller

O `ChamadoController` é a camada responsável por receber as requisições da API
e definir o comportamento dos endpoints de chamados. Ele lista, filtra,
consulta e cadastra chamados, valida os dados recebidos no POST e define o
status HTTP da criação. Neste projeto, ele também mantém a coleção em memória,
pois não há camada de serviço ou repositório.

### Conversão entre Java e JSON

O Spring Boot usa o Jackson, integrado ao `spring-boot-starter-web`, para fazer
essa conversão automaticamente. Na resposta, objetos `Chamado` e registros
como `Resumo` são serializados de objetos Java para JSON. No POST, o JSON do
corpo da requisição é desserializado para o registro `NovoChamado`; depois da
validação, os dados são usados para criar um novo objeto `Chamado`.

### Persistência dos chamados

Os chamados são armazenados em uma coleção Java criada na memória do processo.
Esse armazenamento é temporário: ao encerrar ou reiniciar a aplicação, a
memória é liberada e a coleção volta a ser inicializada apenas com os 10
chamados fictícios definidos no código. Como o projeto não utiliza banco de
dados ou arquivos de persistência, os chamados cadastrados via POST não
permanecem após o reinício.

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