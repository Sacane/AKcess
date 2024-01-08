package fr.pentagone.akcess.security;

import java.util.HashSet;

public class SecurityPathConfigurer {
    private final HashSet<String> excludes = new HashSet<>();

    public SecurityPathConfigurer exclude(String keyword) {
        this.excludes.add(keyword);
        return this;
    }
    public boolean isExcluded(String path) {
        return excludes.stream().anyMatch(path::contains);
    }
}
