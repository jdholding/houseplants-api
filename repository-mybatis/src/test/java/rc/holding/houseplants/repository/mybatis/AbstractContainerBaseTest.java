package rc.holding.houseplants.repository.mybatis;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Config class for testcontainers to start/stop postgres container during mybatis integration
 * tests.
 */
public abstract class AbstractContainerBaseTest {
  static final JdbcDatabaseContainer<?> POSTGRES_CONTAINER;

  static {
    DockerImageName myImage =
        DockerImageName.parse("postgis/postgis:14-master").asCompatibleSubstituteFor("postgres");
    POSTGRES_CONTAINER = new PostgreSQLContainer<>(myImage).withInitScript("init.sql");
    POSTGRES_CONTAINER.start();
    System.setProperty("spring.datasource.url", POSTGRES_CONTAINER.getJdbcUrl());
    System.setProperty("spring.datasource.username", POSTGRES_CONTAINER.getUsername());
    System.setProperty("spring.datasource.password", POSTGRES_CONTAINER.getPassword());
    System.setProperty(
        "spring.datasource.driver-class-name", POSTGRES_CONTAINER.getDriverClassName());
  }
}
