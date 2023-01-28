package rc.holding.houseplants.mybatis.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.repository.api.PlantRepository;

@Repository
public class PlantMybatisRepository implements PlantRepository {

    private final String namespace = "rc.holding.houseplants.mybatis.repository.mapper.plantMapper.";
    private SqlSession sqlSession;
    
    public PlantMybatisRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession; 
    }
    
    @Override
    public List<Plant> findAllPlants() {
        return sqlSession.selectList(namespace + "findAllPlants");
    }

    @Override
    public Optional<Plant> findById(Integer id) {
        return Optional.ofNullable(sqlSession.selectOne(namespace + "findById", id)); 
    }

    @Override
    public Plant insert(Plant plant) {
        Integer id = sqlSession.insert(namespace + "insert", plant); 
        return sqlSession.selectOne(namespace + "findById", id); 
    }

    @Override
    public Plant update(Plant plant) {
        sqlSession.update(namespace + "update", plant);
        return sqlSession.selectOne(namespace + "findById", plant.getId());  
    }
}
