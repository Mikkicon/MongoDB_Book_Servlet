package http.server.servlet;


import http.server.Request;
import http.server.Request;
import http.server.Response;
import http.server.Response;
import java.io.IOException;

/**
 *
 * @author Andrii_Rodionov
 */
public interface Servlet {

    void destroy();

    void init();

    void service(Request req, Response res) throws IOException;
    
}
