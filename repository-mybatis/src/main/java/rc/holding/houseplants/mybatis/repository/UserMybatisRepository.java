package rc.holding.houseplants.mybatis.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.repository.api.UserRepository;

@Repository
@AllArgsConstructor
public class UserMybatisRepository implements UserRepository{
    private final String namespace = "rc.holding.houseplants.mybatis.repository.mapper.userMapper.";
    private SqlSession sqlSession; 

    @Override
    public List<User> findAll() {
        return sqlSession.selectList(namespace + "findAll");
    }
    
    @Override
    public User insert(User user) {
        Integer id = sqlSession.insert(namespace + "insert", user); 
        return sqlSession.selectOne(namespace + "findById", id); 
    }
}
