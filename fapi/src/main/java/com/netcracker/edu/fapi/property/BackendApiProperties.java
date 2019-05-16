package com.netcracker.edu.fapi.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BackendApiProperties {

    @Value("${backend.server.uri}")
    private String backendUri;

    private String getApiUri() {
        return backendUri + "/api";
    }

    public String getUsersUri() {
        return getApiUri() + "/users";
    }

    public String getProjectsUri() {
        return getApiUri() + "/projects";
    }
}
