package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button btnEnviar;
    EditText mensajeEscrito;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnviar = findViewById(R.id.btnEnviar);
        mensajeEscrito = findViewById(R.id.editTxtTexto);
        scrollView = findViewById(R.id.scrollMensajes);
        Connection connection = new Connection(this, scrollView);
        connection.connect();

        btnEnviar.setOnClickListener(view -> {
            //Log.i("daasd", mensajeEscrito.getText().toString());
            connection.sendMessages(mensajeEscrito.getText().toString());
        });
        //connection.getMessages();
    }
}