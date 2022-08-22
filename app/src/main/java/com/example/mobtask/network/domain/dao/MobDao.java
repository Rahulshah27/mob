package com.example.mobtask.network.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobtask.presentation.model.Mob;

import java.util.List;

@Dao
public interface MobDao {

 @Insert
 void insertRecord(Mob mob);

 @Update
 void updateRecord(Mob mob);

 @Query("SELECT * FROM Mob ORDER BY uid DESC")
 List<Mob> getAllMobList();

 @Query("DELETE FROM Mob WHERE uid = :id")
 void deleteById(int id);
}

