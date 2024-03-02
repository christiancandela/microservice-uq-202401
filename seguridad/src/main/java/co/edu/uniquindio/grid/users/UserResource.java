package co.edu.uniquindio.grid.users;

import co.edu.uniquindio.grid.users.dto.UserView;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Path("/users")
public class UserResource {

    private final UserRepository repository;

    @Inject
    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public User register(UserView userView) {
        return repository.save(userView.toUser());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Page<User> findAll(@QueryParam("page") Integer page, @QueryParam("per_page") Integer size) {
        if( page == null ){
            page = 0;
        }
        if( size == null ){
            size = 10;
        }
        return repository.findAll(PageRequest.of(page,size));
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public User find(@PathParam("username") String username) {
        return repository.findById(username).orElseThrow(()->new WebApplicationException("Recurso no enconrado", Response.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public User delete(@PathParam("username") String username) {
        var user = find(username);
        repository.delete( user );
        return user;
    }

    @PUT
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "user"})
    public User update(@PathParam("username") String username,UserView userView) {
        var user = find(username);
        user.setPassword(userView.password());
        return repository.save(user);
    }
}
