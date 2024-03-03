package org.example.services.docker_parser.contracts;

import org.example.services.docker_parser.PodParser;

import java.time.Instant;

public class PodStatusContract {

    public String containerId;
    public String image;
    public String command;
    public String created;
    public String status;
    public String ports;
    public String names;
    public String ns;
    public Instant age;


    public StatsContainerContract getStat() {
        return PodParser.getContainerStats(names, containerId);
    }

    public void Log() {
        System.out.println("XXXX= " + ns + " " + image + " " + created);
    }
}
