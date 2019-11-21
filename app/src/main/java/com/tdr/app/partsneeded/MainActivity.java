package com.tdr.app.partsneeded;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private String BUTTON_TEXT = "button_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout mainActivityLayout = findViewById(R.id.main_layout);

        AnimationDrawable animation = (AnimationDrawable) mainActivityLayout.getBackground();
        animation.setEnterFadeDuration(10);
        animation.setExitFadeDuration(2500);
        animation.start();

        Button smsButton = findViewById(R.id.smsButtonId);
        Button emailButton = findViewById(R.id.emailButtonId);

        smsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(MainActivity.this, PartEntryActivity.class);

                // Will change button in PartEntryActivity to Send Text
                smsIntent.putExtra(BUTTON_TEXT, "Send Text");
                startActivity(smsIntent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(MainActivity.this, PartEntryActivity.class);

                emailIntent.putExtra(BUTTON_TEXT, "Email");
                startActivity(emailIntent);
            }
        });
    }
}
