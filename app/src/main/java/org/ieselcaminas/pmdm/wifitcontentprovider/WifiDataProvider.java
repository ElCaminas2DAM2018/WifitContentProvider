package org.ieselcaminas.pmdm.wifitcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vmalonso on 15/01/17.
 */

public class WifiDataProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse(
            "content://org.ieselcaminas.victor.wifidataprovider/Wifi");

    public static final class Wifi implements BaseColumns {
        private Wifi() {    }

        public static final String COL_BSSID = "bssid";
        public static final String COL_SSID = "ssid";
        public static final String COL_SECURITY = "security";
        public static final String COL_DESC = "desc";
    }

    private WifiSqliteHelper wifiHelper;
    private static final String DB_WIFI = "DBWifi";
    private static final int DB_VERSION = 1;
    private static final String TABLE_WIFI = "Wifi";

    //UriMatcher
    private static final int WIFI = 1;
    private static final int WIFI_ID = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("org.ieselcaminas.victor.wifidataprovider","Wifi", WIFI);
        uriMatcher.addURI("org.ieselcaminas.victor.wifidataprovider","Wifi/#", WIFI_ID);
    }

    @Override
    public boolean onCreate() {
        wifiHelper = new WifiSqliteHelper(getContext(), DB_WIFI, null, DB_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String orderBy) {
        String where = selection;
        if (uriMatcher.match(uri) == WIFI_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = wifiHelper.getWritableDatabase();
        Cursor c = db.query(TABLE_WIFI, projection, where, selectionArgs, null, null, orderBy);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        String where = selection;
        int count;
        if (uriMatcher.match(uri) == WIFI_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = wifiHelper.getWritableDatabase();
        count = db.update(TABLE_WIFI, contentValues, where, selectionArgs);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where = selection;
        int count;
        if (uriMatcher.match(uri) == WIFI_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = wifiHelper.getWritableDatabase();
        count = db.delete(TABLE_WIFI, where, selectionArgs);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long regId = 1;
        SQLiteDatabase db = wifiHelper.getWritableDatabase();
        regId = db.insert(TABLE_WIFI, null, contentValues);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case WIFI:
                return "vnd.android.cursor.dir/vnd.victoralonso.wifi";
            case WIFI_ID:
                return "vnd.android.cursor.item/vnd.victoralonso.wifi";
        }
        return null;
    }

}