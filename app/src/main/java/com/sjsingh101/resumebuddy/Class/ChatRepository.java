package com.sjsingh101.resumebuddy.Class;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.sjsingh101.resumebuddy.Interface.ChatDao;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {
    private ChatDao chatDao;

    private LiveData<List <ChatDataType> > allChats;

    public ChatRepository(Application application)
    {
        ChatDatabase chatDatabase=ChatDatabase.getInstance(application);
        chatDao=chatDatabase.chatDao();
        allChats=chatDao.getAllChats();
    }

    public void insert(ChatDataType chatDataType) {
      new insertChatAsyncTask(chatDao).execute(chatDataType);
    }

    public void delete(ChatDataType chatDataType) {
       new deleteChatAsyncTask(chatDao).execute(chatDataType);
    }

    public void update(ChatDataType chatDataType) {
        new updateChatAsyncTask(chatDao).execute(chatDataType);

    }

    public void deleteAllChats() {
        new deleteAllChatsAsyncTask(chatDao).execute();

    }

    public LiveData<List<ChatDataType>> getAllChats() {
        return allChats;
    }

    private static class insertChatAsyncTask extends AsyncTask<ChatDataType,Void,Void>
    {
        private ChatDao chatDao;

        public insertChatAsyncTask(ChatDao chatDao) {
            this.chatDao=chatDao;
        }

        @Override
        protected Void doInBackground(ChatDataType... chatDataTypes) {
            chatDao.insert(chatDataTypes[0]);
            return null;
        }
    }

    private static class updateChatAsyncTask extends AsyncTask<ChatDataType,Void,Void>
    {
        private ChatDao chatDao;

        public updateChatAsyncTask(ChatDao chatDao) {
            this.chatDao=chatDao;
        }

        @Override
        protected Void doInBackground(ChatDataType... chatDataTypes) {
            chatDao.update(chatDataTypes[0]);
            return null;
        }
    }

    private static class deleteChatAsyncTask extends AsyncTask<ChatDataType,Void,Void>
    {
        private ChatDao chatDao;

        public deleteChatAsyncTask(ChatDao chatDao) {
            this.chatDao=chatDao;
        }

        @Override
        protected Void doInBackground(ChatDataType... chatDataTypes) {
            chatDao.delete(chatDataTypes[0]);
            Log.e("ChatRepository:",chatDataTypes[1].getName()+" "+chatDataTypes[1].getChat());
            return null;
        }
    }

    private static class deleteAllChatsAsyncTask extends AsyncTask<ChatDataType,Void,Void>
    {
        private ChatDao chatDao;

        public deleteAllChatsAsyncTask(ChatDao chatDao) {
            this.chatDao=chatDao;
        }

        @Override
        protected Void doInBackground(ChatDataType... chatDataTypes) {
            chatDao.deleteAllChats();
            return null;
        }
    }

    }
