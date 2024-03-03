package org.example.repositories;

import org.example.core.yml.YmlConfig;
import org.example.services.docker_parser.PodParser;
import org.example.services.docker_parser.contracts.ComposeContract;
import org.example.services.docker_parser.contracts.NsInfoContract;
import org.example.services.docker_parser.contracts.PodStatusContract;

import java.util.ArrayList;
import java.util.List;

public class NsAttachRepository {

    public static List<NsInfoContract> attacingAllData() {

        List<NsInfoContract> lists = new ArrayList<NsInfoContract>();


        List<ComposeContract> compositsList =  PodParser.GetComposits();
        List<PodStatusContract> podsList =   PodParser.GetPodsInfo();

        for (YmlConfig config : NsConfigsRepository.getNsConfgis()) {
            NsInfoContract attach = new NsInfoContract();
            attach.name = config.name;

            lists.add(attach);

            if (config.get("git.owner") != null) {
                attach.repo = config.get("git.owner") + "/" + config.get("git.repo") + ":" + config.get("git.branch");
            }

            if(ComposesFilesRepository.getNsByName(config.name)==null){
                attach.errorStatus = "Not exist ns-files/";
                continue;
            }

            for (ComposeContract compose : compositsList) {
                if (!compose.name.equalsIgnoreCase(config.name)) continue;
                attach.compose = compose;
            }

            for (PodStatusContract pod :podsList) {
  //              System.out.println(pod.names + ":" + pod.containerId+ ":" + pod.ns + ":" + pod.age);
//                if (!pod.ns.equalsIgnoreCase(config.name)) continue;
                if (!pod.names.startsWith(config.name+"-")) continue;
                attach.pods.add(pod);
            }


        }

        return lists;
    }
}
