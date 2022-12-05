package com.example.wechat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection {
    MainActivity mainActivity;
    Socket socket;
    HandlerThread handlerThread, handlerThread2, handlerThread3;
    Handler handler, handler2, handler3;
    Looper looper, looper2, looper3;
    ScrollView scrollView;
    public Connection(MainActivity mainActivity, ScrollView scrollView){
        this.mainActivity = mainActivity;
        this.scrollView = scrollView;
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
                        socket = new Socket("10.0.2.2", 6666);
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


                mainActivity.runOnUiThread(() -> {

                    while (true) {
                        InputStream inputStream = null;
                        try {
                            inputStream = socket.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                Log.i("message", line);
                                TextView textView = new TextView(mainActivity);
                                LinearLayout linearLayout = mainActivity.findViewById(R.id.linearMensajes);
                                linearLayout.addView(textView);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });




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