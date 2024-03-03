package org.example.services.git;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.core.yml.YmlConfig;
import org.example.helpers.IpAttempts;
import org.example.helpers.AppConfiguration;
import org.example.repositories.NsConfigsRepository;
import org.example.services.git.contracts.GitWebHookContract;
import org.example.services.http.HttpService;
import org.example.services.http.contracts.RouteContract;
import org.example.services.sdbu.SdbuController;

public class GitHttpModule {

    public void init() {

        RouteContract contract = new RouteContract();


        contract.route = "/api/git/update/" + AppConfiguration.get().githubWebhookToken ;
        contract.call = this::ActionRoute;
        contract.method = "POST";

        HttpService.addRoute(contract);


    }

    private String ActionRoute(HttpExchange httpExchange) {

        String data = HttpService.readReqBody(httpExchange.getRequestBody());

        //  System.out.println(data);

        //String hashAprocve = SignatureVerifier.calculateSignature("asfasqwegqeg1g13g13g13g", data);

        GitWebHookContract body = new Gson().fromJson(data, GitWebHookContract.class);
     //   System.out.println("X1");
        if (body.ref == null) {
            System.out.println("erorr json hook");
            return "ERORROED";
        }
       // System.out.println("X2");

        IpAttempts.resetIp(httpExchange);

        for (YmlConfig config : NsConfigsRepository.getNsConfgis()) {

            if (!(config.get("git.owner") + "/" + config.get("git.repo")).equals(body.repository.full_name)) continue;

            if (!("refs/heads/" + config.get("git.branch")).equalsIgnoreCase(body.ref)) continue;


            System.out.println("Git webhook push:" + body.repository.full_name);
            //  System.out.println(config.name);

            new Thread(() -> {
                SdbuController autoComboCmd = SdbuController.getInstance();
                autoComboCmd.Sdbu(autoComboCmd.argToList(config.name));
            }).start();
            break;

        }


        return "200ok";
    }
}
