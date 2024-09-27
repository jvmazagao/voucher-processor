### Voucher Processor

Esse projeto faz a implementação do seguinte desáfio (Caju)[https://caju.notion.site/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72] e fora implementado utilizando Kotlin 
os requisitos L1-L3.

Sobre o requisito L4:

Diante de um cenário real, o uso de um banco de dados relacional para fazer a manipulação do banco de dados ocorreria de forma natural, ou seja, um simples lock da tabela na recepção da primeira chamada concorrente
poderia evitar um processamento errado de uma carteira. Dessa forma, resolveria o problema de forma minima. 

Utilizariamos uma tabela wallet que referenciaria as carteiras necessárias para serem debitadas os creditos. Como o atributo Account é um atributo unico para cada usuário poderiamos fazer uma busca com esse idexador
e o tipo de carteira que devemos buscar dessa forma retornariamos a carteira especifica para se atualizar. 

Na ação de update onde liberariamos a transaction caso sucesso travaria não deixaria nenhuma outra transação ser executada de forma simultanea. 



