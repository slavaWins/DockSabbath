package org.example.services.docker_parser.contracts;

public class ComposeContract {

    public String name;
    public PodStatusEnum status = PodStatusEnum.CREATED;
    public int podsCount=0;
    public String configFiles;

    public void Log() {
        System.out.println("XXXX= "+name + " " + status);
    }


}
