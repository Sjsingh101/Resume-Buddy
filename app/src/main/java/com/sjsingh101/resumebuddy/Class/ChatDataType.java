package com.sjsingh101.resumebuddy.Class;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChatData_table")
public class ChatDataType {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String chat;

    public ChatDataType (String name,String chat)
    {
        this.name=name;
        this.chat=chat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChat() {
        return chat;
    }

    public String getName() {
        return name;
    }
}
