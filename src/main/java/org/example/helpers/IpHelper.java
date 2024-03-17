package org.example.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public final class IpHelper {

    public static String getIp() {
        try {
            URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatIsMyIP.openStream()));

            String ip = in.readLine();
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getLovalIp();
    }

    private static String getLovalIp() {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
