package http.server.servlet;


import http.server.servlet.Servlet;
import http.server.Request;
import http.server.Response;
import java.io.IOException;

/**
 *
 * @author Andrii_Rodionov
 */
public abstract class AbstractServlet implements Servlet {
    
    @Override
    public void init() {
    }


    @Override
    public void destroy() {
    }
    
}
