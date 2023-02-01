package edu.hcmus.doc;

import static java.util.stream.Collectors.toList;

import edu.hcmus.doc.model.DocUser;
import edu.hcmus.doc.service.DocUserClient;
import java.util.List;

class DemoRepository {

    private final List<DocUser> users;

    DemoRepository(DocUserClient client) {
        users = client.getUsers();
    }

    List<DocUser> getAllUsers() {
        return users;
    }

    int getUsersCount() {
        return users.size();
    }

    DocUser findUserById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    DocUser findUserByUsernameOrEmail(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    List<DocUser> findUsers(String query) {
        return "*".equals(query)
            ? users
            : users.stream()
                .filter(
                    user -> user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(toList());
    }

    boolean validateCredentials(String username, String password) {
        return true;
    }

    boolean updateCredentials(String username, String password) {
        return true;
    }

}
