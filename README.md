# BoardcampJava

## Portuguese

Este é um sistema de gerenciamento de aluguéis de jogos de tabuleiro desenvolvido em Java.

### Descrição

O BoardcampJava é uma aplicação desenvolvida em Java que permite aos usuários gerenciar um catálogo de jogos de tabuleiro e seus respectivos aluguéis. Ele oferece uma interface intuitiva para registrar novos jogos, realizar aluguéis, consultar o histórico de aluguéis e realizar outras operações relacionadas a jogos de tabuleiro.

### Funcionalidades Principais

- **Cadastro de Jogos:** Os usuários podem adicionar novos jogos ao catálogo, incluindo informações como nome do jogo, descrição, ano de lançamento, entre outros.
  
- **Registro de Aluguéis:** Permite aos usuários alugar jogos disponíveis, especificando o jogo desejado e a data de retirada.
  
- **Consulta de Jogos Disponíveis:** Oferece uma funcionalidade para verificar quais jogos estão atualmente disponíveis para aluguel.
  
- **Histórico de Aluguéis:** Mantém um registro detalhado dos aluguéis realizados, incluindo informações como data de aluguel, data de devolução e custo total.

- **Gerenciamento de Clientes:** Permite o cadastro e gerenciamento de informações de clientes, como nome, telefone e endereço.

### Pré-requisitos

Antes de começar, verifique se você atende aos seguintes requisitos:

- JDK (Java Development Kit) instalado: O projeto foi desenvolvido com Java e requer a JDK para compilação e execução.
  
- IDE Java (por exemplo, IntelliJ IDEA, Eclipse): Recomenda-se uma IDE Java para facilitar o desenvolvimento e a execução do projeto.
  
- Git: É necessário ter o Git instalado para clonar o repositório.

### Instalação

Para clonar este repositório e executar o projeto localmente, siga estas etapas:

1. Clone este repositório usando o comando:

```bash
git clone https://github.com/DanBellini/BoardcampJava.git
```

2. Abra o projeto em sua IDE Java preferida.

3. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis de ambiente:

```bash
DB_URL=[your-database]
DB_USERNAME=[your-username]
DB_PASSWORD=[your-password]
```


4. Crie um banco de dados correspondente com as informações fornecidas nas variáveis de ambiente.

5. Execute o aplicativo a partir da classe principal `Main.java`.

### Testes

O BoardcampJava possui uma suíte de testes automatizados para garantir a qualidade do código. Os testes são implementados usando [JUnit](https://junit.org/junit5/) e podem ser encontrados no diretório `src/test/java`.

#### Executando os Testes

Para executar os testes, siga estas etapas:

1. Certifique-se de que o projeto está configurado corretamente em sua IDE ou ambiente de desenvolvimento.

2. Navegue até o diretório raiz do projeto.

3. Execute o seguinte comando no terminal para compilar o projeto e executar os testes:

```bash
mvn test
```

Este comando irá compilar o projeto, executar todos os testes automatizados e exibir os resultados no console.

#### Cobertura de Testes

Se você estiver usando um plugin de cobertura de testes com Maven, você pode gerar um relatório de cobertura de testes usando o seguinte comando:

```bash
mvn jacoco:report
```

Isso irá gerar um relatório de cobertura de testes que pode ser encontrado em `target/site/jacoco/index.html`. Abra esse arquivo em seu navegador para visualizar os detalhes da cobertura de testes do projeto.

Lembre-se de que é uma boa prática manter uma alta cobertura de testes para garantir a robustez e a qualidade do código.

### Implantação

O projeto BoardcampJava já está implantado e pode ser acessado em [boardcamp-java.onrender.com](https://boardcamp-java.onrender.com/). Sinta-se à vontade para visitar o site e explorar as funcionalidades oferecidas pelo projeto.

Se você encontrar algum problema durante o uso do site, não hesite em nos informar para que possamos corrigi-lo o mais rápido possível.


### Contato

Se você tiver alguma dúvida ou sugestão relacionada a este projeto, sinta-se à vontade para entrar em contato com o proprietário do repositório.

---

## English

This is a board game rental management system developed in Java.

### Description

BoardcampJava is a Java application that allows users to manage a catalog of board games and their respective rentals. It provides an intuitive interface for registering new games, making rentals, checking rental history, and performing other operations related to board games.

### Key Features

- **Game Registration:** Users can add new games to the catalog, including information such as game name, description, release year, among others.
  
- **Rental Registration:** Allows users to rent available games, specifying the desired game and pickup date.
  
- **Available Games Inquiry:** Provides functionality to check which games are currently available for rent.
  
- **Rental History:** Maintains a detailed record of rentals made, including information such as rental date, return date, and total cost.

- **Customer Management:** Allows for the registration and management of customer information, such as name, phone number, and address.

### Prerequisites

Before you begin, ensure you have met the following requirements:

- JDK (Java Development Kit) installed: The project was developed with Java and requires JDK for compilation and execution.
  
- Java IDE (e.g., IntelliJ IDEA, Eclipse): A Java IDE is recommended for easier development and execution of the project.
  
- Git: Git must be installed to clone the repository.

### Installation

To clone this repository and run the project locally, follow these steps:

1. Clone this repository using the command:
```bash
git clone https://github.com/DanBellini/BoardcampJava.git
```

2. Open the project in your preferred Java IDE.

3. Create a `.env` file in the project root with the following environment variables:

```bash
DB_URL=[your-database]
DB_USERNAME=[your-username]
DB_PASSWORD=[your-password]
```

4. Create a corresponding database with the information provided in the environment variables.

5. Run the application from the `Main.java` main class.

### Tests

BoardcampJava has a suite of automated tests to ensure code quality. The tests are implemented using [JUnit](https://junit.org/junit5/) and can be found in the `src/test/java` directory.

#### Running Tests

To run the tests, follow these steps:

1. Ensure the project is set up correctly in your IDE or development environment.

2. Navigate to the project's root directory.

3. Run the following command in the terminal to compile the project and run the tests:
```bash
mvn test
```

This command will compile the project, run all automated tests, and display the results in the console.

#### Test Coverage

If you're using a test coverage plugin with Maven, you can generate a test coverage report using the following command:

```bash
mvn jacoco:report
```


This will generate a test coverage report that can be found at `target/site/jacoco/index.html`. Open this file in your browser to view the details of the project's test coverage.

Remember that maintaining a high test coverage is a good practice to ensure code robustness and quality.

### Deployment

The BoardcampJava project is already deployed and can be accessed at [boardcamp-java.onrender.com](https://boardcamp-java.onrender.com/). Feel free to visit the site and explore the functionalities offered by the project.

If you encounter any issues while using the site, please don't hesitate to let us know so we can fix it as soon as possible.

### Contact

If you have any questions or suggestions related to this project, feel free to reach out to the repository owner.
