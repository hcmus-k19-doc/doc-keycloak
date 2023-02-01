package edu.hcmus.doc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.hcmus.doc.model.CredentialsDto;
import edu.hcmus.doc.model.DocUser;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

@Slf4j
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
  public Optional<DocUser> getUserById(String id) {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/" + id, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByUsername(String username) {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/username/" + username, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByEmail(String email) {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/email/" + email, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public int getTotalUsers() {
    SimpleHttp http = SimpleHttp.doGet("http://host.docker.internal:8080/api/v1/users/total-users", httpClient);
    return http.asJson(int.class);
  }

  @SneakyThrows
  @Override
  public boolean validateCredentialsByUserId(String id, String password) {
    CredentialsDto credentialsDto = new CredentialsDto();
    credentialsDto.setPassword(password);
    SimpleHttp http = SimpleHttp
        .doPost("http://host.docker.internal:8080/api/v1/users/" + id + "/auth/validate-credentials", httpClient)
        .json(credentialsDto);
    return http.asJson(boolean.class);
  }
}
