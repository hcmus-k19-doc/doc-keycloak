FROM quay.io/keycloak/keycloak:20.0.3
WORKDIR /opt/keycloak/providers
ADD ../jar/doc-user-provider-1.0.4.jar ./doc-user-provider-1.0.4.jar
WORKDIR /opt/keycloak/data/import
ADD deployment-realm.json ./deployment-realm.json
EXPOSE 8080
