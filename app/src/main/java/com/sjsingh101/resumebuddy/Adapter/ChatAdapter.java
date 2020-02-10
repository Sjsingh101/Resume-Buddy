package com.sjsingh101.resumebuddy.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsingh101.resumebuddy.Activity.ProfileActivity;
import com.sjsingh101.resumebuddy.Class.ChatDataType;
import com.sjsingh101.resumebuddy.R;


import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.DataViewHolder> {

    List<ChatDataType> botChatData=new ArrayList<>();
    Context profileContext;
    public ChatAdapter(Context context) {
        profileContext=context;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context=viewGroup.getContext();
        int layoutIdForEachItem= R.layout.chat_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view=inflater.inflate(layoutIdForEachItem,viewGroup,shouldAttachToParentImmediately);
        return new DataViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.e("Chat adapter size:", String.valueOf(botChatData.size()));
        return botChatData.size();

    }

    public void setChatData(List<ChatDataType> botChatData)
    {
        this.botChatData=botChatData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
       holder.bind(position);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout messageLayoutRL;
        private TextView userName;
        private TextView userMessage;


        public DataViewHolder(@NonNull View itemView) {

            super(itemView);

            userName=itemView.findViewById(R.id.user_name);
            userMessage=itemView.findViewById(R.id.user_message);
            messageLayoutRL=itemView.findViewById(R.id.user_message_layout);
        }

        void bind(int position)
        {
            if (position%2==0) {
                messageLayoutRL.setBackgroundColor(ContextCompat.getColor(profileContext,R.color.green));
            }

            else {
                messageLayoutRL.setBackgroundColor(ContextCompat.getColor(profileContext,R.color.GrayGoose));
            }

            userName.setText(botChatData.get(position).getName());
            userMessage.setText(botChatData.get(position).getChat());
        }
    }

}
