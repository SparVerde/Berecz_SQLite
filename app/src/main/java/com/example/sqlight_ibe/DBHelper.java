package com.example.sqlight_ibe;

//beimportálunk extends-en keresztül egy SQLiteOpenHelper osztályt

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//kell konstruktor alt+enter implement metods
//itt írjuk meg és a main activity-ben majd csak meghívjuk és példányosítjuk az osztályt, majd meghívjuk a metódusait is
public class DBHelper extends SQLiteOpenHelper {

//1. változók létrehozása: ez lesz az adatbázisunk: a tanulo.db a prefernces-ben lesz megtalálható, device file explorer
    public static final String DB_NAME ="tanulo.db";
    //egy protokol az int-el amit mindíg végig kell vinnünk
    public static final int DB_VERSION =1;

    //tábla létrehozás és benne oszlopok (COL)
    public static final String TANULO_TABLA = "tanulo";

    public static final String COL_ID = "id";
    public static final String COL_NEV = "nev";
    public static final String COL_EMAIL = "email";
    public static final String COL_JEGY = "jegy";

    //2. metodusok letrehozasa:
    //onCreate leszármazott metodusa, sima sql metodussal
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//2.1. létrehozzuk a táblát, típusokat adunk neki adatbázis szinten egyszerő sql paramccsal !!szóközökre figyelni az sql parancsban!!
        String sql = "CREATE TABLE IF NOT EXISTS "+ TANULO_TABLA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NEV + " VARCHAR(255) NOT NULL," +
                COL_EMAIL + " VARCHAR(255) NOT NULL," +
                COL_JEGY + " INTEGER NOT NULL" +
                ")";
        //Ezt már csak futtani kell execute:
        sqLiteDatabase.execSQL(sql);
    }

//2.2. onUpdate leszármazott metodusa
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //3. itt is írunk egy scriptet: EXISTS után szóköz!!!
        String sql = "DROP TABLE IF EXISTS " + TANULO_TABLA;
                //ha változás történik a az adatbázisben újra meg kell hívni.
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    //4. konstruktorokat hozzuk létre: bemeneti értéke egy context lesz ami egy a main activity lesz vagy más activity az activity ahol használni fogjuk
    //DB_VERSION az upgrade
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //5. adatrögzítés: boolean-el könnyen tudjuk csekkolni, hogy sikerült-e vagy sem, bemeneti értékei, név, email, jegy, a jegy string marad
    public boolean adatRogzites(String nev, String email, String jegy){
//SQliteDatabase változó kell amibe írunk
        SQLiteDatabase db = this.getWritableDatabase();
//Content values is olyan mint  stringBuilder csak kulcs-érték párokat adunk meg pl. COL_NEV - nev
        ContentValues values = new ContentValues();
        values.put(COL_NEV, nev);
        values.put(COL_EMAIL, email);
        values.put(COL_JEGY, jegy);
        return db.insert(TANULO_TABLA, null, values) !=-1;
                //ha nem -1 akkor true
    }
    //6. adatlekérdezés:
    //Cursor adattípussal ami valóban olyan mint egy cursor sorokba teszi az értékeket
    public Cursor adatlekerdezes(){
        //itt is létre kell hozni az SQLiteDatabase-t
        SQLiteDatabase db = this.getReadableDatabase();
        //5 db nullát írunk
        return db.query(TANULO_TABLA, new String[]{COL_ID, COL_NEV, COL_EMAIL, COL_JEGY},
                null, null, null, null, null);
    }
    //7. adatorles ami egy boolen és egy adat id-t kér
    public boolean adatTorles(String id){
        //van az sqLiteDatabase változónk, ami egyenlő this.getWritable, mert bele akarunk írni
        SQLiteDatabase db = this.getWritableDatabase();
        //return a db delete fgv, aminek bemeneti értékei a tábla, where clause és egy string tömb amibe a törölt id-t betesszük,
        // amit egyenlővé teszünk egyel == ha sikeres a törlés akkor akkor 1
        return db.delete(TANULO_TABLA,"id = ?",new String[]{id}) ==1;
    }

    // egy biztonsági metódus: boolean, hogy id létezik-e, rögzítés actvity-ben kérjük majd le
    public boolean idLetezike (String id){
        SQLiteDatabase db = this.getReadableDatabase();
        //először egy cursor result kell egy sql lekérdezéssel
        Cursor result = db.rawQuery("SELECT * FROM TANULO WHERE id = ?", new String[]{id});
        return result.getCount()==1;
        //ha false getCount =0, azaz nem létezik ilyen id-jú, ha true akkor van ilyen id getCount == 1
    }


    //8. adatmódosítás, ez is boolean
    public boolean adatModositas (String id, String nev, String email, String jegy){
        //írunk, modosítunk az adatbázisba, itt is meghívjuk az SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        //szükséges egy Content values, ebbe beletesszük COL_NAME
        ContentValues values = new ContentValues();
        values.put(COL_NEV, nev);
        values.put(COL_EMAIL, email);
        values.put(COL_JEGY, jegy);
        //de szükség van az id-ra where feltételben
        int erintettSorok = db.update(TANULO_TABLA, values, "id =?", new String[]{id});
        //ha van ilyen sor akkor true-t fog visszaadni
        return erintettSorok ==1;

    }

}
//9. létre hozzuk az adatmezőket a RogzitesActivityben és a főmenüben kiolvassuk
