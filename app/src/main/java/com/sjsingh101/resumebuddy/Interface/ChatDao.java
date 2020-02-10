package com.sjsingh101.resumebuddy.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sjsingh101.resumebuddy.Class.ChatDataType;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ChatDao {

    @Insert
    void insert(ChatDataType chatData);

    @Update
    void update(ChatDataType chatDataType);

    @Delete
    void delete(ChatDataType chatDataType);

    @Query("DELETE FROM chatdata_table")
    void deleteAllChats();

    @Query("SELECT * FROM chatdata_table")
    LiveData <List<ChatDataType>> getAllChats();
}
