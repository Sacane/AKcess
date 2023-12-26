package fr.pentagone.akcess.service.session;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class SessionManager {
    private final HashMap<Integer, Set<UUID>> sessionManager = new HashMap<>();
    private final Object lock = new Object();

    public void registerSession(int id, UUID token){
        synchronized (lock){
            sessionManager.merge(id, new HashSet<>(), (userid, set) -> {
                set.add(token);
                return set;
            });
        }
    }
    public boolean isRegistered(int id, UUID token){
        synchronized (lock){
            var session = sessionManager.get(id);
            if(session != null) return session.contains(token);
            return false;
        }
    }
    public void closeSession(int id, UUID token){
        synchronized (lock){
            var session = sessionManager.get(id);
            if(session != null) session.remove(token);
        }
    }
}
