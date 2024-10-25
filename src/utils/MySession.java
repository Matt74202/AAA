package utils;

import jakarta.servlet.http.HttpSession;

public class MySession {
    HttpSession session;

    public MySession(HttpSession session) {
        this.session = session;
    }

    public Object get(String key) {
        return session.getAttribute(key);
    }

    public void add(String key, Object objet) {
        session.setAttribute(key, objet);
    }

    public  void delete(String key) {
        session.removeAttribute(key);
    }
}
