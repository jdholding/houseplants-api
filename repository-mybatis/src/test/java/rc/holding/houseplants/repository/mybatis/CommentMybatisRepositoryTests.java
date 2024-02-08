package rc.holding.houseplants.repository.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.search.CommentParams;
import rc.holding.houseplants.domain.search.CommentParams.Field;
import rc.holding.houseplants.mybatis.repository.CommentMybatisRepository;
import rc.holding.houseplants.repository.mybatis.CommentMybatisRepositoryTests.Config;

@MybatisIntegrationTests
@Import(Config.class)
@Sql("insertCommentData.sql")
public class CommentMybatisRepositoryTests extends AbstractContainerBaseTest {

  @Configuration
  static class Config {
    @Autowired SqlSession sqlSession;

    @Bean
    CommentMybatisRepository repo() {
      return new CommentMybatisRepository(sqlSession);
    }
  }

  @Autowired private CommentMybatisRepository repo;

  private static Map<String, Comment> mocks;

  @BeforeAll
  static void init() {
    var light =
        new Comment(
            1,
            123,
            69,
            OffsetDateTime.of(2023, 1, 23, 12, 29, 17, 498000000, ZoneOffset.UTC),
            "Not enough light",
            false);
    var wow =
        new Comment(
            2,
            124,
            21,
            OffsetDateTime.of(2023, 4, 23, 12, 29, 17, 498000000, ZoneOffset.UTC),
            "Wow! Looks great!",
            false);
    var repot =
        new Comment(
            3,
            123,
            21,
            OffsetDateTime.of(2023, 3, 23, 12, 29, 17, 498000000, ZoneOffset.UTC),
            "Needs to be re-potted",
            false);
    var trash =
        new Comment(
            4,
            134,
            23,
            OffsetDateTime.of(2023, 4, 23, 12, 21, 17, 498000000, ZoneOffset.UTC),
            "Your plant looks like trash and you are a bad person",
            true);
    var cuttings =
        new Comment(
            5,
            136,
            12,
            OffsetDateTime.of(2022, 8, 13, 12, 35, 17, 498000000, ZoneOffset.UTC),
            "Can I take some cuttings?",
            false);

    mocks =
        Map.of("light", light, "wow", wow, "repot", repot, "trash", trash, "cuttings", cuttings);
  }

  @Test
  @DisplayName("findById returns the comment with the matching id")
  public void findByIdExists() {
    assertThat(repo.findById(3).get()).isEqualTo(mocks.get("repot"));
  }

  @Test
  @DisplayName("findById returns empty when id does not exist")
  public void findByIdNotFound() {
    assertThat(repo.findById(36)).isEqualTo(Optional.empty());
  }

  @Test
  @DisplayName("find by params with default sorter returns expected results in the correct order")
  public void findByParamsDefaultSorter() {
    var params = CommentParams.builder().page(0).size(10).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("wow"),
            mocks.get("trash"),
            mocks.get("repot"),
            mocks.get("light"),
            mocks.get("cuttings"));
  }

  @Test
  @DisplayName("findByParams sorted returns expected results in the correct order")
  public void findByParamsPlantIdSorter() {
    var params =
        CommentParams.builder()
            .page(0)
            .size(10)
            .sorter(Field.plantId.asc())
            .sorter(Field.userId.desc())
            .build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("light"),
            mocks.get("repot"),
            mocks.get("wow"),
            mocks.get("trash"),
            mocks.get("cuttings"));
  }

  @Test
  @DisplayName("findByParams with search params returns the expected results in the correct order")
  public void findByParamsWithParams() {
    var params = CommentParams.builder().page(0).size(10).plantId(123).userId(69).build();
    assertThat(repo.findPageByParams(params).getContent()).containsExactly(mocks.get("light"));
  }

  @Test
  @DisplayName("findByParams with pagination returns the expected results in the correct order")
  public void paginationTest() {
    var params = CommentParams.builder().page(2).size(1).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent()).containsExactly(mocks.get("repot"));
  }
}
