package rc.holding.houseplants.mybatis.repository;

import static java.util.Collections.unmodifiableList;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import rc.holding.houseplants.domain.search.tools.Page;
import rc.holding.houseplants.domain.search.tools.PagedQueryParams;
import rc.holding.houseplants.repository.annotations.MapperNamespace;

public abstract class AbstractMapper {

  private final String mapperPackage;

  public AbstractMapper() {
    if (!this.getClass().isAnnotationPresent(MapperNamespace.class)) {
      throw new RuntimeException(
          "you must add the MapperNamespace annotation on class implementing AbstractMapper");
    }

    this.mapperPackage = this.getClass().getAnnotation(MapperNamespace.class).value();
  }

  protected abstract SqlSession getSqlSession();

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#selectOne(String)
   */
  protected <T> T selectOne(String statement) {
    return getSqlSession().selectOne(getFullname(statement));
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#selectOne(String, Object)
   */
  protected <T> T selectOne(String statement, Object params) {
    return getSqlSession().selectOne(getFullname(statement), params);
  }

  /**
   * Same as Mybatis {@link SqlSession#selectOne(String, Object)} except that result is wrapped in
   * an {@link Optional} and it handles mapper namespace for statement name.
   *
   * @see SqlSession#selectOne(String, Object)
   */
  protected <T> Optional<T> selectOptionalOne(String statement, Object params) {
    return Optional.ofNullable(getSqlSession().selectOne(getFullname(statement), params));
  }

  /**
   * Same as Mybatis {@link SqlSession#selectList(String, Object)} except that result is an
   * immutable list and it handles mapper namespace for statement name.
   *
   * @see SqlSession#selectList(String, Object)
   */
  protected <E> List<E> selectList(String statement, Object params) {
    return unmodifiableList(getSqlSession().selectList(getFullname(statement), params));
  }

  /**
   * Same as Mybatis {@link SqlSession#selectList(String, Object, RowBounds)} if page and size
   * params are set otherwise it calls {@link SqlSession#selectList(String, Object)}. Also returned
   * list is immutable and it handles mapper namespace for statement name.
   *
   * @see SqlSession#selectList(String, Object, RowBounds)
   * @see SqlSession#selectList(String, Object)
   */
  protected <E> List<E> selectList(String statement, PagedQueryParams<E> params) {
    List<E> results =
        params.getPage() != null & params.getSize() != null
            ? getSqlSession()
                .selectList(
                    getFullname(statement),
                    params,
                    new RowBounds(params.getOffset(), params.getSize()))
            : getSqlSession().selectList(getFullname(statement), params);
    return unmodifiableList(results);
  }

  /**
   * Returns {@link Page <E>} for given {@link PagedQueryParams} params. If you use Mybatis
   * PageHelper plugin with param <code>rowBoundsWithCount</code> set to true it will use it to get
   * page metadata. Otherwise you need to implement {@link #countByParams(PagedQueryParams)}.
   *
   * @see SqlSession#selectList(String, Object, RowBounds)
   */
  protected <E> Page<E> selectPagedList(String statement, PagedQueryParams<? super E> params) {
    int offset = params.getOffset();
    int size = params.getSize() == null ? RowBounds.NO_ROW_LIMIT : params.getSize();
    int page = params.getPage() == null ? 0 : params.getPage();

    List<E> results =
        getSqlSession().selectList(getFullname(statement), params, new RowBounds(offset, size));

    if (results instanceof com.github.pagehelper.Page) {
      com.github.pagehelper.Page<E> pageResults = (com.github.pagehelper.Page<E>) results;
      if (pageResults.isCount()) {
        return new Page<>(
            pageResults.getResult(),
            page,
            size,
            (int) pageResults.getTotal(),
            pageResults.getPages());
      }
    }

    return new Page<>(results, page, size, countByParams(params));
  }

  /**
   * Returns count of rows matching given params. Needs to be implemented on child classes that uses
   * the {@link AbstractMapper#selectPagedList(String, PagedQueryParams)} method to provides the
   * page metadata if mybatis PageHelper plugin with param <code>rowBoundsWithCount</code> is not
   * set.
   *
   * @see #selectPagedList(String, PagedQueryParams)
   */
  public <T> int countByParams(PagedQueryParams<T> params) {
    throw new UnsupportedOperationException(
        "you need to implement this method to use selectPagedList(...) without PageHelper mybatis plugin");
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#insert(String, Object)
   */
  public int insert(String statement, Object params) {
    return getSqlSession().insert(getFullname(statement), params);
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#insert(String)
   */
  public int insert(String statement) {
    return getSqlSession().insert(getFullname(statement));
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#update(String, Object)
   */
  public int update(String statement, Object params) {
    return getSqlSession().update(getFullname(statement), params);
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#update(String)
   */
  public int update(String statement) {
    return getSqlSession().update(getFullname(statement));
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#delete(String, Object)
   */
  public int delete(String statement, Object params) {
    return getSqlSession().delete(getFullname(statement), params);
  }

  /**
   * Same as Mybatis one but handles mapper namespace for statement name.
   *
   * @see SqlSession#delete(String)
   */
  public int delete(String statement) {
    return getSqlSession().delete(getFullname(statement));
  }

  private String getFullname(String statement) {
    return mapperPackage + "." + statement;
  }
}
