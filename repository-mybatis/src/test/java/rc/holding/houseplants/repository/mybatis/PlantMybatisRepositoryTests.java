package rc.holding.houseplants.repository.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
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
            "Ficus  elastica",
            "Rubber tree",
            OffsetDateTime.of(2023, 01, 13, 12, 29, 17, 498000000, ZoneOffset.UTC));
    var fiddle =
        new Plant(
            124,
            112,
            null,
            23,
            "Moraceae",
            "Ficus",
            "Ficus  lyrata",
            "Fiddle-leaf fig",
            OffsetDateTime.of(2023, 01, 23, 12, 29, 17, 498, ZoneOffset.UTC));
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
            OffsetDateTime.of(2023, 02, 13, 12, 29, 17, 498, ZoneOffset.UTC));
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
            OffsetDateTime.of(2023, 04, 13, 12, 29, 17, 498, ZoneOffset.UTC));
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
            OffsetDateTime.of(2022, 01, 13, 12, 29, 17, 498, ZoneOffset.UTC));
    var coleBaby =
        new Plant(
            631,
            777,
            null,
            12,
            "Lamiaceae",
            "Coleus",
            "Coleus scutellarioides",
            "Coleus cultivar",
            OffsetDateTime.of(2022, 11, 13, 12, 29, 17, 498, ZoneOffset.UTC));

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
}
