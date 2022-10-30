## user-settings-ms
- Swagger: http://localhost:8080/swagger-ui/index.html

## notification-manager
- Swagger: http://localhost:8081/swagger-ui/index.html

## Infra
- RabbitMQ: http://localhost:15672/
- MongoDB: mongodb://root:root@localhost:27017/?authMechanism=DEFAULT
- MySQL: root@127.0.0.1:3306 (password:root)

## Tecnologias
Java, Testcontainers, RabbitMQ (Queue/DLQ), MongoDB, MySQL(Transaction/Lock), MockServer, SpringBoot

## Para subir localmente
- Requisitos: Docker deve estar rodando (utiliza Testcontainers)
- 1) Build de .jar's e imagens: ./build-jars-and-images.sh
- 2) docker-compose up