package napier.example.napierproject3_api21;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DataBaseManipulator {
    private static final String DATABASE_NAME = "myDatabaseProjectLogin.db";
    private static int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "newtable";
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static final String INSERT = "insert into " + TABLE_NAME
            + " (username,surname,name,password) values (?,?,?,?)";

    public DataBaseManipulator(Context context) {
        DataBaseManipulator.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        DataBaseManipulator.db = openHelper.getWritableDatabase();
        this.insertStmt = DataBaseManipulator.db.compileStatement(INSERT);
    }

    public long insert(String username, String surname, String name, String password) {
        this.insertStmt.bindString(1, username);
        this.insertStmt.bindString(2, surname);
        this.insertStmt.bindString(3, name);
        this.insertStmt.bindString(4, password);
        return this.insertStmt.executeInsert();
    }

    public void updateData(String username,String surname, String name, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("surname",surname);
        contentValues.put("name",name);
        contentValues.put("password",password);
        db.update(TABLE_NAME,contentValues, "username = ?",new String[]{username});
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public void deleteData(String id){
        int i = Integer.parseInt(id);
        db.delete(TABLE_NAME,"username" + " = " + i, null);
        db.close();
    }

    public List<String[]> selectAll() {
        List<String[]> list = new ArrayList<String[]>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"username", "surname", "name", "password"}, null, null, null, null, "surname asc");
        int x = 0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1 = new String[]{cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3)};
                list.add(b1);
                x++;
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();
        return list;
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "
                    + TABLE_NAME
                    + " (username TEXT PRIMARY KEY, surname TEXT, name TEXT, password TEXT)");

            ContentValues values = new ContentValues();
            values.put("username","clem");
            values.put("surname","clement");
            values.put("name","fages");
            values.put("password","1234");
            db.insert(TABLE_NAME,null,values);

            values = new ContentValues();
            values.put("username","rom");
            values.put("surname","romain");
            values.put("name","fages");
            values.put("password","4321");
            db.insert(TABLE_NAME,null,values);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}

