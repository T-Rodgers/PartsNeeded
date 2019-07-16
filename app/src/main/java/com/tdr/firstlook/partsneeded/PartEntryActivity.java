package com.tdr.firstlook.partsneeded;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class PartEntryActivity extends AppCompatActivity {

    private TextInputEditText partEntry;
    private TextInputEditText quantityEntry;
    private Button sendButton;
    private TextView partsList;
    private MaterialCardView listCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_entry);

        listCard = findViewById(R.id.part_list_card);
        listCard.setVisibility(View.GONE);

        partEntry = findViewById(R.id.part_edit);
        quantityEntry = findViewById(R.id.quantity_edit);

        sendButton = findViewById(R.id.sendButton);
        Button addButton = findViewById(R.id.addButton);
        partsList = findViewById(R.id.parts_list_text);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String sendType = extras.getString("button_text");

            setSendType(sendType);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPart();
            }
        });

    }

    public void setSendType(String sendType) {

        if (sendType.equals("Send Text")) {
            sendButton.setText(sendType);
        } else if (sendType.equals("Email")) {
            sendButton.setText(sendType);
        }
    }

    public void addPart() {
        String part = partEntry.getText().toString();
        String quantity = quantityEntry.getText().toString();


        if (!TextUtils.isEmpty(part) || !TextUtils.isEmpty(quantity)) {
            listCard.setVisibility(View.VISIBLE);
            partsList.append(part + " - " + quantity + "\n");
            partEntry.setText("");
            quantityEntry.setText("");
        } else {
            Toast.makeText(this, "Please enter part name and quantity",
                    Toast.LENGTH_LONG).show();
        }
    }
}
