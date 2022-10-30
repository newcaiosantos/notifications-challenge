package notifications.manager.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

import static notifications.manager.util.TestUtils.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTests {

    protected static final MySQLContainer<?> mySql;
    protected static final RabbitMQContainer rabbitMQ;
    protected static ClientAndServer mockServer;

    static {
        mySql = new MySQLContainer<>(MYSQL_IMAGE);
        rabbitMQ = new RabbitMQContainer(RABBITMQ_IMAGE);
        mySql.start();
        rabbitMQ.start();
    }

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate http;

    @DynamicPropertySource
    static void applyProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySql::getJdbcUrl);
        registry.add("spring.datasource.username", mySql::getUsername);
        registry.add("spring.datasource.password", mySql::getPassword);

        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQ::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQ::getAdminPassword);
    }

    @BeforeAll
    static void startMockServer() {
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @AfterAll
    static void stopMockServer() {
        mockServer.stop();
    }
}
