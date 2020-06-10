package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;
import java.util.Random;

public class DriverChatActivity extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private ImageButton imageButton;
    private EditText inputChat;
    private ListView listOfMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_chat);

        imageButton = findViewById(R.id.fab);
        inputChat = findViewById(R.id.inputChat);
        listOfMessages = findViewById(R.id.list_of_messages);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference();

                Random r = new Random();

                myRef.push().setValue(new ChatMessage(inputChat.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                // Clear the input
                inputChat.setText("");
            }
        });

        displayChatMessages();
    }

    private void displayChatMessages(){
        Query query = FirebaseDatabase.getInstance().getReference().child("/");
        //The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.message)
                .build();
        //Finally you pass them to the constructor here:
        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = findViewById(R.id.message_text);
                TextView messageUser = findViewById(R.id.message_user);
                TextView messageTime = findViewById(R.id.message_time);

                Toast.makeText(DriverChatActivity.this, "Prueba: " + model.getMessageText(), Toast.LENGTH_SHORT).show();

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}
