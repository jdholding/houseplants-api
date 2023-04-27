package rc.holding.houseplants.hateoas.assembler;


import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import rc.holding.houseplants.contoller.ActionController;
import rc.holding.houseplants.domain.Action;
import rc.holding.houseplants.domain.hateoas.api.ActionModel;

public class ActionModelAssembler extends RepresentationModelAssemblerSupport<Action, ActionModel> {

    public ActionModelAssembler() { super(ActionController.class, ActionModel.class); }

    @Override
    protected ActionModel instantiateModel(Action action){ return new ActionModel(action); }
    @Override
    public ActionModel toModel(Action action) {
        return createModelWithId(action.getId(), action);
    }


}
