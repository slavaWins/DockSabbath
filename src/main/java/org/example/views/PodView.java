package org.example.views;

import org.example.services.docker_parser.contracts.NsInfoContract;
import org.example.services.docker_parser.contracts.PodStatusContract;
import org.example.services.docker_parser.contracts.StatsContainerContract;

import java.util.List;

public class PodView {

    public static void ns(List<NsInfoContract> contracts) {

        System.out.printf("%-26s %-19s %-15s %-20s\n", "NAME",   "STATUS", "AGE", "REPO");

        for (NsInfoContract contract : contracts) {

            if (contract.compose == null) {
                System.out.printf("%-26s %-19s %-15s %-20s\n", contract.name, contract.errorStatus == null? "Disabled" : contract.errorStatus, "N/A", contract.repo);
            } else {
                System.out.printf("%-26s %-19s %-15s %-20s\n", contract.name,  contract.errorStatus == null? contract.compose.status : contract.errorStatus, contract.getAge(), contract.repo);
            }

        }
    }

    public static void pods(NsInfoContract contract) {

        System.out.printf("%-50s %-15s %-5s %-10s %-10s\n", "NAME", "STATUS", "AGE", "CPU", "RAM");

        for (PodStatusContract pod : contract.pods) {
            StatsContainerContract stats = pod.getStat();
            System.out.printf("%-50s %-15s %-5s %-10s %-10s\n", pod.names, pod.status, "815s", stats.cpuRaw, stats.memRaw);

        }
    }
}
