# Notifications Challenge (ML)

## Arquitetura
![alt text](https://github.com/newcaiosantos/notifications-challenge/blob/master/notifications-challenge.drawio.png?raw=true "desenho de arquitetura")
### Para o envio de notificações considerei que...

#### O envio deve ocorrer de forma assíncrona e resiliente:
- Para isto criei o serviço "Notification Sender" que tem o papel de ler uma fila e fazer os envios;
- Caso haja problemas com o serviço, nada é perdido, as notificações serão enviadas assim que o serviço for recuperado;
- Considerando possíveis falhas nos serviços externos idealizei os mecanismos de retry e circuit braker;
- Para cenários onde, por algum motivo, seja impossível enviar a notificação, a mensagem consumida da fila passa para uma DLQ;

#### Deve ser possível o agendamento de notificações:
- Para isto criei o serviço "Notification Manager" que através de uma base de dados e um scheduller permite que sejam criadas notificações para envio futuro;
- O scheduller roda de tempos em tempos (inicialmente adotado 10s) buscando por notificações que já devem ser enviadas e então as publica na fila de notificações;
- A leitura de notificações utiliza o recurso de lock do banco, permitindo que várias instâncias do serviço executem em paralelo sem problemas por concorrência;

#### Deve ser possível o envio instantâneo de notificações:
- Este recurso também é disponibilizado pelo serviço "Notification Manager";
- Ao contrário do agendamento que registra em base e posteriormente publica na fila, esta funcionalidade realiza a publicação direta na fila;
- Uma vez publicada, o serviço "Notification Sender" se encarrega do envio;

#### Deve ser possível realizar opt-in e opt-out do envio de notificações:
- Criei o serviço "User Settings" com esta responsabilidade;
- Ele disponibiliza endpoints com este propósito e registra em base de dados NoSQL/MongoDb;
- Foi adotado MongoDB pelo fato das características do domínio não necessitarem de recursos característicos de bases relacionais como transações, locks ou normalizações;
- Neste serviço é disponibilizado endpoint para consulta das configurações do usuário;
- Neste serviço também há endpoint para que sejam registradas dados do usuário para o envio de notificações (subscription);
- Este serviço é consultado pelo "Notification Manager" para verificar opt-in;
- Este serviço é consultado pelo "Notification Sender" para recuperar a subscription que permite o envio de web notifications;

### Observações
- Considerei que os serviços criados estão já em um contexto autenticado;
- Caso autenticação fosse um requisito do desafio, eu adotaria, por exemplo, JWT e Spring Security e extrairia as informações do usuário a partir do payload do token;
- O trecho do desenho que cita User e Internal Applications somente abstrai aplicações internas e interações do usuário que podem estimular as notificações e não representam necessariamente um único microserviço sendo acessado por um usuário;
- Há uma collection Insomnia "insomnia-collection.json" que pode ser importada para interações com os serviços;
- Com os serviços rodando, há documentação Swagger nos endpoints:
  - Serviço User Settings: http://localhost:8080/swagger-ui/index.html
  - Serviço Notification Manager: http://localhost:8081/swagger-ui/index.html
- Para acesso direto às tecnologias que dão suporte aos serviços, estes são os acessos:
  - RabbitMQ: http://localhost:15672/ (interface gráfica)
  - MongoDB: mongodb://root:root@localhost:27017/?authMechanism=DEFAULT
  - MySQL: root@127.0.0.1:3306 (password:root)
- Escrevi testes que cobrem em média 90% do código das aplicações
- Tecnologias utilizadas
  - Java/Spring
  - Testcontainers
  - MockServer
  - RabbitMQ (Queue/DLQ)
  - MongoDB
  - MySQL(Transaction/Lock)


## Rodando as aplicações
### Requisitos
Docker deve estar rodando (utiliza Testcontainers)
### Build
Para build de .jar's e imagens, executar:
```
./build-jars-and-images.sh
```
### Boot
```
docker-compose up
```
### Testes
Para ver um ciclo completo do funcionamento:
- Importar collection no Insomnia;
- Acessar http://localhost:8083 com um navegador;
  - Chrome preferencialmente
- Habilitar as notificações;
- Pegar os dados de subscription gerados e enviar como payload para o serviço User Settings;
- Realizar opt-in de notificações no serviço User Settings;
- Realizar o envio (ou agendamento) de notificação no serviço Notification Manager;
- Assim que o serviço Notification Sender enviar sua notificação, o navegador a mostrará para você.