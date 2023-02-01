package edu.hcmus.doc.service;

import static edu.hcmus.doc.common.Constants.BASE_URL;

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
  public List<DocUser> getUsers(String query) {
    return getUsers(query, 0, 10);
  }

  @SneakyThrows
  @Override
  public List<DocUser> getUsers(String query, Integer first, Integer max) {
    SimpleHttp http = SimpleHttp
        .doGet(BASE_URL, httpClient)
        .param("query", query)
        .param("first", String.valueOf(first))
        .param("max", String.valueOf(max));
    return http.asJson(new TypeReference<>() {});
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserById(String id) {
    String url = String.format("%s/%s", BASE_URL, id);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByUsername(String username) {
    String url = String.format("%s/%s/%s", BASE_URL, "username", username);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByEmail(String email) {
    String url = String.format("%s/%s/%s", BASE_URL, "email", email);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public int getTotalUsers() {
    String url = String.format("%s/%s", BASE_URL, "total-users");
    SimpleHttp http = SimpleHttp.doGet(url, httpClient);
    return http.asJson(int.class);
  }

  @SneakyThrows
  @Override
  public boolean validateCredentialsByUserId(String id, String password) {
    CredentialsDto credentialsDto = new CredentialsDto();
    credentialsDto.setPassword(password);
    String url = String.format("%s/%s/%s", BASE_URL, id, "auth/validate-credentials");
    SimpleHttp http = SimpleHttp
        .doPost(url, httpClient)
        .json(credentialsDto);
    return http.asJson(boolean.class);
  }
}
