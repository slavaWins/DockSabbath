package org.example.core.http_server;

import com.sun.net.httpserver.HttpExchange;

import java.util.function.Function;

public class RouteContract {

    public String route = "/api";
    public String method = "GET";
    public Function<HttpExchange, String> call;

}
