package org.example.services.git;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.example.core.http_server.HttpServiceMain;
import org.example.core.http_server.RouteContract;
import org.example.core.yml.YmlConfig;
import org.example.services.auto.AutoComboCmd;
import org.example.services.git.contracts.GitWebHookContract;
import org.example.services.nsconfigs.NsConfigsCmd;
import org.example.services.nsconfigs.NsHelper;

public class GitHttp {

    public void init() {

        RouteContract contract = new RouteContract();
        contract.route = "/api/git/update";
        contract.call = this::ActionRoute;
        contract.method = "POST";

        HttpServiceMain.addRoute(contract);


    }

    private String ActionRoute(HttpExchange httpExchange) {

        String data = HttpServiceMain.readReqBody(httpExchange.getRequestBody());

      //  System.out.println(data);

        //String hashAprocve = SignatureVerifier.calculateSignature("asfasqwegqeg1g13g13g13g", data);


        GitWebHookContract body = new Gson().fromJson(data, GitWebHookContract.class);

        if (body.ref == null) {
            System.out.println("erorr json hook");
            return "ERORROED";

        }




        for (YmlConfig config : NsHelper.getNsConfgis()) {

            if (!(config.get("git.owner") + "/" + config.get("git.repo")).equals(body.repository.full_name)) continue;

            if (!("refs/heads/" + config.get("git.branch")).equalsIgnoreCase(body.ref)) continue;


          //  System.out.println(config.name);

            new Thread(() -> {
                AutoComboCmd autoComboCmd = AutoComboCmd.getInstance();
                autoComboCmd.Sdbu(autoComboCmd.argToList(config.name));
            }).start();
            break;

        }


        System.out.println(body.repository.name);
        return "eeee";
    }
}
