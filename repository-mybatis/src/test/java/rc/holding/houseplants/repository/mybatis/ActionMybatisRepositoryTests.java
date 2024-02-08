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
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.search.ActionParams;
import rc.holding.houseplants.domain.search.ActionParams.Field;
import rc.holding.houseplants.mybatis.repository.ActionMybatisRepository;
import rc.holding.houseplants.repository.mybatis.ActionMybatisRepositoryTests.Config;

@MybatisIntegrationTests
@Import(Config.class)
@Sql("insertActionData.sql")
public class ActionMybatisRepositoryTests extends AbstractContainerBaseTest {

  @Configuration
  static class Config {
    @Autowired SqlSession sqlSession;

    @Bean
    ActionMybatisRepository repo() {
      return new ActionMybatisRepository(sqlSession);
    }
  }

  @Autowired private ActionMybatisRepository repo;

  private static Map<String, Action> mocks;

  @BeforeAll
  static void init() {
    var aprilPrune =
        new Action(
            431,
            134,
            21,
            3,
            OffsetDateTime.of(2023, 04, 23, 12, 29, 17, 498000000, ZoneOffset.UTC),
            new ActionType(3, "Prune"));
    var mayFert =
        new Action(
            666,
            136,
            21,
            1,
            OffsetDateTime.of(2022, 05, 13, 12, 34, 17, 498000000, ZoneOffset.UTC),
            new ActionType(1, "Fertilize"));
    var octPrune =
        new Action(
            667,
            136,
            21,
            3,
            OffsetDateTime.of(2022, 10, 13, 12, 34, 17, 498000000, ZoneOffset.UTC),
            new ActionType(3, "Prune"));
    var repot =
        new Action(
            777,
            631,
            21,
            2,
            OffsetDateTime.of(2023, 03, 13, 12, 34, 17, 498000000, ZoneOffset.UTC),
            new ActionType(2, "Re-pot"));
    var marchFert =
        new Action(
            999,
            124,
            23,
            1,
            OffsetDateTime.of(2023, 03, 10, 12, 34, 17, 498000000, ZoneOffset.UTC),
            new ActionType(1, "Fertilize"));

    mocks =
        Map.of(
            "aprilPrune",
            aprilPrune,
            "mayFert",
            mayFert,
            "octPrune",
            octPrune,
            "repot",
            repot,
            "marchFert",
            marchFert);
  }

  @Test
  @DisplayName("findById returns the action with the matching id")
  public void findByIdExists() {
    assertThat(repo.findById(666).get()).isEqualTo(mocks.get("mayFert"));
  }

  @Test
  @DisplayName("findById returns empty when id does not exist")
  public void findByIdDoesNotExist() {
    assertThat(repo.findById(98)).isEqualTo(Optional.empty());
  }

  @Test
  @DisplayName("findByParams with default sorter returns the expected results in the correct order")
  public void findByParamsDefaultSorter() {
    var params = ActionParams.builder().page(0).size(10).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("aprilPrune"),
            mocks.get("repot"),
            mocks.get("marchFert"),
            mocks.get("octPrune"),
            mocks.get("mayFert"));
  }

  @Test
  @DisplayName(
      "findByParams sorted by userId and plantId returns the expected results in the correct order")
  public void findByParamsSorted() {
    var params =
        ActionParams.builder()
            .page(0)
            .size(10)
            .sorter(Field.userId.asc())
            .sorter(Field.plantId.desc())
            .sorter(Field.DEFAULT_SORTER)
            .build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("repot"),
            mocks.get("octPrune"),
            mocks.get("mayFert"),
            mocks.get("aprilPrune"),
            mocks.get("marchFert"));
  }

  @Test
  @DisplayName("findByParams with search params returns the expected results in the correct order")
  public void findBySearchParams() {
    var params =
        ActionParams.builder()
            .page(0)
            .size(10)
            .userId(21)
            .dateAfter(OffsetDateTime.of(2022, 06, 01, 12, 00, 00, 00, ZoneOffset.UTC))
            .dateBefore(OffsetDateTime.of(2023, 4, 22, 12, 00, 00, 00, ZoneOffset.UTC))
            .sorter(Field.DEFAULT_SORTER)
            .build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(mocks.get("repot"), mocks.get("octPrune"));
  }
}
