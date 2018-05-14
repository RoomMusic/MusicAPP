package com.example.vidiic.appmusic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vidiic.appmusic.R;
import com.example.vidiic.appmusic.classes.User;

import java.util.List;

public class UserChatAdapter  extends RecyclerView.Adapter<UserChatAdapter.ViewHolder>{


    private List<User> userList;
    Context context;


    public UserChatAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName, lastMessageText;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userChatName);
            userImage = itemView.findViewById(R.id.userChatImage);
            lastMessageText = itemView.findViewById(R.id.lastMessage);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View userChatItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_user_chat, parent, false);


        return new ViewHolder(userChatItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);

        if (user.getUserImage() != null){
            holder.userImage.setImageBitmap(user.getUserImage().getDrawingCache());
        }else{
            holder.userImage.setImageResource(R.drawable.ic_action_music);
        }
        holder.userName.setText(user.getNickName());
        holder.lastMessageText.setText("Great Spirit....");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
