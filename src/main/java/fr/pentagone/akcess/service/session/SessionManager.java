package fr.pentagone.akcess.service.session;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class SessionManager {
    private final HashMap<Integer, HashSet<String>> sessionManager = new HashMap<>();
    private final Object lock = new Object();

    public void registerSession(int id, String token){
        synchronized (lock){
            sessionManager.computeIfAbsent(id, (k) -> new HashSet<>()).add(token);
        }
    }
    public boolean isRegistered(int id, String token){
        synchronized (lock){
            var session = sessionManager.get(id);
            if(session != null) {
                return session.contains(token);
            }
            return false;
        }
    }
    public void closeSession(int id, String token){
        synchronized (lock){
            var session = sessionManager.get(id);
            if(session != null) session.remove(token);
        }
    }
}
