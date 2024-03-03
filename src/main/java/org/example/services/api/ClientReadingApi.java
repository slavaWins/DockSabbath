package org.example.services.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.example.Main;
import org.example.helpers.ChatColor;
import org.example.helpers.IpAttempts;
import org.example.helpers.AppConfiguration;
import org.example.services.hashing.HashingApi;
import org.example.services.http.HttpServiceBase;
import org.example.services.http.contracts.RouteContract;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ClientReadingApi {

    public void init() {

        RouteContract contract = new RouteContract();
        contract.route = "/api/rcon/command";
        contract.call = this::ActionRoute;
        contract.method = "POST";

        HttpServiceBase.addRoute(contract);


    }

    public static String replaceUnicode2(String input) {

        input = input.replace(ChatColor.YELLOW.toString(), "");
        input = input.replace(ChatColor.BLUE.toString(), "");
        input = input.replace(ChatColor.RED.toString(), "");
        input = input.replace(ChatColor.WHITE.toString(), "");
        input = input.replace(ChatColor.RESET.toString(), "");
        input = input.replace(ChatColor.GREEN.toString(), "");

        return input;
    }


    private String ActionRoute(HttpExchange httpExchange) {

        String data = HttpServiceBase.readReqBody(httpExchange.getRequestBody());


        ClientResponseContract responseContract = new ClientResponseContract();


        if (!IpAttempts.canTryFromIp(httpExchange)) {
            return new Gson().toJson(responseContract.error("blocked ip"));
        }


        ClientApiContract body = new Gson().fromJson(data, ClientApiContract.class);

        if (body.hash == null) {
            System.out.println("not corect");
            return new Gson().toJson(responseContract.error("hash is not correct"));
        }
        AppConfiguration appConfiguration = AppConfiguration.get();


        if (!appConfiguration.clientApiEnabled) {
            return new Gson().toJson(responseContract.error("Client api disabled edit config.json in server"));
        }

        if (!HashingApi.verify(body.hash, body.data, appConfiguration.clientApiToken)) {
            return new Gson().toJson(responseContract.error("hash is not correct"));
        }

        IpAttempts.resetIp(httpExchange);

        String consoleOutput = "auto";

        System.out.println("From client /" + body.data);


        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        Main.commandHandle(body.data);

        consoleOutput = baos.toString();
        System.setOut(originalOut);

        responseContract.IsSuccess = true;
        responseContract.Data = replaceUnicode2(consoleOutput);

        System.out.println(responseContract.Data);

        Gson gson = new GsonBuilder()
                // .setPrettyPrinting()
                .create();

        String json = gson.toJson(responseContract);

        return json;
    }
}
