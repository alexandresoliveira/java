# Pautas API

### Introdução

Sistema para computar votos para pautas.

### Integração com mensageria

--- ***Em andamento*** ---

### Versionamento da Api

Foi analisado o versionamento por path e por headers eu escolhi por path
por ser mais visivel para o desenvolvedor.

A versão também foi adicionanda nos packages dos casos de uso.

### Validação do CPF

Não foi encontrado um recurso externo ativo para a integração. 
Foi feito localmente com classe de terceiros.

### Rotas da api

Está api tem as seguintes rotas:

- POST http://localhost:8080/api/v1/pautas  
    -   Request  
        ```json    
        {
            "nome": "Pauta 1"
        } 
        ```
    -   Response | 201
        ```json    
        {
            "id": "0e36461a-efa1-49ad-a0b6-0c8c1fe3b8cd",
            "nome": "Pauta 1",
            "dataCriacao": "2020-11-05T00:14:49.194+00:00"
        } 
        ```
- POST http://localhost:8080/api/v1/votacoes/sessoes/abrir
    -   Request
        ```json
        {
            "pautaId": "0e36461a-efa1-49ad-a0b6-0c8c1fe3b8cd",
            "encerraEm": <opcional, formato: "2020-11-05T21:47:39">
        }
        ```
    -   Response | 201
        ```json
        {
            "idVotacao": "fb84220c-12b9-4eb7-825b-249cce26a96c",
            "pauta": "Pauta 1",
            "encerraEm": "2020-11-05T21:47:39.607+00:00"
        }
        ```
- GET http://localhost:8080/api/v1/votacoes/resultado/{idVotacao}
    -   Response | 200
        ```json
        {
            "pauta": "Pauta 1",
            "status": "Encerrado",
            "dataEncerramento": "2020-11-05T21:47:39.607+00:00",
            "numVotosSim": 1,
            "numVotosNao": 2
        }
        ```
- POST http://localhost:8080/api/v1/votos
    -   Request
        ```json
        {
            "votacaoId": "fb84220c-12b9-4eb7-825b-249cce26a96c",
            "cpf": "61142233352",
            "resposta": "NAO"
        }
        ```
    -   Response | 201
    
Foi usado o [***Insomnia***](https://insomnia.rest/) para testes da api 
e segue o link para o arquivo de restauração do workspace 
[***Insomnia.json***](./Insomnia.json)

### Erros
    
As exceções são tratadas no Advice e todas são personalizadas.

### Executando o projeto

No diretório raiz do projeto, siga os passos:

-   Para instalar:
    ```bash
    $ mvn clean install
    ```

-   Para executar
    ```bash
    $ java -jar ./target/pautas-0.0.1.jar -Dprofile=dev
    ```



