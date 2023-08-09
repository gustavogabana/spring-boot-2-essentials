# Spring Boot 2 Essentials

Projeto de teste para estudar e aprender Spring Boot.

# On Running

Para executar o projeto é necessário a configuração das váriaveis de ambiente do JAVA (JAVA_HOME) e do MAVEN.

# Tests

Para executar os testes de integração é necessário:

1º: Comentar a configuração do Banco de Dados MySQL e descomentar a configuração do Banco de Dados H2
no arquivo properties.yml, pois os testes são executados em um banco de dados local.

2º: Para executar os testes de integração, nomeados com a conveção IT ao final do nome da classe java, é necessário
executar o comando **mvn test -Pintegration-tests** no terminal, devendo este estar aberto dentro da pasta do projeto, pois
os testes de integração estão em um profile diferente, conforme a tag <profiles> no arquivo pom.xml.