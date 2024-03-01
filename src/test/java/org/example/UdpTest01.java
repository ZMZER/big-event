package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpTest01 {

    public static void main(String[] args) throws IOException {


        int port = 10086;

        DatagramSocket socket = new DatagramSocket(port);

        byte[] data = new byte[1024];

        DatagramPacket packet = new DatagramPacket(data, data.length);

        socket.receive(packet);

        byte[] data1 = packet.getData();
        System.out.println(new String(data1,0,packet.getLength()));

        socket.close();
    }

}
