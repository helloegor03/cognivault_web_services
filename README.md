Cognivault — это образовательная и контентная платформа, построенная на микросервисной архитектуре.

Авторизация реализована с помощью JWT токенов, которые хранят в себе роль и юзернейм пользователя. 
Для того, чтобы пройти авторизацию нужно пройти через MaiLService и ввести код полученный на почту.
<img width="1280" height="690" alt="image" src="https://github.com/user-attachments/assets/3c04d1d7-c8b6-49fe-abf6-4a250c834fe4" />
<img width="1280" height="341" alt="image" src="https://github.com/user-attachments/assets/341ca781-4cf1-4d97-a8a8-e43027544086" />

Создать пост может только пользователь с ролью "ROLE_ADMIN"
После авторизации пользователь роль можно выдать с помощью SQL запроса
UPDATE cogniusers
SET role = 'ROLE_ADMIN'
WHERE id = 1;
<img width="1280" height="695" alt="image" src="https://github.com/user-attachments/assets/af01ce2f-62dd-4479-85f4-dd09ce2954be" />
<img width="1280" height="691" alt="image" src="https://github.com/user-attachments/assets/9faeda69-124d-401f-905b-d866539302ec" />

Чтобы не пропустить новый пост на сайте, можно оформить подписку через почту и получать уведомления
Информация передаётся из post-service в subscribers-service через брокер сообщений и вызывается метод для отправки уведомления
<img width="1280" height="160" alt="image" src="https://github.com/user-attachments/assets/740dda4f-3d4e-408b-ba75-9de6832723b3" />

🛠️ Технологии

Java 17

Spring Boot 3.5.5

Spring Data JPA / Hibernate

Spring Security + JWT

Spring Kafka

Spring Data Redis (Lettuce + Jedis)

PostgreSQL / MySQL

Cloudinary (для изображений)

Docker + Docker Compose

Kafka

Redis

Zipkin



