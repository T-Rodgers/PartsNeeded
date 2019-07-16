package com.tdr.firstlook.partsneeded;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PartEntryActivity extends AppCompatActivity {

    private Button sendButton;
    private Button addBUtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_entry);


        sendButton = findViewById(R.id.sendButton);
        addBUtton = findViewById(R.id.addButton);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String sendType = extras.getString("button_text");

            setSendType(sendType);
        }

    }

    public void setSendType(String sendType) {

        if (sendType.equals("Send Text")) {
            sendButton.setText(sendType);
        } else if (sendType.equals("Email")) {
            sendButton.setText(sendType);
        }

    }
}
