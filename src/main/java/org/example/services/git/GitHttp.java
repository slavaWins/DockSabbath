package org.example.services.git;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.core.yml.YmlConfig;
import org.example.repositories.NsConfigsRepository;
import org.example.services.git.contracts.GitWebHookContract;
import org.example.services.http.HttpServiceBase;
import org.example.services.http.contracts.RouteContract;
import org.example.services.sdbu.SdbuController;

public class GitHttp {

    public void init() {

        RouteContract contract = new RouteContract();
        contract.route = "/api/git/update";
        contract.call = this::ActionRoute;
        contract.method = "POST";

        HttpServiceBase.addRoute(contract);


    }

    private String ActionRoute(HttpExchange httpExchange) {

        String data = HttpServiceBase.readReqBody(httpExchange.getRequestBody());

      //  System.out.println(data);

        //String hashAprocve = SignatureVerifier.calculateSignature("asfasqwegqeg1g13g13g13g", data);


        GitWebHookContract body = new Gson().fromJson(data, GitWebHookContract.class);

        if (body.ref == null) {
            System.out.println("erorr json hook");
            return "ERORROED";

        }




        for (YmlConfig config : NsConfigsRepository.getNsConfgis()) {

            if (!(config.get("git.owner") + "/" + config.get("git.repo")).equals(body.repository.full_name)) continue;

            if (!("refs/heads/" + config.get("git.branch")).equalsIgnoreCase(body.ref)) continue;


          //  System.out.println(config.name);

            new Thread(() -> {
                SdbuController autoComboCmd = SdbuController.getInstance();
                autoComboCmd.Sdbu(autoComboCmd.argToList(config.name));
            }).start();
            break;

        }


        System.out.println(body.repository.name);
        return "eeee";
    }
}
