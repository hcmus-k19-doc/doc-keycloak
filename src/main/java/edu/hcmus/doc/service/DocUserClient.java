package edu.hcmus.doc.service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import edu.hcmus.doc.model.DocUser;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface DocUserClient {

  @GET
  List<DocUser> getUsers();

  @GET
  @Path("/{id}")
  DocUser getUserById(@PathParam("id") String id);

  @GET
  @Path("/username/{username}")
  DocUser getUserByUsername(@PathParam("username") String username);
}
