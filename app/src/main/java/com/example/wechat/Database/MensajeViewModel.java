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
    private final LiveData<List<Mensaje>> lastMessage;

    public MensajeViewModel (Application application) {
        super(application);
        mRepository = new MensajeRepository(application);
        allMensaje = mRepository.getAllMensaje();
        lastMessage = mRepository.lastMessage();
    }

    public LiveData<List<Mensaje>> getAllMensaje() {
        return allMensaje;
    }
    public LiveData<List<Mensaje>> lastMessage() {
        return lastMessage;
    }
    public void insert(Mensaje mensaje) { mRepository.insert(mensaje); }
    
    public void nukeAll() { mRepository.deleteAll(); }
}
