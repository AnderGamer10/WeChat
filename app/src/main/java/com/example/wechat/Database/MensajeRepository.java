package com.example.wechat.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MensajeRepository {
    private MensajeDAO mensajeDAO;
    private LiveData<List<Mensaje>> allMensaje;
    private LiveData<List<Mensaje>> lastMessage;

    MensajeRepository(Application application) {
        DBMensaje db = DBMensaje.getDatabase(application);
        mensajeDAO = db.mensajeDAO();
        allMensaje = mensajeDAO.getAllMensajes();
        lastMessage = mensajeDAO.ultimoMensaje();
    }

    LiveData<List<Mensaje>> getAllMensaje() {
        return allMensaje;
    }

    LiveData<List<Mensaje>> lastMessage() {
        return lastMessage;
    }

    void insert(Mensaje mensaje) {
        DBMensaje.databaseWriteExecutor.execute(() -> {
            mensajeDAO.insertarMensaje(mensaje);
        });
    }
    void deleteAll() {
        DBMensaje.databaseWriteExecutor.execute(() -> {
            mensajeDAO.nukeTable();
        });
    }
}
