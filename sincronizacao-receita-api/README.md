# Sincronização Receita

### Introdução

### Tecnologias

- Java 8
    ```bash
    $ java -version                                                      
      openjdk version "1.8.0_252"
    ```
### Projeto

No diretório base do projeto deve ser executado os seguintes comandos:

- Para construir o projeto use:
    ```bash
    $ mvn clean && mvn install -DskipTests
    ```

- O resultado do comando está localizado em ***./target/sincronizacaoreceita-0.0.1-SNAPSHOT.jar***

- Agora para executar o processo de sincronização com a receita:
    ```bash
    $ java -jar ./target/sincronizacaoreceita-0.0.1-SNAPSHOT.jar <csv-file>
    ```

- O resultado será o arquivo csv com a data da execução localizado no mesmo diretório do arquivo enviado.

    Exemplo: ***data_02112020-093303.csv***
  
### Observações

- O formato do arquivo csv de entrada é:
    
    ```csv
    agencia;conta;saldo;status      
    0101;12225-6;100,00;A      
    0101;12226-8;3200,50;A      
    3202;40011-1;-35,12;I      
    3202;54001-2;0,00;P      
    3202;00321-2;34500,00;B
    ```
- O formato do arquivo csv de saída é:
    
    ```csv
    agencia;conta;saldo;status;resultado
    0101;12225-6;100,00;A;ENVIADO
    0101;12226-8;3200,50;A;ENVIADO
    3202;40011-1;-35,12;I;ENVIADO
    3202;54001-2;0,00;P;ERRO
    3202;00321-2;34500,00;B;ENVIADO
    ```
    


