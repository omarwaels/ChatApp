package iti.jets.app.client.utils;

public class ServerIPAddress {

    private static String ip = "localhost";
    private static int port = 8189;

    public static String getIp() {
        return ip;
    }
    public static void setIp(String ip) {
        ServerIPAddress.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ServerIPAddress.port = port;
    }
}
