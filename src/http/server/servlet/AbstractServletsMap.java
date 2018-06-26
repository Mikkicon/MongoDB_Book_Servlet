package http.server.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andrii
 */
public class AbstractServletsMap {
    protected final Map<String, Servlet> servlets = new HashMap<>();

    public AbstractServletsMap() {
    }

    public void callInit() {
        for (Servlet servlet : servlets.values()) {
            servlet.init();
        }
    }

    public void callDestroy() {
        for (Servlet servlet : servlets.values()) {
            servlet.destroy();
        }
    }

    public Servlet getServlet(String uri) {
        return servlets.get(uri);
    }
    
}
