package com.tdr.firstlook.partsneeded;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
                startActivity(smsIntent);
            }
        });
    }
}
