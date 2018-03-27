package controllers;

import models.User;
import play.cache.AsyncCacheApi;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.mvc.*;
import repositories.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class SignOutController extends Controller {

    private final DynamicForm dynamicForm;

    private final AsyncCacheApi asyncCacheApi;

    @Inject
    private UserRepository userRepository;

    @Inject
    public SignOutController(DynamicForm dynamicForm, AsyncCacheApi asyncCacheApi) {
        this.dynamicForm = dynamicForm;
        this.asyncCacheApi = asyncCacheApi;
    }

    public Result index() {
        return Results.ok(views.html.signout.index.render());
    }

    @Transactional
    public Result deleteUser() {
        DynamicForm requestData = dynamicForm.bindFromRequest();

        Long id = Long.valueOf(requestData.get("id"));
        User user = userRepository.findById(id);
        CompletionStage<Object> UUID = asyncCacheApi.get(user.UUID);

        return Results.ok(views.html.signup.top.render());
    }

}
