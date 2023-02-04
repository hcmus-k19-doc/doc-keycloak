package edu.hcmus.doc;

import edu.hcmus.doc.common.Constants;
import java.util.List;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

public class DocUserStorageProviderFactory implements UserStorageProviderFactory<DocUserStorageProvider> {

    private static final String PROVIDER_ID = "DOC";

    @Override
    public DocUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new DocUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
            .property(Constants.BASE_URL_PROP_KEY, "URL", "", ProviderConfigProperty.STRING_TYPE, Constants.BASE_URL, null)
            .property(Constants.SECRET_KEY_PROP_KEY, "Secret key", "", ProviderConfigProperty.STRING_TYPE, Constants.SECRET_KEY, null)
            .build();
    }
}
