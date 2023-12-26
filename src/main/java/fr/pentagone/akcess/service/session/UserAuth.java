package fr.pentagone.akcess.service.session;

import java.util.Set;
import java.util.UUID;

public record UserAuth(Set<UUID> tokens) {

}
