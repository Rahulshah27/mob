package com.example.mobtask.network.domain.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mobtask.network.domain.dao.MobDao;
import com.example.mobtask.presentation.model.Mob;


@Database(entities = {Mob.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MobDao mobDao();
}


