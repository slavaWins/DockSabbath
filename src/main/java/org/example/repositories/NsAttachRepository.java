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

        List<PodStatusContract> podsList = PodParser.GetPodsInfo();
        List<ComposeContract> composesList = PodParser.GetComposits();


        for (YmlConfig config : NsConfigsRepository.getNsConfgis()) {
            NsInfoContract attach = new NsInfoContract();
            attach.name = config.name;

            if (config.get("git.owner") != null) {
                attach.repo = config.get("git.owner") + "/" + config.get("git.repo") + ":" + config.get("git.branch");
            }
            for (ComposeContract compose : composesList) {
                if (!compose.name.equalsIgnoreCase(config.name)) continue;
                attach.compose = compose;
            }

            for (PodStatusContract pod : podsList) {
                if (!pod.ns.equalsIgnoreCase(config.name)) continue;
                attach.pods.add(pod);
            }

            lists.add(attach);
        }

        return lists;
    }
}
