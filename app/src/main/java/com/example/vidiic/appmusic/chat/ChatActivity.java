package com.example.vidiic.appmusic.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vidiic.appmusic.R;
import com.example.vidiic.appmusic.adapters.MessageListAdapter;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.FileMessage;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.UserMessage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String channelURL;
    private RecyclerView rvChat;
    private MessageListAdapter messageListAdapter;
    private List<BaseMessage> baseMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button btnSendMessage = findViewById(R.id.button_chatbox_send);
        EditText messageText = findViewById(R.id.edittext_chatbox);
        rvChat = findViewById(R.id.reyclerview_message_list);

        Intent socialIntent = getIntent();

        //obtenemos los ids pasados por el intent, id de usuario app e id del usuario escogido
        List<String> ids = Arrays.asList(socialIntent.getStringArrayExtra("userids"));

        //Log.d("sergio", "USERIDS " + ids[0] + " " + ids[1]);


        //abrimos el canal con el usuario especificado
        GroupChannel.createChannelWithUserIds(ids, true, (GroupChannel groupChannel, SendBirdException e) -> {
            if (e != null) Log.d("sergio", "error en abrir channel");

            Log.d("sergio", "exito en abrir channel");


                channelURL = groupChannel.getUrl();

                Log.d("sergio", "url: " + channelURL);


            PreviousMessageListQuery previousMessageListQuery = groupChannel.createPreviousMessageListQuery();

            previousMessageListQuery.load(20, false, (list, e12) -> {
                if (e12 != null) return;
                for (BaseMessage b : list) {
                    Log.d("sergio", "mensaje previos " + ((UserMessage) b).getMessage());
                }

                messageListAdapter = new MessageListAdapter(ChatActivity.this, list);
                rvChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                rvChat.setAdapter(messageListAdapter);


                //recibir mensaje
                SendBird.addChannelHandler(channelURL, new SendBird.ChannelHandler() {
                    @Override
                    public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                        if (baseMessage instanceof UserMessage) {

                            Log.d("sergio", "MENSAJE: " + ((UserMessage) baseMessage).getMessage());

                            list.add(baseMessage);

                            messageListAdapter.notifyDataSetChanged();
                            rvChat.smoothScrollToPosition(list.size() - 1);
                            Log.d("sergio", "NOTIFY DATA SET CHANGED");


                        } else if (baseMessage instanceof FileMessage) {

                        }
                    }
                });

                btnSendMessage.setOnClickListener(view -> {

                    if (!TextUtils.isEmpty(messageText.getText())) {

                        groupChannel.sendUserMessage(messageText.getText().toString(), (userMessage, e1) -> {
                            if (e1 != null) Log.d("sergio", "error al enviar mensaje");
                            Log.d("sergio", "exito al enviar mensaje");

                            list.add(userMessage);
                            messageListAdapter.notifyDataSetChanged();
                            rvChat.smoothScrollToPosition(list.size() - 1);

                            //borrar texto escrito por el usuario
                            messageText.setText("");
                        });
                    }

                });
            });


        });




    }
}
