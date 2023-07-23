# prj-doc-keycloak custom image

This is a custom Keycloak image that contains a custom User Federation.

## Prerequisites

Before getting started, make sure you have the following tools installed:

- [Docker](https://www.docker.com/)
- [IntelliJ](https://www.jetbrains.com/idea/) (Please use IntelliJ as some configurations can only be used in IntelliJ)
- [Eclipse Temurin JDK 17.0.5](https://adoptium.net/releases.html?variant=openjdk17&jvmVariant=hotspot) (or any 17 version, but it is recommended to use the proposed version)
- [Maven](https://maven.apache.org/)

## Getting Started

### Build Custom User Federation in Keycloak

To implement custom User Federation in Keycloak, follow these steps:

1. The implementation is put in the `src` package. You can customize your own idea on that source. Don't forget to specify the `META-INF\services\org.keycloak.storage.UserStorageProviderFactory.java` file in the resources package; it is critical. If you miss it, your customization will not work.

2. Run the following command to build the .jar:
    ```shell
    mvn clean install
    ```

3. Put the .jar file that you have built into `/opt/keycloak/providers` in Keycloak.

4. After starting your Keycloak, you should now see your customization in the `User Federation` menu.

### Build prj-doc-keycloak Image
To use custom User Federation and get the project preconfigured in Keycloak, follow these steps:

1. Run the Dockerfile in `etc/docker/keycloak`:
   - The default Dockerfile uses `deployment` configuration.
   - If you want to use `development` configuration, switch the `deployment-realm.json` to `realm-export.json`.

2. Custom Keycloak User Federation:
   There is a package called `providers` that contains a .jar file, which is a custom Keycloak User Federation file.
   - If you want to use your own custom User Federation, put your file in the "providers" package, and then place it in `/opt/keycloak/providers` of the container.

## Contact
- [Nguyen Duc Nam](https://github.com/namworkmc)
- [Hoang Nhu Thanh](https://github.com/thanhhoang4869)
- [Le Ngoc Minh Nhat](https://github.com/minhnhat02122001)
- [Hoang Huu Giap](https://github.com/hhgiap241)
