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

    List<DocUser> findUsers(String query) {
        return "*".equals(query)
            ? users
            : users.stream()
                .filter(
                    user -> user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(toList());
    }
}
