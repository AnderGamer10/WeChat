package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView mensajes;
    Button btnEnviar;
    EditText mensajeEscrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnviar = findViewById(R.id.btnEnviar);
        mensajes = findViewById(R.id.idMensajes);
        mensajeEscrito = findViewById(R.id.editTxtTexto);

        Connection connection = new Connection(this);
        connection.connect();
        btnEnviar.setOnClickListener(view -> {
            Log.i("daasd", mensajeEscrito.getText().toString());
            connection.sendMessages(mensajeEscrito.getText().toString());
        });
    }
}