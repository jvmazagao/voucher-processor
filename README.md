### Voucher Processor

Esse projeto faz a implementação do seguinte desáfio (Caju)[https://caju.notion.site/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72] e fora implementado utilizando Kotlin 
os requisitos L1-L3.

Sobre o requisito L4:

Diante de um cenário real, o uso de um banco de dados relacional para fazer a manipulação do banco de dados ocorreria de forma natural, ou seja, um simples lock da tabela na recepção da primeira chamada concorrente
poderia evitar um processamento errado de uma carteira. Dessa forma, resolveria o problema de forma minima. 

Utilizariamos uma tabela wallet que referenciaria as carteiras necessárias para serem debitadas os creditos. Como o atributo Account é um atributo unico para cada usuário poderiamos fazer uma busca com esse idexador
e o tipo de carteira que devemos buscar dessa forma retornariamos a carteira especifica para se atualizar. 

Na ação de update onde liberariamos a transaction caso sucesso travaria não deixaria nenhuma outra transação ser executada de forma simultanea. 


# Sobre o projeto

Esse projeto tem como finalide um serviço de Authorização de Transação que processa transações financeiras derivadas de carteira mista de beneficios. Sendo um esboço de um serviço de transação sem o auxilio de um banco de dados ou qualquer outra interação semelhantes. 

## Tecnologias
- Kotlin
- Ktor

### Estrutura do projeto
O projeto é orientado a modulos, sendo o unico modulo disponivel o de Transactions, dentro desses modulos encontramos:
- models: que concentra a implementação das classes de modelo.
- service: que concentra a implementação das camadas de regras de negócio.
- plugins.rounting: concentra a implementação das chamadas de endpoint.

### Configuração e execução
- Instalação do kotlin e java11+
#### Execução do projeto
```
./gradlew run
```

#### Execução dos testes
```
./gradlew test
```

### Endpoints

- POST /transactions
  e deve ser enviado o seguinte payload simplificado de cartão de crédito
  ```
  {
	"account": "123",
	"totalAmount": 100.00,
	"mcc": "5811",
	"merchant": "PADARIA DO ZE               SAO PAULO BR"
}
  ```
