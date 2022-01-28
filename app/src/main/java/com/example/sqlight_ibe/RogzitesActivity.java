package com.example.sqlight_ibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RogzitesActivity extends AppCompatActivity {
    //1. iniciálunk: lesz 3 EditText + 2 button + adatbázis segítségünket is
    private EditText editTextNev, editTextEmail, editTextJegy;
    private Button buttonRogz, buttonRogzVissza;
    private DBHelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rogzites);
        init();
        //buttonMentes es vissza is onclick esemény lesz
        buttonRogz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adatRogzites metodust hozun létre void tipusút
                adatRogzites();

            }
        });
        buttonRogzVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intenet esemény lesz a rögzítésből a main class-ba visszamegyünk
                Intent vissza_menube = new Intent(RogzitesActivity.this, MainActivity.class);
                //stratuljuk és finisheljük
                startActivity(vissza_menube);
                finish();
            }
        });
    }
    //2. értéket adunk
    public void init(){
        editTextNev = findViewById(R.id.editTextNev);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextJegy = findViewById(R.id.editTextJegy);
        buttonRogz = findViewById(R.id.buttonRogz);
        buttonRogzVissza = findViewById(R.id.buttonRogzVissza);

        adatbazis = new DBHelper(RogzitesActivity.this);
    }
//3. adatrögzítés:
    public void adatRogzites(){
        //az értékeket kitettük stringekbe
        //get az edit mezőből + trim-et is érdemes hozzátenni
        String nev = editTextNev.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String jegy = editTextJegy.getText().toString().trim();
        //if, ha üres toast üzenet , context:this; vagy .setError-ral is lehet
        //minden if végén kell return, hogy visszalépjen az elemzésekből
        if (nev.isEmpty()){
            Toast.makeText(this, "Név megadása kötelező", Toast.LENGTH_SHORT).show();
            editTextNev.setError("Név megadása kötelező");
            return;
        }
        if (email.isEmpty()){
            Toast.makeText(this, "Email megadása kötelező", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Email megadása kötelező");
            return;
        }
        if (jegy.isEmpty()){
            Toast.makeText(this, "Jegy megadása kötelező", Toast.LENGTH_SHORT).show();
            editTextJegy.setError("Jegy megadása kötelező!");
            return;
        }
        //a jegynél a számjegyek számát is le kell ellenőriznünk ehhez segédfüggvény kell inté alakításra
        int jegyszam = Integer.parseInt(jegy);
        if (jegyszam <1 || jegyszam >5){
            Toast.makeText(this, "A jegy 1 és 5 közötti szám lehet", Toast.LENGTH_SHORT).show();
            editTextJegy.setError("A jegy 1 és 5 közötti szám lehet");
            return;
        }
        // az adatbázist meghívjuk adatrogzites metodussal, ha adatrögzítés true, kiíratjuk hogy sikeres
        if (adatbazis.adatRogzites(nev, email, jegy)){
            Toast.makeText(this, "Sikeres adatrögzítés", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(this, "Sikertelen adatrögzítés", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}