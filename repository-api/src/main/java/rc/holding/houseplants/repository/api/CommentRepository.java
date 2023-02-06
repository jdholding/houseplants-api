package rc.holding.houseplants.repository.api;

import rc.holding.houseplants.repository.tools.PagedReadRepository;
import rc.holding.houseplants.domain.Comment;

public interface CommentRepository extends PagedReadRepository<Comment, Integer> {

    Integer insert(Comment comment);

    Comment update(Comment comment);
}
