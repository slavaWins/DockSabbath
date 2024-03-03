package org.example.services.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.helpers.IpAttempts;
import org.example.helpers.IpHelper;
import org.example.helpers.Lang;
import org.example.services.http.contracts.RouteContract;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class HttpService {

    public static List<RouteContract> routes = new ArrayList<>();

    public static int port = 8000;

    public static void addRoute(RouteContract route) {
        System.out.println("Init route: " + getDomain() + route.route);
        routes.add(route);
    }

    public static String getDomain() {
        return "http://" + IpHelper.getIp() + ":" + port;
    }



    public static void start() {

        HttpServer server;


        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            System.out.println("ERR");
            throw new RuntimeException(e);
        }
        System.out.println( Lang.t("http.start","Запущен Http сервер для вебхуков гита и подключение клиентской части. Адрес: ") + getDomain());
    }


    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if(!IpAttempts.canTryFromIp(exchange)){
                exchange.close();
                return;
            }

            String response = null;
            for (RouteContract route : routes) {


                if (!exchange.getRequestURI().toString().startsWith(route.route)) continue;
                //System.out.println(exchange.getRequestURI().toString() + " == " + route.route);

//                if (!exchange.getRequestMethod().toString().equalsIgnoreCase(route.method)) continue;
                response = route.call.apply(exchange);
                break;
            }


            if (response != null) {


                //response = "кирилица";

                byte[] bytes = response.getBytes("UTF-8");

                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.flush();
                os.close();

                return;
            }

            //System.out.println(exchange.getRequestMethod() + " " + exchange.getRequestURI());
            //System.out.println(readReqBody(exchange.getRequestBody()));

            exchange.close();
            return ;
            /*
            exchange.sendResponseHeaders(404, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();*/
        }
    }

    public static String readReqBody(InputStream requestBodyInput) {
        try {
            InputStreamReader isr = null;

            isr = new InputStreamReader(requestBodyInput, "utf-8");

            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            String requestBodyAsString = requestBody.toString();
            return requestBodyAsString;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
