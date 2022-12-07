package com.example.wechat.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MensajeRepository {
    private MensajeDAO mensajeDAO;
    private LiveData<List<Mensaje>> allMensaje;

    MensajeRepository(Application application) {
        DBMensaje db = DBMensaje.getDatabase(application);
        mensajeDAO = db.mensajeDAO();
        allMensaje = mensajeDAO.findAllUser();
    }

    LiveData<List<Mensaje>> getAllMensaje() {
        return allMensaje;
    }

    void insert(Mensaje mensaje) {
        DBMensaje.databaseWriteExecutor.execute(() -> {
            mensajeDAO.insert(mensaje);
        });
    }
}
