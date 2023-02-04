package edu.hcmus.doc.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.hcmus.doc.common.Constants;
import edu.hcmus.doc.model.dto.CredentialsDto;
import edu.hcmus.doc.model.DocUser;
import edu.hcmus.doc.service.DocUserClient;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

public class DocClientSimpleHttp implements DocUserClient {

  private final CloseableHttpClient httpClient;
  private final String baseUrl;
  private final String secretKey;

  public DocClientSimpleHttp(KeycloakSession session, ComponentModel model) {
    this.httpClient = session.getProvider(HttpClientProvider.class).getHttpClient();
    this.baseUrl = model.get(Constants.BASE_URL_PROP_KEY);
    this.secretKey = model.get(Constants.SECRET_KEY_PROP_KEY);
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
        .doGet(baseUrl, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey)
        .param("query", query)
        .param("first", String.valueOf(first))
        .param("max", String.valueOf(max));
    return http.asJson(new TypeReference<>() {});
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserById(String id) {
    String url = String.format("%s/%s", baseUrl, id);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByUsername(String username) {
    String url = String.format("%s/%s/%s", baseUrl, "username", username);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public Optional<DocUser> getUserByEmail(String email) {
    String url = String.format("%s/%s/%s", baseUrl, "email", email);
    SimpleHttp http = SimpleHttp.doGet(url, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey);
    return Optional.ofNullable(http.asJson(DocUser.class));
  }

  @SneakyThrows
  @Override
  public int getTotalUsers() {
    String url = String.format("%s/%s", baseUrl, "total-users");
    SimpleHttp http = SimpleHttp.doGet(url, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey);
    return http.asJson(int.class);
  }

  @SneakyThrows
  @Override
  public boolean validateCredentialsByUserId(String id, CredentialsDto credentialsDto) {
    String url = String.format("%s/%s/%s", baseUrl, id, "auth/validate-credentials");
    SimpleHttp http = SimpleHttp
        .doPost(url, httpClient)
        .header(Constants.AUTH_TOKEN_HEADER_NAME, secretKey)
        .json(credentialsDto);
    return http.asJson(boolean.class);
  }
}
