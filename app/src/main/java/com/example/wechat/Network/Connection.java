package com.example.wechat.Network;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.wechat.Database.DBMensaje;
import com.example.wechat.Database.Mensaje;
import com.example.wechat.Database.MensajeDAO;
import com.example.wechat.Database.MensajeRepository;
import com.example.wechat.Database.MensajeViewModel;
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
            hConnect.post(() -> {
                try {
                    socket = new Socket(IP,PORT);

                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    getMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessages(){
        hReceive.post(() -> {
            while (true){
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        String finalLine = line;
                        hMain.post(() -> {
                            Mensaje mensaje = new Mensaje();
                            mensaje.user = "Usuario " + finalLine.substring(8,10) + ":";
                            mensaje.message= finalLine.substring(18);

                            MensajeViewModel mensajeViewModel;
                            mensajeViewModel = new ViewModelProvider(mainActivity).get(MensajeViewModel.class);
                            mensajeViewModel.insert(mensaje);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendMessages(String mensajeStr){
        hSend.post(() -> {
            if(mensajeStr.trim().length() > 0){
                try {
                    bw.write(mensajeStr);
                    bw.newLine();
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}