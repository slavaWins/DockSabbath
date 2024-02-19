package org.example.core.http_server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpServiceMain {

    public static List<RouteContract> routes = new ArrayList<>();

    public static int port = 8000;

    public static void addRoute(RouteContract route) {
        System.out.println("Adding route " + getDomain() + route.route);
        routes.add(route);
    }

    public static String getDomain() {
        return "http://localhost:" + port;
    }


    public static void start() {

        HttpServer server;


        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Starting http server on: " + getDomain());
    }


    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = null;
            for (RouteContract route : routes) {


                if (!exchange.getRequestURI().toString().startsWith(route.route)) continue;
                System.out.println(exchange.getRequestURI().toString() + " == " + route.route);

//                if (!exchange.getRequestMethod().toString().equalsIgnoreCase(route.method)) continue;
                response = route.call.apply(exchange);
                break;
            }


            if (response != null) {

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();


                return;
            }

            response = "not found";
            exchange.sendResponseHeaders(404, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
