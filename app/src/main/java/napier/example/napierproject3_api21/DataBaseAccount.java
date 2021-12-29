package napier.example.napierproject3_api21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAccount {
    private static final String DATABASE_NAME = "myDatabaseAccountTest.db";
    private static int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "newtable";
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static final String INSERT = "insert into " + TABLE_NAME
            + " (id,name,id_client) values (?,?,?)";

    public DataBaseAccount(Context context) {
        DataBaseAccount.context = context;
        DataBaseAccount.OpenHelper openHelper = new DataBaseAccount.OpenHelper(this.context);
        DataBaseAccount.db = openHelper.getWritableDatabase();
        this.insertStmt = DataBaseAccount.db.compileStatement(INSERT);
    }

    public long insert(String id, String name, String id_client) {
        this.insertStmt.bindString(1,id);
        this.insertStmt.bindString(2, name);
        this.insertStmt.bindString(3, id_client);
        return this.insertStmt.executeInsert();
    }

    public void updateData(String id, String name,String id_client){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("id_client",id_client);
        db.update(TABLE_NAME,contentValues, "id = ?",new String[]{id});
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public void deleteData(String id){
        int i = Integer.parseInt(id);
        db.delete(TABLE_NAME,"id" + " = " + i, null);
        db.close();
    }

    public List<String[]> selectAll() {
        List<String[]> list = new ArrayList<String[]>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "name", "id_client"}, null, null, null, null, "id asc");
        int x = 0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1 = new String[]{cursor.getString(0),
                        cursor.getString(1), cursor.getString(2)};
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
                    + " (id TEXT PRIMARY KEY, name TEXT, id_client TEXT)");

            ContentValues values = new ContentValues();
            values.put("id","0");
            values.put("name","Account 1");
            values.put("id_client","clem");
            db.insert(TABLE_NAME,null,values);

            values = new ContentValues();
            values.put("id","1");
            values.put("name","Account 2");
            values.put("id_client","clem");
            db.insert(TABLE_NAME,null,values);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
