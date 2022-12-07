package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.wechat.Database.MensajeViewModel;
import com.example.wechat.Network.Connection;

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
        TextView textView = new TextView(this);
        LinearLayout linearLayout = findViewById(R.id.linearMensajes);
        linearLayout.addView(textView);

        mensajeViewModel = new ViewModelProvider(this).get(MensajeViewModel.class);
        mensajeViewModel.getAllMensaje().observe(this, words -> {

        });

        btnEnviar.setOnClickListener(view -> {
//            Log.i("daasd", mensajeEscrito.getText().toString());
            connection.sendMessages(mensajeEscrito.getText().toString());
        });




    }
}