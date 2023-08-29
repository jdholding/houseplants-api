package rc.holding.houseplants.mybatis.repository;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import rc.holding.houseplants.domain.User;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;
import rc.holding.houseplants.repository.api.UserRepository;

@Repository
@AllArgsConstructor
@MapperNamespace("rc.holding.houseplants.mybatis.repository.mapper.userMapper")
public class UserMybatisRepository extends AbstractMapper implements UserRepository {

  @Getter(AccessLevel.PROTECTED)
  private SqlSession sqlSession;

  @Override
  public Optional<User> findById(Integer id) {
    return selectOptionalOne("findById", id);
  }

  @Override
  public Iterable<User> findAllByParams(QueryParams<User> params) {
    return selectList("findAllByParams", params);
  }

  @Override
  public Page<User> findPageByParams(PagedQueryParams<User> params) {
    return selectPagedList("findAllByParams", params);
  }

  @Override
  public Integer insert(User user) {
    insert("insert", user);
    return user.getId();
  }

  @Override
  public User update(User user) {
    update("update", user);
    return selectOne("findById", user.getId());
  }

  @Override
  public void delete(Integer id) {
    delete("delete", id);
  }
}
