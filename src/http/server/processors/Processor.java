/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server.processors;

import http.server.Request;
import http.server.Request;
import http.server.Response;
import http.server.Response;

/**
 *
 * @author andrii
 */
public interface Processor {

    void process(Request request, Response response);
    
}
