package com.tdr.firstlook.partsneeded;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String EMAIL_TITLE = "email";
    private String SMS_TITLE = "sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button smsButton = findViewById(R.id.smsButtonId);
        Button emailButton = findViewById(R.id.emailButtonId);

        smsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(MainActivity.this, PartEntryActivity.class);

                // Will change button in PartEntryActivity to Send Text
                smsIntent.putExtra(SMS_TITLE, "Send Text");
                startActivity(smsIntent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(MainActivity.this, PartEntryActivity.class);

                emailIntent.putExtra(EMAIL_TITLE, "Email");
                startActivity(emailIntent);
            }
        });
    }
}
