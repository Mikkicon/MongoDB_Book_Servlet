/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp;

import http.server.HttpServer;
import http.server.servlet.AbstractServletsMap;
import java.io.IOException;

/**
 *
 * @author Andrii_Rodionov
 */
public class RunServer {
     public static void main(String[] args) throws IOException {
        AbstractServletsMap servletsMap = new ServletsMap();
        HttpServer server = new HttpServer(servletsMap);
        server.await();
    }
}
