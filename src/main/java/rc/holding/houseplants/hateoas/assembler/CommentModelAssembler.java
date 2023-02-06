package rc.holding.houseplants.hateoas.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import rc.holding.houseplants.contoller.CommentController;
import rc.holding.houseplants.domain.Comment;
import rc.holding.houseplants.domain.hateoas.api.CommentModel;


public class CommentModelAssembler extends RepresentationModelAssemblerSupport<Comment, CommentModel> {

    public CommentModelAssembler () { super(CommentController.class, CommentModel.class); }

    @Override
    protected CommentModel instantiateModel(Comment comment) { return new CommentModel(comment); }

    @Override
    public CommentModel toModel(Comment comment) { return createModelWithId(comment.getId(), comment); }
}
