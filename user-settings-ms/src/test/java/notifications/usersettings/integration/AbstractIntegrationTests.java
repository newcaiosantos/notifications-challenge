package notifications.usersettings.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import static notifications.usersettings.util.TestUtils.MONGODB_IMAGE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTests {

    protected static final MongoDBContainer mongoDb;

    static {
        mongoDb = new MongoDBContainer(MONGODB_IMAGE);
        mongoDb.start();
    }

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate http;

    @DynamicPropertySource
    static void applyProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDb::getReplicaSetUrl);
    }
}
