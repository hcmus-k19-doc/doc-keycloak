package edu.hcmus.doc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocUser {

    private String id;
    private String username;
    private String email;
    private String fullName;
    private String role;
}
