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
    @Query("SELECT * FROM mensaje ORDER BY user ASC")
    LiveData<List<Mensaje>> findAllUser();

    @Insert
    void insertAll(Mensaje... mensajes);

    @Delete
    void delete(Mensaje mensaje);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mensaje mensaje);
}
