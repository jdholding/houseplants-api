package rc.holding.houseplants.mybatis.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.ActionType;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;
import rc.holding.houseplants.repository.api.ActionRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@MapperNamespace("rc.holding.houseplants.mybatis.repository.mapper.actionMapper")
public class ActionMybatisRepository extends AbstractMapper implements ActionRepository {

    @Getter(AccessLevel.PROTECTED)
    private SqlSession sqlSession;

    @Override
    public Optional<Action> findById(Integer id) {
        return selectOptionalOne("findById", id);
    }

    @Override
    public Page<Action> findPageByParams(PagedQueryParams<Action> params) {
        return selectPagedList("findAllByParams", params);
    }

    @Override
    public Iterable<Action> findAllByParams(QueryParams<Action> params) {
        return selectList("findAllByParams", params);
    }

    @Override
    public Integer insert(Action action) {
        insert("insert", action);
        return action.getId();
    }

    @Override
    public Action update(Action action) {
        update("update", action);
        return selectOne("findById", action.getId());
    }

    @Override
    public List<ActionType> findAllTypes() {
        return selectList("findAllTypes", null);
    }
}
