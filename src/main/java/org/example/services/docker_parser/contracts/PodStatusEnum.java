package org.example.services.docker_parser.contracts;

public enum PodStatusEnum {
    CREATED,
    RESTARTING,
    Active,
    PAUSED,
    EXITED;


    public static PodStatusEnum parseStatus(String statusString) {

        statusString=statusString.toLowerCase();
        switch (statusString) {
            case "created":
                return CREATED;
            case "restarting":
                return RESTARTING;
            case "running":
                return Active;
            case "paused":
                return PAUSED;
            case "exited":
                return EXITED;
            default:
                throw new IllegalArgumentException("Invalid status: " + statusString);
        }
    }
}
