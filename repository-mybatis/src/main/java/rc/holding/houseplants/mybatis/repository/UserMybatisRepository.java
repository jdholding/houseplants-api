package rc.holding.houseplants.mybatis.repository;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.repository.api.UserRepository;

@Repository
@AllArgsConstructor
public class UserMybatisRepository implements UserRepository {
  private final String namespace = "rc.holding.houseplants.mybatis.repository.mapper.userMapper.";
  private SqlSession sqlSession;

  @Override
  public List<User> findAll() {
    return sqlSession.selectList(namespace + "findAll");
  }

  @Override
  public Optional<User> findById(Integer id) {
    return Optional.ofNullable(sqlSession.selectOne(namespace + "findById", id));
  }

  @Override
  public User insert(User user) {
    Integer id = sqlSession.insert(namespace + "insert", user);
    return sqlSession.selectOne(namespace + "findById", id);
  }

  @Override
  public User update(User user) {
    sqlSession.update(namespace + "update", user);
    return sqlSession.selectOne(namespace + "findById", user.getId());
  }

  @Override
  public void delete(Integer id) {
    sqlSession.delete(namespace + "delete", id);
  }
}
