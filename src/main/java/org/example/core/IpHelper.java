package org.example.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class IpHelper {

    public static String getIp() {
        try {
            URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatIsMyIP.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getIp2();
    }

    public static String getIp2() {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
