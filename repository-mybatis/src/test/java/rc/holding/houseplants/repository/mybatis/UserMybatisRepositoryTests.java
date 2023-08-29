package rc.holding.houseplants.repository.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.search.UserParams;
import rc.holding.houseplants.domain.search.UserParams.Field;
import rc.holding.houseplants.mybatis.repository.UserMybatisRepository;
import rc.holding.houseplants.repository.mybatis.UserMybatisRepositoryTests.Config;

@MybatisIntegrationTests
@Import(Config.class)
@Sql("insertUserData.sql")
public class UserMybatisRepositoryTests extends AbstractContainerBaseTest {

  @Configuration
  static class Config {
    @Autowired SqlSession sqlSession;

    @Bean
    UserMybatisRepository repo() {
      return new UserMybatisRepository(sqlSession);
    }
  }

  @Autowired private UserMybatisRepository repo;

  private static Map<String, User> mocks;

  @BeforeAll
  static void init() {
    var mike = new User(23, "Michael", "Jordan", "michael.jordan@email.com", "mj23");
    var jimmy = new User(21, "Jimmy", "Butler", "jimmy@email.com", "jimmyG");
    var kirk = new User(12, "Kirk", "Hinrich", "kirk@email.com", "captainKirk");
    var jimmer = new User(69, "Jimmer", "Fredette", "jimmer@email.com", "jimmer");

    mocks = Map.of("mike", mike, "jimmy", jimmy, "kirk", kirk, "jimmer", jimmer);
  }

  @Test
  @DisplayName("findById returns the expected user for the given id")
  public void findByIdFound() {
    assertThat(repo.findById(23).get()).isEqualTo(mocks.get("mike"));
  }

  @Test
  @DisplayName("findById returns empty when id does not exist")
  public void findByIdNotFound() {
    assertThat(repo.findById(45)).isEqualTo(Optional.empty());
  }

  @Test
  @DisplayName("findByParams sorted by username returns the expected results in the correct order")
  public void sortTest() {
    var params = UserParams.builder().page(0).size(10).sorter(Field.username.asc()).build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("kirk"), mocks.get("jimmer"), mocks.get("jimmy"), mocks.get("mike"));
  }

  @Test
  @DisplayName(
      "findByParams sorted by default sorter returns the expected results in the correct order")
  public void defaultSortTest() {
    var params = UserParams.builder().page(0).size(10).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("kirk"), mocks.get("jimmy"), mocks.get("mike"), mocks.get("jimmer"));
  }

  @Test
  @DisplayName("findByParams with username fragment param returns the expected results")
  public void findByParamsUsernameFragment() {
    var params =
        UserParams.builder()
            .usernameFragment("jimm")
            .page(0)
            .size(10)
            .sorter(Field.DEFAULT_SORTER)
            .build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(mocks.get("jimmy"), mocks.get("jimmer"));
  }

  @Test
  @DisplayName("Test size and pagination params return the expected results")
  public void testPagination() {
    var params = UserParams.builder().page(2).size(1).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent()).containsExactly(mocks.get("mike"));
  }
}
