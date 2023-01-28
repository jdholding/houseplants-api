package rc.holding.houseplants.mybatis.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.repository.api.PhotoRepository;

@Repository
@AllArgsConstructor
public class PhotoMybatisRepository implements PhotoRepository{

    private final String namespace = "rc.holding.houseplants.mybatis.repository.mapper.photoMapper.";
    private SqlSession sqlSession;

    @Override
    public List<Photo> findAll() {
        return sqlSession.selectList(namespace + "findAll"); 
    }

    @Override
    public Optional<Photo> findById(Integer id) {
        return Optional.ofNullable(sqlSession.selectOne(namespace + "findById", id));
    }

    @Override
    public Photo insert(Photo photo) {
        var id = sqlSession.insert(namespace + "insert", photo);
        return sqlSession.selectOne(namespace + "findById", id);  
    }
}
