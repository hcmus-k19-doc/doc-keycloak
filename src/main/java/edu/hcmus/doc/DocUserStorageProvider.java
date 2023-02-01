package edu.hcmus.doc;

import edu.hcmus.doc.service.DocClientSimpleHttp;
import edu.hcmus.doc.service.DocUserClient;
import java.util.Map;
import java.util.stream.Stream;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

public class DocUserStorageProvider implements
    UserStorageProvider,
    UserLookupProvider,
    UserQueryProvider,
    CredentialInputValidator {

    private final KeycloakSession session;
    private final ComponentModel model;
    private final DocUserClient client;

    public DocUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
        this.client = new DocClientSimpleHttp(session);
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        return client.validateCredentialsByUserId(StorageId.externalId(user.getId()), cred.getChallengeResponse());
    }

    @Override
    public void close() { /* Leave this empty */ }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        return client.getUserById(StorageId.externalId(id))
            .map(user -> new UserAdapter(session, realm, model, user))
            .orElse(null);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        return client.getUserByUsername(username)
            .map(user -> new UserAdapter(session, realm, model, user))
            .orElse(null);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return client.getUserByEmail(email)
            .map(user -> new UserAdapter(session, realm, model, user))
            .orElse(null);
    }

    @Override
    public int getUsersCount(RealmModel realm) {
        return client.getTotalUsers();
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search) {
        return client.getUsers("*".equals(search) ? null : search)
            .stream()
            .map(user -> new UserAdapter(session, realm, model, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        return client.getUsers("*".equals(search) ? null : search, firstResult, maxResults)
            .stream()
            .map(user -> new UserAdapter(session, realm, model, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return client.getUsers(null)
            .stream()
            .map(user -> new UserAdapter(session, realm, model, user));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.empty();
    }
}
