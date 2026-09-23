# API de Votação - Cooperativa

Esta é uma API RESTful desenvolvida para gerenciar pautas, sessões de votação e a contabilização de votos de associados. O sistema garante o controle de concorrência e integridade das regras de negócio, como limitação de tempo de sessão e prevenção de votos duplicados.

## 🚀 Tecnologias Utilizadas
* **Java / Spring Boot**
* **PostgreSQL** (Banco de Dados Relacional)
* **Docker & Docker Compose** (Conteinerização)
* **JUnit 5 & Mockito** (Testes Unitários)
* **Springdoc OpenAPI / Swagger** (Documentação da API)

---

## 🛠️ Instruções para Rodar a Aplicação

### Pré-requisitos
Para rodar o projeto localmente, certifique-se de ter instalado em sua máquina:
* **Java 17** (ou superior)
* **Gradle**
* **Docker** e **Docker Compose**

### Passos para Execução

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/DanielASantos/votacao.git](https://github.com/DanielASantos/votacao.git)
   cd votacao
2. **Suba a infraestrutura local (Banco de Dados):**
   Utilize o Docker Compose para iniciar o banco PostgreSQL e outros serviços de infraestrutura que estejam mapeados.
   ```bash
   docker-compose up -d
3.  **Inicie a aplicação Spring Boot:**
     Você pode rodar a aplicação diretamente pela sua IDE ou via linha de comando utilizando o Gradle:
    ```bash
    ./gradlew bootRun

 A aplicação iniciará e estará disponível localmente (por padrão na porta 8080).   

 ---

##  📚 Documentação da API (OpenAPI / Swagger)

Com a aplicação em execução, a documentação interativa e os contratos de integração podem ser acessados diretamente pelo navegador. Lá você pode visualizar os schemas dos DTOs e testar os endpoints (Pautas, Sessões e Votos):

* **Swagger UI** (Interface Interativa): http://localhost:8080/swagger-ui.html

* **OpenAPI JSON** (Contrato cru): http://localhost:8080/v3/api-docs

---

## 🏗️ Arquitetura e Motivações de Design

O desenvolvimento desta aplicação foi guiado por princípios robustos de engenharia de software para garantir facilidade de manutenção, alta testabilidade e prontidão para evolução.

### Princípios SOLID e Separação de Responsabilidades
A aplicação adota uma separação rigorosa entre a camada de apresentação (Controllers HTTP), a camada de regras de negócio (Services) e a camada de persistência (Repositories). A utilização ostensiva de DTOs (Data Transfer Objects) blinda o modelo de domínio, evitando o vazamento de entidades diretamente para a web e respeitando o Princípio da Responsabilidade Única (SRP).

### Influências de Domain-Driven Design (DDD)
O sistema foi modelado focando nas regras do domínio central da cooperativa. As regras que definem o tempo de vida de uma sessão de votação, as validações de expiração e o impedimento de multiplicidade de votos pertencem estritamente à camada de domínio e serviço. Esse isolamento mantém o modelo de negócio altamente coerente e imune a lógicas de banco de dados ou requisições HTTP.

### Versionamento Estruturado e Rastreabilidade
A manutenção do ciclo de vida da aplicação é assegurada através de práticas rigorosas de versionamento, garantindo consistência tanto no código-fonte como na infraestrutura de dados:

* **Controle de Código (GitHub e Conventional Commits):** O código-fonte é centralizado no GitHub e segue estritamente a especificação de *Conventional Commits* (utilizando prefixos como `feat:`, `fix:`, `refactor:`). Esta abordagem semântica mantém o histórico do repositório legível, estruturado e rastreável, facilitando a colaboração da equipa e a potencial automação de *changelogs* ou *releases*.
* **Evolução da Base de Dados (Flyway):** A estrutura do PostgreSQL é gerida através do *Flyway*. As migrações do esquema de dados são tratadas como código (em ficheiros como `V1__criar_tabela_pauta.sql`), assegurando que a base de dados evolui de forma determinística, previsível e segura em qualquer ambiente (desenvolvimento, testes ou produção), eliminando a necessidade de intervenções manuais propensas a erros.

### Inversão de Dependência (Integração Externa de CPF)
A comunicação com a API externa para validação do status do CPF foi desenhada com base no Princípio da Inversão de Dependência (o "D" do SOLID). 

O núcleo da aplicação (camada de domínio e serviços) não possui qualquer acoplamento com bibliotecas HTTP (*OpenFeign*) ou com a URL do provedor externo. Em vez disso, o domínio define uma interface de contrato, e a camada de infraestrutura implementa essa interface.

Essa abordagem traz duas vantagens críticas para a arquitetura:
* **Desacoplamento:** Se a cooperativa decidir trocar o provedor de validação de CPF amanhã, ou atualizar a tecnologia de requisições HTTP, nenhuma linha de código das regras de negócio precisará ser alterada.
* **Testabilidade Isolada:** Permite que o serviço de validação seja facilmente substituído por *mocks* durante os testes unitários, garantindo a execução rápida e confiável dos testes sem depender de conectividade de rede ou de APIs de terceiros.






































