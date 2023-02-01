package edu.hcmus.doc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.hcmus.doc.model.DocUser;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

public class DocClientSimpleHttp implements DocUserClient {

  private final CloseableHttpClient httpClient;

  public DocClientSimpleHttp(KeycloakSession session) {
    this.httpClient = session.getProvider(HttpClientProvider.class).getHttpClient();
  }

  @SneakyThrows
  @Override
  public List<DocUser> getUsers() {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users", httpClient);
    return http.asJson(new TypeReference<>() {});
  }

  @SneakyThrows
  @Override
  public DocUser getUserById(String id) {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/" + id, httpClient);
    return http.asJson(DocUser.class);
  }

  @SneakyThrows
  @Override
  public DocUser getUserByUsername(String username) {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/username/" + username, httpClient);
    return http.asJson(DocUser.class);
  }
}
