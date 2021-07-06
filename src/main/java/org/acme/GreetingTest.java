package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/hello")
public class GreetingTest {

    List<String> hellos = new ArrayList();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.ok(hellos).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer helloCounts() {
        return hellos.size();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/addGreeting")
    public Response addGreeting(String greeting) {
        hellos.add(greeting);
        return Response.ok(hellos).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{toUpdate}")
    public Response update(@PathParam("toUpdate") String greetingToUpdate, @QueryParam("hello") String updateHello) {
        hellos = hellos.stream().map(hello -> {
            if (hello.equals(greetingToUpdate)) {
                return updateHello;
            }
            return hello;
        }).collect(Collectors.toList());
        return Response.ok(hellos).build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{toDelete}")
    public Response deleteGreeting(@PathParam("toDelete") String deleteGreeting) {
        boolean remove = hellos.remove(deleteGreeting);
        return remove ? Response.noContent().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}