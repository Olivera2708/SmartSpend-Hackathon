package com.example.smartspend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.smartspend.model.Chat;
import com.example.smartspend.model.Tip;
import com.example.smartspend.navigation.NavBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ArrayList<Chat> chats = new ArrayList<>();
    ScrollView scrollView;
    //View chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        NavBar.setNavigationBar(findViewById(R.id.bottom_navigaiton), this, R.id.navigation_chat);

        scrollView = findViewById(R.id.scroll);
        LinearLayout chatLayout = findViewById(R.id.chat_layout);
        View input = findViewById(R.id.input);
        ImageView requestButton = input.findViewById(R.id.imageView2);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = input.findViewById(R.id.input_text);
                String text = editText.getText().toString();
                editText.setText("");

                Chat chat = new Chat(true, text);
                chats.add(chat);
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View newChat = layoutInflater.inflate(R.layout.you_message, chatLayout, false);
                TextView textView = newChat.findViewById(R.id.message);
                textView.setText(text);
                chatLayout.addView(newChat, chatLayout.getChildCount());

                // After adding the new chat, scroll to the bottom
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));

                Call<Tip> call = ClientUtils.service.chat(text);
                call.enqueue(new Callback<Tip>() {
                    @Override
                    public void onResponse(Call<Tip> call, Response<Tip> response) {
                        Chat chat2 = new Chat(false, response.body().getValue());
                        chats.add(chat2);
                        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                        View newChat = layoutInflater.inflate(R.layout.bot_message, chatLayout, false);
                        TextView textView = newChat.findViewById(R.id.message);
                        textView.setText(response.body().getValue());
                        chatLayout.addView(newChat, chatLayout.getChildCount());

                        // After adding the new chat, scroll to the bottom
                        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                    }

                    @Override
                    public void onFailure(Call<Tip> call, Throwable t) {
                        Log.d("Error123", t.getMessage());
                    }
                });
            }
        });
    }


}