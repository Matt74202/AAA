package util;


import jakarta.servlet.http.HttpSession;

public class MySession {
    HttpSession session;

    public MySession(HttpSession session) {
        this.session = session;
    }

    public Object get(String key) {
        return session.getAttribute(key);
    }

    public void add(String key, Object object) {
        session.setAttribute(key, object);
    }

    public void delete(String key) {
        session.removeAttribute(key);
    }

    public HttpSession getSession() {
        return session;
    }
}
