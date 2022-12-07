package com.example.wechat.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wechat.Database.Mensaje;
import com.example.wechat.Database.MensajeRepository;

import java.util.List;


public class MensajeViewModel extends AndroidViewModel {
    private MensajeRepository mRepository;

    private final LiveData<List<Mensaje>> allMensaje;

    public MensajeViewModel (Application application) {
        super(application);
        mRepository = new MensajeRepository(application);
        allMensaje = mRepository.getAllMensaje();
    }

    public LiveData<List<Mensaje>> getAllMensaje() {
        return allMensaje;
    }


    public void insert(Mensaje mensaje) { mRepository.insert(mensaje); }
}
