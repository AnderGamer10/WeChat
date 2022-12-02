package com.example.wechat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection {
    MainActivity mainActivity;
    Socket socket;
    HandlerThread handlerThread, handlerThread2, handlerThread3;
    Handler handler, handler2, handler3;
    Looper looper, looper2, looper3;
    public Connection(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void connect() {
        try {
//          Socket
            handlerThread = new HandlerThread("Socket");
            handlerThread.start();
            looper = handlerThread.getLooper();
            handler = new Handler(looper);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("localhost", 6666);
//          BufferedReader
                        handlerThread2 = new HandlerThread("BufferedReader");
                        handlerThread2.start();
                        looper2 = handlerThread2.getLooper();
                        handler2 = new Handler(looper2);

//          BufferedWriter
                        handlerThread3 = new HandlerThread("BufferedWriter");
                        handlerThread3.start();
                        looper3 = handlerThread3.getLooper();
                        handler3 = new Handler(looper3);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getMessages(){

        handler2.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void sendMessages(String mensaje){
        handler3.post(new Runnable() {
            @Override
            public void run() {
                if(mensaje.trim().length() > 0){
                    BufferedWriter bufferedWriter = null;
                    try {
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write(mensaje);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


}