package com.stephanusdhimas.UAS_MbuhKAI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextView nama, email, password;
    Button daftar, ke_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.reg_nama);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);

        daftar = findViewById(R.id.daftar);
        ke_login = findViewById(R.id.ke_login);

//        Ke Login
        ke_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

//                Get All values
                String getNama = nama.getText().toString();
                String getEmail = email.getText().toString();
                String getPass = password.getText().toString();
                try {
                    if (getEmail.trim().length() > 0 && getPass.trim().length() > 0 && getNama.trim().length() > 0) {
                        UserHelperClass helperClass = new UserHelperClass(getNama, getEmail, getPass);

                        reference.child(getNama).setValue(helperClass);
                        Toast.makeText(Register.this, "Daftar berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Daftar gagal, lengkapi form!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}