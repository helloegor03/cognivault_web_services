# 🧠 Cognivault  

**Cognivault** — is an intelligent educational and content platform built using a microservice architecture.
The project demonstrates the application of modern technologies — from distributed systems and message brokers to caching and asynchronous event processing.

<img width="1280" height="695" alt="image" src="https://github.com/user-attachments/assets/5e0f76eb-dea9-490e-a1bf-bc96900da44b" />

<img width="866" height="584" alt="image" src="https://github.com/user-attachments/assets/1361f5ac-a21b-48a5-941e-236fbd4111df" />

---

## 🔐 Authorization and Security

- Authentication is implemented using **JWT**.  
- The token contains the user’s role and username.
- Login is handled through the **Mail Service**:  the user receives a one-time code via email and confirms their registration with it. 

<img width="1280" height="689" alt="image" src="https://github.com/user-attachments/assets/abe40684-5af0-4247-8ebb-9741c3f44c26" />  

### 📑 Auth endpoints  

| Method | Endpoint        | Description                    | Access     |
|--------|----------------|---------------------------------|------------|
| POST   | `/auth/register` | Register a new user          | `PermitAll` |
| POST   | `/auth/login`    | Authenticate a user          | `PermitAll` |
| POST   | `/auth/verify`   | Verify the email code        | `PermitAll` |
| POST   | `/auth/resend`   | Resend the verification code | `PermitAll` |

> 🛠️ You can assign an administrator role directly with an SQL query:
> ```sql
> UPDATE cogniusers 
> SET role = 'ROLE_ADMIN' 
> WHERE id = 1;
> ```

<img width="1280" height="693" alt="image" src="https://github.com/user-attachments/assets/33182b96-2cd6-4584-bc83-912c86c73436" />

Images are stored in Cloudinary.

Posts are cached using Redis.

## post
| Method | Endpoint           | Description                     | Access 
|--------|-------------------|----------------------------------|----------------|
| POST   | `/posts/`         | Create a new post                | ROLE_ADMIN       | 
| GET    | `/posts/`         | Get all posts                    | ROLE_USER        | 
| GET    | `/posts/{id}`     | Get post by ID                   | ROLE_USER        | 
| DELETE | `/posts/{id}`     | Delete post by ID                | ROLE_ADMIN       | 

## sub
| Method | Endpoint                 | Description                            | Access         |
|--------|-------------------------|-----------------------------------------|----------------|
| POST   | `/subscribers/subscribe`| Subscribe to the newsletter             | PermitAll      | 
| GET    | `/subscribers/`         | View all newsletter subscribers         | PermitAll      | 

<img width="1280" height="160" alt="image" src="https://github.com/user-attachments/assets/33c00b27-3250-4f86-9504-de23a625c253" />

## 📬 Subscriptions and Notifications

To ensure users don’t miss new posts, Cognivault includes an email subscription system.

🔄 Workflow:
1. A user subscribes to the newsletter via Subscribers Service.**.  
2. When a new post is created, **Post Service** publishes an event to **Kafka**.  
3. **Subscribers Service** receives the event and sends email notifications to all subscribers.  

📡 The system operates asynchronously and scales easily — services don’t depend on each other directly.

---

## 🛠️ Tech Stack

The project uses a modern technology stack:

- ☕ **Java 17**
- 🚀 **Spring Boot 3.5.5**
- 🗄️ **Spring Data JPA / Hibernate**
- 🔐 **Spring Security + JWT**
- 📡 **Spring Kafka**
- ⚡ **Spring Data Redis (Lettuce + Jedis)**
- 🐘 **PostgreSQL / MySQL**
- ☁️ **Cloudinary** (хранение изображений)
- 🐳 **Docker + Docker Compose**
- 🧠 **Redis** (кэширование)
- 📨 **Kafka** (межсервисное взаимодействие)
- 🔍 **Zipkin** (трассировка запросов)

---

## 🌍 Architecture

Microservices communicate through different mechanisms:

- 🔹 **REST API** — for synchronous requests 
- 🔹 **Kafka** — for asynchronous events
- 🔹 **Redis** — for caching and fast data retrieval

Each service is isolated and has its own database
(the Database per Service approach), which improves fault tolerance and scalability.


🔧 Running the Project

1️⃣ Configure the database:
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver
```


2️⃣ Configure Cloudinary:
```yaml
cloudinary:
  cloud_name: ${YOUR_CLOUDINARY_NAME}
  api_key: ${YOUR_CLOUDINARY_API_KEY}
  api_secret: ${YOUR_SECRET}
```

3️⃣ Configure Mail Service (Gmail app password):
```yaml
mail:
  host: smtp.gmail.com
  port: 587
  username: ${SUPPORT_MAIL}
  password: ${APP_PASSWORD}
  properties:
    mail:
      smtp:
        auth: true
        starttls:
          enable: true
```

4️⃣ Start Docker:
```yaml
docker-compose up -d
```

5️⃣ Build and run microservices:
```yaml
mvn clean install
mvn spring-boot:run
```

6️⃣ Test the API using Postman or run the frontend service:
```yaml
npm start
```

🎯 TODO
Add a comment service.
Add an API Gateway.
Move configurations to a separate service.
