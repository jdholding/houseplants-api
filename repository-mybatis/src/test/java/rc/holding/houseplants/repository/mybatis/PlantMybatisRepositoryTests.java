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
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.search.PlantParams;
import rc.holding.houseplants.domain.search.PlantParams.Field;
import rc.holding.houseplants.mybatis.repository.PlantMybatisRepository;
import rc.holding.houseplants.repository.mybatis.PlantMybatisRepositoryTests.Config;

@MybatisIntegrationTests
@Import(Config.class)
@Sql("insertPlantData.sql")
public class PlantMybatisRepositoryTests extends AbstractContainerBaseTest {

  @Configuration
  static class Config {
    @Autowired SqlSession sqlSession;

    @Bean
    PlantMybatisRepository repo() {
      return new PlantMybatisRepository(sqlSession);
    }
  }

  @Autowired private PlantMybatisRepository repo;

  private static Map<String, Plant> mocks;

  @BeforeAll
  static void init() {
    var rubber =
        new Plant(
            123,
            111,
            null,
            23,
            "Moraceae",
            "Ficus",
            "Ficus elastica",
            "Rubber tree",
            OffsetDateTime.of(2023, 01, 13, 11, 29, 17, 498000000, ZoneOffset.UTC));
    var fiddle =
        new Plant(
            124,
            112,
            null,
            23,
            "Moraceae",
            "Ficus",
            "Ficus lyrata",
            "Fiddle-leaf fig",
            OffsetDateTime.of(2023, 01, 23, 11, 29, 17, 498000000, ZoneOffset.UTC));
    var aloe =
        new Plant(
            125,
            121,
            null,
            23,
            "Asphodelaceae",
            "Aloe",
            "Aloe vera",
            null,
            OffsetDateTime.of(2023, 02, 13, 11, 29, 17, 498000000, ZoneOffset.UTC));
    var jade =
        new Plant(
            133,
            116,
            null,
            21,
            "Crassulaceae",
            "Crassula",
            "Crassula ovata",
            "Jade plant",
            OffsetDateTime.of(2023, 02, 03, 11, 29, 17, 498000000, ZoneOffset.UTC));
    var money =
        new Plant(
            134,
            444,
            null,
            21,
            "Malvaceae",
            "Pachira",
            "Pachira aquatica",
            "Money tree",
            OffsetDateTime.of(2023, 04, 13, 10, 29, 17, 498000000, ZoneOffset.UTC));
    var cole =
        new Plant(
            136,
            777,
            null,
            21,
            "Lamiaceae",
            "Coleus",
            "Coleus scutellarioides",
            "Coleus cultivar",
            OffsetDateTime.of(2022, 01, 13, 11, 29, 17, 498000000, ZoneOffset.UTC));
    var coleBaby =
        new Plant(
            631,
            777,
            136,
            12,
            "Lamiaceae",
            "Coleus",
            "Coleus scutellarioides",
            "Coleus cultivar",
            OffsetDateTime.of(2022, 11, 13, 11, 29, 17, 498000000, ZoneOffset.UTC));

    mocks =
        Map.of(
            "rubber",
            rubber,
            "fiddle",
            fiddle,
            "aloe",
            aloe,
            "jade",
            jade,
            "money",
            money,
            "cole",
            cole,
            "coleBaby",
            coleBaby);
  }

  @Test
  @DisplayName("findById returns the plant with the matching id")
  public void findByIdFound() {
    assertThat(repo.findById(133).get()).isEqualTo(mocks.get("jade"));
  }

  @Test
  @DisplayName("findById returns an empty resource when the id does not exist")
  public void findByIdNotFound() {
    assertThat(repo.findById(666)).isEqualTo(Optional.empty());
  }

  @Test
  @DisplayName(
      "findByParams sorted date created descending returns the expected results in the correct order")
  public void findByParamsSortDateDesc() {
    var params = PlantParams.builder().page(0).size(10).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("money"),
            mocks.get("aloe"),
            mocks.get("jade"),
            mocks.get("fiddle"),
            mocks.get("rubber"),
            mocks.get("coleBaby"),
            mocks.get("cole"));
  }

  @Test
  @DisplayName(
      "findByParams sorted by userId desc and plantId asc returns the expected results in the correct order")
  public void findByParamsMultipleSorters() {
    var params =
        PlantParams.builder()
            .page(0)
            .size(10)
            .sorter(Field.userId.asc())
            .sorter(Field.dateCreated.desc())
            .build();
    assertThat(repo.findPageByParams(params).getContent())
        .containsExactly(
            mocks.get("coleBaby"),
            mocks.get("money"),
            mocks.get("jade"),
            mocks.get("cole"),
            mocks.get("aloe"),
            mocks.get("fiddle"),
            mocks.get("rubber"));
  }

  @Test
  @DisplayName("findByParams with userId param returns the expected results")
  public void findByParamsUserId() {
    var params = PlantParams.builder().page(0).size(10).userId(21).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent()).containsExactly(mocks.get("money"), mocks.get("jade"), mocks.get("cole"));
  }

  @Test
  @DisplayName("test pagination on findPageByParams")
  public void testingPagination() {
    var params = PlantParams.builder().page(2).size(2).sorter(Field.DEFAULT_SORTER).build();
    assertThat(repo.findPageByParams(params).getContent()).containsExactly(mocks.get("rubber"),
        mocks.get("coleBaby"));
  }
}
