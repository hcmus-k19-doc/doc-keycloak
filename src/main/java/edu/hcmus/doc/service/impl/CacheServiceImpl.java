package edu.hcmus.doc.service.impl;

import edu.hcmus.doc.model.UserAdapter;
import edu.hcmus.doc.service.CacheService;
import edu.hcmus.doc.service.DocUserClient;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

  private final DocUserClient client;
  private final KeycloakSession session;
  private final ComponentModel model;
  private final Map<String, UserModel> loadedUsers = new HashMap<>();

  @Override
  public UserModel getUserById(RealmModel realm, String id) {
    UserModel userModel = loadedUsers.get(id);
    if (userModel != null) {
      return userModel;
    }

    client.getUserById(id)
        .map(u -> new UserAdapter(session, realm, model, u))
        .ifPresent(userAdapter -> cacheUser(id, userAdapter));
    return getUserById(realm, id);
  }

  @Override
  public void cacheUser(String id, UserModel user) {
    loadedUsers.put(id, user);
  }

  @Override
  public void clear() {
    loadedUsers.clear();
  }
}
