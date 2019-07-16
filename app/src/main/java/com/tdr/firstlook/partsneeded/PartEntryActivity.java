package com.tdr.firstlook.partsneeded;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
    private TextView partsList;
    private MaterialCardView listCard;
    private AlertDialog dialog;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_entry);

        listCard = findViewById(R.id.part_list_card);
        listCard.setVisibility(View.GONE);

        partEntry = findViewById(R.id.part_edit);
        quantityEntry = findViewById(R.id.quantity_edit);

        Button nextButton = findViewById(R.id.next_button);
        Button addButton = findViewById(R.id.addButton);
        partsList = findViewById(R.id.parts_list_text);

        extras = getIntent().getExtras();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPart();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }

    public void addPart() {
        String part = partEntry.getText().toString();
        String quantity = quantityEntry.getText().toString();


        if (TextUtils.isEmpty(part) || TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Please enter part name and quantity",
                    Toast.LENGTH_LONG).show();
        } else {
            listCard.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(partsList.getText().toString())) {
                partsList.append("\n" + part + " - " + quantity);
                partEntry.setText("");
                quantityEntry.setText("");
            } else {
                partsList.setText(part + " - " + quantity);
                partEntry.setText("");
                quantityEntry.setText("");
            }

        }
    }

    private void createPopupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.custom_email_dialog, null);

        final String list = partsList.getText().toString();

        final Button sendButton = view.findViewById(R.id.send_Button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        String sendType = extras.getString("button_text");

        if (sendType != null && sendType.equals("Send Text")) {
            sendButton.setText(sendType);
        } else if (sendType != null && sendType.equals("Email")) {
            sendButton.setText(sendType);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (sendButton.getText().toString()) {
                    case "Email":
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));

                        emailIntent.putExtra(Intent.EXTRA_TEXT, list);

                        if (emailIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(emailIntent);
                        }

                        break;

                    case "Send Text":

                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                        smsIntent.setData(Uri.parse("smsto:"));
                        smsIntent.putExtra("sms_body", list);
                        if (smsIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(smsIntent);
                        }

                        break;
                }
            }
        });

    }

}
