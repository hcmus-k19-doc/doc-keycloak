package edu.hcmus.doc;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class DocUserStorageProviderFactory implements UserStorageProviderFactory<DocUserStorageProvider> {

    @Override
    public DocUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        // here you can setup the user storage provider, initiate some connections, etc.

        return new DocUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return "doc-user-provider";
    }
}
