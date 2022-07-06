package com.stephanusdhimas.UAS_MbuhKAI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    Button btnLogout;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MainActivity.this, Login.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tentang:
                AlertAbout();
                return true;
            case R.id.help:
                AlertHelp();
                return true;
            default:
                return false;
        }
    }

    public void profileMenu(View v) {
        Intent i = new Intent(this, ProfilActivity.class);
        i.putExtra("name", name);
        i.putExtra("email", email);
        startActivity(i);
    }
//
    public void historyMenu(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }
//
    public void bookKereta(View v) {
        Intent i = new Intent(this, BookingActivity.class);
        i.putExtra("name", name);
        startActivity(i);
    }

    public void bookAbout(View v) {
        Intent i = new Intent(this, MapActivity.class);
        startActivity(i);
    }

    private void AlertAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.alert_about);
        builder.create();
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void AlertHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.alert_help);
        builder.create();
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}