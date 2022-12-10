package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.wechat.Database.Mensaje;
import com.example.wechat.Database.MensajeViewModel;
import com.example.wechat.Network.Connection;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//https://developer.android.com/codelabs/android-room-with-a-view?hl=es-419#9
    private MensajeViewModel mensajeViewModel;
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
        Connection connection = new Connection("10.0.2.2", 6666,this);
        connection.connect();
        LinearLayout linearLayout = findViewById(R.id.linearMensajes);

        mensajeViewModel = new ViewModelProvider(this).get(MensajeViewModel.class);

//        Este comando sirve para borrar todos los datos de la tabla mensajes
//        mensajeViewModel.nukeAll();

        mensajeViewModel.getAllMensaje().observe(this, mensajes -> {
            if (linearLayout.getChildCount() != 0){
                TextView textView = new TextView(this);
                textView.setText(mensajes.get(mensajes.size()-1).user + " " + mensajes.get(mensajes.size()-1).message);
                linearLayout.addView(textView);
            }else{
                for (int i = 0; i < mensajes.size();i++){
                    TextView textView = new TextView(this);
                    textView.setText(mensajes.get(i).user + " " + mensajes.get(i).message);
                    linearLayout.addView(textView);
                }
            }
        });

        btnEnviar.setOnClickListener(view -> {
            connection.sendMessages(mensajeEscrito.getText().toString());
        });

    }
}