package com.example.vidiic.appmusic.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sendbird.android.BaseMessage;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<BaseMessage> mMessageList;

    public MessageListAdapter(Context context, List<BaseMessage> mMessageList) {
        this.context = context;
        this.mMessageList = mMessageList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{



        public ReceivedMessageHolder(View itemView) {
            super(itemView);
        }
    }

}
