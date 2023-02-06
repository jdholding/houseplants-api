package rc.holding.houseplants.mybatis.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;
import rc.holding.houseplants.repository.api.CommentRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@MapperNamespace("rc.holding.houseplants.mybatis.repository.mapper.commentMapper")
public class CommentMybatisRepository extends AbstractMapper implements CommentRepository {
    @Getter(AccessLevel.PROTECTED)
    private SqlSession sqlSession;

    @Override
    public Optional<Comment> findById(Integer id) {
        return selectOptionalOne("findById", id);
    }

    @Override
    public Page<Comment> findPageByParams(PagedQueryParams<Comment> params) {
        return selectPagedList("findAllByParams", params);
    }

    @Override
    public Iterable<Comment> findAllByParams(QueryParams<Comment> params) {
        return selectList("findAllByParams", params);
    }

    @Override
    public Integer insert(Comment comment) {
        insert("insert", comment);
        return comment.getId();
    }

    @Override
    public Comment update(Comment comment) {
        update("update", comment);
        return selectOne("findById", comment.getId());
    }
}
