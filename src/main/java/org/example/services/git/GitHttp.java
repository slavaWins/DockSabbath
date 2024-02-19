package org.example.services.git;

import com.sun.net.httpserver.HttpExchange;
import org.example.core.http_server.HttpServiceMain;
import org.example.core.http_server.RouteContract;

public class GitHttp {

    public void init() {

        RouteContract contract = new RouteContract();
        contract.route = "/api/git/action";
        contract.call = this::ActionRoute;

        HttpServiceMain.addRoute(contract);


    }

    private String ActionRoute(HttpExchange httpExchange) {

        return "eeee";
    }
}
