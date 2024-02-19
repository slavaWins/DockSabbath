package org.example.services.http_service;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpServiceMain {


    public static void start() {

        int port = 8000;
        HttpServer server;


            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", new MyHandler());
                server.setExecutor(null); // creates a default executor
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        System.out.println("Starting http server on port in your ip: localhost:" + port );
    }


    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println(exchange.getRequestMethod());
            System.out.println(exchange.getRequestURI());

            String response = "This is the response";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
