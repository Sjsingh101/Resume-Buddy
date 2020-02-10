package com.sjsingh101.resumebuddy.Class;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sjsingh101.resumebuddy.Interface.ChatDao;

@Database(entities = ChatDataType.class ,version =1,exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public abstract ChatDao chatDao();

    public static synchronized ChatDatabase getInstance(Context context)
    {
      if (instance==null)
      {
          instance= Room.databaseBuilder(context,ChatDatabase.class,
                  "chat_database").
                  fallbackToDestructiveMigration().
                  addCallback(roomCallBack).
                  build();
      }

      return instance;
    }

    private static RoomDatabase.Callback roomCallBack=new RoomDatabase.Callback()
    {
       public void onCreate(SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new populateDBAsyncTask(instance).execute();
        }
    };

    private static class populateDBAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private ChatDao chatDao;

        private populateDBAsyncTask(ChatDatabase db)
        {
            chatDao=db.chatDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            chatDao.insert(new ChatDataType("bot","hi"));
            return null;
        }
    }
}
