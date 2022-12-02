package com.example.wechat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {

    public void run() {
        try {
//          Socket
            HandlerThread handlerThread = new HandlerThread("Socket");
            handlerThread.start();
            Looper looper = handlerThread.getLooper();
            Handler handler = new Handler(looper);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("localhost", 6666);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

//          BufferedReader
            HandlerThread handlerThread2 = new HandlerThread("BufferedReader");
            handlerThread2.start();
            Looper looper2 = handlerThread2.getLooper();
            Handler handler2 = new Handler(looper2);
            handler2.post(new Runnable() {
                @Override
                public void run() {

                }
            });

//          BufferedWriter
            HandlerThread handlerThread3 = new HandlerThread("BufferedWriter");
            handlerThread3.start();
            Looper looper3 = handlerThread3.getLooper();
            Handler handler3 = new Handler(looper3);
            handler3.post(new Runnable() {
                @Override
                public void run() {

                }
            });

//            TODO: Creacion Socket
//            Socket socket = new Socket("localhost", 6666);

//            TODO: Aceptacion del socket
//            ServerSocket serverSocket = new ServerSocket(6666);
//            Socket cliente = serverSocket.accept();


            /*
            InputStream inputStream = cliente.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea;
            while ((linea = bufferedReader.readLine()) != null){
                String lineaFinal = "Cliente " + contador +" escribe: " + linea;
                System.out.println(lineaFinal);
                main.listSockets.forEach(socket -> {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write(lineaFinal);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createSocket(){

    }

    public void sendMessages(){

    }

    public void getMessages(){

    }



}