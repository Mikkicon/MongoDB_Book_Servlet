/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Andrii_Rodionov
 */
public interface Request {

    String getParameter(String name);

    Set<String> getParameterNames();

    Collection<String> getParameterValues();
    
    String getRequestAsText();

    String getURI();

    String getType();
}
