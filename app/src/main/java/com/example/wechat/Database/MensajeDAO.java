package com.example.wechat.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MensajeDAO {
    @Query("SELECT * FROM mensaje")
    LiveData<List<Mensaje>> getAllMensajes();

    @Query("SELECT * FROM mensaje ORDER BY id ASC LIMIT 1")
    LiveData<List<Mensaje>> ultimoMensaje();

    @Insert
    void insertarMensaje(Mensaje... mensajes);

    @Delete
    void delete(Mensaje mensaje);

    @Query("DELETE FROM mensaje")
    void nukeTable();
}

