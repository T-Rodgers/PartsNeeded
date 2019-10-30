package com.tdr.firstlook.partsneeded;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PartEntryActivity extends AppCompatActivity {


    private AutoCompleteTextView partEntry;
    private EditText quantityEntry;
    private EditText partsList;
    private MaterialCardView listCard;
    private AlertDialog dialog;
    private Bundle extras;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            adapterView.getItemAtPosition(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_entry);

        CoordinatorLayout partEntryLayout = findViewById(R.id.part_activity_layout);

        AnimationDrawable animation = (AnimationDrawable) partEntryLayout.getBackground();
        animation.setEnterFadeDuration(10);
        animation.setExitFadeDuration(2500);
        animation.start();

        PartsAdapter adapter = new PartsAdapter(this, R.layout.custom_adapter_item
                , getData());
        partEntry = findViewById(R.id.part_edit);

        partEntry.setAdapter(adapter);
        partEntry.setOnItemClickListener(onItemClickListener);

        listCard = findViewById(R.id.part_list_card);
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
                if (TextUtils.isEmpty(partsList.getText().toString())) {
                    Toast.makeText(PartEntryActivity.this, "Please add a part to the list",
                            Toast.LENGTH_SHORT).show();
                } else {
                    createPopupDialog();
                }
            }
        });

    }

    public void addPart() {
        String part = partEntry.getText().toString().trim();
        String quantity = quantityEntry.getText().toString().trim();


        if (TextUtils.isEmpty(part) || TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Please enter part name and quantity",
                    Toast.LENGTH_LONG).show();
        } else {

            if (!TextUtils.isEmpty(partsList.getText().toString())) {
                partsList.append("\n\n" + part + " - " + quantity);
                partEntry.setText("");
                quantityEntry.setText("");
            } else {
                partsList.setText(part + " - " + quantity);
                partEntry.setText("");
                quantityEntry.setText("");
            }

        }

        partEntry.requestFocus();
    }

    private void createPopupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);


        final Button sendButton = view.findViewById(R.id.send_Button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        final TextInputEditText jobNameEntry = view.findViewById(R.id.job_name_edit);
        final RadioButton orderRadioButton = view.findViewById(R.id.order_rb);
        final RadioButton quoteRadioButton = view.findViewById(R.id.quote_rb);


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
                        String jobName = jobNameEntry.getText().toString();
                        String list = partsList.getText().toString();
                        String order = getOrderType(orderRadioButton);
                        String quote = getOrderType(quoteRadioButton);
                        String orderMessage = getResources().getString(R.string.orderMessage);
                        String quoteMessage = getResources().getString(R.string.quoteMessage);

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        if (orderRadioButton.isChecked()) {
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, order + jobName);
                            emailIntent.putExtra(Intent.EXTRA_TEXT, orderMessage + list);
                        } else if (quoteRadioButton.isChecked()) {
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, quote + jobName);
                            emailIntent.putExtra(Intent.EXTRA_TEXT, quoteMessage + list);
                        }
                        if (emailIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(emailIntent);
                        }

                        dialog.dismiss();

                        break;

                    case "Send Text":

                        list = partsList.getText().toString();
                        orderMessage = getResources().getString(R.string.orderMessage);
                        quoteMessage = getResources().getString(R.string.quoteMessage);

                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                        smsIntent.setData(Uri.parse("smsto:"));
                        if (orderRadioButton.isChecked()) {
                            smsIntent.putExtra("sms_body", orderMessage + list);
                        } else if (quoteRadioButton.isChecked()) {
                            smsIntent.putExtra("sms_body", quoteMessage + list);
                        }
                        if (smsIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(smsIntent);
                        }

                        dialog.dismiss();

                        break;
                }
            }
        });
    }

    public String getOrderType(View view) {

        String title = null;

        switch (view.getId()) {
            case R.id.quote_rb:

                title = "Quote: ";

                break;

            case R.id.order_rb:

                title = "Order: ";

                break;
        }
        return title;
    }

    private List<String> getData() {
        List<String> partsList = new ArrayList<>();
        partsList.add("15A Duplex Receptacle");
        partsList.add("20A Duplex Receptacle");
        partsList.add("Square D QO 1P 15A Breaker");
        partsList.add("Square D QO 1P 20A Breaker");
        partsList.add("Square D QO 2P 15A Breaker");
        partsList.add("1G Duplex Receptacle Plate");
        partsList.add("2G Duplex Receptacle Plate");
        partsList.add("CH 2P 40A Breaker");
        partsList.add("CH Panel Blank");
        partsList.add("Red Wirenuts");
        partsList.add("Yellow Wirenuts");
        partsList.add("Blue Wirenuts");
        partsList.add("3G Duplex Plate");
        return partsList;
    }

}
