# SOS - Backend

API REST desenvolvida com Spring Boot para o sistema de chamados de resgate de animais.

## Tecnologias

- **Spring Boot** 3.5.7
- **Java** 17
- **Spring Security** com JWT
- **Spring Data JPA**
- **PostgreSQL** / **H2** (desenvolvimento)
- **Maven**

## Funcionalidades

- Sistema de chamados para resgate de animais (cachorro, gato, outros)
- Autenticação e autorização com JWT
- Upload de imagens
- Gerenciamento de status dos chamados (Aberto, Atendido, Cancelado)
- API REST para administradores

## Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL (produção) ou H2 (desenvolvimento)

### Instalação

1. Clone o repositório
2. Instale as dependências:
```bash
./mvnw clean install
```

### Configuração do Banco de Dados

O projeto está configurado para usar H2 em memória por padrão. Para usar PostgreSQL, edite o arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Executando

### Modo Desenvolvimento

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## Estrutura do Projeto

- `controller/` - Controllers REST
- `model/` - Entidades JPA (Chamado, Admin)
- `repository/` - Repositórios JPA
- `service/` - Lógica de negócio
- `security/` - Configuração de segurança e JWT
- `config/` - Configurações gerais

## Endpoints Principais

- `/api/auth/login` - Autenticação de administrador
- `/api/chamados` - CRUD de chamados
- `/api/admin` - Gerenciamento de administradores

