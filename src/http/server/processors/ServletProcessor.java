package http.server.processors;

import http.server.Request;
import http.server.Response;
import http.server.servlet.Servlet;
import http.server.servlet.AbstractServletsMap;

public class ServletProcessor implements Processor {

    private final AbstractServletsMap servletsMap;

    public ServletProcessor(AbstractServletsMap servletsMap) {
        this.servletsMap = servletsMap;
    }   
    
    @Override
    public void process(Request request, Response response) {

        String servletName = getServletName(request);        
        Servlet servlet = servletsMap.getServlet(servletName);        
        if (servlet == null) {
            return;
        }
        
        try {
            servlet.service(request, response);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String getServletName(Request request) {
        String uri = request.getURI();
        int start = uri.lastIndexOf("/");
        int end = uri.lastIndexOf("?");
        end = (end < 0) ? uri.length() : end;
        String servletName = uri.substring(start, end);
        return servletName;
    }
}
