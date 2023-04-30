package rc.holding.houseplants.repository.api;

import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.repository.tools.PagedReadRepository;

public interface CommentRepository extends PagedReadRepository<Comment, Integer> {

  Integer insert(Comment comment);

  Comment update(Comment comment);
}
