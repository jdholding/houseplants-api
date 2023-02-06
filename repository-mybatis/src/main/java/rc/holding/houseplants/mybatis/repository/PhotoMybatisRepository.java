package rc.holding.houseplants.mybatis.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import rc.holding.houseplants.domain.Photo;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.domain.search.tools.QueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;
import rc.holding.houseplants.repository.api.PhotoRepository;

@Repository
@AllArgsConstructor
@MapperNamespace("rc.holding.houseplants.mybatis.repository.mapper.photoMapper")
public class PhotoMybatisRepository extends AbstractMapper implements PhotoRepository{

    @Getter(AccessLevel.PROTECTED)
    private SqlSession sqlSession;

    @Override
    public Optional<Photo> findById(Integer id) {
        return selectOptionalOne("findById", id);
    }

    @Override
    public Page<Photo> findPageByParams(PagedQueryParams<Photo> params) {
        return selectPagedList("findAllByParams", params); 
    }

    @Override
    public Iterable<Photo> findAllByParams(QueryParams<Photo> params) {
        return selectList("findAllByParams", params);
    }
    
    @Override
    public Integer insert(Photo photo) {
        insert("insert", photo);
        return photo.getId();  
    }
}
