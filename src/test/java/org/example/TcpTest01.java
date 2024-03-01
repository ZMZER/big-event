package org.example;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpTest01 {

    public static void main(String[] args) throws Exception {
        ServerSocket ts = new ServerSocket(10086);

        Socket accept = ts.accept();

        InputStream inputStream = accept.getInputStream();


        while (true) {
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            if (len == -1) {
                break;
            }
            System.out.println(new String(bytes, 0, len));
        }


    }
}
