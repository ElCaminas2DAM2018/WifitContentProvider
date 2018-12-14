package org.ieselcaminas.pmdm.wifitcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vmalonso on 15/01/17.
 */

public class WifiSqliteHelper extends SQLiteOpenHelper {

    String SQL_SENTENCE = "CREATE TABLE Wifi " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "BSSID TEXT, SSID TEXT, SECURITY TEXT, DESC TEXT)";

    public WifiSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_SENTENCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Wifi");
        sqLiteDatabase.execSQL(SQL_SENTENCE);
    }
}