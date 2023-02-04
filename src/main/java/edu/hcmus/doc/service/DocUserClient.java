package edu.hcmus.doc.service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import edu.hcmus.doc.model.dto.CredentialsDto;
import edu.hcmus.doc.model.DocUser;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface DocUserClient {

  @GET
  List<DocUser> getUsers(@QueryParam("query") String query);

  @GET
  List<DocUser> getUsers(@QueryParam("query") String query, @QueryParam("first") Integer first, @QueryParam("max") Integer max);

  @GET
  @Path("/{id}")
  Optional<DocUser> getUserById(@PathParam("id") String id);

  @GET
  @Path("/username/{username}")
  Optional<DocUser> getUserByUsername(@PathParam("username") String username);

  @GET
  @Path("/email/{email}")
  Optional<DocUser> getUserByEmail(@PathParam("email") String email);

  @GET
  @Path("/total-users")
  int getTotalUsers();

  @GET
  @Path("/{id}/auth/validate-credentials")
  @Produces(MediaType.APPLICATION_FORM_URLENCODED)
  boolean validateCredentialsByUserId(@PathParam("id") String id, CredentialsDto credentialsDto);
}
