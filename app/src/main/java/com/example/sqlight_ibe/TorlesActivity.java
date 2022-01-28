package com.example.sqlight_ibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TorlesActivity extends AppCompatActivity {
    private Button buttonAdatTorles, buttonAdatTorlesVissza;
    private TextView edittextIdTorles;
    private DBHelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torles);
        init();
        //buttonAdatTorlesVissza egy intent lesz
        buttonAdatTorlesVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent visszaFoMenube = new Intent (TorlesActivity.this, MainActivity.class);
                startActivity(visszaFoMenube);
                finish();
            }
        });
        //buttonra onClick eseményt valósítunk meg:
        buttonAdatTorles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt kitesszük Stringbe az Id-t az edittextIDTorles-ből get metódussal toString-é alakítva és trimmeljük, majd if-el vizsgáljuk
                // és beállítunk egy setError-t:
                //if után mindíg tegyünk return-t
                String id = edittextIdTorles.getText().toString().trim();
                if(id.isEmpty()){
                    Toast.makeText(TorlesActivity.this, "Nem lehet üres mező", Toast.LENGTH_SHORT).show();
                    edittextIdTorles.setError("Nem lehet üres mező");
                    return;
                }
                if(!adatbazis.idLetezike(id)){
                    Toast.makeText(TorlesActivity.this, "Nincs ilyen rekord", Toast.LENGTH_SHORT).show();
                    edittextIdTorles.setError("Nincs ilyen rekord");
                    return;
                }
                if(adatbazis.adatTorles(id)){
                    Toast.makeText(TorlesActivity.this, "Sikeres törlés", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(TorlesActivity.this, "Sikertelen törlés", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void init(){
        buttonAdatTorles = findViewById(R.id.buttonAdatTorles);
        buttonAdatTorlesVissza = findViewById(R.id.buttonAdatTorlesVissza);
        edittextIdTorles = findViewById(R.id.editTextIdTorles);
        adatbazis = new DBHelper(TorlesActivity.this);
    }
}