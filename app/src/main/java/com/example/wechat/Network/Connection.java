package com.example.wechat.Network;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.wechat.Database.MensajeRepository;
import com.example.wechat.MainActivity;
import com.example.wechat.R;

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
    HandlerThread htConnect, htSend, htReceive;
    Handler hConnect, hSend, hReceive, hMain;

    BufferedReader br;
    BufferedWriter bw;

    private final String IP;
    private final int PORT;

    public Connection(String IP, int PORT, MainActivity mainActivity){
        this.IP = IP;
        this.PORT = PORT;
        this.mainActivity = mainActivity;
        hMain = new Handler();

//          Socket
        htConnect = new HandlerThread("Socket");
        htConnect.start();
        hConnect = new Handler(htConnect.getLooper());

//          BufferedReader
        htReceive = new HandlerThread("BufferedReader");
        htReceive.start();
        hReceive = new Handler(htReceive.getLooper());

//          BufferedWriter
        htSend = new HandlerThread("BufferedWriter");
        htSend.start();
        hSend = new Handler(htSend.getLooper());

    }

    public void connect() {
        try {
            hConnect.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(IP,PORT);

                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                        getMessages();
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
        hReceive.post(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String line;
                    try {
                        while ((line = br.readLine()) != null) {
                            String finalLine = line;

//                            MensajeRepository mensajeRepository **********************************


                            hMain.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("message", finalLine);
                                    TextView textView = new TextView(mainActivity);
                                    textView.setText(finalLine);
                                    LinearLayout linearLayout = mainActivity.findViewById(R.id.linearMensajes);
                                    linearLayout.addView(textView);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendMessages(String mensaje){
        hSend.post(new Runnable() {
            @Override
            public void run() {
                if(mensaje.trim().length() > 0){
                    try {
                        bw.write(mensaje);
                        bw.newLine();
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}