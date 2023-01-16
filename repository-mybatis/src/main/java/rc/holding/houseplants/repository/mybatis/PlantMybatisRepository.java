package rc.holding.houseplants.repository.mybatis; 

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import rc.holding.houseplants.domain.Plant;
import rc.holding.houseplants.repository.api.PlantRepository;

@Repository
public class PlantMybatisRepository implements PlantRepository {

    private final String namespace = "rc.holding.houseplants.repository.mybatis.mapper.plantMapper"; 
    private SqlSession sqlSession; 
    
    @Override
    public List<Plant> findAllPlants() {
        return sqlSession.selectList(namespace + "findAll"); 
    }
}