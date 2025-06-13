![Aurawave Logo](./assets/Domain Driven Desing - CHALLENGE.png)
---
# Aurawave Service
Obs: Este documento te ajudará a corrigir esta tarefa.

1. [O que é a Aurawave](#sobre)
2. [Explicação do projeto](#projeto)
3. [Como iniciar a aplicação](#aplicacao) 
4. **[Requisitos Challenge](#requisitos)**

---

## Sobre
<div id="sobre"></div>
A **Aurawave** é uma **startup inovadora** dedicada a transformar a **gestão de materiais** nas unidades laboratoriais. Utilizando **tecnologia RFID** e **sensores inteligentes**, a Aurawave oferece uma **solução automatizada e precisa** para o controle em tempo real 
de reagentes e materiais. Nosso objetivo é **eliminar falhas humanas**, **reduzir desperdícios** e **otimizar a logística** nos mais de 900 laboratórios e 20 centros de distribuição da Dasa, garantindo **eficiência**, **transparência** e **controle total** do consumo 
de materiais.

Com a Aurawave, buscamos **transformar a cadeia de supply chain** em uma operação inteligente e ágil, promovendo **economia de custos** e **agilidade operacional**. Nosso compromisso é **melhorar a gestão de recursos**, garantindo uma operação mais **eficiente** e 
**sustentável** para a Dasa.

---

## Projeto
<div id="projeto"></div>
Este projeto é um MVP que reflete o **sistema de controle de consumo** da **Aurawave**, com foco no **registro automatizado de materiais** e **geração de relatórios detalhados**. A solução foi projetada para **integrar o controle de estoque com sistemas internos** da Dasa (como o ERP), 
possibilitando que a gestão de materiais seja feita de maneira **inteligente e sem erros**.

Nosso objetivo principal é que, ao final de cada operação, o sistema registre **automaticamente os créditos gerados**, que são essenciais para a **sustentabilidade da operação**, servindo também como **fundamento para geração de relatórios** de consumo e análise 
de eficiência.

---

## Como iniciar a aplicação
<div id="aplicacao"></div>
Para iniciar a aplicação, siga os seguintes passos:

### Pré-requisitos

- **Java 17**: A aplicação foi desenvolvida utilizando o Java 17. Certifique-se de que o Java esteja instalado corretamente na sua máquina. Você pode verificar a versão do Java com o comando:

```bash
  java -version
```

Maven: A aplicação usa Maven para gerenciamento de dependências. Verifique se o Maven está instalado executando:

```bash
mvn -version
```
### Passos para rodar a aplicação:

#### 1. Clone o repositório.
Se você ainda não clonou o repositório, faça isso utilizando o Git:

```bash
https://github.com/pedroamcerto/aurawave-service
cd aurawave-service
```
#### 2. Compilar e Rodar a aplicação com Maven:

Para compilar e rodar a aplicação, basta executar o seguinte comando dentro do diretório do projeto:

```bash
mvn spring-boot:run
```

Isso irá compilar o projeto e iniciar o servidor Spring Boot.

A classe principal:

A classe principal da aplicação é HiveServiceApplication, localizada no pacote com.hive. Ela é a responsável por iniciar o contexto do Spring Boot e a execução da aplicação.


[AurawaveServiceApplication:](./src/main/java/com/aurawave/AurawaveServiceApplication.java)
```java
package com.aurawave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AurawaveServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AurawaveServiceApplication.class, args);
	}
}

```

#### 3. Acessando a aplicação:

Após rodar o comando mvn spring-boot:run, a aplicação estará acessível no navegador no endereço padrão:
```
http://localhost:8080
```
Caso queira rodar a aplicação em outra porta, é possível configurar isso no arquivo [application.yml](./src/main/resources/application.yml):
```properties
server:
    port=8081
```

#### 4. Verificando se a aplicação está funcionando:

Para verificar se a aplicação iniciou corretamente, acesse a URL mencionada acima e verifique se o serviço responde conforme esperado. Além disso, você pode verificar no terminal por mensagens de log indicando que o Spring Boot iniciou corretamente.

#### 5. Testando

Para testar abra e importe o arquivo [Collection](./Aurawave.postman_collection.json) para sua aplicação de chamadas REST. Recomendação: Postman.

---

## Requisitos Challenge
<div id="requisitos"></div>


 Criação do projeto Java com todas as classess, atributos e em seusdevidospacotes, que deve estar de acordo com o Diagrama de 
Classe e o projeto proposto.

- [Projeto](src)


Desenvolva no mínimo três métodos operacionais (diferentes dos getters & setters) que recebam algum parâmetro e retorne algum valor. Adicione Javadoc acima dos métodos para descrever a sua função, seus parâmetros e retorno. Desenvolva pelo menos um método com sobrecarga e outro com sobrescrita.

- [Sobrecarga](./src/main/java/com/aurawave/service/ItemService.java)
- [Sobrescrita](./src/main/java/com/aurawave/service)
- [Métodos diferentes](./src/main/java/com/aurawave/domain/interfaces/ServiceInterface.java)
- [Encapsulamento](./src/main/java/com/aurawave/domain/model)
- [Encapsulamento](./src/main/java/com/aurawave/dto)

**Obs:** A aplicação conta com JavaDoc nas principais classes e métodos, e também possui diversos outros métodos que contem requisitos como os descritos, fique a vontade para explorar o restante do projeto! :)

<br>

Implemente uma classe com o método main para o usuário informar os valores para os objetos criados e depois exiba os valores dos atributos. Pode utilizar o Scanner ou JOptionPane.

Conforme combinado com o professor, desenvolvemos a aplicação utilizando Spring. Para interagir com a aplicação, você pode realizar requisições HTTP. Além disso, disponibilizamos uma collection para facilitar os testes, evitando a necessidade de buscar manualmente os endpoints:  [Collection](./Aurawave.postman_collection.json).


<br>


---

A Aurawave nasce com um propósito claro: utilizar tecnologia avançada de sensores RFID e inteligência artificial para transformar a gestão de materiais e melhorar a eficiência logística na Dasa. Acreditamos que, ao unir inovação tecnológica e gestão inteligente de recursos, podemos criar um futuro mais sustentável e eficiente para a Dasa.

Nosso time de fundadores, Pedro Certo (556268), Fabiano Zague (555524), Lorran dos Santos (558982), Maria Clara (557478) e Vinícius Matereli (555200), está comprometido com a implementação de soluções que gerem impacto real e positivo para as operações da Dasa, garantindo uma gestão eficiente e precisa de materiais e reagentes.

Agradecemos pela sua contribuição e pelo interesse em nossa solução. Estamos entusiasmados com o futuro da Aurawave e as possibilidades de impactar positivamente a eficiência operacional da Dasa.

