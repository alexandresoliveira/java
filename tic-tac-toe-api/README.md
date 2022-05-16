# jogo-da-velha-api

Api para o jogo da velha!

O que foi usado:

- Java 11
- Mave 3.6.3
- Spring Boot 2.4.5
- Starters:
  - data-jpa
  - web
  - validation
  - test
- database
  - h2
  - flyway-core
- lombok

Para executar, basta seguir os passos:

```bash
# clonr o projeto
$ git clone git@github.com:alexandresoliveira/jogo-da-velha-api.git

# entrar no diretório do projeto
$ cd jogo-da-velha-api

# executar o projeto
## aqui o projeto sera executado com as configurações de desenvolvimento cadastradas no profile do pom.xml
$ mvn spring-boot:run -P dev
```

## Api

No mesmo repositório, existe uma coleção do Insomnia com rotas já definidas para testes e verificação de parâmetros.

### Tabuleiro

|           |           |           |
| --------- | --------- | --------- |
| (x=0 y=2) | (x=1 y=2) | (x=2 y=2) |
| (x=0 y=1) | (x=1 y=1) | (x=2 y=1) |
| (x=0 y=0) | (x=1 y=0) | (x=2 y=0) |

### POST - /game

Request: no body

Response:

```json
{
  "id": "4175d180-6b86-4af4-ab20-e8671677abe2",
  "firstPlayer": "X"
}
```

### POST - /game/{id}/movement

Request:

```json
{
  "id": "4175d180-6b86-4af4-ab20-e8671677abe2", // id game
  "player": "X", // jogador atual - valores possíveis X ou O
  "position": {
    "x": 0, // posição x no tabuleiro - min 0, max 2
    "y": 0 // posição y no tabuleiro - min 0, max 2
  }
}
```

Response:

- 200: Próxima jogada

- 200 - Game finished:

  ```json
  {
    "status": "Partida Finalizada",
    "winner": "X" // X || O || Draw
  }
  ```

- (Exception) - 400 - Game not found:

  ```json
  {
    "msg": "Partida não encontrada"
  }
  ```

- (Exception) - 400 - Turno do jogador: Quando o jogador tenta marcar 2 ou mais jogadas sem passar a vez.

  ```json
  {
    "msg": "Não é o turno do jogador"
  }
  ```

- (Exception) - 400 - Movimento inválido: Quando o jogador tenta marcar em uma posição já preenchida.

  ```json
  {
    "msg": "Movimento inválido, x=0, y=2, player=X"
  }
  ```
