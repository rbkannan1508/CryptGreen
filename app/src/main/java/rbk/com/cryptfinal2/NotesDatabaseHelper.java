package rbk.com.cryptfinal2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Notepad.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Notes";
    private static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + " (_id integer primary key autoincrement,name TEXT,encryptedtext TEXT,key TEXT)";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS " + TABLE_NAME;

    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Example", "Upgrading database, this will drop tables and recreate.");
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}
