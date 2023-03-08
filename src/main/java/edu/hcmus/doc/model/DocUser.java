package edu.hcmus.doc.model;

import java.util.Set;
import lombok.Data;

@Data
public class DocUser {

    private String id;
    private String username;
    private String email;
    private Set<String> roles;
}
