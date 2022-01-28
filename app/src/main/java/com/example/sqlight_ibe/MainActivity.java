package com.example.sqlight_ibe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //1. iniciálunk
    private Button buttonOlvasas, buttonRogzit, buttonTorles, buttonModosit;
    private TextView TextviewAdatok;
    //DBHelpert is fell kell vennünk
    private DBHelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //3. Meghívás
        init();
        //4.1. Rögzítés elkészítése setOnClikListener függvénnyel (new View....) és egy másik activity-ben lesz, amit elkészítünk mint new empty activity
        buttonRogzit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //másikRozgitesActivity aktivity-t hívunk meg MainActivity.this név
                //An intent is to perform an action on the screen. It is mostly used to start activity
                Intent intentRogzit = new Intent(MainActivity.this, RogzitesActivity.class);
                //az Intent-et (intentRogzit9 le startoljuk és lefinisheljük
                startActivity(intentRogzit);
                finish();
            }
        });
        //4.2. Olvasásra onClick esemény
        buttonOlvasas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adatlekerdezes();
            }
        });
        //4.3. Törlésre onClick esemény Intent-el és majd activity-jét létre kell hozni: TorlesActivity
        buttonTorles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTorles = new Intent(MainActivity.this, TorlesActivity.class);
                //az Intent-et (intentRogzit) le startoljuk és lefinisheljük
                startActivity(intentTorles);
                finish();
            }
        });

        //4.4. Módosításra onClick esemény intent-el és majd activity-jét létre kell hozni: ModositActivity
        buttonModosit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModosit = new Intent(MainActivity.this, ModositActivity.class);
                //az Intent-et (intentRogzit) le startoljuk és lefinisheljük
                startActivity(intentModosit);
                finish();
            }
        });
        }
    //5. adatbázis elkészítése: new Class létrehozása DBHelper

    //2. inicializálunk: gomb = find by Id
    public void init(){
        buttonOlvasas = findViewById(R.id.buttonOlvasas);
        buttonRogzit = findViewById(R.id.buttonRogzites);
        buttonTorles = findViewById(R.id.buttonTorles);
        buttonModosit = findViewById(R.id.buttonModositas);
        TextviewAdatok = findViewById(R.id.TextviewAdatok);
        adatbazis = new DBHelper(MainActivity.this);

        //hogy TextView scrollozható legyen ehhez kell egy metódust meghívni
        TextviewAdatok.setMovementMethod(new ScrollingMovementMethod());
    }
    public void adatlekerdezes(){
        //az adatok amit visszakapunk egy cursor lesz amit az adatbazsi-bol fogunk visszakapni
        Cursor adatok = adatbazis.adatlekerdezes();
        //if végén return!!!
        if (adatok==null){
            Toast.makeText(this, "Hiba történt a lekérdezés során", Toast.LENGTH_SHORT).show();
            return;
        }
        if (adatok.getCount()==0){
            Toast.makeText(this, "Még nincs felvéve adat!", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            //a StringBuilder olyan mint egy string csak több adat fér el benne, a Cursoros értékeket fűzi össze, a textView-ban meghívható

            StringBuilder builder = new StringBuilder();
            while (adatok.moveToNext()){
                builder.append("ID:").append(adatok.getInt(0)).append("\n");
                builder.append("Név:").append(adatok.getString(1)).append("\n");
                builder.append("Email:").append(adatok.getString(2)).append("\n");
                builder.append("Jegy:").append(adatok.getInt(3)).append("\n");
            }
            //setText-el beletesszük a builder összefűzött adatokat majd toast-tal kiiratjuk
            TextviewAdatok.setText(builder);
            Toast.makeText(this, "Sikeres adatlekérdezés", Toast.LENGTH_SHORT).show();
        }
    }

}