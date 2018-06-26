/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Andrii_Rodionov
 */
public interface Response {

    PrintWriter getWriter() throws IOException;

    /* This method is used to serve a static page */
    void sendStaticResource(String resourceURI) throws IOException;
    
}
