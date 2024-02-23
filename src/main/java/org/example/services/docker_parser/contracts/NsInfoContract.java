package org.example.services.docker_parser.contracts;

import org.example.helpers.TimeParseHelper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NsInfoContract {

    public String name;
    public ComposeContract compose;
    public List<PodStatusContract> pods = new ArrayList<PodStatusContract>();
    public String repo = "none";
    public String errorStatus;

    public String getAge() {

        Instant ageMax = null;
        long ageMaxL = 0;
        for(PodStatusContract pod : pods) {

            if(pod.age.getEpochSecond()> ageMaxL) {
                ageMaxL=  pod.age.getEpochSecond();
                ageMax = pod.age;
            }
        }

        return TimeParseHelper.convertToString(ageMax);
    }
}
