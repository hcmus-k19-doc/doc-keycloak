package edu.hcmus.doc.service;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public interface CacheService {

  UserModel getUserById(RealmModel realm, String id);

  void cacheUser(String id, UserModel user);

  void clear();
}
