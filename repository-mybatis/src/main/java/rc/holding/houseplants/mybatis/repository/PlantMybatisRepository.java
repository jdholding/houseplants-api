package rc.holding.houseplants.mybatis.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;
import rc.holding.houseplants.repository.api.PlantRepository;

@Repository
@AllArgsConstructor
@MapperNamespace("rc.holding.houseplants.mybatis.repository.mapper.plantMapper")
public class PlantMybatisRepository extends AbstractMapper implements PlantRepository {

    @Getter(AccessLevel.PROTECTED)
    private SqlSession sqlSession;
    
    @Override
    public Optional<Plant> findById(Integer id) {
        return selectOptionalOne("findById", id);
    }

    @Override
    public Iterable<Plant> findAllByParams(QueryParams<Plant> params) {
        return selectList("findAllByParams", params);
    }

    @Override
    public Page<Plant> findPageByParams(PagedQueryParams<Plant> params) {
        return selectPagedList("findAllByParams", params); 
    }

    @Override
    public Integer insert(Plant plant) {
       insert("insert", plant);
       return plant.getId();  
    }

    @Override
    public Plant update(Plant plant) {
        update("update", plant);
        return selectOne("findById", plant.getId());  
    }
}
