package com.example.sqlight_ibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModositActivity extends AppCompatActivity {
    private EditText editTextIdModositas, editTextNevModositas, editTextEmailModositas, editTextJegyModositas;
    private Button buttonModositas, buttonModositasVissza;
    private DBHelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modosit);
        init();
        buttonModositas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adatmodositas();
            }
        });
        //adatModositasVissza onClickkel visszamegyünk a főmenübe (MainActivity.class-ba) Intent-el
        buttonModositasVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVisszaFomenube = new Intent(ModositActivity.this, MainActivity.class);
                //Start és finish
                startActivity(intentVisszaFomenube);
                finish();
            }
        });
    }
    public void  init(){
        editTextIdModositas = findViewById(R.id.editTextIdModositas);
        editTextNevModositas = findViewById(R.id.editTextNevModositas);
        editTextEmailModositas = findViewById(R.id.editTextEmailModositas);
        editTextJegyModositas = findViewById(R.id.editTextJegyModositas);
        buttonModositas = findViewById(R.id.buttonModositas);
        buttonModositasVissza = findViewById(R.id.buttonModositasVissza);
        adatbazis = new DBHelper(ModositActivity.this);
    }
    //készítünk egy adatmodositas metodust és majd ezt hívjuk meg az adatmodositas.setOnClik-ben
    //adatmodositas() soran mindegyiket kitesszuk kulon string-be
    public void adatmodositas(){
        String id = editTextIdModositas.getText().toString().trim();
        String nev = editTextNevModositas.getText().toString().trim();
        String email = editTextEmailModositas.getText().toString().trim();
        String jegy = editTextJegyModositas.getText().toString().trim();
        if(id.isEmpty()){
            Toast.makeText(ModositActivity.this, "Id kitöltése kötelező", Toast.LENGTH_SHORT).show();
            editTextIdModositas.setError("Id kitöltése kötelező");
            return;}
        if(nev.isEmpty()){
            Toast.makeText(ModositActivity.this, "Név kitöltése kötelező", Toast.LENGTH_SHORT).show();
            editTextNevModositas.setError("Név kitöltése kötelező");
            return;}
        if(email.isEmpty()){
            Toast.makeText(ModositActivity.this, "Email kitöltése kötelező", Toast.LENGTH_SHORT).show();
            editTextEmailModositas.setError("IEmail kitöltése kötelező");
            return;}
        if(jegy.isEmpty()){
            Toast.makeText(ModositActivity.this, "Jegy kitöltése kötelező", Toast.LENGTH_SHORT).show();
            editTextJegyModositas.setError("Jegy kitöltése kötelező");
            return;}
        int jegyszam = Integer.parseInt(jegy);
        if (jegyszam <1 || jegyszam >5){
            Toast.makeText(this, "A jegy 1 és 5 közötti szám lehet", Toast.LENGTH_SHORT).show();
            editTextJegyModositas.setError("A jegy 1 és 5 közötti szám lehet");
            return;
        }
        if(adatbazis.adatModositas(id, nev, email, jegy)){
            Toast.makeText(this, "Sikeres adatmódosítás!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Sikertelen adatmódosítás!", Toast.LENGTH_SHORT).show();
        }
    }
}