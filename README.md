# Food Delivery API

API REST para um sistema de delivery de comida, com autenticação via JWT, cadastro de lojas/produtos e fluxo de pedidos.

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- Spring Security (OAuth2 Resource Server / JWT)
- Banco de dados H2 (arquivo local)
- Lombok
- Maven

## Como rodar o projeto

Pré-requisito: Java 21 instalado.

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

O banco H2 é salvo em arquivo (`./data/fooddelivery`) e o console web fica disponível em:

```
http://localhost:8080/h2-console
```

### Configuração

As principais configurações estão em `src/main/resources/application.properties`:

- `jwt.secret`: segredo usado para assinar o token JWT (pode ser sobrescrito pela variável de ambiente `JWT_SECRET`).
- `jwt.expiration-seconds`: tempo de expiração do token (padrão: 3600s).
- `spring.jpa.hibernate.ddl-auto=update`: o schema do banco é atualizado automaticamente a partir das entidades.

## Autenticação

A API usa JWT. O fluxo é:

1. Criar uma conta em `POST /api/auth/register`.
2. Fazer login em `POST /api/auth/login` para receber o token.
3. Enviar o token nas próximas requisições no header:

```
Authorization: Bearer <token>
```

Existem dois papéis (`role`) de usuário:

- `CUSTOMER`: pode criar pedidos.
- `RESTAURANT`: ao se registrar, ganha automaticamente uma loja (`Store`); pode cadastrar produtos e atualizar o status dos pedidos da sua loja.

## Endpoints

### Auth (`/api/auth`) — públicos

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/auth/register` | Cria um usuário (`CUSTOMER` ou `RESTAURANT`) |
| POST | `/api/auth/login` | Autentica e retorna o token JWT |

### Stores (`/api/stores`) — autenticado

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/stores` | Lista todas as lojas |
| GET | `/api/stores/{id}` | Busca uma loja por id |

### Products (`/api/products`)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/api/products` | autenticado | Lista produtos (filtro opcional por `?storeId=`) |
| GET | `/api/products/{id}` | autenticado | Busca produto por id |
| POST | `/api/products` | `RESTAURANT` | Cria produto na loja do usuário autenticado |
| PUT | `/api/products/{id}` | `RESTAURANT` | Atualiza um produto da própria loja |

### Orders (`/api/orders`)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/api/orders` | `CUSTOMER` | Cria um novo pedido |
| GET | `/api/orders` | autenticado | Lista os pedidos do usuário autenticado |
| GET | `/api/orders/{id}` | autenticado | Busca um pedido por id |
| PATCH | `/api/orders/{id}/status` | `RESTAURANT` | Atualiza o status do pedido |

Status possíveis de um pedido (`OrderStatus`): `RECEBIDO`, `EM_PREPARO`, `SAIU_PARA_ENTREGA`, `ENTREGUE`, `CANCELADO`.

## Exemplo de uso

**1. Registrar um dono de restaurante**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "User",
    "email": "user@restaurante.com",
    "password": "senha123",
    "role": "RESTAURANT"
  }'
```

**2. Login**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@restaurante.com",
    "password": "senha123"
  }'
```

**3. Criar um produto (usando o token retornado no login)**

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pizza Margherita",
    "description": "Molho, muçarela e manjericão",
    "price": 39.90
  }'
```

## Testes

```bash
./mvnw test
```
