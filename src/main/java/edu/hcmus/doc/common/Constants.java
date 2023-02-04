package edu.hcmus.doc.common;

public class Constants {

  private Constants() {

  }

  public static final String BASE_URL = "http://host.docker.internal:8080/api/v1/keycloak/users";
  public static final String SECRET_KEY = "secret";
  public static final String BASE_URL_PROP_KEY = "base-url";
  public static final String SECRET_KEY_PROP_KEY = "secret-key";
  public static final String AUTH_TOKEN_HEADER_NAME = "DOC-Auth-Token";
}
