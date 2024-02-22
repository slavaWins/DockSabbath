package org.example.services.docker_parser;

import org.example.helpers.TimeParseHelper;
import org.example.services.docker_parser.contracts.ComposeContract;
import org.example.services.docker_parser.contracts.PodStatusContract;
import org.example.services.docker_parser.contracts.PodStatusEnum;
import org.example.services.docker_parser.contracts.StatsContainerContract;
import org.example.services.procces.NsProcessed;
import org.example.services.procces.ProccesedResponseContract;

import java.util.ArrayList;
import java.util.List;

public class PodParser {
    public static List<ComposeContract> GetComposits() {

        List<ComposeContract> containers = new ArrayList<ComposeContract>();

        ProccesedResponseContract responseContract = NsProcessed.anonimProcess("front", "docker-compose ls", true, 0);

        String txt = responseContract.text;
        //System.out.println(txt);
        int lineNumber = 0;
        for (String line : txt.split("\n")) {
            if (line.trim().isEmpty()) continue;
            lineNumber++;
            if (lineNumber == 1) continue;

            String[] parts = line.split("\\s{2,}");

            ComposeContract contract = new ComposeContract();
            contract.name = parts[0];
            String status = parts[1];
            contract.configFiles = parts[2];


            String input = status;
            int startIndex = input.indexOf('(');
            int endIndex = input.indexOf(')');

            if (startIndex != -1 && endIndex != -1) {
                String word = input.substring(0, startIndex);
                contract.status = PodStatusEnum.parseStatus(word);
                contract.podsCount = Integer.parseInt(input.substring(startIndex + 1, endIndex));
            }

            containers.add(contract);

        }
        return containers;
    }

    public static List<PodStatusContract> GetPodsInfo() {

        List<PodStatusContract> containers = new ArrayList<PodStatusContract>();

        ProccesedResponseContract responseContract = NsProcessed.anonimProcess("front", "docker ps", true, 0);

        String txt = responseContract.text;
        // System.out.println(txt);

        int lineNumber = 0;
        for (String line : txt.split("\n")) {
            if (line.trim().isEmpty()) continue;
            lineNumber++;
            if (lineNumber == 1) continue;

            String[] parts = line.split("\\s{2,}");

            PodStatusContract contract = new PodStatusContract();
            contract.containerId = parts[0];
            contract.image = parts[1];
            contract.command = parts[2];
            contract.created = parts[3];
            contract.status = parts[4];
            contract.ports = parts[5];
            contract.names = parts[6];


            contract.age = TimeParseHelper.convertToUnixDate(contract.status);


            contract.ns = contract.names.split("-")[0];


            containers.add(contract);


            // contract.Log();
        }
        return containers;
    }


    public static StatsContainerContract getContainerStats(String containerId) {
        if (containerId == null) return null;
        //System.out.println("getContainerStats " + containerId);

        String txt = NsProcessed.anonimProcess("front", "docker stats " + containerId, true, 2).text;
        String[] lines = txt.split("\n");

        if (lines.length < 3) return null;

        String[] stats = lines[2].split("\\s{2,}");
        StatsContainerContract contract = new StatsContainerContract();

        contract.cpuRaw = stats[2];
        contract.memRaw = stats[4];

        return contract;

    }


}