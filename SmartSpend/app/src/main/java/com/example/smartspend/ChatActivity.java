package com.example.smartspend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartspend.model.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ArrayList<Chat> chats = new ArrayList<>();
    //View chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RelativeLayout chatLayout = (android.widget.RelativeLayout) findViewById(R.id.chat_layout);
        View input = chatLayout.findViewById(R.id.input);
        ImageView requestButton = input.findViewById(R.id.imageView2);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = input.findViewById(R.id.input);
                editText.setText("");
                String text = editText.getText().toString();

                Chat chat = new Chat(true, text);
                chats.add(chat);
                LinearLayout newChat = findViewById(R.id.you_message);
                TextView textView = newChat.findViewById(R.id.message);
                textView.setText(text);
                chatLayout.addView(newChat);

                Call<String> call = ClientUtils.service.chat(text);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200 && response.body() != null) {
                            Chat chat2 = new Chat(false, response.body());
                            chats.add(chat2);
                            LinearLayout newChat2 = findViewById(R.id.you_message);
                            TextView textView = newChat2.findViewById(R.id.message);
                            textView.setText(response.body());
                            chatLayout.addView(newChat2);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Error", "Top accommodations");
                    }
                });
            }
        });
    }


}