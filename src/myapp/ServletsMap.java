package myapp;

import http.server.servlet.AbstractServletsMap;
import myapp.web.BookServlet;

/**
 *
 * @author andrii
 */
public class ServletsMap extends AbstractServletsMap {


    public ServletsMap() {
        servlets.put("/book", new BookServlet());
    }

}
