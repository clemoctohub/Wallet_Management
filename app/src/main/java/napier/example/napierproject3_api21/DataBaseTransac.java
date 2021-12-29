package napier.example.napierproject3_api21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTransac {
    private static final String DATABASE_NAME = "myDatabaseProjectTransaction.db";
    private static int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "newtable";
    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static final String INSERT = "insert into " + TABLE_NAME
            + " (amount,type,date,idt,id_account,commentary) values (?,?,?,?,?,?)";

    public DataBaseTransac(Context context) {
        DataBaseTransac.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        DataBaseTransac.db = openHelper.getWritableDatabase();
        this.insertStmt = DataBaseTransac.db.compileStatement(INSERT);
    }

    public long insert(String amount, String type, String date, String idt ,String id_account, String commentary) {
        this.insertStmt.bindString(1, amount);
        this.insertStmt.bindString(2, type);
        this.insertStmt.bindString(3, date);
        this.insertStmt.bindString(4, idt);
        this.insertStmt.bindString(5, id_account);
        this.insertStmt.bindString(6, commentary);
        return this.insertStmt.executeInsert();
    }

    public void updateData(String id,String amount, String type, String date, String idt, String id_account, String commentary){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount",amount);
        contentValues.put("type",type);
        contentValues.put("date",date);
        contentValues.put("idt",idt);
        contentValues.put("id_account",id_account);
        contentValues.put("commentary",commentary);
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
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "amount", "type", "date", "idt", "id_account", "commentary"}, null, null, null, null, "date asc");
        int x = 0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1 = new String[]{cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6)};
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
                    + " (id INTEGER PRIMARY KEY, amount TEXT, type TEXT, date TEXT, idt TEXT, id_account TEXT, commentary TEXT)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
