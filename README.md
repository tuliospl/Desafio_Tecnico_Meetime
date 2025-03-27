# Integração HubSpot

##  Descrição do Projeto

Este projeto implementa uma integração com a plataforma HubSpot, oferecendo funcionalidade de autenticação, criação de contatos e recebimento de eventos.

## ✨ Funcionalidades

- Autenticação OAuth2 com HubSpot
- Criação de novos contatos
- Receber registro de eventos de contatos

##  Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Framework**: Spring Boot
- **Persistência**:
    - Spring Data JPA
    - MongoDB
- **Segurança**: Spring Security OAuth2
- **Cache**: Redis
- **Testes**:
    - MockK
    - JUnit 5

##  Requisitos Prévios

- JDK 17
- Maven 3.6.3+
- Redis
- MongoDB
- Ngrok
- Conta de desenvolvedor HubSpot

##  Instalação e Configuração

## 1. Clonar o Repositório

```csv
git clone [https://github.com/tuliospl/Desafio_Meetime](https://github.com/tuliospl/Desafio_Meetime)
```

## 2. Configuração do Ambiente

### Preparação HubSpot

1.  Crie uma conta de desenvolvedor no [HubSpot](https://developers.hubspot.com/).
2.  Crie um aplicativo com as seguintes configurações:
    - **URL de direcionamento:** `http://localhost:8080/auth/callback`
    - **Escopos necessários:**
        - `crm.objects.contacts.read`
        - `crm.objects.contacts.write`
3.  Anote o **Client ID** e o **Client Secret** do seu aplicativo.

### Configuração do Projeto

1.  Instale o [JDK 17](https://www.google.com/search?q=https://www.oracle.com/java/technologies/javase-jdk17-archive-downloads.html) ou superior.
2.  Instale o [Maven](https://maven.apache.org/install.html).
3.  Instale o [Ngrok](https://ngrok.com/download).
4.  Configure as credenciais do HubSpot no arquivo `src/main/resources/application.yaml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          hubspot:
            client-id: SEU_CLIENT_ID
            client-secret: SEU_CLIENT_SECRET
```

## 3. Executar o Projeto

```bash
# Iniciar containers do banco de dados (Redis e MongoDB)
  make up

# Iniciar a aplicação Spring Boot
  make run
```

## Autenticação

1.  Acesse a URL de autenticação no seu navegador: `http://localhost:8080/auth/authorize`
2.  Faça login com uma conta de teste do HubSpot.
3.  Conceda as permissões solicitadas pelo aplicativo.

## Exemplos de Uso

### Criação de Contato via cURL

```bash
curl --location 'http://localhost:8080/contact/create' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'firstname=Tulio' \
--data-urlencode 'lastname=Dantas' \
--data-urlencode 'email=tulio@gmail.com'
```

## Configuração de Webhook

1.  Inicie o Ngrok para expor sua aplicação localmente: `ngrok http 8080`
2.  Copie a URL gerada pelo Ngrok.
3.  No HubSpot, adicione um novo webhook:
    - **URL:** `{URL_NGROK}/webhook/contact-create`
    - Selecione os campos relevantes para o evento de criação de contato.
4.  Crie a assinatura do webhook.

## Testes

Para executar os testes da aplicação:

```bash
./mvnw test
```
