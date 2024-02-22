package org.example.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class IpAttempts {
    private static Map<String, Integer> attemptsMap = new HashMap<>();

    private static int attemptsMax = 3;

    public static boolean canTryFromIp(String ip) {
        if (!attemptsMap.containsKey(ip)) {
            attemptsMap.put(ip, 1);
        }

        int attempts = attemptsMap.get(ip);

        if (attempts >= attemptsMax) {
            return false;
        }
        attempts++;

        if (attempts == attemptsMax) {
            System.out.println("BLOCKED AGGRESIVE IP: " + ip);
        }

        attemptsMap.put(ip, attempts );
        return true;
    }

    public static void resetIp(String ip) {
        attemptsMap.remove(ip);
    }


    public static boolean canTryFromIp(HttpExchange httpExchange) {

        InetSocketAddress remoteAddress = httpExchange.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        return canTryFromIp(clientIp);
    }

    public static void resetIp(HttpExchange httpExchange) {
        InetSocketAddress remoteAddress = httpExchange.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        resetIp(clientIp);
    }
}
