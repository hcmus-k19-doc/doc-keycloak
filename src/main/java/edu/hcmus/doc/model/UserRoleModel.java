package edu.hcmus.doc.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.ReadOnlyException;

public class UserRoleModel implements RoleModel {

    private static final String READ_ONLY = "User role is read only";
    
    private String name;
    private final RealmModel realm;

    public UserRoleModel(String name, RealmModel realm) {
        this.name = name;
        this.realm = realm;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public void addCompositeRole(RoleModel role) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public void removeCompositeRole(RoleModel role) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public Stream<RoleModel> getCompositesStream() {
        return null;
    }

    @Override
    public Stream<RoleModel> getCompositesStream(String s, Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public boolean isClientRole() {
        return false;
    }

    @Override
    public String getContainerId() {
        return realm.getId();
    }

    @Override
    public RoleContainerModel getContainer() {
        return realm;
    }

    @Override
    public boolean hasRole(RoleModel role) {
        return this.equals(role) || this.name.equals(role.getName());
    }

    @Override
    public void setSingleAttribute(String name, String value) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public void setAttribute(String name, List<String> values) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public void removeAttribute(String name) {
        throw new ReadOnlyException(READ_ONLY);
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        return null;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return Collections.emptyMap();
    }
}
