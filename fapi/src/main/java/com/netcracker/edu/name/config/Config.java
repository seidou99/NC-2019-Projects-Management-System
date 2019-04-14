package com.netcracker.edu.name.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${backend.server.uri}")
    private String backendUri;

    private String getApiUri() {
        return backendUri + "/api";
    }

    public String getUsersUri() {
        return getApiUri() + "/users";
    }

    public String getTasksUri() {
        return getApiUri() + "/tasks";
    }

    public String getProjectsUri() {
        return getApiUri() + "/projects";
    }
}
