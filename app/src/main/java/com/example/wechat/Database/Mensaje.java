package com.example.wechat.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mensaje {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user")
    public String user;

    @ColumnInfo(name = "message")
    public String message;

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
