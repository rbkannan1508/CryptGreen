package rbk.com.cryptfinal2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesDBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ENCRYPTEDTEXT = "encryptedtext";
    public static final String KEY_SECURITY = "key";
    private static final String TABLE_NAME = "Notes";
    private Context context;
    private SQLiteDatabase db;
    private NotesDatabaseHelper dbHelper;


    public NotesDBAdapter(Context context) {
        this.context = context;
    }

    public NotesDBAdapter open() throws SQLException {
        dbHelper = new NotesDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String name, String encryptedtext, String key) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_ENCRYPTEDTEXT, encryptedtext);
        values.put(KEY_SECURITY, key);
        return values;
    }

    public long insertNotes(String name, String encryptedtext, String key){
        ContentValues initialValues = createContentValues(name, encryptedtext, key);
        return db.insert(TABLE_NAME, null, initialValues);
    }

    public boolean updateNotes(long mrowId, String name, String encryptedtext, String key) {
        ContentValues updateValues = createContentValues(name, encryptedtext, key);
        return db.update(TABLE_NAME, updateValues, KEY_ROWID + "=" + mrowId, null) > 0;
    }

    public boolean deleteNotes(long mrowId) {
        return db.delete(TABLE_NAME, KEY_ROWID + "=" + mrowId, null) > 0;
    }

    public Cursor fetchAllNotes() {
        return db.query(TABLE_NAME, new String[] { KEY_ROWID,KEY_NAME,KEY_ENCRYPTEDTEXT,KEY_SECURITY }, null, null, null,
                null, KEY_NAME + " COLLATE NOCASE ASC");
    }

    public Cursor searchNotes(String nsearch) {
        return db.query(TABLE_NAME, new String[] { KEY_ROWID,KEY_NAME,KEY_ENCRYPTEDTEXT,KEY_SECURITY }, KEY_NAME + " LIKE '%" + nsearch + "%'", null, null,
                null, KEY_ROWID + " COLLATE NOCASE ASC");
    }

    public Cursor fetchNotes(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, TABLE_NAME, new String[] {
                        KEY_ROWID,KEY_NAME,KEY_ENCRYPTEDTEXT,KEY_SECURITY},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
