package com.sjsingh101.resumebuddy.Class;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ChatDataViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;
    private LiveData<List<ChatDataType>> chats;

    public ChatDataViewModel(@NonNull Application application) {
        super(application);

        chatRepository=new ChatRepository(application);
        chats=chatRepository.getAllChats();
    }

    public void insert(ChatDataType chatDataType){
        chatRepository.insert(chatDataType);
    }

    public void update(ChatDataType chatDataType){
        chatRepository.update(chatDataType);
    }

    public void delete(ChatDataType chatDataType){
        chatRepository.delete(chatDataType);
    }

    public void deleteAllChats(){
        chatRepository.deleteAllChats();
    }

    public LiveData<List<ChatDataType>> getChats(){
        return chats;
    }


}
